package ve.usb.libGrafo

data class VerticePERT(val n: Int) {
    var d: Double = Double.NEGATIVE_INFINITY
    var pred: VerticePERT? = null
}
/*
 Implementación del algoritmo para encontrar el
 caminos crítico de un PERT, representado como un DAG con costos en los lados.
 Si el digrafo de entrada no es DAG, entonces se lanza una RuntimeException.
*/
public class CaminoCriticoPERT(val g: GrafoDirigidoCosto, val s: Int) {
    var listaVertices: MutableList<VerticePERT>

    init {
        // Verificamos que el grafo de entrada sea un DAG
        val dfs: DFS = DFS(g)
        if (dfs.hayLadosDeVuelta()) {
            throw RuntimeException("CaminoCriticoPERT().init: El grafo dado no es un DAG")
        }

        if (s < 0 || s >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("CaminoCriticoPERT().init: El vertive $s no pertenece al grafo")
        }

        val grafoOrdenTopologico: OrdenamientoTopologicoCosto = OrdenamientoTopologicoCosto(g)
        val ordenTopologico: Iterable<Int> =  grafoOrdenTopologico.obtenerOrdenTopologico()

        listaVertices = mutableListOf()
        for (i in 0 until g.obtenerNumeroDeVertices()) {
            listaVertices.add(VerticePERT(i))
        }

        listaVertices[s].d = 0.0 // Inicializamos la fuente fija

        for (u in ordenTopologico) {
            for (v in g.adyacentes(u)) {
                relajacion(u, v.y.n, v.obtenerCosto())
            }
        }
    }

    private fun relajacion(u: Int, v: Int, w: Double) {
        if (listaVertices[v].d < listaVertices[u].d + w) {
            listaVertices[v].d = listaVertices[u].d + w 
            listaVertices[v].pred = listaVertices[u]
        }
    }
    /* 
        Devolvemos el costo del camino critico

        Para esto obtenemos los arcos del camino
        y sumamus sus costos

        {P: true}
        {Q: sum(lado.obtenerCosto para cada lado del camino critico)}

        Input: ~~
        Output: El costo del camino critico

        Tiempo de ejecucion O(|V||E|)
    */
    fun costo() : Double { 
        var costoCamino: Double = 0.0
        for (arco in this.obtenerCaminoCritico()) {
            costoCamino += arco.obtenerCosto()
        }

        return costoCamino
    }

    /* 
        Obtiene el camino critico resultante

        Para esto obtenemos el camino en forma de vertice,
        luego iteramos sobre el grafo para obtener los costos
        de cada lado

        {P: true}
        {Q: el camino <s, u>, <u, v>, ..., <x, y> tal que 
            sea el camino critico del grafo}

        Input: ~~
        Output: El camino critico del grafo

        Tiempo de ejecucion O(|V||E|)
    */
    fun obtenerCaminoCritico() : Iterable<ArcoCosto> { 
        var camino: MutableList<Arco> = mutableListOf()
        for (i in listaVertices) {
            if (i.pred != null) {
                camino.add(Arco(Vertice(i.pred!!.n), Vertice(i.n)))
            }
        }

        var caminoCosto: MutableList<ArcoCosto> = mutableListOf()
        for (c in camino) {
            for (l in g.iterator()) {
                if (c.inicio.n == l.x.n && c.fin.n == l.y.n) {
                    caminoCosto.add(l)
                }
            }
        }
        return caminoCosto.asIterable()
    }
}
