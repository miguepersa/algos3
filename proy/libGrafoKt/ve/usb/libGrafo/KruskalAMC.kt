package ve.usb.libGrafo
import java.util.LinkedList

/*
 Determina el árbol mínimo cobertor de un grafo no dirigido usando el algoritmo de Kruskal.
 Si el grafo de entrada no es conexo, entonces se lanza un RuntineException.
 El algoritmo de Kruskal debe estar basado en la clase ConjuntosDisjuntos de esta librería.
 */
public class KruskalAMC(val g: GrafoNoDirigidoCosto) {
    var amc: LinkedList<AristaCosto>
    init {
        amc = LinkedList<AristaCosto>()
        var set = ConjuntosDisjuntos(g.nVertices)
        var listaLados = Array<AristaCosto>(g.obtenerNumeroDeLados(), {i -> g.listaLados[i]})
        mergeSortAristaCosto(listaLados, 0, g.nLados - 1)
        for (i in listaLados) {
            if (set.encontrarConjunto(i.x.n) != set.encontrarConjunto(i.y.n)) {
                amc.add(i)
                set.union(i.x.n, i.y.n)
            }
        }
    }

    // Retorna un objeto iterable que contiene los lados del árbol mínimo cobertor.
    fun obtenerLados() : Iterable<AristaCosto> {
        return amc.asIterable()
    }
    
    // Retorna el costo del árbol mínimo cobertor. 
    fun obtenerCosto() : Double {
        var costo: Double = 0.0
        for (i in amc) {
            costo = costo + i.costo
        }
        return costo
    }
}
