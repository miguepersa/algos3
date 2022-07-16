import ve.usb.libGrafo.*
import kotlin.math.pow

fun main(args: Array<String>) {
    val grafo = GrafoDirigidoCosto(args[0])

    val bellman = CCM_BellmanFord(grafo, 6)

    println(bellman.tieneCicloNegativo())

    for (i in bellman.obtenerCicloNegativo()) {
        print(i)
    }
    println()
    
}
