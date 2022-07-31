package ve.usb.libGrafo

/* 
    Implementa un minHeap de elementos del tipo (id, llave)

    El minimo se determina con respecto a llave
*/
public class MinHeap() {
    var heap: MutableList<Int> = mutableListOf() // representacion de la cola
    var heapLlaves: MutableList<Int> = mutableListOf() // valor de la llave del elemento i
    var heapIndices: MutableList<Int> = mutableListOf() // posicion dentro de heap del elemento i
    var heapTamano: Int = 0 // cantidad de elementos de la cola

    private fun padre(i: Int): Int = (i - 1) / 2
    private fun hijoIzq(i: Int): Int = (2 * i) + 1
    private fun hijoDer(i: Int): Int = (2 * i) + 2

    /* 
        Devuelve el tama;o del minHeap

        Tiempo de ejecucion O(1)
    */
    fun tamano(): Int = heapTamano

    /* 
        Agrega un elemento con identificador id y con
        una llave

        Tiemo de ejecucion O(lg |heapTamano|)
    */
    fun agregar(id: Int, llave: Int) {
        heap.add(id)
        heapLlaves.add(Int.MAX_VALUE)
        heapIndices.add(heapTamano)
        heapTamano++
        this.disminuirLlave(heap[heapTamano - 1], llave)
    } 

    fun disminuirLlave(id: Int, llave: Int) {
        var ind = heapIndices[id]
        heapLlaves[ind] = llave

        while (ind > 0 && heapLlaves[padre(ind)] > heapLlaves[ind]) {
            // swap(parent(ind), ind) en el arreglo de indices
            val auxInd: Int = heapIndices[heap[ind]]
            heapIndices[heap[ind]] = heapIndices[heap[padre(ind)]]
            heapIndices[heap[padre(ind)]] = auxInd

            //swap(parent(ind), ind) en el arreglo del heap
            val auxHeap: Int = heap[ind]
            heap[ind] = heap[padre(ind)]
            heap[padre(ind)] = auxHeap

            //swap(parent(ind), ind) en el arreglo de llaves
            val auxLlave: Int = heapLlaves[ind]
            heapLlaves[ind] = heapLlaves[padre(ind)]
            heapLlaves[padre(ind)] = auxLlave

            ind = padre(ind)             
        }
    }

    fun extraerMinimo(): Int {
        val minimo: Int = heap[0]

        //swap(heap[0], heap[heapTamano - 1]) en el arreglo de indices
        val auxInd: Int = heapIndices[heap[0]]
        heapIndices[heap[0]] = heapIndices[heap[heapTamano - 1]]
        heapIndices[heap[heapTamano - 1]] = auxInd

        heap[0] = heap[heapTamano - 1]
        heapLlaves[0] = heapLlaves[heapTamano - 1]
        heapTamano--

        minHeapify(0)

        return minimo
    }

    /* 
        Se devuelve la propiedad de minHeap al heap

        Tiempo de ejecucion O(lg heapTamano)
    */
    private fun minHeapify(ind: Int) {
        val i: Int = hijoIzq(ind)
        val d: Int = hijoDer(ind)
        var menor: Int = ind 

        if (i < heapTamano && heapLlaves[i] < heapLlaves[ind]) menor = i 

        if (d < heapTamano && heapLlaves[d] < heapLlaves[menor]) menor = d

        if (menor != ind) {
            // swap(heap[ind], heap[menor]) en el arreglo de indices
            val auxInd: Int = heapIndices[heap[ind]]
            heapIndices[heap[ind]] = heapIndices[heap[menor]]
            heapIndices[heap[menor]] = auxInd

            // swap(ind, menor) en el arreglo del heap
            val auxHeap: Int = heap[ind]
            heap[ind] = heap[menor]
            heap[menor] = auxHeap

            // swap(ind, menor) en el arreglo de llaves
            val auxLlave: Int = heapLlaves[ind]
            heapLlaves[ind] = heapLlaves[menor]
            heapLlaves[menor] = auxLlave

            minHeapify(menor)
        } 
    }
}