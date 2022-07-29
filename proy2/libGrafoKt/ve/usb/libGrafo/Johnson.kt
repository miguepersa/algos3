package ve.usb.libGrafo

/*
 Implementación del algoritmo de Johnson para encontrar los
 caminos de costo mínimo entre todos los pares de vértices de un grafo.
 El constructor recibe como entrada un digrafo con costos en los arcos.
 */
public class Johnson(val g: GrafoDirigidoCosto) {
    var gPrimo: GrafoDirigidoCosto  // Grafo G'
    var s: Int  // Vertice 's' del grafo G'
    var bellmanFord: CCM_BellmanFord    // Instancia de Bellman-Ford con G'
    var d: Array<Array<Double>>    // Arreglo de |V| x |V|
    var h: Array<Double>    // Arreglo de costos h
    var mPredecesores: Array<Array<Int?>>   // Matriz de predecesores

    init {
        gPrimo = GrafoDirigidoCosto(g.nVertices + 1)
        s = g.nVertices // Vertice 's' estara de ultima en G'
        h = Array<Double>(gPrimo.nVertices, {Double.POSITIVE_INFINITY}) // Costos inicializados en infinito
        d = Array<Array<Double>>(s, {Array(s, {Double.POSITIVE_INFINITY})}) // Costos de caminos minimos inicializados en infinito
        mPredecesores = Array<Array<Int?>>(s, {Array(s, {null})})   // Matriz de predecesores inicializada en null

        // Computamos G'
        for (i in g.listaLados) gPrimo.agregarArcoCosto(i)  // Agregamos a G' los lados de G
        for (v in 0 until g.nVertices) {    // Agregamos a G' los lados (s,v) para cada v de G
            gPrimo.agregarArcoCosto(ArcoCosto(gPrimo.arrVertices[s], gPrimo.arrVertices[v], 0.0))
        }

        bellmanFord = CCM_BellmanFord(gPrimo, s)    // Aplicamos Bellman-Ford en G' partiendo de 's'
        if (bellmanFord.cicloNegativo) {
            println("Ciclo negativo")

        } else {
            for (v in gPrimo.arrVertices) {
                h[v.n] = bellmanFord.costo(v.n) // Guardamos los costos calculados en Bellman-Ford en nuestro arreglo de costos
            }

            var grafoAux = GrafoDirigidoCosto(g.nVertices)  // Grafo auxiliar
            for (lado in gPrimo.listaLados) {
                var wcosto = lado.costo + h[lado.x.n] - h[lado.y.n]
                if (lado.x.n != s) {
                    var u = grafoAux.arrVertices[lado.x.n]
                    var v = grafoAux.arrVertices[lado.y.n]
                    grafoAux.agregarArcoCosto(ArcoCosto(u,v,wcosto))    // Agregamos los lados con el costo nuevo
                }
            }
            for (u in grafoAux.arrVertices) {
                var dijkstra = CCM_Dijkstra(grafoAux, u.n)  // Aplicamos Dijkstra en cada vertice
                for (v in grafoAux.arrVertices) {
                    d[u.n][v.n] = dijkstra.listaVertices[v.n].d + h[v.n] - h[u.n]   // Agregamos el costo del camino u->v a la matriz
                    if (dijkstra.listaVertices[v.n].pred != null && u.n != v.n) {
                        mPredecesores[u.n][v.n] = dijkstra.listaVertices[v.n].pred!!.n  // Agregamos el predecesor a la matriz
                    }
                }
            }
        }
    }
    
    // Retorna true si hay un ciclo negativo, false en caso contrario.
    fun hayCicloNegativo() : Boolean = bellmanFord.cicloNegativo
    
    // Retorna la matriz con las distancias de los caminos de costo mínimo
    // entre todos los pares de vértices. Si hay un ciclo negativo se lanza un un RuntimeException.
    fun obtenerMatrizDistancia() : Array<Array<Double>> {
        if (this.hayCicloNegativo()) {
            throw RuntimeException("Johnson.obtenerMatrizDistancia: Hay ciclo negativo")
        }
        return d
    }

    // Retorna la matriz con los predecesores de todos los vértices en los caminos de costo mínimo
    // entre todos los pares de vértices.
    fun obtenerMatrizPredecesores() : Array<Array<Int?>> = mPredecesores
    
    // Retorna la distancia del camino de costo mínimo desde el vértice u hasta el vértice v.
    // Si alguno de los dos vértices  no existe, se lanza un RuntimeException.
    // Si hay un ciclo negativo se lanza un un RuntimeException.
    // Si no existe un camino desde u hasta v, entonces se lanza un RuntimeException.
    fun costo(u: Int, v: Int) : Double {
        if (!this.existeUnCamino(u,v)) {
            throw RuntimeException("Johnson.costo: No existe camino desde $u hasta $v")
        }
        return d[u][v]
    }

    // Retorna cierto si hay un camino desde u hasta el vértice v.
    // Si alguno de los dos vértices  no existe, se retorna un RuntimeException.
    // Si hay un ciclo negativo se lanza un un RuntimeException.
    fun existeUnCamino(u: Int, v: Int) : Boolean {
        if (u < 0 || v < 0 || u >= g.nVertices || v >= g.nVertices) {
            throw RuntimeException("Johnson.existeUnCamino: Uno de los vertices no existe")
        }
        if (this.hayCicloNegativo()) {
            throw RuntimeException("Johnson.existeUnCamino: Hay ciclo negativo")
        }
        return mPredecesores[u][v] != null || u == v
    }

    // Retorna los arcos del camino de costo mínimo desde u hasta v.
    // Si alguno de los dos vértices  no existe, se retorna un RuntimeException.
    // Si hay un ciclo negativo se lanza un un RuntimeException.
    // Si no existe un camino desde u hasta v, entonces se lanza una RuntimeException.
    fun obtenerCaminoDeCostoMinimo(u: Int, v: Int) : Iterable<Arco> {
        if (!this.existeUnCamino(u,v)) {
            throw RuntimeException("Johnson.obtenerCaminoDeCostoMinimo: No existe camino desde $u hasta $v")
        }
        var i: Int? = mPredecesores[u][v]
        var j: Int? = v
        var camino = mutableListOf<Arco>()
        while (i != null) {
            camino.add(Arco(Vertice(i),Vertice(j!!)))
            j = i
            i = mPredecesores[u][j]
        }
        return camino.asIterable()
    }
}
