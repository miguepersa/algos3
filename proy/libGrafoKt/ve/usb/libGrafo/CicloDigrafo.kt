package ve.usb.libGrafo
import java.util.LinkedList

/* 
   Determina la existencia o no de un ciclo en un digrafo.
   En el momento de la creación de un objeto de este tipo,
   se ejecuta una versión de  DFS que determina la existencia 
   o no de un ciclo. Es decir, el algoritmo de detección de
   ciclo e ejecuta en el constructor. 
*/
public class CicloDigrafo(val g: GrafoDirigido) {
    var dfs: DFS
    val hayCiclo: Boolean
    var ciclo: LinkedList<Int> = LinkedList<Int>()

    init {
        dfs = DFS(g)
        hayCiclo = dfs.hayLadosDeVuelta()
    }

    /* 
        Determina si hay un ciclo den el grafo dirigido g

        El grafo g tiene un ciclo si el bosque resultante de ejecutar dfs
        tiene un lado de vuelta.

        {P: true}
        {Q: true}

        Input: ~~
        Output: true -> Si existe un ciclo en el grafo
                false -> caso contrario

        Tiempo de ejecucion O(1)
    */
    fun existeUnCiclo() : Boolean {
        return hayCiclo
    }

    /* 
        Devuelve un ciclo que se encuentre en el grafo, en caso de que tenga ciclo.

        Para hallar el ciclo partimos del vertice inicial del lado de vuelta encontrado,
        de ahi adelante seguimos por los predecesores de ese vertice hasta llegar
        al vertice final de lado de vuelta.

        {P: true}
        {Q: true}

        Input: ~~
        Output: Un iterable que contiene los vertices de un ciclo del grafo

        Tiempo de ejecuion O(v.d - u.d) Siendo el lado (u, v)
    
    */
    fun cicloEncontrado() : Iterable<Int> {
        if (!this.existeUnCiclo()) {
            throw RuntimeException("CicloDigrafo.cicloEncontrado(): El grafo dado no tiene ciclo")
        }

        // buscamos un lado de vuelta
        val ldv = dfs.ladosDeVuelta()
        val lado = ldv.next() // tomamos el primer lado y la buscamos el ciclo

        var ciclo: LinkedList<Int> = LinkedList<Int>()
        val verInit: Int = lado.b.n
        var verFin: Int = lado.a.n


        for (v in dfs.caminoDesdeHasta(verInit, verFin)) {
            ciclo.addFirst(v)
        }

        ciclo.addFirst(verInit)

        return ciclo.asIterable()
    }
} 
