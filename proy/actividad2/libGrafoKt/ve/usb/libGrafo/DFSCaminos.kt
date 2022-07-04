package ve.usb.libGrafo
import java.util.LinkedList

/* 
    Dado un grafo dirigido y un vertice raiz

    Aplicar una busqueda como DFS para obtener
    todos los caminos desde ese vertice hasta los demas

    Los caminos se obtienen al momento de creacion de una clase
*/
data class VerticeDFSCaminos(val n: Int) {
    var pred: VerticeDFSCaminos? = null
    var color: Color = Color.BLANCO
}

public class DFSCaminos(val g: GrafoDirigido, val inicio: Int) {
    var listaVerticesDFSCaminos: MutableList<VerticeDFSCaminos>
    var listaCaminos: MutableList<LinkedList<Int>>

    init {
        listaCaminos = mutableListOf()
        listaVerticesDFSCaminos = mutableListOf()
        for (i in 0 until g.obtenerNumeroDeVertices()) { // Inicializamos los vertices, todos de color BLANCO y y con predecesor nulo
            listaVerticesDFSCaminos.add(VerticeDFSCaminos(i))
        }

        dfsVisitCaminos(g, inicio)
    }

    private fun dfsVisitCaminos(g: GrafoDirigido, ver: Int) {
        listaVerticesDFSCaminos[ver].color = Color.GRIS

        if (g.gradoExterior(ver) == 0) { // Si su grado exterior es 0, no tiene ady
            // Obtenemos el camino desde inicio, ver
            println(ver)
            listaCaminos.add(obtenerCamino(inicio, ver))
            listaVerticesDFSCaminos[ver].color = Color.NEGRO
        } else {
            for (u in g.adyacentes(ver)) {
                if (listaVerticesDFSCaminos[u.b.n].color == Color.BLANCO) {
                    listaVerticesDFSCaminos[u.b.n].pred = listaVerticesDFSCaminos[ver]
                    dfsVisitCaminos(g, u.b.n)
                }
            }
            
            listaVerticesDFSCaminos[ver].color = Color.BLANCO
        }
        
    }

    private fun obtenerCamino(u: Int, v: Int): LinkedList<Int> {
        var camino: LinkedList<Int> = LinkedList<Int>()
        var ver: Int = v

        while (listaVerticesDFSCaminos[ver].n != u) {
            camino.addFirst(ver)
            ver = listaVerticesDFSCaminos[ver].pred!!.n
        }

        camino.addFirst(u)

        return camino
    }

    fun obtenerTodosLosCaminos(): Iterable<LinkedList<Int>> = listaCaminos.asIterable()

    fun obtenerCaminoMasLargo(): LinkedList<Int> {
        val caminos: Iterable<LinkedList<Int>> = this.obtenerTodosLosCaminos()
        var caminoMasLargo: LinkedList<Int> = caminos.last()

        for (c in caminos) {
            if (caminoMasLargo.count() < c.count()) caminoMasLargo = c
        }

        return caminoMasLargo
    }
}