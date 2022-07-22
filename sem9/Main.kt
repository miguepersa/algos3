import ve.usb.libGrafo.*
import kotlin.math.pow
import kotlin.math.sqrt

fun main(args: Array<String>) {
    val grafo = GrafoDirigidoCosto(args[0])
    var j = Johnson(grafo)
    var d = j.obtenerMatrizDistancia()
    var p = j.obtenerMatrizPredecesores()

    println("\n\n Distancias:")
    for (i in d) {
        var linea = "[\t"
        for (j in i) {
            linea = linea + "\t" + j.toString()
        }
        linea = linea + "\t]"
        println(linea)
    }

    println("\n\n Predecesores:")
    for (i in p) {
        var linea = "[\t"
        for (j in i) {
            linea = linea + "\t" + j.toString()
        }
        linea = linea + "\t]"
        println(linea)
    }
}
