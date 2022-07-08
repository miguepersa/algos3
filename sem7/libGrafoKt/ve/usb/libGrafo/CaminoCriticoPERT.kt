package ve.usb.libGrafo

/*
 Implementación del algoritmo para encontrar el
 caminos crítico de un PERT, representado como un DAG con costos en los lados.
 Si el digrafo de entrada no es DAG, entonces se lanza una RuntimeException.
*/
public CaminoCriticoPERT(val g: GrafoDirigidoCosto, val s: Int) {

    // Retorna el costo del camino crítico.
    fun costo() : Double { }

    // Retorna los arcos del camino crítico.
    fun obtenerCaminoCritico() : Iterable<ArcoCosto> { }
}
