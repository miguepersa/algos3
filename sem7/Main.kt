import ve.usb.libGrafo.*
import kotlin.math.pow

fun main(args: Array<String>) {
    val grafo = GrafoDirigidoCosto(args[0])

    val dag = CaminoCriticoPERT(grafo, 1)
    
    for (i in dag.obtenerCaminoCritico()) {
        print(i)
    }
    println("\n"+ dag.costo().toString())
    
    
}
