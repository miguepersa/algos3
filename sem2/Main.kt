import ve.usb.libGrafo.*
import java.io.File
import kotlin.random.Random

fun main(args: Array<String>) {
    var g = GrafoDirigido(args[1])
    /*
    var bfs = BFS(g, 4)
    for (i in 0 until 8) {
        println("$i : " + bfs.obtenerDistancia(i).toString())
    }*/
    println(g)
    var bfs = DFS(g)
    bfs.mostrarBosqueDFS()
}