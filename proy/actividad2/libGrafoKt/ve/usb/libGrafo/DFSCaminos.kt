package ve.usb.libGrafo
import java.util.LinkedList

/* 
    Tipo de vertice usado en DFSCaminos
*/
data class VerticeDFSCaminos(val n: Int) {
    var pred: VerticeDFSCaminos? = null
    var color: Color = Color.BLANCO
}

/* 
    Dado un grafo dirigido y un vertice raiz

    Aplicar una busqueda como DFS para obtener todos los caminos 
    desde el vertice raiz todos los vertice sumideros del grafo

    {P: g es un grafo dirigido y el vertice inicio pertenece al grafo}
    {Q: Se determinan todos los caminos desde el vertice raiz
        hasta todos los vertices sumidos del grafo}

    Input: g -> GrafoDirigido
            inicio -> Entero, vertice que pertenece al grafo
                      es el vertice raiz de todos los caminos
    
    Tiempo de ejecucion O(|E|)
*/
public class DFSCaminos(val g: GrafoDirigido, val inicio: Int) {
    var listaVerticesDFSCaminos: MutableList<VerticeDFSCaminos>
    var listaCaminos: MutableList<LinkedList<Int>>

    init {
        listaCaminos = mutableListOf()
        listaVerticesDFSCaminos = mutableListOf()
        for (i in 0 until g.obtenerNumeroDeVertices()) { // Inicializamos los vertices, todos de color BLANCO y y con predecesor nulo
            listaVerticesDFSCaminos.add(VerticeDFSCaminos(i))
        }

        listaVerticesDFSCaminos[inicio].color = Color.NEGRO
        dfsVisitCaminos(g, inicio)
    }

    private fun dfsVisitCaminos(g: GrafoDirigido, ver: Int) {
        listaVerticesDFSCaminos[ver].color = Color.GRIS

        for (u in g.adyacentes(ver)) {
            val currVer: Int = u.b.n
            if (listaVerticesDFSCaminos[currVer].color == Color.BLANCO) {
                listaVerticesDFSCaminos[currVer].pred = listaVerticesDFSCaminos[ver]
                dfsVisitCaminos(g, currVer)
            }
        }
            
        // cuando ver no tiene adyacentes a;adimos el camino desde  el
        // vertice inicial hasta ver
        listaCaminos.add(caminoHasta(ver))
        listaVerticesDFSCaminos[ver].color = Color.BLANCO
        
    }
    
    /* 
        Obtenemos el camino desde el vertice inicio hasta el vertice u

        {P: u es un vertice que pertenece al grafo y
            existe un camino desde inicio hasta u}
        {Q: un camino simple desde el vertice inicio hasta u}

        Input: u -> Entero que es un vertice del grafo
        Output: Una lista enlazada que representa un camino simple
                desde el vertice inicio hasta u

        Tiempo de ejecucion O(|E|)
    */
    private fun caminoHasta(u: Int): LinkedList<Int> {
        var camino: LinkedList<Int> = LinkedList<Int>()
        var verAux: Int = u

        while (verAux != inicio) {
            camino.addFirst(verAux)
            verAux = listaVerticesDFSCaminos[verAux].pred!!.n
        }

        camino.addFirst(inicio)

        return camino
    }

    /* 
        Se devuelven todos los caminos que se obtuvieron desde el vertice raiz
        hasta los demas vertices sumideros del grafo

        Se devuelve un iterable de caminos y cada caminos esta representado
        como una lista enlazada

        {P: true}
        {Q: Se devuelven todos los caminos desde la raiz hasta los vertices
            sumideros del grafo}
    
        Tiempo de ejecucion O(1)
    */
    fun obtenerTodosLosCaminos(): Iterable<LinkedList<Int>> = listaCaminos.asIterable()


    /* 
        Se recorren todos los caminos obtenidos en la creacion de la clase
        y se devuelve el camino mas largo

        {P: true}
        {Q: se devuelve el camino con mas vertices desde la raiz}

        Tiempo de ejecucion O(cantidad de caminos obtenidos)
    */
    fun obtenerCaminoMasLargo(): LinkedList<Int> {
        val caminos: Iterable<LinkedList<Int>> = this.obtenerTodosLosCaminos()
        var caminoMasLargo: LinkedList<Int> = caminos.last()

        for (c in caminos) {
            if (caminoMasLargo.count() < c.count()) caminoMasLargo = c
        }

        return caminoMasLargo
    }
}