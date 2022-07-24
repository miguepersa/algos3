import ve.usb.libGrafo.*

fun main(args: Array<String>) {
    val grafo = GrafoDirigidoCosto(args[0])
    
    println("pruebas bellmanFord")
    for (i in 0 until grafo.obtenerNumeroDeVertices()) {
        val bellman = CCM_BellmanFord(grafo, i)

        print("Vertice $i  ")
        for (j in 0 until grafo.obtenerNumeroDeVertices()) {
            if (j == i) {
                print("0\t")
            } else if (!bellman.existeUnCamino(j)) {
                print("$Double.POSITIVE_INFINITY\t")
            } else {
                print("${bellman.costo(j)}\t")
            }
        }   
        println()     
    }

    println("\npruebas dijkstra")
    for (i in 0 until grafo.obtenerNumeroDeVertices()) {
        val dijkstra = CCM_Dijkstra(grafo, i)

        print("Vertice $i  ")
        for (j in 0 until grafo.obtenerNumeroDeVertices()) {
            if (j == i) {
                print("0\t")
            } else if (!dijkstra.existeUnCamino(j)) {
                print("$Double.POSITIVE_INFINITY\t")
            } else {
                print("${dijkstra.costo(j)}\t")
            }
        }
        println()
    }

    println("\npruebas a estrella")
    for (i in 0 until grafo.obtenerNumeroDeVertices()) {
        print("Vertice $i  ")
        for (j in 0 until grafo.obtenerNumeroDeVertices()) {
            val aestrella = AEstrella(grafo, i, setOf(j), {0.0})
            print("${aestrella.costo()}\t")
        }
        println()
    }

    println("\npruebas floyd warshall")
    val floyd = FloydWarshall(matrizDis(grafo))
    var m = floyd.obtenerMatrizDistancia()
    for (i in 0 until grafo.obtenerNumeroDeVertices()) {
        print("Vertice $i  ")
        for (j in 0 until grafo.obtenerNumeroDeVertices()) {
            print("${m[i][j]}\t")
        }
        println()
    }

    println("\npruebas johnson")
    val johnson = Johnson(grafo)
    m = johnson.obtenerMatrizDistancia()
    for (i in 0 until grafo.obtenerNumeroDeVertices()) {
        print("Vertice $i  ")
        for (j in 0 until grafo.obtenerNumeroDeVertices()) {
            print("${m[i][j]}\t")
        }
        println()
    }
}

fun matrizDis(g: GrafoDirigidoCosto): Array<Array<Double>> {
    var n = g.obtenerNumeroDeVertices()
    var d = Array<Array<Double>>(n, {i -> Array<Double>(n ,{i-> Double.POSITIVE_INFINITY})})

    for (l in g) {
        d[l.a.n][l.b.n] = l.obtenerCosto()
    }
    for (i in 0 until n) {
        d[i][i] = 0.0
    }

    return d
}
