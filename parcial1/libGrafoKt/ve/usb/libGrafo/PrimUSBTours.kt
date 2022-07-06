package ve.usb.libGrafo
import java.util.LinkedList

/*
    Determina el árbol mínimo cobertor de un grafo no dirigido usando el algoritmo de Prim.
    Si el grafo de entrada no es conexo, entonces se lanza un RuntineException.
*/
public class PrimUSB(val g: GrafoNoDirigidoCosto, v: Int) {
    var costo: Double
    
    init {  // ejecutamos el algoritmo de prim desde el vertice v
        costo = 0.0
        val compConexas: ComponentesConexasCostoDFS = ComponentesConexasCostoDFS(g)
        if (compConexas.nCC() != 1) {
            throw RuntimeException("PrimUSB.init: El grafo no es conexo")
        }

        var inQueue: Array<Boolean> = Array<Boolean>(g.obtenerNumeroDeVertices(), {true})

        g.arrVertices[v].key = 0.0

        var q: ColaDePrioridad = ColaDePrioridad(g.obtenerNumeroDeVertices())

        for (i in 0 until g.obtenerNumeroDeVertices()) {
            q.add(g.arrVertices[i])
        }

        while (!q.vacia()) {
            var u: Vertice = q.extrearMinimo()
            costo += u.key
            inQueue[u.n] = false
            for (a in g.adyacentes(u.n)) {
                if (inQueue[a.b.n] && a.obtenerCosto() <= g.arrVertices[a.b.n].key) {
                    g.arrVertices[a.b.n].pred = u
                    g.arrVertices[a.b.n].key = a.obtenerCosto()
                    q.decreaseKey(a.b.n, a.obtenerCosto())
                }
            }
        }
    }

    /* 
        Devuelte un objeto que itera sobre los lados del arbol

        {P: true}
        {Q: true}

        Input: ~~
        Output: Iterador sobre las aristas del arbol

        Tiempo de ejecucion O(|V|)
    */
    fun obtenerLados() : Iterable<Arista> {
        var ladosArbol: LinkedList<Arista> = LinkedList<Arista>()

        for (v in g.arrVertices){
            val ver: Vertice? = v.pred
            if (ver != null) {
                var lado: Arista = Arista(ver, v)
                ladosArbol.add(lado)
            }
        }

        return ladosArbol.asIterable()
    }
    
    /* 
        Devuelve el costo del arbol minimo cobertor

        {P: true}
        {Q: true}

        Input: ~~
        OUtput: El costo del arbol minimo cobertor

        Tiempo de ejecucion O(|V|)
    */
    fun obtenerCosto() : Double {
        return costo
    }

}
