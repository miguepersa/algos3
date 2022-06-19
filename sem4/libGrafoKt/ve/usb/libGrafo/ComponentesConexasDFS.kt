package ve.usb.libGrafo

/*
  Determina las componentes conexas de un grafo no dirigido usando DFS. 
  La componentes conexas se determinan cuando 
  se crea un nuevo objeto de esta clase.
*/
public class ComponentesConexasDFS(val g: GrafoNoDirigido) {

    /*
     Retorna true si los dos vertices están en la misma componente conexa y
     falso en caso contrario. Si el algunos de los dos vértices de
     entrada, no pertenece al grafo, entonces se lanza un RuntineException
     */
    fun estanMismaComponente(v: Int, u: Int) : Boolean {
	
    }

    // Indica el número de componentes conexas
    fun nCC() : Int {
	
    }

    /*
     Retorna el identificador de la componente conexa donde está contenido 
     el vértice v. El identificador es un número en el intervalo [0 , nCC()-1].
     Si el vértice v no pertenece al grafo g se lanza una RuntimeException
     */
    fun obtenerComponente(v: Int) : Int {
	
    }

    /* Retorna el número de vértices que conforman una componente conexa dada.
     Se recibe como entrada el identificado de la componente conexa.
     El identificador es un número en el intervalo [0 , nCC()-1].
     Si el identificador no pertenece a ninguna componente conexa, entonces se lanza una RuntimeException
     */
    fun numVerticesDeLaComponente(compID: Int) : Int {

    }

}
