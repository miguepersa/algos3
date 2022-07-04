import ve.usb.libGrafo.*
import java.io.File
import java.util.LinkedList

/* 
    Cliente que implementa una solucion al problema de escalera de edicion

    La idea es representar el problema como un grafo y usar alguno de los 
    algoritmos implementados para solucionar el problema.

    El problema se encuentra en: https://icpc.global/worldfinals/problems

    Sobre la solucion al problema

    Tenemos un diccionario.
    Para representar el problema como un grafo debemos de crear conexiones entre
    cada par de palabras. Sean 2 palabras w1 y w2, se le a;aden al grafo el 
    lado (w1, w2) si y solo si hay un paso de edicion entre w1 y w2.

    Ahora que tenemos el grafo en donde cada lado (u, v) representa un paso
    de edicion entre u y v, tenemos que hallar el camino mas largo de 
    ediciones consecutivas.

    Para esto ejecutamos una version de DFS que nos permita obtener
    todos los caminos desde un vertice raiz

    Luego, entre todos esos caminos nos quedamos con el mas largo.

    Repitiendo este proceso con todos los vertices nos queda que guardamos
    el camino mas largo desde todos los vertices.

    Con eso, queda seleccionar el mas largo el cual es la solucion al problema
    
    Sobre el tiempo de ejecucion
        Cargar todos los lados al grafo toma O(|V^2|), pues recorremos cada par de palabras
        del diccionario

        Ejecutar BFS es O(|E| + |V|)
            Ejecutar BFS sobre cada vertice O(|V|(|V| + |E|))
            Determinar cual tiene la mayor distancia en cada ejecucin de BFS O(|V|)

        Comparar y determinar el vertice cuya distancia se la mayor O(|V|)
        Obtener el camino correspondiente O(|V|)

        En conclusion, el programa tiene un tiempo de ejecucion O(|V|^2 + |E||V|)
*/

fun main(args: Array<String>) {
    try {
        // Verificamos que los parametros con los que se ejecuto el programa
        // esten correctos
        if (!entradaCorrecta(args)) {
            throw RuntimeException("main: Los parametros con los cuales se ejecuto el programa son invalidos")
        }

        // Obtenemos una lista que contiene cada palabra del diccionario a usar
        val diccionario: List<String> = File(args[0]).readLines()
        
        // Creamos un grafo con tantos vertices como palabras tengamos
        val grafoDiccionario: GrafoDirigido = GrafoDirigido(diccionario.size)
        llenarGrafoDiccionario(grafoDiccionario, diccionario) // Agregamos los lados al grafo

        // Con el grafo lleno, procedemos a ejecutar BFS sobre cada vertice
        val arrBFS: Array<BFS> = Array<BFS>(grafoDiccionario.obtenerNumeroDeVertices(), {i -> BFS(grafoDiccionario, i)})
        val mayorVerticeRaiz: Int = obtenerMayorVerticeRaiz(arrBFS) // Obtenemos el indice del grafo que tenga la mayor distancia

        // Obtenemos el camino desde la raiz desde el vertice raiz hasta el vertice con mayor 
        // distancia del grafo en el cual esta distancia sea la mayor
        val caminoMayorDistancia: Iterable<Int> = arrBFS[mayorVerticeRaiz].caminoHasta(arrBFS[mayorVerticeRaiz].obtenerMayorVerticeDistancia())
        val cadenaDeEdicion: String = obtenerCadenaDeEdicion(caminoMayorDistancia, diccionario)

        println(cadenaDeEdicion)
        println(arrBFS[mayorVerticeRaiz].obtenerMayorDistancia() + 1)

    } catch (e: RuntimeException) {
        println(e)
    } catch (e: java.io.FileNotFoundException) {
        println("main: El archivo especificado no existe")
    }
}

/* 
    Determinar si los parametros con los cuales se ejecuto el programa son correctos

    Los parametros estan correctos si son de la forma

    <archivoEntrada>

    Esto es que solo se hayan pasado un solo parametro de entrada

    {P: args contiene los parametros con los cuales se ejecuto el programa}
    {Q: true si args.size == 0, false caso contrario}

    Input: args -> Array<String> cada args[i] contiene el parametro i con los cuales
                    se ejecuto el programa
    Output: true -> Si los parametros de entrada estan correctos
            false -> Caso contrario

    Tiempo de ejecucion O(1)
*/
fun entradaCorrecta(args: Array<String>): Boolean = args.size == 1

/* 
    Agregamos los lados correspondientes al grafo.

    La idea es iterar sobre cada par de palabras en dic y determinar si existe
    un paso de edicion entre las palabras. En caso de que haya un paso de
    edicion entre las palabras se a;ade al grafo el lado correspondiente

    {P: g es un grafo sin lados
        dic es una lista de palabras que corresponde a un diccionario}
    {Q: Cada lado (w1, w2) representa que hay un paso de edicion entre w1 y w2}

    Input: g -> GrafoNoDirigido que no contiene lados
            dic -> Lista de string que contiene el diccionario
    Output: ~~

    Tiempo de ejecucion O(|V|^2)
*/
fun llenarGrafoDiccionario(g: GrafoDirigido, dic: List<String>) {
    // Recorremos cada par de palabras en la lista
    for (i in 0 until dic.size) {
        for (j in i + 1 until dic.size) {
            if (hayUnPasoDeEdicion(dic[i], dic[j])) {
                g.agregarArco(Arco(Vertice(i), Vertice(j)))
            }
        }
    }
}

/* 
    Determinamos si hay un paso de edicion entre s1 y s2

    Los posibles pasos de edicion son
        cambiar un letra de s1 para transformarla en s2
        agregar un letra a s1 para transformarla en s2
        eliminar una letra de s1 para transformarla en s2

        Estos pasos tambien se verifican de de s2 a s1 

    {P: s1 y s2 son string}
    {Q: Se devuelve true si si se agrego o elimino una letra a s1 
        para transformarla a s2 o si se modifico s1 para obtener s2.
        de igual manera de s2 a s1}

    Input: s1 y s2 -> Strings
    Output: true ->

    Tiempo de ejeucion O(max(s1, s2))
*/
fun hayUnPasoDeEdicion(s1: String, s2: String): Boolean = cambio(s1, s2) || agregar(s1, s2) || eliminar(s1, s2)

/* 
    Determina si hubo un paso de edicion entre s1 y s2.
    El paso de edicion que se verifica es el de cambiar una letra
    de s1 para obtener s2 y viceversa.

    Para ver si se realizo el paso de edicion verificamos 
    que ambas palabras tengan el mismo tama;o y que 
    s1 tenga exactamente s1.length - 1 letras iguales que s2 y que esten 
    en la misma posicion

    {P: s1 y s2 son string}
    {Q: true si s1.length == s2.length y s1 y s2 comparten 3 letras en el mismo orden}

    Input: s1, s2 -> Strings
            true -> Si hay un paso de edicion entre s1 y s2 o viceversa

    Tiempo de ejecucion O(|s1.length|)
*/
fun cambio(s1: String, s2: String): Boolean {
    if (s1.length == s2.length) { // Verificamos que tengan el mismo tama;o
        // Contamos cuantas letras tienen iguales y en la misma posicion
        var contLetras: Int = 0

        for (i in 0 until s1.length) {
            if (s1[i] == s2[i]) {
                contLetras++
            }
        }

        return contLetras == s1.length - 1
    }

    return false
}

/* 
    Determina si se agrego una letra a s1 para obtener s2. 
    
    Para ver si se agrego una letra a s1 para obtener s2 verificamos que
    s1.length == s2.length - 1 y que s2 este contenida en s1.

    {P: s1 y s2 son string}
    {Q: true si s1.length == s2.length - 1 y que s2 este contenido en s1}

    Input: s1 y s2 -> String
    Output: true -> Si hay un paso de edicion de agregar entre s1 y s2
            false -> Caso contrario

    Tiempo de ejecucion O(s1.length)
*/
fun agregar(s1: String, s2: String): Boolean  = s1.length == s2.length - 1 && estaContenido(s1, s2)


/* 
    Determina si se elimino una letra a s1 para obtener s2.

    Para ver si se elimino una letra a s1 para obtener s2 verificamos que
    s1.length == s2.length + 1 y que s1 este contenida en s2

    {Q: s1 y s2 son strings}
    {Q: true - si s1.length == s2.length + 1 y que s1 este contenida en s2}

    Input: s1 y s2 -> String
    Output: true -> Si hay un paso de edicion de eliminar entre s1 y s2
            false -> Caso contrario

    Tiempo de ejecucion O(s2.length)
*/
fun eliminar(s1: String, s2: String): Boolean = s1.length == s2.length + 1 && estaContenido(s2, s1)

/* 
    Determina si un un string s1 esta contenido en un string s2.

    s1 esta contenido en s2 si eliminando una letra de s2 obtenemos s1

    Ademas se verifica si s1 esta contenido a derecha o a izq de s2

    {P: s1.length  < s2.length}
    {Q: s1 esta contenido a derecha o a izq de s2}

    Input: s1 y s2 -> Strings
    Output: true -> s1 esta contenido en s2
            false -> caso contrario
    
    Tiempo de ejecucion O(s2.length)
*/
fun estaContenido(s1: String, s2: String): Boolean {
    // Determinamos cual es la letra de s2 que no este en s1
    val letraS2: String = obtenerLetraExtra(s1, s2)
    val nuevoS2: String = s2.replaceFirst(letraS2, "")

    return s1 == nuevoS2 || estaContenidoIzq(s1, s2) || estaContenidoDerecha(s1, s2)
}

/* 
    Se determina si s1 esta contenido a izq en s2

    Por ejemplo: Sea s1 = [l1, l2, l3, l4], s2 debe ser 
    de la forma s2 = [l1, l2, l3, l4, l5]

    {P: s1.length < s2.length}
    {Q: Devuelve si s1[i] == s2[i] para todo i en s1.length}

    Input: s1 y s2 -> Strings
    Output: true -> s1 esta contenido a derecha de s2
            false -> caso contrario

    Tiempo de ejecucion O(s1.length)
*/
fun estaContenidoIzq(s1: String, s2: String): Boolean {
    for (i in 0 until s1.length) {
        if (s1[i] != s2[i]) return false
    }

    return true
}

/* 
    Se determina si s2 esta contenido a derecha en s2

    Por ejemplo: Sea s1 = [l1, l2, l3, l4], s2 debe ser
    de la forma s2 = [l0, l1, l2, l3, l4]

    {P: s1.length < s2.length}
    {Q: Devuelve si s1[i] == s2[i+1] para todo i en s1.length}

    Input: s1 y s2 -> Strings
    Output: true -> s1 esta contenido a derecha en s2
            false -> caso contrario

    Tiempo de ejecucion O(s1.length)
*/
fun estaContenidoDerecha(s1: String, s2: String): Boolean {
    for (i in 0 until s1.length) {
        if (s1[i] != s2[i + 1]) return false
    }
    return true
}
/* 
    Dado dos strings, s1 y s2, obtener la letra en s2 que no
    este en s1

    Para esto recorremos s1 y cada vez que una letra de s1 aparezca en
    s2, eliminamos esa letra de s2

    Asi hasta que hayamos recorrido s1 completamente

    {P: s1.length < s2.lenght}
    {Q: se devuelve la primera letra de s1 que no aparezca en s2 o que aparezca repetida}

    Input: s1 y s2 -> Strings
    Output: Char que es la letra en s2 que no esta en s1

    Tiempo de ejecucion O(s1*s2)
*/
fun obtenerLetraExtra(s1: String, s2: String): String {
    var s2Aux: String = s2 

    for (l in s1) {
        var i = 0
        while (i < s2Aux.length && l != s2Aux[i].toChar()) {
            i++
        }

        s2Aux = s2Aux.replaceFirst(l.toString(), "")
    }

    return s2Aux
}

/* 
    Dado un arreglo de grafos en donde se ejecuto BFS
    se desea obtener el indice del grafo que tenga la mayor
    distancia desde el vertice raiz desde el cual se ejecuto BFS

    {P: arrGrafo es un arreglo que conteien ejecuciones de BFS
        sobre el mismo grafo pero usando como raiz cada vertice}
    {Q: El indice del arreglo tal que 
        arrGrafo[i].obtenerMayorDistancia() <= arrGrafo[max].obtenerMayorDistancia()}

    Input: arrGrafo -> Arreglo de grafos sobre los cuales se ejecuto BFS
    Output: El indice del arreglo cuyo grafo tenga la mayor distancia

    Tiempo de ejecucion O(|V|^2)
*/
fun obtenerMayorVerticeRaiz(arrGrafo: Array<BFS>): Int {
    var maxIndice: Int = 0

    for (i in 1 until arrGrafo.size) {
        if (arrGrafo[maxIndice].obtenerMayorDistancia() < arrGrafo[i].obtenerMayorDistancia()) {
            maxIndice = i
        }
    }

    return maxIndice
}

/* 
    La idea es que dado un camion de un grafo, devoler el mismo camino
    pero con su representacion como string ordenado lexicograficamente

    {P: camino es un camino simple y dic es un diccionario que se subio 
        desde un archivo}
    {Q: String es la representacion del camino en string usando
        las palabras del diccionario}

    Input: camino -> iterable de entero que representa un camino en un grafo
            dic -> diccionario que contiene la palabra de cada entero
    Output: String que es la representacion del camino ordenado lexicografimante

    Tiempo de ejecucion O(camino.size * ln(camino.size))
*/
fun obtenerCadenaDeEdicion(camino: Iterable<Int>, dic: List<String>): String {
    var caminoDic: LinkedList<String> = LinkedList<String>()

    for (c in camino) {
        caminoDic.add(dic[c])
    }

    val arrCaminoDic = caminoDic.toArray()

    var caminoString: String = ""

    for (i in 0 until arrCaminoDic.size) {
        caminoString += "${arrCaminoDic[i]} "
    }

    return caminoString
}