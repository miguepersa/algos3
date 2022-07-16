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
    var listaVerticesCicloNegativo: MutableList<Int>
    var listaArcosCicloNegativo: MutableList<ArcoCosto>
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
        listaVerticesCicloNegativo = mutableListOf()
        cicloNegativo = false
        for (arco in g.iterator()) {
            val u: Int = arco.x.n
            val v: Int = arco.y.n
            if (listaVertices[v].d > listaVertices[u].d + arco.obtenerCosto()) {
                cicloNegativo = true
                listaVerticesCicloNegativo.add(v)
            }
        }

        // A los vertice alcanzables desde s en los que haya 
        // un ciclo de costo negativo marcamos 
        for (u in listaVerticesCicloNegativo) {
            DFSCiclonegativo(u)
        }

        // Para cada lado que tenga extremos 
        // tengan distancia negativa se a;aden a la lisca
        // de ciclo negativo
        listaArcosCicloNegativo = mutableListOf()
        for (arco in g.iterator()) {
            val u: Int = arco.x.n 
            val v: Int = arco.y.n
            if (listaVertices[u].d == Double.MIN_VALUE && listaVertices[v].d == Double.MIN_VALUE) {
                listaArcosCicloNegativo.add(arco)
            }
        }
    }
    
    private fun relajacion(u: Int, v: Int, w: Double) {
        if (listaVertices[v].d > listaVertices[u].d + w) {
            listaVertices[v].d = listaVertices[u].d + w
            listaVertices[v].pred = listaVertices[u]
        }
    }

    private fun DFSCiclonegativo(u: Int) {
        if (listaVertices[u].d != Double.MIN_VALUE) {
            listaVertices[u].d = Double.MIN_VALUE

            for (v in g.adyacentes(u)) {
                DFSCiclonegativo(v.y.n)
            }
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

    /* 
        Retorna los arcos del ciclo de costo negativo

        Los arcos los retorna en forma <x,y>, <y, z>, ... <u, x>

        Para esto hacemos lo siguiente
            1. Escogemos el primer arco de la listaArcosCicloNegativo, digamos <a,b>
            2. Recorremos la lista hasta encontrar un lado <b, c>
            3. Se repite el paso 2 hasta que c == a

        {P: Hay un ciclo negativo en el grafo}
        {Q: Se devuelve un camino <x, y>, <y, z>, ... , <u, x> tal que su costo sea negativo}

        Input: ~~
        Output: El ciclo de costo negativo del grafo

        Tiempo de ejecucion O(|E|^2)
    */  
    fun obtenerCicloNegativo() : Iterable<ArcoCosto> { 
        if (!this.tieneCicloNegativo()) {
            throw RuntimeException("CCM_BellmanFord.obtenerCicloNegativo: El grafo no tiene un ciclo negativo alcanzable desde $s")
        }
        
        var ciclo: MutableList<ArcoCosto> = mutableListOf()
        
        // Escogemos el primer arco del ciclo
        var currArco: ArcoCosto = listaArcosCicloNegativo[0]
        ciclo.add(currArco)
        // A;adimos el arco inicial al arco
        val verticeInit: Int = currArco.x.n 

        while (currArco.y.n != verticeInit) {
            for (arco in listaArcosCicloNegativo) {
                if (currArco.y.n == arco.x.n) {
                    currArco = arco
                    break
                }
            }
            ciclo.add(currArco)
        }

        return ciclo.asIterable()
    }

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
        var caminoCosto: MutableList<ArcoCosto> = mutableListOf()

        if (!this.existeUnCamino(v)) { // Si no existe el camino devolvemos un iterable vacio
            return caminoCosto
        }

        var auxver: Int = v 
        while (auxver != s ) {
            camino.add(0, auxver)
            auxver = listaVertices[auxver].pred!!.n
        }

        camino.add(0, s) // Cada lado es (camino[i], camino[i + 1])

        // Ahora, obtenemos el camino de cada lado del camino

        for (i in 0 until camino.size - 1) {
            val a: Int = camino[i]
            val b: Int = camino[i + 1]
            
            // buscamos el lado (camino[i], camino[i+1])
            for (l in g.iterator()) {
                if (a == l.x.n && b == l.y.n) {
                    caminoCosto.add(l)
                }
            }
        }

        return caminoCosto.asIterable()
    }
}
