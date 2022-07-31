package ve.usb.libGrafo

data class VerticeDijstra(val n: Int) {
    var pred: VerticeDijstra? = null
    var d: Int = Int.MAX_VALUE
}

/*
 Implementación del algoritmo de Dijkstra para encontrar los
 caminos de costo mínimo desde un vértice fuente s fijo. La
 implementación debe usar como cola de prioridad un min-heap o un fibonacci heap.
 Si el grafo de entrada tiene un lado con costo negativo,  entonces se retorna una RuntimeException.
 Si el vértice s no pertenece al grafo, entonces se retorna una RuntimeException.
*/
public class CCM_Dijkstra(val g: GrafoDirigidoCosto, val s: Int) {
    var listaVertices: MutableList<VerticeDijstra>
    var conjuntoVertices: MutableList<Int>
    var t: Int

    init {
        if (s < 0 || s >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("CCM_Dijkstra.init: El vertice fuente $s no pertenece al grafo")
        }

        for (l in g.iterator()) {
            if (l.costo < 0) {
                throw RuntimeException("CCM_Dijkstra.init: El grafo dado tiene un lado con costo negativo")
            }
        }
        
        // Inicializar fuente fija
        t = 0
        listaVertices = mutableListOf()
        for (i in 0 until g.obtenerNumeroDeVertices()) listaVertices.add(VerticeDijstra(i))
        listaVertices[s].d = 0

        conjuntoVertices = mutableListOf()

        var q: ColaDePrioridad = ColaDePrioridad()

        for (i in 0 until g.obtenerNumeroDeVertices()) {
            q.add(i, listaVertices[i].d)
        }

        while (!q.vacia()) {
            t++
            var u: Int = q.extraerMinimo()

            conjuntoVertices.add(u)

            for (v in g.adyacentes(u)) {
                if (v.x.n == u) {
                    if (listaVertices[v.y.n].d > listaVertices[u].d + v.obtenerCosto(t)) { // Relajacion
                        listaVertices[v.y.n].d = listaVertices[u].d + v.obtenerCosto(t)
                        listaVertices[v.y.n].pred = listaVertices[u]
                        q.decreaseKey(v.y.n, listaVertices[v.y.n].d) 
                    }
                }
                else if (v.y.n == u) {
                    if (listaVertices[v.x.n].d > listaVertices[u].d + v.obtenerCosto(t)) { // Relajacion
                        listaVertices[v.x.n].d = listaVertices[u].d + v.obtenerCosto(t)
                        listaVertices[v.x.n].pred = listaVertices[u]
                        q.decreaseKey(v.x.n, listaVertices[v.x.n].d) 
                    }
                }
            }
        }
        
    }

    // Retorna cierto si hay un camino desde s hasta el vértice v.
    // Si el vértice v no existe, se retorna un RuntimeException.
    fun existeUnCamino(v: Int) : Boolean {
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw  RuntimeException("CCM_Dijkstra.existeUnCamino: el vertice $v no existe en el grafo")
        }
        
        return listaVertices[v].d < Double.POSITIVE_INFINITY
    }

    // Retorna la distancia del camino de costo mínimo desde s hasta el vértice v. 
    // Si el vértice v no existe, se retorna un RuntimeException.
    fun costo(v: Int) : Int {
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw  RuntimeException("CCM_Dijkstra.existeUnCamino: el vertice $v no existe en el grafo")
        }

        return listaVertices[v].d
    }

    // Retorna los arcos del camino de costo mínimo hasta v.
    // Si el vértice v no existe, se retorna un RuntimeException.
    fun obtenerCaminoDeCostoMinimo(v: Int) : Iterable<ArcoCosto> {
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("CCM_Dijkstra.obtenerCaminoCostoMinimo: El vertice $v no pertenece al grafo")
        }

        if (v == s) {
            throw RuntimeException("CCM_Dijkstra.obtenerCaminoDeCostoMinomo: No hay camino hasta el vertice raiz")
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
