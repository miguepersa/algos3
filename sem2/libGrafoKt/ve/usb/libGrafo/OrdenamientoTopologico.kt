package ve.usb.libGrafo
import java.util.LinkedList

/*
  Determina el orden topológico de un DAG. El ordenamiento topológico
  se determina en el constructor de la clase.  
*/
public class OrdenamientoTopologico(val g: GrafoDirigido) {
    var tiempo: Int
    var arrVertices: Array<Vertice> = Array<Vertice>(g.obtenerNumeroDeVertices(), {i -> Vertice(i)})
	var ordenTopologico: LinkedList<Int> = LinkedList<Int>()
    /* 
		Determina si el grafo dirigido es un DAG, esto es, determina si el grafo tiene un ciclo.

		Para determinar si tiene o no un ciclo, ejecutamos DFS, si el bosque tiene un lado de vuelta,
		el grafo tiene un ciclo

		{P: true}
		{Q: true}

		Input: ~~
		Output: true -> Si el grafo es un DAG, es decir, si no tiene un ciclo
				false -> Caso contrario

		Tiempo de ejecucion O(|V| + 2|E|)
    */
    fun esDAG() : Boolean { 
        var gDFS = DFS(g)

      return !gDFS.hayLadosDeVuelta()
    }

    // Retorna el ordenamiento topológico del grafo g. Si el grafo G no es DAG,
    // entonces se lanza una RuntineException()
    fun obtenerOrdenTopologico() : Iterable<Int> { 
        if (this.esDAG()) {
            throw RuntimeException("Ordenamiento Topologico: El grafo no es un DAG")
        }

		tiempo = 0
		for (v in arrVertices) {
			if (v.color == Color.BLANCO) {
				dfsVisitTopologico(g, v.n)
			}
		}

		return ordenTopologico.asIterable()
    }

	private fun dfsVisitTopologico(g: GrafoDirigido, u: Int) {
		tiempo++
		var v = arrVertices[u]

		v.d = tiempo
		v.color = Color.GRIS

		arrVertices[u].d = tiempo
		arrVertices[u].color = Color.GRIS

		var ady = g.adyacentes(u)
		for (i in ady) {
			var p = arrVertices[i.elOtroVertice(u).n] {
				p.pred = v 
				arrVertices[p.n].pred = v 
				dfsVisitTopologico(g, p.n)
			}
		}

		tiempo++

		v.f = tiempo
		v.color = color.NEGRO

		arrVerties[u].color = Color.NEGRO
		arrVertices[u].f = tiempo

		ordenTopologico.addFirst(u.n)
		
	}
}
