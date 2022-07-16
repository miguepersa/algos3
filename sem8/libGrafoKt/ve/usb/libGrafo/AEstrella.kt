package ve.usb.libGrafo

/*
 Implementación del algoritmo de A* para encontrar un camino
 desde un vértice fuente s fijo hasta alguno de los vértices objetivos. 
 La implementación debe usar como cola de prioridad un heap.
 
 Los parármetros de entradas son los siguientes:
 g: Digrafo con los costos en los lados. 
 s: Vértice de partida.
 objs: Conjunto de vértices objetivos. 
 hHat: función estimativa de h llamada h sombrero. Recibe como entrada un vértice v y retorna 
    el costo estimado desde ese vértice v, hasta cualquiera de los vértices 
    de la meta en objs.

 Como precondiciones se tiene que:
    * Si el grafo de entrada tiene un lado con costo negativo,  entonces se retorna una RuntimeException.
    * Si el vértice s no pertenece al grafo, entonces se retorna una RuntimeException.
    * Si alguno de los vértices en objs no pertenece al grafo, entonces se retorna una RuntimeException.
    * Al menos uno de los vértices de objs debe ser alcanzable desde s. Esta precondición no se chequea por razones de eficiencia.
*/
public class AEstrella(val g: GrafoDirigidoCosto, 
		       val s: Int,
		       val objs: Set<Int>,
		       val hHat: (Int) -> Double) {
    
    // Retorna el vértice meta del conjunto objs que fue alcanzado por el algoritmo.
    fun objetivoAlcanzado() : Int { }

    // Retorna el costo del camino desde s hasta el vértice objetivo alcanzado. 
    fun costo() : Double {}

    // Retorna los arcos del camino desde s hasta el vértice objetivo alcanzado. 
    fun obtenerCamino() : Iterable<ArcoCosto> { }
}
