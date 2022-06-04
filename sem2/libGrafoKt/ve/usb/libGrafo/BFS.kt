package ve.usb.libGrafo
import java.util.LinkedList

/* 
   Implementación del algoritmo BFS. 
   Con la creación de la instancia, se ejecuta el algoritmo BFS
   desde el vértice s
*/
public class BFS(val g: Grafo, val s: Int) {
    
    var arrVertices: Array<Vertice> = Array<Vertice>(g.obtenerNumeroDeVertices(), {i -> Vertice(i)}) // color blanco y dist 0 y pred nulo
    
    /** 
        Ejecuta BFS sobre el grafo g desde el vertice s
    */
    init {
        arrVertices[s].color = Color.GRIS
        arrVertices[s].dist = 0
        arrVertices[s].pred = null

        var q: LinkedList<Vertice> = LinkedList<Vertice>()
        q.addLast(arrVertices[s])
        
        while (!q.isEmpty()) {
            var u: Vertice = q.getFirst()
            q.removeFirst()

            for (v in g.adyacentes(u.n)) {

                if (arrVertices[v.b.n].color == Color.BLANCO) {
                    // hacemos los cambios en la lista de adyacencias
                    v.b.color = Color.GRIS
                    v.b.dist = u.dist + 1
                    v.b.pred = u

                    // hacemos los cambios en el arreglo de vertices
                    arrVertices[v.b.n].color = Color.GRIS
                    arrVertices[v.b.n].dist = u.dist + 1
                    arrVertices[v.b.n].pred = u

                    q.addLast(v.b)

                }
            }
            
            u.color = Color.NEGRO
            arrVertices[u.n].color = Color.NEGRO
        }
    }

    /*
        Retorna el predecesor de un vértice v.

        {P: v es un vertice valido}
        {Q: true}

        Input. v -> Entero, vertice del cual se sabra el su predecesor
    */
    fun obtenerPredecesor(v: Int) : Int? {
        if (v < 0 || v >= g.nVertices) {
            throw RuntimeException("BFS.obtenerPredecesor: Vertice invalido")
        }

        return arrVertices[v].pred?.n
    } 

    /*
     Retorna la distancia, del camino obtenido por BFS, desde el vértice inicial s 
     hasta el un vértice v. En caso de que el vétice v no sea alcanzable desde s,
     entonces se retorna -1.
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException. 
     */
    fun obtenerDistancia(v: Int) : Int {
        if (v < 0 || v >= g.nVertices) {
            throw RuntimeException("BFS.obtenerDistancia: Vertice invalido")
        }
        
        return arrVertices[v].dist
    }
    /*
     Indica si hay camino desde el vértice inicial s hasta el vértice v.
     Si el camino existe retorna true, de lo contrario falso.
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException. 
     */ 
    fun hayCaminoHasta(v: Int) : Boolean {
        if (v < 0 || v >= g.nVertices) {
            throw RuntimeException("BFS.hayCaminoHasta: Vertice invalido")
        }
        
        return arrVertices[v].dist != Int.MAX_VALUE
    }

    /*
     Retorna el camino con menos lados, obtenido por BFS, desde el vértice inicial s 
     hasta el un vértice v. El camino es representado como un objeto iterable con
     los vértices del camino desde s hasta v.
     En caso de que el vétice v no sea alcanzable desde s, entonces se lanza una RuntimeException.
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException.
     */ 
    fun caminoHasta(v: Int) : Iterable<Int> {
        if (v < 0 || v >= nVertices) {
            throw RuntimeException("BFS.caminoHasta: Vertice invalido")
        }

        if (arrVertices[v].d == Int.MAX_VALUE) {
            throw RuntimeException("BFS.caminoHasta: Vertice inalcanzable")
        }
        var vertice: Vertice? = arrVertices[v]
        var camino: LinkedList<Int> = LinkedList<Int>()
        
        while (vertice?.pred != null) {
            camino.add(vertice?.n)
            vertice = vertice?.pred
        }

        return camino.asIterable()
    }

    // Imprime por la salida estándar el breadth-first tree
    // Muestra todos los lados (u, v) tales que v.pred = u
    fun mostrarArbolBFS() {
        print(" ")
    }
}
