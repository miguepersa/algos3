package ve.usb.libGrafo
import java.util.LinkedList

/*
  Determina el orden topológico de un DAG. El ordenamiento topológico
  se determina en el constructor de la clase.  
*/
public class OrdenamientoTopologico(val g: GrafoDirigido) {
    var tiempo: Int
    var arrVertices: Array<Vertice>
	var ordenTopologico: LinkedList<Int>

	init {
		tiempo = 0
		arrVertices = Array<Vertice>(g.obtenerNumeroDeVertices(), {i -> Vertice(i)})
		ordenTopologico = LinkedList<Int>()

		for (v in arrVertices) {
			if (v.color == Color.BLANCO) {
				dfsVisitTopologico(g, v.n)
			}
		}
	}

	private fun dfsVisitTopologico(g: Grafo, u: Int) {
		tiempo++
		var v = arrVertices[u]

		v.d = tiempo
		v.color = Color.GRIS

		arrVertices[u].d = tiempo
		arrVertices[u].color = Color.GRIS

		var ady = g.adyacentes(u)
		for (i in ady) {
			var p = arrVertices[i.elOtroVertice(u).n] 
			if (arrVertices[p.n].color == Color.BLANCO)
				p.pred = v 
				arrVertices[p.n].pred = v 
				dfsVisitTopologico(g, p.n)
			
		}

		tiempo++

		v.f = tiempo
		v.color = Color.NEGRO

		arrVertices[u].color = Color.NEGRO
		arrVertices[u].f = tiempo

		ordenTopologico.addFirst(u)
	}

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
		return ordenTopologico.asIterable()
    }
}
