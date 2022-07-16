package ve.usb.libGrafo

/*
 Implementación del algoritmo de Dijkstra para encontrar los
 caminos de costo mínimo desde un vértice fuente s fijo. La
 implementación debe usar como cola de prioridad un min-heap o un fibonacci heap.
 Si el grafo de entrada tiene un lado con costo negativo,  entonces se retorna una RuntimeException.
 Si el vértice s no pertenece al grafo, entonces se retorna una RuntimeException.
*/
public class CCM_Dijkstra(val g: GrafoDirigidoCosto, val s: Int) {

    val nVertices: Int  // Numero de vertices de g
    var arrVertices: Array<Vertice> // Arreglo de vertices de g
    var verticesCamino: MutableList<Vertice>
    var q: ColaDePrioridad
    
    init {
        nVertices = g.nVertices
        arrVertices = Array<Vertice>(nVertices, {i -> Vertice(i)})
        arrVertices[s].key = 0.0
        verticesCamino = mutableListOf<Vertice>()
        q = ColaDePrioridad()
        
        for (i in arrVertices) {
            q.add(i)
        }

        while (!q.vacia()) {
            var u: Int = q.extraerMinimo()
            verticesCamino.add(arrVertices[u])
            var ady = g.adyacentes(u)
            for (i in ady) {
                var v = arrVertices[i.y.n]
                if (v.key > arrVertices[u].key + i.costo) {  // Relajacion
                    q.decreaseKey(v.n, arrVertices[u].key + i.costo)
                    v.pred = arrVertices[u]
                }
            }
        }
    }

    // Retorna cierto si hay un camino desde s hasta el vértice v.
    // Si el vértice v no existe, se retorna un RuntimeException.
    fun existeUnCamino(v: Int) : Boolean {
        if (v < 0 || v >= nVertices) {
            throw  RuntimeException("CCM_Dijkstra.existeUnCamino: el vertice $v no existe en el grafo")
        }
        var u: Vertice? = arrVertices[v]
        while (u != null) {
            u = u.pred
            if (u == null) {
                break
            }
            if (u.n == s) {
                return true
            }
        }
        return false
    }

    // Retorna la distancia del camino de costo mínimo desde s hasta el vértice v. 
    // Si el vértice v no existe, se retorna un RuntimeException.
    fun costo(v: Int) : Double {
        if (this.existeUnCamino(v)) {
            var costo: Double = 0.0
            var u: Vertice? = arrVertices[v]
            while (u != null) {
                costo += u.key
                u = u.pred
            }
            return costo
        }
        return Double.MAX_VALUE
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
            auxver = arrVertices[auxver].pred!!.n
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
