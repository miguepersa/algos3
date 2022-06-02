import ve.usb.libGrafo.*
import java.io.File
import kotlin.random.Random

/* 
    Input: Arreglo de string que contiene los parametros con los cuales se ejecuto el programa
    Output: true -> Si el primer elemento esta en formato correcto.
            false -> Caso contrario

    El formato correcto es que sea de la gorma -g[d|n|c|p] 
*/
fun verificarTipoGrafoEntrada(args: Array<String>): Boolean {
    val par: String = args[0]

    if (par.length != 3) {
        return false
    }

    if (par[0] != '-' || par [1] != 'g') {
        return false
    }

    if (par[2] != 'd' && par[2] != 'n' && par[2] != 'c' && par[2] != 'p') {
        return false
    }

    return true
}


/* 
    Devuelve la opcion ingresada por el usuario

    {P: s es de la forma -g[d|n|c|p]}
    {Q: true}

    Input: s -> String que correponde al parametro de entrada en el comando con el cual se ejecuta el pograma
    Output: La opcion que especifca el parametro s
*/
fun obtenerOpcionTipoGrafo(s: String): Char = s[2]

/* 
    Las siguientes funciones realizaon operaciones sobre grafos y muestran el resulado
    de las operaciones.

    Todas tienen la misma entrada y es la direccions de un archivo que contiene un grafo.

    El grafo sobre el cual se ejecutaran las pruebas se carga desde el archivo
*/
fun operacionesGrafoDirigido(archivo: String) { // Operaciones sobre un grafo dirigido
    println(".: Pruebas sobre un grafo dirigido :.")
    println("El grafo se cargarada desde el archivo $archivo")

    var grafoPrueba: GrafoDirigido = GrafoDirigido(archivo)

    println("El grafo tiene ${grafoPrueba.nVertices} vertices y ${grafoPrueba.nLados} lados")
    println("El grafo cargado es: ")
    println(grafoPrueba)

    val n: Int = Random(System.nanoTime()).nextInt(0, grafoPrueba.nVertices)
    val ady: Iterable<Arco> = grafoPrueba.adyacentes(n)
    var arco: Arco = grafoPrueba.listaLados.getFirst()

    println("El grado del vertice $n es: ${grafoPrueba.grado(n)}")
    println("Los lados adyacentes al vertice $n son:")
    for (i in ady) {
        print(i.toString() + " ")

        if (Random(System.nanoTime()).nextInt(0, n + 1) % 2 == 0) {
            arco = i
        }
    }

    val adyl: Iterable<Arco> = grafoPrueba.ladosAdyacentes(arco)
    println("\nLos lados adyacentes al lado $arco son: ")
    for (i in adyl) {
        print(i.toString() + " ")
    }
}

fun operacionesGrafoNoDirigido(archivo: String) {  // Operaciones sobre un grafo no dirigido
    println(".: Pruebas sobre un grafo no dirigido :.")
    println("El grafo se cargarada desde el archivo $archivo")

    var grafoPrueba: GrafoNoDirigido = GrafoNoDirigido(archivo)

    println("El grafo tiene ${grafoPrueba.nVertices} vertices y ${grafoPrueba.nLados} lados")
    println("El grafo cargado es: $grafoPrueba")

    val n: Int = Random(System.nanoTime()).nextInt(0, grafoPrueba.nVertices)
    val ady: Iterable<Arista> = grafoPrueba.adyacentes(n)
    var arista: Arista = grafoPrueba.listaLados.getFirst()

    println("El grado del vertice $n es: ${grafoPrueba.grado(n)}")
    println("Los lados adyacentes al vertice $n son: ")
    for (i in ady) {
        print(i.toString() + " ")

        if (Random(System.nanoTime()).nextInt(0, n + 1) % 2 == 0) {
            arista = i
        }
    }

    val adyl: Iterable<Arista> = grafoPrueba.ladosAdyacentes(arista)
    println("\nLos lados adyacentes al lado $arista son: ")
    for (i in adyl) {
        print(i.toString() + " ")
    }
}

fun operacionesGrafoDirigidoCosto(archivo: String) { // Operaciones sobre un grafo dirigido costo
    println(".: Pruebas sobre un grafo dirigdo costo :.")
    println("El grafo se cargarada desde el archivo $archivo")

    var grafoPrueba: GrafoDirigidoCosto = GrafoDirigidoCosto(archivo)

    println("El grafo tiene ${grafoPrueba.nVertices} vertices y ${grafoPrueba.nLados} lados")
    println("El grafo cargado es: ")
    println(grafoPrueba)

    val n: Int = Random(System.nanoTime()).nextInt(0, grafoPrueba.nVertices)
    val ady: Iterable<ArcoCosto> = grafoPrueba.adyacentes(n)
    var arco: ArcoCosto = grafoPrueba.listaLados.getFirst()

    println("El grado del vertice $n es: ${grafoPrueba.grado(n)}")
    println("Los lados adyacentes al vertice $n son:")
    for (i in ady) {
        print(i.toString() + " ")

        if (Random(System.nanoTime()).nextInt(0, n) % 2 == 0) {
            arco = i
        }
    }

    val adyl: Iterable<ArcoCosto> = grafoPrueba.ladosAdyacentes(arco)
    println("\nLos lados adyacentes al lado $arco son: ")
    for (i in adyl) {
        print(i.toString() + " ")
    }
}

fun operacionesGrafoNoDirigidoCosto(archivo: String) { // Operaciones sobre un grafo no dirigido costo
    println(".: Pruebas sobre un grafo no dirigido costo :.")
    println("El grafo se cargarada desde el archivo $archivo")

    var grafoPrueba: GrafoNoDirigidoCosto = GrafoNoDirigidoCosto(archivo)

    println("El grafo tiene ${grafoPrueba.nVertices} vertices y ${grafoPrueba.nLados} lados")
    println("El grafo cargado es: ")
    println(grafoPrueba)

    val n: Int = Random(System.nanoTime()).nextInt(0, grafoPrueba.nVertices)
    val ady: Iterable<AristaCosto> = grafoPrueba.adyacentes(n)
    var arista: AristaCosto = grafoPrueba.listaLados.getFirst()

    println("El grado del vertice $n es: ${grafoPrueba.grado(n)}")
    println("Los lados adyacentes al vertice $n son:")
    for (i in ady) {
        print(i.toString() + " ")

        if (Random(System.nanoTime()).nextInt(0, n) % 2 == 0) {
            arista = i
        }
    }

    val adyl: Iterable<AristaCosto> = grafoPrueba.ladosAdyacentes(arista)
    println("\nLos lados adyacentes al lado $arista son: ")
    for (i in adyl) {
        print(i.toString() + " ")
    }
}

fun main(args: Array<String>) {
    try {

        if(args.size != 2) {
            throw InvalidArgumentException("Cantidad de parametros erronea.")
        }

        if (!verificarTipoGrafoEntrada(args)) {
            throw InvalidArgumentException("Error el primer argumento. Formato correcto -g[d|n|c|p]")
        }

        val opt: Char = obtenerOpcionTipoGrafo(args[0])

        println(".: Cliente de pruebas de grafos :.")
        println("Se realizaran pruebas sobre un tipo de grafo y se mostraran los resultados \n")
        
        when (opt) {
            'd' -> operacionesGrafoDirigido(args[1])
            'n' -> operacionesGrafoNoDirigido(args[1])
            'c' -> operacionesGrafoDirigidoCosto(args[1])
            'p' -> operacionesGrafoNoDirigidoCosto(args[1])
        }


    } catch (e: InvalidArgumentException) {
        println(e)

    } catch (e: java.io.FileNotFoundException) {
        println(e)

    } catch (e: RuntimeException) {
        println(e)

    } finally {
        println("\n\nPruebas terminadas")
    }
}