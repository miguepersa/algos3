import ve.usb.libGrafo.*
import java.io.File
import kotlin.random.Random

fun main(args: Array<String>) {
    var g = GrafoNoDirigido(args[1])
    /*
    var bfs = BFS(g, 4)
    for (i in 0 until 8) {
        println("$i : " + bfs.obtenerDistancia(i).toString())
    }*/
    println(g)
    var dfs = DFS(g)
    println("Bosque: ${dfs.hayLadosDeBosque()}")
    var lados = dfs.ladosDeBosque()
    for (l in lados) {
        print(l.toString() + " ")
    }

    println("\n Ida: ${dfs.hayLadosDeIda()}")
    lados = dfs.ladosDeIda()
    for (l in lados) {
        print(l.toString() + " ")
    }
    println("\n Vuelta: ${dfs.hayLadosDeVuelta()}")
    lados = dfs.ladosDeVuelta()
    for (l in lados) {
        print(l.toString() + " ")
    }
    println("\n Cruzados: ${dfs.hayLadosCruzados()}")
}