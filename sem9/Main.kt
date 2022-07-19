import ve.usb.libGrafo.*
import kotlin.math.pow
import kotlin.math.sqrt

fun h(i: Int): Double {
    return 0.0
}

fun main(args: Array<String>) {
    val grafo = GrafoDirigidoCosto(args[0])
    val s: Int = 4

/*     var bfs = BFS(grafo, 1)
    for (i in 0 until grafo.nVertices) {
        if (grafo.arrVertices[i].d != Int.MAX_VALUE) {
            println(bfs.caminoHasta(i))
        }
    } */

    /* for (i in 0 until 5) {
        if (i != s) {
    
        }
        
    } */
    var prueba = AEstrella(grafo, 1, setOf(4), {0.0})
    println(prueba.objetivoAlcanzado())
    println(prueba.costo())
    println(prueba.obtenerCamino())
    println()
}
