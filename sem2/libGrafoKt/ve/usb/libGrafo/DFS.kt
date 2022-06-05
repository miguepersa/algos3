package ve.usb.libGrafo

/* 
   Implementación del algoritmo DFS. 
   Con la creación de la instancia, se ejecuta el algoritmo DFS
   desde todos los vértices del grafo
*/
public class DFS(val g: Grafo) {
    
    var tiempo: Int
    var arrVertices: Array<Vertice> = Array<Vertice>(g.obtenerNumeroDeVertices(), {i -> Vertice(i)}) // color blanco y dist 0 y pred nulo
    var raices: LinkedList<Int> = LinkedList<Int>()
    var arboles: LinkedList<LinkedList<Int>> = LinkedList<LinkedList<Int>>()

    init {
        tiempo = 0;
        for (v in arrVertices) {
            if (v.color == Color.BLANCO) {
                raices.add(v.n)
                arboles.add(LinkedList<Int>())
                dfsVisit(g, v.n)
            }
        }
    }

    private fun dfsVisit(g: Grafo, u: Int) {
        arboles[raices.size-1].add(u)
        tiempo++
        var v = arrVertices[u]
        v.d = tiempo
        v.color = Color.GRIS
        var ady: Iterable<Lado> = g.adyacentes(u)
        for (i in ady) {
            var p: Vertice = arrVertices[i.elOtroVertice(u).n]
            if (p.color == Color.BLANCO) {
                p.pred = v
                dfsVisit(g, p.n)
            }
        }
        v.color = Color.NEGRO
        tiempo++
        v.f = tiempo
    }

    /*
     Retorna el predecesor de un vértice v. Si el vértice no tiene predecesor 
     se retorna null. En caso de que el vértice v no exista en el grafo se lanza
     una RuntimeException 
     */    
    fun obtenerPredecesor(v: Int) : Int? {
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("DFS.obtenerPredecesor: Vertice invalido")
        }

        return arrVertices[v].pred?.n
    }

     /*
     Retorna un par con el tiempo inical y final de un vértice durante la ejecución de DFS. 
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException 
     */
    fun obtenerTiempos(v: Int) : Pair<Int, Int> {
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("DFS.obtenerTiempos: Vertice invalido")
        }
        
        return Pair<arrVertices[v].d, arrVertices[v].f>
    }

    /*
     Indica si hay camino desde el vértice inicial u hasta el vértice v.
     Si el camino existe retorna true, de lo contrario falso
     En caso de que alguno de los vértices no exista en el grafo se lanza una RuntimeException 
     */ 
    fun hayCamino(u: Int, v: Int) : Boolean {
        if (v < 0 || v >= g.obtenerNumeroDeVertices() || u < 0 || u >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("DFS.hayCamino: Vertice invalido")
        }

        var p = arrVertices[v].pred
        while (p != null) {
            if (p.n == u) {
                return true
            }
            p = p.pred
        }
        return false
    }

    /*
     Retorna el camino desde el vértice  u hasta el un vértice v. 
     El camino es representado como un objeto iterable con los vértices del camino desde u hasta v.
     En caso de que no exista un camino desde u hasta v, se lanza una RuntimeException. 
     En caso de que alguno de los vértices no exista en el grafo se lanza una RuntimeException.
     */ 
    fun caminoDesdeHasta(u: Int, v: Int) : Iterable<Int>  {
        if (v < 0 || v >= g.obtenerNumeroDeVertices() || u < 0 || u >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("DFS.caminoDesdeHasta: Vertice invalido")
        }
        
        var p = arrVertices[v].pred
        var camino = LinkedList<int>()
        camino.addFirst(v)
        while (p != null) {
            camino.addFirst(p.n)
            if (p.n == u) {
                return camino.asIterable()
            }
            p = p.pred
        }
        throw RuntimeException("DFS.caminoDesdeHasta: Camino desde $u hasta $v no existe")
    }

    // Retorna true si hay lados del bosque o false en caso contrario.
    fun hayLadosDeBosque(): Boolean {
        for (i in arboles) {
            if (i.size > 1) {
                return true
            }
        }
        return false
    }
    
    // Retorna los lados del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosDeBosque() : Iterator<Lado> {
        if (!self.hayLadosDeBosque()) {
            throw RuntimeException("DFS.ladosDeBosque: No hay lados del bosque")
        }
        var lados: LinkedList<Lado> = LinkedList<Lado>()
        for (i in arboles) {
            for (j in 0..i.size-2) {
                lados.add(Lado(arrVertices[i[j]], arrVertices[i[j+1]]))
            }
        }
        return lados.asIterable()
    }

    // Retorna true si hay forward edges o false en caso contrario.
    fun hayLadosDeIda(): Boolean {}

    // Retorna los forward edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosDeIda() : Iterator<Lado> { }

    // Retorna true si hay back edges o false en caso contrario.
    fun hayLadosDeVuelta(): Boolean {  }

    // Retorna los back edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosDeVuelta() : Iterator<Lado> { }

    // Retorna true si hay cross edges o false en caso contrario.
    fun hayLadosCruzados(): Boolean {  }

    // Retorna los cross edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosCruzados() : Iterator<Lado> { }

    // Imprime por la salida estándar el depth-first forest.
    fun mostrarBosqueDFS() {
        var nArbol = 1
        for (i in arboles) {
            prinln("Arbol " + nArbol.toString() + ":\n")
            for (j in 0..i.size-1) {
                if (j == i.size-1) {
                    println(i[j].toString() + "\n")
                }
                else {
                    println(i[j].toString() + " ->")
                }
            }
            nArbol++
        }
    }
    
}
