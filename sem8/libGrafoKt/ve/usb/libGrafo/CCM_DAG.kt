package ve.usb.libGrafo

data class VerticeCcmDAG(val n: Int) {
    var pred: VerticeCcmDAG? = null
    var d: Double = Double.MAX_VALUE
}
/*
 Implementación del algoritmo para encontrar los
 caminos de costo mínimo desde un vértice fuente s fijo en DAGs. 
 Si el digrafo de entrada no es DAG, entonces se lanza una RuntimeException.
 Si el vértice s no existe en el grafo se retorna un RuntimeException.
*/
public class CCM_DAG(val g: GrafoDirigidoCosto, val s: Int) {
    var listaVertices: MutableList<VerticeCcmDAG>

    // Ejecutamos el algoritmo
    init {
        // Verificamos que el grafo sea un DAG
        val dfs: DFS = DFS(g)
        if (dfs.hayLadosDeVuelta()) {
            throw RuntimeException("CCM_DAG.init: El grafo dado no es un DAG")
        }

        if (s < 0 || s >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("CCM_DAG.init: El vertice $s no pertenece al grafo")
        }

        val grafoOrdenTopologico: OrdenamientoTopologicoCosto = OrdenamientoTopologicoCosto(g)
        val ordenTopologico: Iterable<Int> = grafoOrdenTopologico.obtenerOrdenTopologico()

        listaVertices = mutableListOf()

        for (i in 0 until g.obtenerNumeroDeVertices()) {
            listaVertices.add(VerticeCcmDAG(i))    
        }

        listaVertices[s].d = 0.0 // Inicializamos la fuente fija

        for (u in ordenTopologico) {
            for (v in g.adyacentes(u)) {
                relajacion(u, v.y.n, v.obtenerCosto())
            }
        }
    }

    private fun relajacion(u: Int, v: Int, w: Double) {
        if (listaVertices[v].d > listaVertices[u].d + w) {
            listaVertices[v].d = listaVertices[u].d + w
            listaVertices[v].pred = listaVertices[u]
        }
    }

    /*
        Determina si existe un camino desde s hasta v

        {P: v es un vertice del grafo}
        {Q: Existe un camino si y solo si la distancia hasta el vertice v
            es disinta de infinito}

        Input: v -> Entero que representa un vertice del grafo
        Output: true -> Si existe un camino desde s hasta v
                false -> caso contrario

        Tiempo de ejecucion O(1)
    */
    fun existeUnCamino(v: Int) : Boolean { 
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("CCM_DAG.existeUnCamino(): El vertice $v no pertenece al grafo")
        }

        return listaVertices[v].d != Double.MAX_VALUE
    }

    /* 
        Retorna la distancia del camino de costo minimo desdde s hasta v

        {P: v es un vertice del grafo}
        {Q: se devuelve v.d}

        Input: v -> Entero que representa un vertice del grafo
        Output: Se devuelve el costo del camino de costo minimo 
                desde s hasta v

        Tiempo de ejecucion O(1)
    */
    fun costo(v: Int) : Double { 
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("CCM_costo(): El vertice $v no pertenece al grafo")
        }

        return listaVertices[v].d
    }

    /* 
        Se obtiene el camino de costo minimo desde el vertice s
        hasta el vertice v

        Primero obtenemos el camino de vertices.
        Luego, buscamos el costo del lado correspondiente

        {P: v es un vertic del grafo}
        {Q: se devuelve el camino <v_s, v_x>, <v_x, v_y>, ... , <v_w, v_v>}

        Input: v -> Entero que representa un vertice del grafo
        Output: El camino de costo minimo desde s hasta v

        Tiempo de ejecucion O(|V||G|)
    */
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
        var auxver: Int = v 
        
        if (!this.existeUnCamino(v)) { // Si no existe el camino devolvemos un iterable vacio
            return caminoCosto
        }
        
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
