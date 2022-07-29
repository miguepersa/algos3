package ve.usb.libGrafo

import java.io.File

/*
 Esta clase recibe como entrada un archivo que contiene un grafo 
 dirigido, en donde los vértices están identificados con un String, en 
 lugar de un entero. Se crea un objeto que tiene una estructura de datos
 que permite asociar cada nombre de vértice, con un índice que es un número entero. 
 Si |V| = n, entonces a cada vértice se le asigna un índice que es número entero
 en el intervalo [0, n-1]. También se crea una estructura que dado un índice de un 
 vértice, permite obtener el nombre del vértice. Por último, cuando 
 se llama al constructor, Se crea un grafo dirigido en el que los
 vértices están identificados con los índices de los vértices del grafo. 
*/
public class TablaGrafoDirigido(val nombreArchivo: String) {
  	var tabla: HashMap<String, Int>
	val nVertices: Int
	val fileContent: List<String>
  	var arrVertices: List<String>

    init {
        fileContent = File(nombreArchivo).readLines()
		nVertices = fileContent[0].toInt()
		arrVertices = fileContent[2].split("\t")
		tabla = HashMap<String, Int>()
		for (i in 0..nVertices-1) {
			tabla.put(arrVertices[i],i)
		}
    }
    
    /*
     Retorna True si el índice de un vértice pertenece a un grafo dirigido,
     de lo contrario retorna False. 
     Si el índice no pertenece al grafo dirigido, entonces se lanza una RuntimeException.
     El tiempo de esta función es O(1).
     */
    fun contieneVertice(v: Int) : Boolean = v >= 0 && v < nVertices

    /*
     Dado el nombre de un vértice, retorna el índice del vertice. Si no existe ningún vértice
     con ese nombre, entonces se lanza una RuntimeException.
     El tiempo de esta función es O(1).
     */
    fun indiceVertice(nombre: String) : Int {
		if (tabla[nombre] == null) {
			throw RuntimeException("No existe vertice con $nombre de identificador")
		}

		var n: Int = tabla[nombre]!!
		return n
	}

    /*
     Dado el índice de un vértice, retorna el nombre del vértice. Si el entero de la entrada
     no corresponde a ningún índice que corresponde a un vértice del grafo dirigido,
     entonces, se lanza una RuntimeException. 
     El tiempo de esta función es O(1).
     */
    fun nombreVertice(v: Int) : String {
		if (!this.contieneVertice(v)) {
			throw RuntimeException("No existe vertice con $v de indice")
		}
		return arrVertices[v]
	}

    /*
     Retorna el grafo dirigido asociado al grafo con vértices con nombres, indicado
     en el archivo de entrada. 
     El tiempo de esta función es O(1).
     */
    fun obtenerGrafoDirigido() : GrafoDirigido {
		var g = GrafoDirigido(nVertices)
		for (i in 3 until fileContent.size) {
			var vertices = fileContent[i].split("\t")	
			var v1 = Vertice(this.indiceVertice(vertices[0]))
			v1.name = vertices[0]
			var v2 = Vertice(this.indiceVertice(vertices[1]))
			v2.name = vertices[1]
			var arco = Arco(v1, v2)
			g.agregarArco(arco)
		}

		return g
	}
    
}
