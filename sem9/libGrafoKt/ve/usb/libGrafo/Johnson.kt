package ve.usb.libGrafo

/*
 Implementación del algoritmo de Johnson para encontrar los
 caminos de costo mínimo entre todos los pares de vértices de un grafo.
 El constructor recibe como entrada un digrafo con costos en los arcos.
 */
public Johnson(val g: GrafoDirigidoCosto) {
    
    // Retorna true si hay un ciclo negativo, false en caso contrario.
    fun hayCicloNegativo() : Boolean { }
    
    // Retorna la matriz con las distancias de los caminos de costo mínimo
    // entre todos los pares de vértices. Si hay un ciclo negativo se lanza un un RuntimeException.
    fun obtenerMatrizDistancia() : Array<Array<Double>> { } 

    // Retorna la matriz con los predecesores de todos los vértices en los caminos de costo mínimo
    // entre todos los pares de vértices.
    fun obtenerMatrizPredecesores() : Array<Array<Int?>> { } 
    
    // Retorna la distancia del camino de costo mínimo desde el vértice u hasta el vértice v.
    // Si alguno de los dos vértices  no existe, se lanza un RuntimeException.
    // Si hay un ciclo negativo se lanza un un RuntimeException.
    // Si no existe un camino desde u hasta v, entonces se lanza un RuntimeException.
    fun costo(u: Int, v: Int) : Double { }

    // Retorna cierto si hay un camino desde u hasta el vértice v.
    // Si alguno de los dos vértices  no existe, se retorna un RuntimeException.
    // Si hay un ciclo negativo se lanza un un RuntimeException.
    fun existeUnCamino(u: Int, v: Int) : Boolean { }

    // Retorna los arcos del camino de costo mínimo desde u hasta v.
    // Si alguno de los dos vértices  no existe, se retorna un RuntimeException.
    // Si hay un ciclo negativo se lanza un un RuntimeException.
    // Si no existe un camino desde u hasta v, entonces se lanza una RuntimeException.
    fun obtenerCaminoDeCostoMinimo(u: Int, v: Int) : Iterable<Arco> { }
}
