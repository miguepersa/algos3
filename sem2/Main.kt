import ve.usb.libGrafo.*
import java.io.File
import kotlin.random.Random

fun main(args: Array<String>) {
    var g = GrafoDirigidoCosto(args[1])
    var g1 = GrafoNoDirigidoCosto(args[1])
    /*
    var bfs = BFS(g, 4)
    for (i in 0 until 8) {
        println("$i : " + bfs.obtenerDistancia(i).toString())
    }*/
     
    var bfs = BFS(g, 1)
    var bfs1 = BFS(g1, 1)
    println(bfs.mostrarArbolBFS())
    println(bfs1.mostrarArbolBFS())
}