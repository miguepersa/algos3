import ve.usb.libGrafo.*
import kotlin.math.pow

fun verificarInputTamano(args: Array<String>): Boolean = args.size == 1

fun pruebaComponenteConexaCD(g: GrafoNoDirigido): Pair<Double, Int> {
    val tiempoInicial: Long = System.nanoTime()

    val gComponentes: ComponentesConexasCD = ComponentesConexasCD(g)

    val tiempoFinal: Long = System.nanoTime()

    return Pair(nanoSegundosASegundos((tiempoFinal - tiempoInicial).toDouble()), gComponentes.nCC())
}

fun nanoSegundosASegundos(tiempo: Double): Double = tiempo / (10.0).pow(9)

fun main(args: Array<String>) {
    try {
        println(".: Cliente de pruebas de deteccion de componentes conexas en grafos no dirigidos :.")

        if (!verificarInputTamano(args)) {
            throw RuntimeException("main: Cantidad de aprametros en el comando incorrecta. Se esperan 1 solo parametro, se recibieron ${args.size}")
        }

        // creamos el grafo
        val g: GrafoNoDirigido = GrafoNoDirigido(args[0])
        val resCD: Pair<Double, Int> = pruebaComponenteConexaCD(g)

        println("------------------------------------------------------------------")
        println(".: Resultados para las componentes conexas usando conjuntos disjuntos :.")
        println("Tiempo de ejecucion: ${resCD.first} segundos. Cantidad de componentes conexas: ${resCD.second}")

    } catch (e: RuntimeException) {
        println(e)
    } finally {
        println("Pruebas finalizadas\n")
    }
}
