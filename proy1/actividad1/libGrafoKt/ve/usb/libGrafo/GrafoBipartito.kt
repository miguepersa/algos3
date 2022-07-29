package ve.usb.libGrafo

/* 
    Determina si un grafo no dirigido es un grafo bipartito.

    Para esto usamos el algoritmo 2-coloreable visto en clase.
    El algoritmo se ejecuta al momento de creacion de un objeto
    de la clase.

    El tiempo de ejecucion es O(|E| + |V|)
*/

public class GrafoBipartito(val g: GrafoNoDirigido) {
    // Atributos de la clas
    var esBipartito: Boolean
    var arrVerticesBipartito: Array<VerticeBipartito>

    init {
        esBipartito = true
        
        // Todos los vertices tienen color blanco y kcolor 1
        arrVerticesBipartito = Array<VerticeBipartito>(g.obtenerNumeroDeVertices(), {i -> VerticeBipartito(i)})

        for (i in 0 until g.obtenerNumeroDeVertices()) {
            if (arrVerticesBipartito[i].color == Color.BLANCO) {
                dfsVisitColor(g, i)
            }
        }

        // Verificamos que para cada lado, los vertices tengan distinto color 
        for (l in g) {
            val a: Int = l.a.n
            val b: Int = l.b.n 

            if (arrVerticesBipartito[a].kcolor == arrVerticesBipartito[b].kcolor) {
                esBipartito = false
                break
            }
        }
    }

    /* 
        Pintamos los vertices del grafo usando DFS
    */
    private fun dfsVisitColor(g: GrafoNoDirigido, v: Int) {
        arrVerticesBipartito[v].color = Color.GRIS

        for (u in g.adyacentes(v)) {
            if (arrVerticesBipartito[u.b.n].color == Color.BLANCO) {

                if (arrVerticesBipartito[v].kcolor == 1) {
                    arrVerticesBipartito[u.b.n].kcolor = 2
                } else {
                    arrVerticesBipartito[u.b.n].kcolor = 1
                }

                dfsVisitColor(g, u.b.n)
            }
        }

        arrVerticesBipartito[v].color = Color.NEGRO
    }

    /* 
        Devuelve true si el grafo es bipartito, false caso contrario
        Esto se determina cuando se crea un objeto de clase

        {P: true}
        {Q: Devuelve true si el grafo es 2-coloreable, false caso contrario}
        
        Input: ~~
        Output: Boolean que determina si el grafo es o no bipartiro
        
        Tiempo de ejecucion O(1)
    */
    fun esGrafoBipartito(): Boolean = esBipartito

    /* 
        Determina si dos vertices estan en el mismo conjunto de vertices
        en el grafo bipartito.

        Dos vertices estan en el mismo conjunto de vertices si y solo si
        u.kcolor == v.kcolor

        {P: u y v son vertices que pertenecen al grafo}
        {Q: Devuelve true si ambos vertices estan en el mismo conjunto de vertices,
            false caso contrario y que el grafo sea bipartito}

        Input: Enteros que representan dos vertices del grafo
        Output: Boolean que determina si ambos vertices estan en el mismo conjunto de vertices

        Tiempo de ejecucion O(1)
    */
    fun estanEnElMismoConjunto(u: Int, v: Int): Boolean {
        if (u < 0 || u >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("GrafoBipartito.estanEnElMismoConjunto(): El vertice $u no pertenece al grafo")
        }

        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("GrafoBipartito.estanEnElMismoConjunto(): El vertice $v no pertenece al grafo")
        }

        if (!this.esGrafoBipartito()) {
            throw RuntimeException("GrafoBipartito.estanEnElMismoConjunto(): El grafo no es biparttito")
        }

        return arrVerticesBipartito[u].kcolor == arrVerticesBipartito[v].kcolor
    }
}