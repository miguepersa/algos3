import ve.usb.libGrafo.*
import java.util.LinkedList
import kotlin.math.ceil

fun main(args: Array<String>) {
    try {
        println(".: Cliente de optimizacion de ruta de USBTours :.")

        if (args.size != 4) {
            throw RuntimeException("main: Cantidad de parámetros en el comando incorrecta. Se esperan 4 parametros, se recibieron ${args.size}")
        }

        // creamos el grafo
        val fileContent = File(args[3]).readLines()
		val nVertices = fileContent[0].toInt()
        var g: GrafoNoDirigidoCosto = GrafoNoDirigidoCosto(nVertices)
        var map = HashMap<String, Int>()
        var n: Int = 0
        for (i in 2 until fileContent.size) {
            var line = fileContent[i].split(",")
            if (map[line[0]] == null || map[line[1]] == null) {
                if (map[line[0]] == null) {
                    map.put(line[0], n)
                    g.arrVertices[n].name = line[0]
                    n++
                }
                if (map[line[1]] == null) {
                    map.put(line[1], n)
                    g.arrVertices[n].name = line[1]
                    n++
                }
            }
            var v1 = g.arrVertices[map[line[0]]!!]
            var v2 = g.arrVertices[map[line[1]]!!]
            var arista: AristaCosto = AristaCosto(v1, v2, line[2].toInt())
            g.agregarAristaCosto(arista)
        }
        var ruta: PrimUSB = PrimUSB(g, map[args[0]]!!)
        var v: Vertice? = g.arrVertices[map[args[1]]!!]
        var rutaString = LinkedList<String>()
        var k: Double = Double.MAX_VALUE
        while (v != null) {
            if (v.key < n) {
                k = v.key
            }
            rutaString.addFirst(v.name)
            v = v.pred
        }
        var string = ""
        for (i in 0 until rutaString.size-1) {
            string = string + i + "-"
        }
        string = string + rutaString[rutaString.size-1]
        println(string)
        println(ceil(rutaString.size/k))

    } catch (e: RuntimeException) {
        println(e)
    } finally {
        println("Operacion finalizada\n")
    }
}
