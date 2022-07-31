package ve.usb.libGrafo

/**  
    Implementacion de cola de prioridad 
*/
public class ColaDePrioridad() {
    var queue: MinHeap = MinHeap() // Representamos la cola de prioridad como un minHeap

    fun add(i: Int, k: Int) {
        queue.agregar(i, k)
    }

    fun decreaseKey(i: Int, n: Int) {
        queue.disminuirLlave(i, n)
    }

    fun extraerMinimo(): Int {
        return queue.extraerMinimo()
    }

    fun vacia(): Boolean = queue.heapTamano == 0

    fun imprimir() {
        println(queue.heap)
    }
}