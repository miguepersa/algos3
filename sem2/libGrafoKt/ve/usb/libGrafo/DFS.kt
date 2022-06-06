package ve.usb.libGrafo
import java.util.LinkedList

/* 
   Implementación del algoritmo DFS. 
   Con la creación de la instancia, se ejecuta el algoritmo DFS
   desde todos los vértices del grafo
*/
public class DFS(val g: Grafo) {
    
    var tiempo: Int
    var arrVertices: Array<Vertice> = Array<Vertice>(g.obtenerNumeroDeVertices(), {i -> Vertice(i)}) // color blanco y dist 0 y pred nulo
    var raices = LinkedList<Int>()

    init {
        tiempo = 0;
        for (v in arrVertices) {
            if (v.color == Color.BLANCO) {
                raices.add(v.n)
                dfsVisit(g, v.n)
            }
        }
    }

    private fun dfsVisit(g: Grafo, u: Int) {
        tiempo++
        var v = arrVertices[u]
        v.d = tiempo
        v.color = Color.GRIS
        var ady: Iterable<Lado> = g.adyacentes(u)
        for (i in ady) {
            var p = arrVertices[i.elOtroVertice(u)!!.n]
            if (p.color == Color.BLANCO) {
                p.pred = v
                dfsVisit(g, p.n)
            }
        }
        arrVertices[u].color = Color.NEGRO
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
        
        return Pair<Int,Int>(arrVertices[v].d, arrVertices[v].f)
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
        var camino = LinkedList<Int>()
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
        for (v in arrVertices) {
            if (v.pred != null) {
                return true
            }
        }
        
        return false
    }
    
    // Retorna los lados del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosDeBosque() : Iterator<Lado> {
        if (!this.hayLadosDeBosque()) {
            throw RuntimeException("DFS.ladosDeBosque: No hay lados del bosque")
        }
        var gLados = g.iterator()                           // Lados del grafo
        var lados: LinkedList<Lado> = LinkedList<Lado>()    // Lados del bosque
        for (v in arrVertices) {
            if (v.pred != null) {
                for (l in gLados) {
                    if (l.a.n == v.pred!!.n && l.b.n == v.n) {
                        lados.add(l)
                    }
                }
            }
        }
        return lados.iterator()
    }

    // Retorna true si hay forward edges o false en caso contrario.
    fun hayLadosDeIda(): Boolean {
        var ldg = g.iterator()          // Lados del grafo
        var ldb = this.ladosDeBosque()  // Lados del bosque
        var lnb = LinkedList<Lado>()    // Lados del grafo que no son lados del bosque
        
        for (i in ldg) {
            for (j in ldb) {
                if (i != j) {
                    lnb.add(i)
                }
            }
        }

        for (i in arrVertices) {
            var ady = g.adyacentes(i.n)
            for (j in ady) {
                for (k in lnb) {
                    if (j == k) {
                        var pred = arrVertices[k.b.n].pred
                        while (pred != null) {
                            if (pred == i) {
                                return true
                            }
                            pred = pred.pred
                        }
                    }
                }
            }
        }
        return false
    }

    // Retorna los forward edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosDeIda() : Iterator<Lado> {
        if (!this.hayLadosDeIda()) {
            throw RuntimeException("DFS.ladosDeIda: No hay lados de ida")
        }
        var ldb = this.ladosDeBosque()  // Lados del bosque
        var ldg = g.iterator()          // Lados del grafo
        var lnb = LinkedList<Lado>()    // Lados del grafo que no son lados del bosque
        var ldi = LinkedList<Lado>()    // Lados de ida

        for (i in ldg) {
            for (j in ldb) {
                if (i != j) {
                    lnb.add(i)
                }
            }
        }

        var v: Vertice?
        var pred: Vertice?
        for (i in arrVertices) {
            v = i
            pred = v.pred
            while (pred != null) {
                pred = pred.pred
                if (pred != null) {
                    for (j in lnb) {
                        if (j.a.n == pred.n && j.b.n == v.n) {
                            ldi.add(j)
                        }
                    }
                }
            }
        }
        return ldi.iterator()
    }

    // Retorna true si hay back edges o false en caso contrario.
    fun hayLadosDeVuelta(): Boolean {
        var ldg = g.iterator()          // Lados del grafo
        var ldb = this.ladosDeBosque()  // Lados del bosque
        var lnb = LinkedList<Lado>()    // Lados del grafo que no son lados del bosque
        
        for (i in ldg) {
            for (j in ldb) {
                if (i != j) {
                    lnb.add(i)
                }
            }
        }

        for (i in arrVertices) {
            var ady = g.adyacentes(i.n)
            for (j in ady) {
                for (k in lnb) {
                    if (j == k) {
                        var pred = i.pred
                        while (pred != null) {
                            if (pred.n == k.b.n) {
                                return true
                            }
                            pred = pred.pred
                        }
                    }
                }
            }
        }
        return false
    }

    // Retorna los back edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosDeVuelta() : Iterator<Lado> {
        if (!this.hayLadosDeVuelta()) {
            throw RuntimeException("DFS.ladosDeVuelta: No hay lados de vuelta")
        }
        var ldb = this.ladosDeBosque()  // Lados del bosque
        var ldg = g.iterator()          // Lados del grafo
        var lnb = LinkedList<Lado>()    // Lados del grafo que no son lados del bosque
        var ldv = LinkedList<Lado>()    // Lados de vuelta

        for (i in ldg) {
            for (j in ldb) {
                if (i != j) {
                    lnb.add(i)
                }
            }
        }

        var v: Vertice?
        var pred: Vertice?
        for (i in arrVertices) {
            v = i
            pred = v.pred
            while (pred != null) {
                pred = pred.pred
                for (j in lnb) {
                    if (j.b.n == pred!!.n && j.a.n == v.n) {
                        ldv.add(j)
                    }
                }
            }
        }
        return ldv.iterator()
    }

    // Retorna true si hay cross edges o false en caso contrario.
    fun hayLadosCruzados(): Boolean {
        var ldg = g.iterator()          // Lados del grafo
        var ldi = this.ladosDeIda()     // Lados de ida
        var ldv = this.ladosDeVuelta()  // Lados de vuelta

        for (i in ldg) {
            for (j in ldi) {
                if (i != j) {
                    for (k in ldv) {
                        if (i != k) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    // Retorna los cross edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosCruzados() : Iterator<Lado> {
        if (!this.hayLadosCruzados()) {
            throw RuntimeException("DFS.ladosDeVuelta: No hay lados de vuelta")
        }

        var ldg = g.iterator()          // Lados del grafo
        var ldi = this.ladosDeIda()     // Lados de ida
        var ldv = this.ladosDeVuelta()  // Lados de vuelta
        var ldc = LinkedList<Lado>()   // Lados cruzados

        for (i in ldg) {
            for (j in ldi) {
                if (i != j) {
                    for (k in ldv) {
                        if (i != k) {
                            ldc.add(i)
                        }
                    }
                }
            }
        }
        return ldc.iterator()
    }

    // Imprime por la salida estándar el depth-first forest.
    fun mostrarBosqueDFS() {
        var nArbol = 1
        for (r in raices) {
            println("Arbol " + nArbol.toString() + ":")
            println(r)
            nArbol++
        }
    }
}
