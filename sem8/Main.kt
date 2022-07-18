import ve.usb.libGrafo.*
import kotlin.math.pow
import kotlin.math.sqrt

fun h(i: Int): Double {
    return 0.0
}

fun main(args: Array<String>) {
    val grafo = GrafoDirigidoCosto(args[0])
    val s: Int = 4

    for (i in 0 until 5) {
        if (i != s) {
            var prueba = AEstrella(grafo, s, setOf(i), {j -> 0.0})
            println(i)
            println(prueba.objetivoAlcanzado())
            println(prueba.costo())
            println(prueba.obtenerCamino())
            println()
        }
        
    }
}
