package ve.usb.libGrafo

/**  
    Implementacion de cola de prioridad como un heap tree
    Recibe un arreglo del cual se obtendra la cola de prioridad    
*/
public class ColaDePrioridad(val cap: Int) {
    var heapSize: Int = 0
    var max: Int = cap
    var minHeap: Array<Vertice> = Array<Vertice>(cap, {i -> Vertice(i)})

    fun add(v: Vertice) {
        if (heapSize == max) {
            throw RuntimeException("ColaDePrioridad.add: La cola esta llena")
        }

        // Añadimos la clave al final
        heapSize++
        var i: Int = heapSize - 1
        minHeap[i] = v

        // Devolvemos la prioridad de min heap
        while (i != 0 && minHeap[parent(i)] > minHeap[i]) {
            swap(minHeap, i, parent(i))
            i = parent(i)
        }
    }

    fun decreaseKey(i: Int, n: Double) {
        var j: Int = 0
        
        while (minHeap[j].n != i) {
            j++
        }

        minHeap[j].key = n
        var index: Int = j
                
        while (index != 0 && minHeap[parent(index)] > minHeap[index]) {
            swap(minHeap, index, parent(index))
            index = parent(index)
        }
    }

    fun extrearMinimo(): Vertice {
        if (heapSize <= 0) {
            throw  RuntimeException("ColaDePrioridad.extraerMinimo(): La cola esta vacia")
        }

        if (heapSize == 1) {
            heapSize--
            return minHeap[0]
        }

        var root = minHeap[0]
        minHeap[0] = minHeap[heapSize - 1]
        heapSize--
        minHeapify(0)

        return root
    }

    fun minHeapify(i: Int) {
        var l: Int = left(i)
        var r: Int = right(i)
        var smallest: Int = i

        if (l < heapSize && minHeap[l] < minHeap[i]) smallest = l
        if (r < heapSize && minHeap[r] < minHeap[smallest]) smallest = r 


        if (smallest != i) {
            swap(minHeap, i, smallest)
            minHeapify(smallest)
        }
    }

    fun vacia(): Boolean = heapSize == 0

    fun parent(i: Int): Int = (i - 1) / 2

    fun left(i: Int): Int = (2 * i) + 1
    
    fun right(i: Int): Int = (2 * i) + 2

    fun swap(arr: Array<Vertice>, a: Int, b: Int) { 
        val temp: Vertice = arr[a]
        arr[a] = arr[b]
        arr[b] = temp
    }

}