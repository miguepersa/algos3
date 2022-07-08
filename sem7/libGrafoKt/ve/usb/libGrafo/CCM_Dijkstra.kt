package ve.usb.libGrafo

/*
 Implementación del algoritmo de Dijkstra para encontrar los
 caminos de costo mínimo desde un vértice fuente s fijo. La
 implementación debe usar como cola de prioridad un min-heap o un fibonacci heap.
 Si el grafo de entrada tiene un lado con costo negativo,  entonces se retorna una RuntimeException.
 Si el vértice s no pertenece al grafo, entonces se retorna una RuntimeException.
*/
public CCM_Dijkstra(val g: GrafoDirigidoCosto, val s: Int) {

    // Retorna cierto si hay un camino desde s hasta el vértice v.
    // Si el vértice v no existe, se retorna un RuntimeException.
    fun existeUnCamino(v: Int) : Boolean { }

    // Retorna la distancia del camino de costo mínimo desde s hasta el vértice v. 
    // Si el vértice v no existe, se retorna un RuntimeException.
    fun costo(v: Int) : Double { }

    // Retorna los arcos del camino de costo mínimo hasta v.
    // Si el vértice v no existe, se retorna un RuntimeException.
    fun obtenerCaminoDeCostoMinimo(v: Int) : Iterable<ArcoCosto> { }
}
