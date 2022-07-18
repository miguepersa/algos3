import ve.usb.libGrafo.*
import kotlin.math.pow
import kotlin.math.sqrt

fun h(i: Int): Double {
    return 0.0
}

fun main(args: Array<String>) {
    val grafo = GrafoDirigidoCosto(args[0])

    var a = AEstrella(grafo, 0, setOf<Int>(3), ::h)

    println(a.objetivoAlcanzado())
    
}
