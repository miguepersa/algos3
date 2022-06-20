import ve.usb.libGrafo.*
import kotlin.math.pow

fun verificarInputTamano(args: Array<String>): Boolean = args.size == 1

fun pruebaComponenteConexaCD(g: GrafoNoDirigido): Pair<Double, Int> {
    val tiempoInicial: Long = System.nanoTime()

    val gComponentes: ComponentesConexasCD = ComponentesConexasCD(g)

    val tiempoFinal: Long = System.nanoTime()

    return Pair((tiempoFinal - tiempoInicial) / 1000000.0, gComponentes.nCC())
}

fun pruebaCCDFS(archivo: String) {
    var grafo = GrafoNoDirigido(archivo)
    var tiempoInicial: Long = System.nanoTime()
    var ccDFS = ComponentesConexasDFS(grafo)
    var tiempo = (System.nanoTime() - tiempoInicial) / 1000000.0
    println("Tiempo de ejecucion: " + tiempo.toString() + "ms -- Componentes conexas: " + ccDFS.nCC().toString() + "\n\n")
}

fun main(args: Array<String>) {
    try {
        println(".: Cliente de pruebas de deteccion de componentes conexas en grafos no dirigidos :.")

        if (!verificarInputTamano(args)) {
            throw RuntimeException("main: Cantidad de parámetros en el comando incorrecta. Se esperan 1 solo parametro, se recibieron ${args.size}")
        }

        // creamos el grafo
        val g: GrafoNoDirigido = GrafoNoDirigido(args[0])
        val resCD: Pair<Double, Int> = pruebaComponenteConexaCD(g)

        println("------------------------------------------------------------------")
        println(".: Resultados para las componentes conexas usando conjuntos disjuntos :.")
        println("Tiempo de ejecucion: ${resCD.first}ms -- Componentes conexas: ${resCD.second}")
        println("------------------------------------------------------------------")
        println(".: Resultados para las componentes conexas usando DFS :.")
        pruebaCCDFS(args[0])

    } catch (e: RuntimeException) {
        println(e)
    } finally {
        println("Pruebas finalizadas\n")
    }
}
