package ve.usb.libGrafo

/* 
    Tipo de vertice que utiliza el algoritmo
    de bellman ford
*/
data class VerticeBellmanFord(val n: Int) {
    var pred: VerticeBellmanFord? = null
    var d: Double = Double.MAX_VALUE
}

/*
 Implementación del Algoritmo de Bellman-Ford para encontrar los
 caminos de costo mínimo desde un vértice fuente s fijo.
 Si el vértice s no existe en el grafo se retorna un RuntimeException.
 */
public class CCM_BellmanFord(val g: GrafoDirigidoCosto, val s: Int) {
    var listaVertices: MutableList<VerticeBellmanFord>
    var cicloNegativo: Boolean

    init {
        // Llenamos nuestra lista con los vertices del grafo
        listaVertices = mutableListOf()
        for (i in 0 until g.obtenerNumeroDeVertices()) listaVertices.add(VerticeBellmanFord(i))

        listaVertices[s].d = 0.0 // Inicializamos la fuente fija

        for (i in 0 until g.obtenerNumeroDeVertices()) {
            for (arco in g.iterator()) {
                val u: Int = arco.x.n
                val v: Int = arco.y.n
                relajacion(u, v, arco.obtenerCosto())
            }
        }

        // Verificamos  que no hayan ciclos de costo negativo
        cicloNegativo = true
        for (arco in g.iterator()) {
            val u: Int = arco.x.n
            val v: Int = arco.y.n
            if (listaVertices[v].d > listaVertices[u].d + arco.obtenerCosto()) cicloNegativo = false
        }
    }
    
    private fun relajacion(u: Int, v: Int, w: Double) {
        if (listaVertices[v].d > listaVertices[u].d + w) {
            listaVertices[v].d = listaVertices[u].d + w
            listaVertices[v].pred = listaVertices[v]
        }
    }

    /* 
        Retorna si es cierto que hay un ciclo negativo en el grafo

        {P: true}
        {Q: devuelve true si hay un ciclo negativo}

        Input: ~~
        Output: cicloNegativo

        Tiempo de ejecucion O(1)
    */
    fun tieneCicloNegativo() : Boolean = cicloNegativo

    // Retorna los arcos del ciclo negativo con la forma <u, v>, <v, w>, ... ,<y, x>, <x, u>  
    // fun obtenerCicloNegativo() : Iterable<ArcoCosto> { }

    /* 
        Determine si existe un camino entre el vertice raiz s y vertice v

        Existe un camino desde s hasta v si y solo si luego de la ejecucion
        del algoritmo v.d <= inf

        En nuestro caso inf = Double.MAX_VALUE

        {P: Un vertice que pertenece al grafo}
        {Q: v.d <= inf}

        Input: v -> Entero que representa el vertice de un grafo
        Output: true -> Si existe un camino desde s hasta v
                false -> caso contrario
        
        Tiempo de ejecucion O(1)
    */
    fun existeUnCamino(v: Int) : Boolean { 
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("CCM_BellmanFord.existeUnCamino(): El vertice $v no pertenece al grafo")
        }

        return listaVertices[v].d != Double.MAX_VALUE
    }

    /* 
        Devuelve el costo del camino desde el vertice s hasta un vertice v

        {P: el vertice v pertenece al grafo}
        {Q: v.d}

        Input: v -> Entero que representa un vertice del grafo
        Output: El costo del camino desde el vertice s hasta el vertice v
    */
    fun costo(v: Int) : Double { 
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("CCM_BellmanFord.costo(): El vertice $v no pertenece al grafo")
        }

        if (!this.existeUnCamino(v)) {
            throw RuntimeException("CCM_BellmanFord.costo(): No existe camino desde $s hasta el vertice $v")
        }

        return listaVertices[v].d
    }

    // Retorna los arcos del camino de costo mínimo hasta v.
    // Si el vértice v no existe, se retorna un RuntimeException.
    fun obtenerCaminoDeCostoMinimo(v: Int) : Iterable<ArcoCosto> { 
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("CCM_DAG.obtenerCaminoCostoMinimo: El vertice $v no pertenece al grafo")
        }

        if (v == s) {
            throw RuntimeException("CCM_DAG.obtenerCaminoDeCostoMinomo: No hay camino hasta el vertice raiz")
        }

        // Obtenemos el camino desde s hasta v
        var camino: MutableList<Int> =  mutableListOf()
        var auxver: Int = v 
        while (auxver != s ) {
            camino.add(0, auxver)
            auxver = listaVertices[auxver].pred!!.n
        }

        camino.add(0, s) // Cada lado es (camino[i], camino[i + 1])

        // Ahora, obtenemos el camino de cada lado del camino
        var caminoCosto: MutableList<ArcoCosto> = mutableListOf()

        for (i in 0 until camino.size - 1) {
            val a: Int = camino[i]
            val b: Int = camino[i + 1]
            
            // buscamos el lado (camino[i], camino[i+1])
            for (l in g.iterator()) {
                if (a == l.x.n && b == l.y.n) {
                    caminoCosto.add(0, l)
                }
            }
        }

        return caminoCosto.asIterable()
    }
}
