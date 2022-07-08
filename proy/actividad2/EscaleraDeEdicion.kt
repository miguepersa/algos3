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

    Utilizaremos un grafo dirigido para mantener el orden lexicografico
    del diccionario. El orden lexicografico del diccionario se mantiene porque
    en el lado (w1, w2), w1 precede a w2 en el diccionario.

    Luego, basta escoger la cadena de edicion mas larga. Para esto ejecutamos 
    una version de DFS que nos permita obtener todos los caminos desde un vertice raiz

    Luego, entre todos esos caminos nos quedamos con el mas largo.

    Repitiendo este proceso desde cada vertice del grafo, bastaria seleccionar
    el camino mas largo entre todos los caminos.

    Este camino, es la solucion del problema

    Sobre el tiempo de ejecucion
        Se crea un grafo de tantos vertices como palabras tenga el diccionario.
        Llenar este grafo toma O(|V|^2)

        Ahora, ejecutar DFSCaminos desde un vertice toma O(|E|). Ejecutarlo sobre
        todos los vertices del grafo toma O(|V||E|)

        Obtener la cadena de edicion mas larga toma O(|V|*cantidad de caminos obtenidos)
        
        Transformar esta cadena como string toma O(largo de la cadena)

        En conclusion el tiempo de ejecucion del programa es: O(|V|^2 + |V||E| + |V|*cantidad de caminos).
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

        // Con el grafo lleno, procedemos a ejecutar DFSCaminos usando como raiz
        // cada vertice del grafo
        val arrDFSCaminos: Array<DFSCaminos> = Array<DFSCaminos>(grafoDiccionario.obtenerNumeroDeVertices(), {i -> DFSCaminos(grafoDiccionario, i)})
        
        // Obtenemos la cadena de edicion mas larga
        // Esto es obtener el camino mas largo entre todos los caminos mas largos
        val cadenaDeEdicion: LinkedList<Int> = obtenerCadenaDeEdicion(arrDFSCaminos)

        // Mostramos la cadena de edicion mas larga y su tama;o
        println(cadenaDeEdicionDiccionario(cadenaDeEdicion, diccionario))
        println(cadenaDeEdicion.count())
        
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
    {Q: true si args.size == 1, false caso contrario}

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

    Tiempo de ejecucion O(s1.length*s2.length)
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
    Dado un arreglo de grafos a los cuales se le aplico DFSCaminos
    obtener el caminos mas largo entre todos los caminos.

    Para esto obtenemos el camino mas largo de cada grafo.

    Luego, seleccionamos el camino mas largo entre estos caminos.

    {P: arrGrafo.size > 0}
    {Q: Un camino c tal que c.count() >= arrGrafo[i].obtenerCaminoMasLargo.count()
        para i en [0, arrGrafo.size)}

    Input: Array<DFSCaminos> -> Array de elementos de DFSCaminos,
            arrGrafo[i] representa la ejecucion de DFSCaminos sobre 
            un grafo con raiz en el vertice i
    Output: El camino mas largo entre todos los caminos

    Sobre el tiempo de ejecucion
        Obtener el caminos mas largo O(cantidad de caminos obtenidos)
        Esto se repite con todos los grafos

        En conclusion, esta funciona toma O(arrGrafo.size * cantidad de caminos obtenidos en cada grafo)
*/
fun obtenerCadenaDeEdicion(arrGrafo: Array<DFSCaminos>): LinkedList<Int> {
    var cadenaDeEdicion: LinkedList<Int> = arrGrafo[0].obtenerCaminoMasLargo()

    for (g in arrGrafo) {
        if (cadenaDeEdicion.count() < g.obtenerCaminoMasLargo().count()) cadenaDeEdicion = g.obtenerCaminoMasLargo()
    }

    return cadenaDeEdicion
}

/* 
    Dado una cadena de edicion, se devuelve un string con la representacion
    de la cadena en donde cada posicion de la cadena. Esto es que 
    cadena = <v0, v1, v2, ... , vk> se devuelve 
    "dic[v0] dic[v1] dic[v2] ... dic[vk]"

    {P: cadena es un camino simple en un grafo y dic contiene la representacion
        como string de cada vertice del camino}
    {Q: Se devuelve un string con la palabra asociada a cada vertice del camino}

    Input: cadena -> LinkedList<Int> Representa un camino simple en un grafo
    Output: Representacion como string de la cadena

    Tiempo de ejecucion O(cadena.count())
*/
fun cadenaDeEdicionDiccionario(cadena: LinkedList<Int>, dic: List<String>): String {
    var cadenaString: String = ""

    for (i in cadena) {
        cadenaString += dic[i] + " "
    }

    return cadenaString
}