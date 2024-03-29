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
		cfc = Array<LinkedList<Int>>(g.obtenerNumeroDeVertices(), {LinkedList<Int>()}) 
		cfcCont = 0

		// ejecutamos dfs tal que el ciclo principal siga los vertices en el orden
		for (v in orden) {
			if (gInv.arrVertices[v].color == Color.BLANCO) {
				cfc[cfcCont].add(v)
				dfsVisitCFC(gInv, v)
				cfcCont++
			}
		}
	}

	private fun dfsVisitCFC(gi: GrafoDirigido, u: Int) {
		tiempo++
		var v = gi.arrVertices[u]

		v.d = tiempo
		v.color = Color.GRIS

		gi.arrVertices[u].d = tiempo
		gi.arrVertices[u].color = Color.GRIS

		var ady: Iterable<Lado> = gi.adyacentes(u)

		for (i in ady) {
			var p = gi.arrVertices[i.elOtroVertice(u).n]
			if (gi.arrVertices[p.n].color == Color.BLANCO) {
				cfc[cfcCont].add(p.n)
				p.pred = v 
				gi.arrVertices[p.n].pred = v 
				dfsVisitCFC(gi, p.n)
			}
		}

		tiempo++

		v.f = tiempo
		v.color = Color.NEGRO

		gi.arrVertices[u].color = Color.NEGRO
		gi.arrVertices[u].f = tiempo
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
		Output: Canditad de CFCs del grafo

		Tiempo de ejecucion O(1)
	*/
    fun numeroDeCFC() : Int = cfcCont + 1

    /*

	*/
    fun obtenerIdentificadorCFC(v: Int) : Int {
		if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
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
    fun obtenerCFC() : Iterable<MutableSet<Int>> {
		var set = Array<MutableSet<Int>>(cfcCont, {mutableSetOf<Int>()})

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
		for (i in cfc) {
			for (j in i) {
				var adyacentes = g.adyacentes(j)
				for (k in adyacentes) {
					var u = grafoComponente.arrVertices[this.obtenerIdentificadorCFC(j)]
					var v = grafoComponente.arrVertices[this.obtenerIdentificadorCFC(k.elOtroVertice(j).n)]
					if (u != v) {
						grafoComponente.agregarArco(Arco(u,v))
					}
				}
			}
		}
		return grafoComponente
    }
}
