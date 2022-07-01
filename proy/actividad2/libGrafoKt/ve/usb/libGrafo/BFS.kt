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

        Tiemo de ejecucion O(numero de vertices de g + numero de lados de g)
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
                var ver: Int = v.b.n

                if (ver == u.n) { // Siempre agarramos la coordenada diferente al vertice
                    ver = v.a.n
                }

                if (arrVertices[ver].color == Color.BLANCO) {
                    if (ver == v.b.n) {
                        // hacemos los cambios en la lista de adyacencias
                        v.b.color = Color.GRIS
                        v.b.dist = u.dist + 1
                        v.b.pred = u
                    } else {
                        v.a.color = Color.GRIS
                        v.a.dist = u.dist + 1
                        v.a.pred = u
                    }

                    // hacemos los cambios en el arreglo de vertices
                    arrVertices[ver].color = Color.GRIS
                    arrVertices[ver].dist = u.dist + 1
                    arrVertices[ver].pred = u

                    q.addLast(arrVertices[ver])
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

        Input: v -> Entero, vertice del cual se sabra el su predecesor
        Output: Entero -> Representa el predecesor del vertice v
                null -> Si el vertice v no tiene predecesor

        Tiempo de ejecucion O(1)
    */
    fun obtenerPredecesor(v: Int) : Int? {
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("BFS.obtenerPredecesor: Vertice invalido")
        }

        return arrVertices[v].pred?.n
    } 

    /*
        Obtiene la distancia de un vertice dado.

        {P: v es un vertice valido}
        {Q: true}

        Input: v -> Entero, vertice del cual se sabra su distancia desde el vertice raiz
        Output: Entero -> v.d si el vertice es alcanzable desde la raiz
                          -1 si el vertice no es alcanzable desde la raiz

        Tiempo de ejecucion O(1) 
    */
    fun obtenerDistancia(v: Int) : Int {
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("BFS.obtenerDistancia: Vertice invalido")
        }

        if (arrVertices[v].dist == Int.MAX_VALUE) {
            return -1
        }
        
        return arrVertices[v].dist
    }
    /*
        Determina si hay o no un camino desde la raiz hasta v

        {P: v es un vertice valido}
        {Q: true}

        Input: v -> Entero, vertice del cual se determinara si existe un camino hasta el desde la raiz
        Output: true -> Si existe un camino desde la raiz hasta v
                false -> Caso contrario

        Tiempo de ejecucion O(1)
    */ 
    fun hayCaminoHasta(v: Int) : Boolean {
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("BFS.hayCaminoHasta: Vertice invalido")
        }
        
        return arrVertices[v].dist != Int.MAX_VALUE
    }

    /*
        Devuelve el camino desde la raiz hasta un vertice v

        {P: v es un vertice valido}
        {Q: true}

        Input: v -> Entero, que representa el vertice del cual se buscara su camino desde la raiz
        Output: Un iterable que contiene los elementos del camino que va desde la raiz hasta v 
    */ 
    fun caminoHasta(v: Int) : Iterable<Int> {
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("BFS.caminoHasta: Vertice invalido")
        }

        if (arrVertices[v].d == Int.MAX_VALUE) {
            throw RuntimeException("BFS.caminoHasta: Vertice inalcanzable")
        }
        var vertice: Vertice? = arrVertices[v]
        var camino: LinkedList<Int> = LinkedList<Int>()
        
        while (vertice?.pred != null) {
            camino.addFirst(vertice.n)
            vertice = vertice.pred
        }
        camino.addFirst(s)

        return camino.asIterable()
    }

    /* 
        Obtiene el indice cuya distancia desde la raiz sea la mayor


        {P: true}
        {Q: sea v el vertice que devuelve v.d >= u.d para todo u en V}

        Input:~~
        Output: Entero -> representa el vertice con mayor distancia desde la raiz

        Tiempo de ejecucion O(|V|)
    */
    fun obtenerMayorVerticeDistancia(): Int {
        var mayorVerticeDistancia: Int = 0

        for (i in 0 until g.obtenerNumeroDeVertices()) {
            if (arrVertices[mayorVerticeDistancia].d < arrVertices[i].d) mayorVerticeDistancia = i
        }

        return mayorVerticeDistancia
    }

    /* 
        Se devuelve la mayor distancia que hay desde la raiz a algun vertice del grafo

        {P: true}
        {Output: se devuelve la mayor distancia de un vertice desde la raiz}

        Input: ~~
        Output: Entero -> La mayor distancia desde un vertice desde la raiz

        Tiempo de ejecucion O(1)    
    */
    fun obtenerMayorDistancia(): Int = this.obtenerDistancia(this.obtenerMayorVerticeDistancia())

    // Imprime por la salida estándar el breadth-first tree
    fun mostrarArbolBFS() {
        for (i in 0 until g.obtenerNumeroDeVertices()) {
            if (arrVertices[i].pred != null) {
                print("(${arrVertices[i].pred?.n}, ${i}) ")
            }
        }
        println()
    }
}
