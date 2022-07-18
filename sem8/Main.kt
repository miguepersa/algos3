import ve.usb.libGrafo.*
import kotlin.math.pow
import kotlin.math.sqrt

fun h(i: Int): Double {
    return sqrt(2.toDouble()) * i.toDouble()
}

fun main(args: Array<String>) {
    val grafo = GrafoDirigidoCosto(args[0])

    var a = AEstrella(grafo, 1, setOf<Int>(4), ::h)

    for (i in a.obtenerCamino()) {
        println(i)
    }
    
}
