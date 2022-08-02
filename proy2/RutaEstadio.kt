import ve.usb.libGrafo.*
import java.io.File

fun main(args: Array<String>) {
    try {
        if (args.size != 1) {
            throw InvalidArgumentException("Cantidad incorrecta de argumentos")
        }

        var fileContent = File(args[0]).readLines()     // Contenido del archivo de entrada
        var n = fileContent[0].split(" ")[0].toInt()    // Numero de vertices del grafo
        var grafo: GrafoNoDirigidoCosto = GrafoNoDirigidoCosto(n + 1)   // Representación de las calles de CaracasSur
        var m = fileContent[0].split(" ")[1].toInt()    // Numero de lados del grafo
        var casa = fileContent[fileContent.size-1].split(" ")[0].toInt()    // Vertice donde se encuentra la casa
        var estadio = fileContent[fileContent.size-1].split(" ")[1].toInt() // Vertice donde se encuentra el estadio
        
        for (i in 1 until m+1) {
            var lado = fileContent[i].split(" ")
            grafo.agregarAristaCosto(AristaCosto(Vertice(lado[0].toInt()), Vertice(lado[1].toInt()), lado[2].toInt()))
        }

        for (i in m+2 until fileContent.size - 1) {
            var limpieza = fileContent[i].split(" ")
            grafo.listaLados[limpieza[0].toInt() - 1].agregarLimpieza(limpieza[1].toInt(), limpieza[2].toInt())
        }

        var dijkstra = CCM_Dijkstra(grafo, casa, estadio)    // Ejecutamos Dijkstra en el grafo de CaracasSur
        
        var camino: String = ""     // String donde se guardara el camino de costo minimo desde la casa hasta el estadio

        var v: VerticeDijstra? = dijkstra.listaVertices[estadio]    // Vertice donde se encuentra el estadio
        while (v != null) {
            camino = v.n.toString() + " " + camino  // Construccion del string del camino de costo minimo
            v = v.pred
        }

        // Salida del programa
        println(camino)
        println(dijkstra.costo(estadio))
        
    } catch (e: InvalidArgumentException) {
        println(e)
    } catch (e: RuntimeException) {
        println(e)
    } catch (e: java.io.FileNotFoundException) {
        println(e)
    }
}
