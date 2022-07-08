package ve.usb.libGrafo

/*
 Implementación del Algoritmo de Bellman-Ford para encontrar los
 caminos de costo mínimo desde un vértice fuente s fijo.
 Si el vértice s no existe en el grafo se retorna un RuntimeException.
 */
public CCM_BellmanFord(val g: GrafoDirigidoCosto, val s: Int) {

    // Retorna cierto si hay un ciclo negativo en el camino hasta los vértices alcanzables desde s
    fun tieneCicloNegativo() : Boolean { }

    // Retorna los arcos del ciclo negativo con la forma <u, v>, <v, w>, ... ,<y, x>, <x, u>  
    fun obtenerCicloNegativo() : Iterable<ArcoCosto> { }

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
