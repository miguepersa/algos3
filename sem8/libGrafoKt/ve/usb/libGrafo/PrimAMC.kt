package ve.usb.libGrafo
import java.util.LinkedList

/*
    Determina el árbol mínimo cobertor de un grafo no dirigido usando el algoritmo de Prim.
    Si el grafo de entrada no es conexo, entonces se lanza un RuntineException.
*/
public class PrimAMC(val g: GrafoNoDirigidoCosto) {
    var arrVertices: Array<Vertice>
    var costo: Double
    
    init {  // ejecutamos el algoritmo de prim desde el vertice 0
        costo = 0.0
        val compConexas: ComponentesConexasCostoDFS = ComponentesConexasCostoDFS(g)
        if (compConexas.nCC() != 1) {
            throw RuntimeException("PrimAMC.init: El grafo no es conexo")
        }

        var inQueue: Array<Boolean> = Array<Boolean>(g.obtenerNumeroDeVertices(), {true})

        arrVertices = Array<Vertice>(g.obtenerNumeroDeVertices(), {i -> Vertice(i)})
        arrVertices[0].key = 0.0

        var q: ColaDePrioridad = ColaDePrioridad()

        for (i in 0 until g.obtenerNumeroDeVertices()) {
            q.add(i, arrVertices[i].key)
        }

        while (!q.vacia()) {
            var u: Int = q.extraerMinimo()
            costo += arrVertices[u].key
            inQueue[u] = false
            for (v in g.adyacentes(u)) {
                if (inQueue[v.b.n] && v.obtenerCosto() < arrVertices[v.b.n].key) {
                    arrVertices[v.b.n].pred = arrVertices[u]
                    arrVertices[v.b.n].key = v.obtenerCosto()
                    q.decreaseKey(v.b.n, v.obtenerCosto())
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

        for (v in arrVertices){
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
