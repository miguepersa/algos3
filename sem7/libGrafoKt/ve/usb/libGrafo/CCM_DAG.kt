package ve.usb.libGrafo

/*
 Implementación del algoritmo para encontrar los
 caminos de costo mínimo desde un vértice fuente s fijo en DAGs. 
 Si el digrafo de entrada no es DAG, entonces se lanza una RuntimeException.
 Si el vértice s no existe en el grafo se retorna un RuntimeException.
*/
public CCM_DAG(val g: GrafoDirigidoCosto, val s: Int) {

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
