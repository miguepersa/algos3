package ve.usb.libGrafo
import java.util.LinkedList

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

        arrVertices[u].d = tiempo
        arrVertices[u].color = Color.GRIS

        var ady: Iterable<Lado> = g.adyacentes(u)

        for (i in ady) {
            var p = arrVertices[i.elOtroVertice(u).n]
            if (arrVertices[p.n].color == Color.BLANCO) {
                p.pred = v
                arrVertices[p.n].pred = v
                dfsVisit(g, p.n)
            }
        }
        
        tiempo++

        v.f = tiempo
        v.color = Color.NEGRO

        arrVertices[u].color = Color.NEGRO
        arrVertices[u].f = tiempo
    }

    /*
        Obtiene el predecesor de un vertice v.

        {P: v es un vertice valido}
        {Q: true}

        Input: v -> Entero, vertice al cual obtendremos el predecesor
        Output: v.pred -> Entero, en caso de que el vertice v tenga predecesor
                null -> En caso que que el vertice v no tenga predecesor
         
        Tiempo de ejecucion O(1)
    */    
    fun obtenerPredecesor(v: Int) : Int? {
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("DFS.obtenerPredecesor: Vertice invalido")
        }

        return arrVertices[v].pred?.n
    }

    /*
        Obtiene el tiempo en el que se descubrio y se termino el vertice v

        {P: v es un vertice valido}
        {Q: true}

        Input: v -> Eneri, vertice del cual obtendremos su tiempo de descubrimiento y terminado
        Output: Pair<Int, Int> -> Par que contiene <v.d, v.f>

        Tiempo de ejecucion O(1)
    
    */
    fun obtenerTiempos(v: Int) : Pair<Int, Int> {
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("DFS.obtenerTiempos: Vertice invalido")
        }
        
        return Pair<Int,Int>(arrVertices[v].d, arrVertices[v].f)
    }

    /*
        Determina si existe un camino entre un vertice u y un vertice v.

        Para determinar si existe un camino o no usamos el anidamiento de intervalos de descendientes

        {P: u y v son vertices validos}
        {Q: true}

        Input: u, v -> Enteros, vertices
        Output: true -> Si existe camino desde u hasta v
                false -> Caso contrario

        Tiempo de ejecucion O(1)
    
    */ 
    fun hayCamino(u: Int, v: Int) : Boolean {
        if (v < 0 || v >= g.obtenerNumeroDeVertices() || u < 0 || u >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("DFS.hayCamino: Vertice invalido")
        }

        return arrVertices[u].d < arrVertices[v].d  && arrVertices[v].d < arrVertices[v].f  && arrVertices[v].f < arrVertices[u].f
    }

    /*
        Devuelve un iterable que contiene los vertices que forman el camino desde u hasta v

        {P: u y v son vertices validos y existes un camino entre u y v}
        {Q: true}

        Input: u, v -> Enteros, vertices del grafo
        Output: Un iterable con los vertices del camino desde u hasta v

        Tiempo de ejecucion O(v.d - u.d) // v se descubrio luego de que se descubriera u
    */ 
    fun caminoDesdeHasta(u: Int, v: Int) : Iterable<Int>  {
        if (v < 0 || v >= g.obtenerNumeroDeVertices() || u < 0 || u >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("DFS.caminoDesdeHasta: Vertice invalido")
        }

        if (!this.hayCamino(u, v)) {
            throw RuntimeException("DFS.caminoDesdeHasta: Camino desde $u hasta $v no existe")
        }
        
        var camino = LinkedList<Int>()
        camino.addFirst(v)

        var p = arrVertices[v].pred

        while (p?.n != u) {
            camino.addFirst(p?.n)
            p = p?.pred
        }

        camino.addFirst(u)
        
        return camino.asIterable()
    }

    /* 
        Determina si existen lados bosque (tree edges) luego de la ejecuion de DFS

        Un lado (u,v) es un lado bosque si y solo si existe un vertice cuyo predecesor no sea
        nulo, pues el lado seria (u.pred, u), ademas se cumple el anidamiento de intervalos
        descendientes

        {P: true}
        {Q: true}

        Input: ~
        Output: true -> Si existe un vertce u, cuyo predecesor sea no nulo

        Tiempo de ejecucion O(|V|) 
    
    */
    fun hayLadosDeBosque(): Boolean {
        for (v in arrVertices) {
            if (v.pred != null) {
                return true
            }
        }
        
        return false
    }
    
    /* 
        Devuelve un iterador con todos los lados bosque del grafo.

        {P: true}
        {Q: true}

        Input: ~~
        Output: Un iterador que itera sobre todos los lados arbol del grafo

        Tiempo de ejecucion O(|V|)
    */
    fun ladosDeBosque() : Iterator<Lado> {
        if (!this.hayLadosDeBosque()) {
            throw RuntimeException("DFS.ladosDeBosque: No hay lados del bosque")
        }
        var ldg = g.iterator()
        var ldb: LinkedList<Lado> = LinkedList<Lado>()    // Lados del bosque
        
        for (l in ldg) {
            var verInit: Vertice = l.a
            var verFin: Vertice = l.b
            var anidamiento: Boolean = arrVertices[verInit.n].d < arrVertices[verFin.n].d && arrVertices[verFin.n].d < arrVertices[verFin.n].f  && arrVertices[verFin.n].f < arrVertices[verInit.n].f
            if (arrVertices[verFin.n].pred == verInit && anidamiento) {
                ldb.add(l)
            }
        }
        return ldb.iterator()
    }

    /* 
        Determina si hay lados de ida (fordward edges) en el arbol DFS

        Un lado de  (u, v) cumple con el anidamiento de intervalos de descendientes
        pero no se cumple que u = v.pred

        {P: true}
        {Q: true}

        Input: ~~
        Output: true -> Si existe un lado de ida
                false -> Caso contrario

        Tiempo de ejecucion O(|E|)
    
    */
    fun hayLadosDeIda(): Boolean {
        var ldg = g.iterator() // Lados del grafoif ()
        
        for (l in ldg) {
            var verInit: Vertice = l.a
            var verFin: Vertice = l.b
            var anidamiento: Boolean = arrVertices[verInit.n].d < arrVertices[verFin.n].d && arrVertices[verFin.n].d < arrVertices[verFin.n].f  && arrVertices[verFin.n].f < arrVertices[verInit.n].f
            if (arrVertices[verFin.n].pred != verInit && anidamiento) {
                return true
            }
        }

        return false
    }

    /* 
        Devuelve un iterados de lados que itera sobre los lados de ida
        del arbol DFS

        {P: true}
        {Q: true}

        Input: ~~
        Output: Un iterados de lados que itera sobre los lados de ida del arbol

        Tiempo de ejecucion O(|E|)
    */
    fun ladosDeIda() : Iterator<Lado> {
        if (!this.hayLadosDeIda()) {
            throw RuntimeException("DFS.ladosDeIda: No hay lados de ida")
        }

        var ldg = g.iterator()          // Lados del grafo
        var ldi = LinkedList<Lado>()    // Lados de ida

        for (l in ldg) {
            var verInit: Vertice = l.a
            var verFin: Vertice = l.b
            var anidamiento: Boolean = arrVertices[verInit.n].d < arrVertices[verFin.n].d && arrVertices[verFin.n].d < arrVertices[verFin.n].f  && arrVertices[verFin.n].f < arrVertices[verFin.n].f 
            if (arrVertices[verFin.n].pred != verInit && anidamiento) {
                ldi.add(l)
            }
        }

        return ldi.iterator()
    }

    /* 
        Determina si hay lados de vuelta en el grafo luego de ejecuar DFS

        {P: true}
        {Q: true}

        Input: ~~
        Output: true -> Si existe al menos un lado de vuelta en el grafo
                false -> Caso contrario
    */
    fun hayLadosDeVuelta(): Boolean {
        var ldg = g.iterator()          // Lados del grafo
        
        for (l in ldg) {
            var verInit: Vertice = l.a
            var verFin: Vertice = l.b
            var anidamiento: Boolean = arrVertices[verFin.n].d <= arrVertices[verInit.n].d && arrVertices[verInit.n].d < arrVertices[verInit.n].f && arrVertices[verInit.n].f <= arrVertices[verFin.n].f
            if (anidamiento) {
                return true
            } 
        }
        
        return false
    }

    /* 
        Devuelve un iterador de lados que contiene los lados de vuelta del arbol DFS

        {P: true}
        {Q: true}

        Input: ~~
        Output: Un iterador de lados que itera sobre los lados devuelta del arbols DFS

        Tiempo de ejecucion O(|E|)
    */
    fun ladosDeVuelta() : Iterator<Lado> {
        if (!this.hayLadosDeVuelta()) {
            throw RuntimeException("DFS.ladosDeVuelta: No hay lados de vuelta")
        }
        
        var ldg = g.iterator()          // Lados del grafo
        var ldv = LinkedList<Lado>()    // Lados de vuelta
        
        for (l in ldg) {
            var verInit: Vertice = l.a
            var verFin: Vertice = l.b
            var anidamiento: Boolean = arrVertices[verFin.n].d <= arrVertices[verInit.n].d && arrVertices[verInit.n].d < arrVertices[verInit.n].f && arrVertices[verInit.n].f <= arrVertices[verFin.n].f
            if (anidamiento) {
                ldv.add(l)
            } 
        }
        return ldv.iterator()
    }

    /* 
        Determina si exsite un lado cruzado en el grafo luego de DFS

        {P: true}
        {Q: true}

        Input: ~~
        Output: true -> Si existe un lado cruzado en el grafo
                false -> Caso contrario

        Tiempo de ejecucion O(|E|) 
    */
    fun hayLadosCruzados(): Boolean {
        var ldg = g.iterator()          // Lados del grafo
        
        for (l in ldg) {
            var verInit: Vertice = l.a
            var verFin: Vertice = l.b
            var anidamiento: Boolean = arrVertices[verFin.n].d < arrVertices[verFin.n].f && arrVertices[verFin.n].f < arrVertices[verInit.n].d && arrVertices[verInit.n].d < arrVertices[verInit.n].f
            if (anidamiento) {
                return true
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
        var ldc = LinkedList<Lado>()   // Lados cruzados

        for (l in ldg) {
            var verInit: Vertice = l.a
            var verFin: Vertice = l.b
            var anidamiento: Boolean = arrVertices[verFin.n].d < arrVertices[verFin.n].f && arrVertices[verFin.n].f < arrVertices[verInit.n].d && arrVertices[verInit.n].d < arrVertices[verInit.n].f
            if (anidamiento) {
                ldc.add(l)
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
