package ve.usb.libGrafo

/*
 Implementación del algoritmo de Floyd-Warshall para encontrar los
 caminos de costo mínimo todos los pares de vértices de un grafo.
 El constructor recibe como entrada una matriz de costos, que corresponde
 a la matriz de costos asociado a un digrafo con pesos en los lados.
 La matriz de costos es construida con la función dada en clase.
 Se asume que la matriz de costos corresponde a un digrafo sin ciclos negativos.
 Si el digrafo tiene ciclo negativo, el resultado del algoritmo no es especificado.
 La matriz de entrada es cuadrada de dimensiones nxn, donde n es el número de 
 vértices del grafo. Si la matriz de entrada no es cuadrada, entoces se lanza una RuntimeException.
 */
public class FloydWarshall(val W : Array<Array<Double>>) {
    var matrizD: Array<Array<Double>>
    var matrizPred: Array<Array<Int?>>
    var nVertices: Int
    init {
        nVertices = W.size

        for (i in 0 until nVertices) {
            if (W[i].size != nVertices) throw RuntimeException("FloydWarshall.init: La matriz dada no es cuadrada")
        }

        matrizD = W // Copiamos la matriz W

        // Llenamos la matriz de predecesores
        matrizPred = Array<Array<Int?>>(nVertices, {i -> Array<Int?>(nVertices, {j -> null})})
        for (i in 0 until nVertices) {
            for (j in 0 until nVertices) {
                if (i == j || matrizD[i][j] == Double.POSITIVE_INFINITY) {
                    matrizPred[i][j] = null
                } else if (i != j || matrizD[i][j] < Double.POSITIVE_INFINITY) {
                    matrizPred[i][j] = i
                }
            }
        }

        for (k in 0 until nVertices) {
            for (i in 0 until nVertices) {
                for (j in 0 until nVertices) {
                    if (matrizD[i][j] > matrizD[i][k] + matrizD[k][j]) { // Solo revisamos este caso, pues el otro mantiene las matrices iguales
                        matrizD[i][j] = matrizD[i][k] + matrizD[k][j] // modificamos el costo del camino
                        matrizPred[i][j] = matrizPred[k][j] // Modificamos el predecesor
                    }
                }
            }
        }
    }

    /* 
        Devuelve la matriz las distancias del camino de costo minimo 
        entre cada par de vertices

        {P: true}
        {Q: true}

        Input: ~~
        Output: Una matriz de reales que contiene las distancias

        Tiempo de ejecucion O(1)
    */
    fun obtenerMatrizDistancia() : Array<Array<Double>> = matrizD

    /* 
        Devuelve la matriz de predecesores del camino de costo minimo
        entre cada par de vertices

        {P: true}
        {Q: true}

        Input: ~~
        Output: Una matriz de Enteros o nulos que contiene los predecesores del camino de costo minimo

        Tiempo de ejecucion O(1)
    */
    fun obtenerMatrizPredecesores() : Array<Array<Int?>> = matrizPred
    
    /* 
        Devuelve el costo del camino de costo minio desde un vertice u hasta v

        {P: u y v son vertices del grafo y existe un camino desde u hasta v}
        {Q: matrizD[u][v]}

        Input: u y v son vertices del grafo
        Output: El costo del camino de costo minimo desde u hasta v

        Tiempo de ejecucion O(1)
    */
    fun costo(u: Int, v: Int) : Double { 
        if (u < 0 || u >= nVertices) {
            throw RuntimeException("FloydWarshall.costo(): El vertice $u no pertenece al grafo")
        }

        if (v < 0 || v >= nVertices) {
            throw RuntimeException("FloydWarshall.costo(): El vertice $v no pertenece al grafo")
        }

        if (!this.existeUnCamino(u, v)) {
            throw RuntimeException("FloydWarshall.costo(): No hay camino desde $u hasta $v")
        }

        return matrizD[u][v]
    }

    /* 
        Devuelve si es cierto que hay un cam ino desde u hasta v

        {P: u y v son vertices del grafo}
        {Q: matrizPred[u][v] != null}

        Input: u y v vertices del grafo
        Output: true -> Si existe un camino desde u hasta v
                false -> caso contrario

        Tiempo de ejecucion O(1)
    */
    fun existeUnCamino(u: Int, v: Int) : Boolean { 
        if (u < 0 || u >= nVertices) {
            throw RuntimeException("FloydWarshall.costo(): El vertice $u no pertenece al grafo")
        }

        if (v < 0 || v >= nVertices) {
            throw RuntimeException("FloydWarshall.costo(): El vertice $v no pertenece al grafo")
        }

        return matrizPred[u][v] != null
    }

    /* 
        Devuelve los arcos del camino de costo minimo del camino
        desde el vertice u hasta v

        {P: u y v son vertices del grafo}
        {Q: <u, x>, <x, y>, ..., <v.pred, v>}

        Input: u y v Vertices del grafo
        Output: Los arcos del camino de costo minimo del camino desde el vertice u hasta el vertive v

        Tiempo de ejecucion O(nVertices)
    */
    fun obtenerCaminoDeCostoMinimo(u: Int, v: Int) : Iterable<Arco> { 
        if (u < 0 || u >= nVertices) {
            throw RuntimeException("FloydWarshall.costo(): El vertice $u no pertenece al grafo")
        }

        if (v < 0 || v >= nVertices) {
            throw RuntimeException("FloydWarshall.costo(): El vertice $v no pertenece al grafo")
        }

        if (!this.existeUnCamino(u, v)) {
            throw RuntimeException("FloydWarshall.costo(): No hay camino desde $u hasta $v")
        }
        var currVer: Int = v
        var camino: MutableList<Arco> = mutableListOf()
        while (currVer != u) {
            val pred: Int? = matrizPred[u][currVer]!!
            camino.add(Arco(Vertice(pred!!), Vertice(currVer)))
            currVer = pred
        }

        return camino.asIterable()
    }
}
