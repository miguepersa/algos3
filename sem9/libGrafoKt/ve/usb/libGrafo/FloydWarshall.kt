package ve.usb.libGrafo

/*
 Implementación del algoritmo de Floyd-Warshall para encontrar los
 caminos de costo mínimo todos los pares de vértices de un grafo.
 El constructor recibe como entrada una matriz de costos, que corresponde
 a la matriz de costos asociado a un digrafo con pesos en los lados.
 La matriz de costos es construida con la función dada en clase.
 Se asume que la matriz de costos corresponde a un digrafo sin ciclos negativos.
 Si el digrafo tiene ciclo negativo, el resultado del algoritmo no es especificado.
 La matriz de entrada es cuadrada de dimensiones nxn, donde n es el número de 
 vértices del grafo. Si la matriz de entrada no es cuadrada, entoces se lanza una RuntimeException.
 */
public FloydWarshall(val W : Array<Array<Double>>) {

    // Retorna la matriz con las distancias de los caminos de costo mínimo
    // entre todos los pares de vértices.
    fun obtenerMatrizDistancia() : Array<Array<Double>> { } 

    // Retorna la matriz con los predecesores de todos los vértices en los caminos de costo mínimo
    // entre todos los pares de vértices.
    fun obtenerMatrizPredecesores() : Array<Array<Int?>> { } 
    
    // Retorna la distancia del camino de costo mínimo desde el vértice u hasta el vértice v.
    // Si alguno de los dos vértices  no existe, se lanza una RuntimeException.
    // Si no existe un camino desde u hasta v, entonces se lanza una RuntimeException.
    fun costo(u: Int, v: Int) : Double { }

    // Retorna cierto si hay un camino desde u hasta el vértice v.
    // Si alguno de los dos vértices no existe, se retorna un RuntimeException.
    fun existeUnCamino(u: Int, v: Int) : Boolean { }

    // Retorna los arcos del camino de costo mínimo desde u hasta v.
    // Si alguno de los dos vértices  no existe, se retorna un RuntimeException.
    // Si no existe un camino desde u hasta v, entonces se lanza una RuntimeException.
    fun obtenerCaminoDeCostoMinimo(u: Int, v: Int) : Iterable<Arco> { }
}
