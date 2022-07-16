package ve.usb.libGrafo

/**  
    Implementacion de cola de prioridad 
*/
public class ColaDePrioridad() {
    var queue: MinHeap = MinHeap() // Representamos la cola de prioridad como un minHeap

    fun add(v: Vertice) {
        queue.agregar(v.n, v.key)
    }

    fun decreaseKey(i: Int, n: Double) {
        queue.disminuirLlave(i, n)
    }

    fun extraerMinimo(): Int {
        return queue.extraerMinimo()
    }

    fun vacia(): Boolean = queue.heapTamano == 0
}