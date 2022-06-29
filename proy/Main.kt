import ve.usb.libGrafo.*
import kotlin.math.pow

fun main(args: Array<String>) {
    val grafo: GrafoNoDirigidoCosto = GrafoNoDirigidoCosto(args[0])
    
    val arbolKruskal: KruskalAMC = KruskalAMC(grafo)
    val arbolPrim: PrimAMC = PrimAMC(grafo)

    println(arbolKruskal.obtenerCosto())
    println(arbolPrim.obtenerCosto()) 
    
}
