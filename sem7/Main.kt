import ve.usb.libGrafo.*
import kotlin.math.pow

fun main(args: Array<String>) {
    val grafo = GrafoDirigidoCosto(args[0])

    val dag = CCM_Dijkstra(grafo, 0)
    
    for (i in dag.obtenerCaminoDeCostoMinimo(4)) {
        print(i)
    }

    for (i in dag.arrVertices) {
        println("\n${i.n} ${i.key}")
    }    
    
}
