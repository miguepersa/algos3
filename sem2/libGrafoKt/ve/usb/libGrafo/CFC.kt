package ve.usb.libGrafo
import java.util.LinkedList

/*
  Obtiene las componentes fuertementes conexas de un grafo 
  La componentes fuertementes conexas se determinan cuando 
  se crea un nuevo objeto de esta clase, es decir, en el constructor.
*/
public class CFC(val g: GrafoDirigido) {
	var tiempo: Int = 0
	var cfc: Array<LinkedList<Int>> // contiene las cfc
	var cfcCont: Int // indica el arbol actual

	init {
		val gTopologico: OrdenamientoTopologico = OrdenamientoTopologico(g)
		val orden: Iterable<Int> = gTopologico.obtenerOrdenTopologico() // obtenermos orden topologico de g
		val gInv: GrafoDirigido = digrafoInverso(g) // obtenermos grafo inverso de g
		
		tiempo = 0
		cfc = Array<LinkedList<Int>>(g.obtenerNumeroDeVertices(), {i -> LinkedList<Int>()}) 
		cfcCont = -1

		// ejecutamos dfs tal que el ciclo principal siga los vertices en el orden
		for (v in orden) {
			if (g.arrVertices[v].color == Color.BLANCO) {
				cfcCont++
				cfc[cfcCont].add(v)
				dfsVisitCFC(gInv, v)
			}
		}
	}

	private fun dfsVisitCFC(g: GrafoDirigido, u: Int) {
		tiempo++
		var v = g.arrVertices[u]

		v.d = tiempo
		v.color = Color.GRIS

		g.arrVertices[u].d = tiempo
		g.arrVertices[u].color = Color.GRIS

		var ady: Iterable<Lado> = g.adyacentes(u)

		for (i in ady) {
			var p = g.arrVertices[i.elOtroVertice(u).n]
			if (g.arrVertices[p.n].color == Color.BLANCO) {
				cfc[cfcCont].add(p.n)
				p.pred = v 
				g.arrVertices[p.n].pred = v 
				dfsVisitCFC(g, p.n)
			}
		}

		tiempo++

		v.f = tiempo
		v.color = Color.NEGRO

		g.arrVertices[u].color = Color.NEGRO
		g.arrVertices[u].f = tiempo
	}
    
    /*
		Determina si dos vertices estan en la misma CFC

		{P: v y u son vertices validos y pertenecen al grafo}
		{Q: true}

		Input: v, u -> Enteros, representan un vertice del grafo
		Output: true -> Si u y v pertenecen a la misma CFC
				false -> Caso contrario

		Tiempo de ejecucion O(|V|)
    */
    fun estanEnLaMismaCFC(v: Int, u: Int) : Boolean {
		for (i in 0 until cfcCont) {
			if (cfc[i].contains(v) && cfc[i].contains(u)) {
				return true
			}
		}

		return false
    }

    /* 
		Devuelve la cantidad de CFC que tiene el grafo

		{P: true}
		{Q: true}

		Input: ~~
		Output: Canditad de CFCs del grado

		Tiempo de ejecucion O(1)
	*/
    fun numeroDeCFC() : Int = cfcCont + 1

    /*

	*/
    fun obtenerIdentificadorCFC(v: Int) : Int {
		if (0 < v || v >= g.obtenerNumeroDeVertices()) {
			throw RuntimeException("CFC.obtenerIdentificadorCFC: El vertice dado no pertenece al grafo")
		}

		var id: Int = 0

		while (!cfc[id].contains(v)) {
			id++
		}

		return id
    }
    
    /*
		Devuelve un iterador sobre los CFCs del grafo

		Cada CFC esta representada como un conjunto de vertices.

		{Input: true}
		{Output: true}

		Input: ~~
		Output: Un iterables sobres las CFCs del grafo

		Tiempo de ejecucion O(cfcCont)
     */
    fun  obtenerCFC() : Iterable<MutableSet<Int>> {
		var set = Array<MutableSet<Int>>(cfcCont, {i -> mutableSetOf<Int>()})

		for (i in 0 until cfcCont) {
			for (l in cfc[i]) {
				set[i].add(l)
			}
		}
		return set.asIterable()
    }

    /*
     Retorna el grafo componente asociado a las componentes fuertemente conexas. 
     El identificador de los vértices del grafo componente está asociado 
     con los orden de la CFC en el objeto iterable que se obtiene del método obtenerCFC. 
     Es decir, si por ejemplo si los vértices 5,6 y 8 de G pertenecen a la
     componentemente fuertemente conexa cero, entonces 
     obtenerIdentificadorCFC(5) = obtenerIdentificadorCFC(6) = obtenerIdentificadorCFC(8) = 0
     y se va a tener que el en grafo componente el vértice 0 
     representa a la CFC que contiene a los vértices 5,6 y 8 de G.
     */
    fun obtenerGrafoComponente() : GrafoDirigido {
		var grafoComponente: GrafoDirigido = GrafoDirigido(cfcCont)

		return grafoComponente
    }

}
