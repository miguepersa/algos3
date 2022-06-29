package ve.usb.libGrafo
import java.util.LinkedList

/* 
    Retorna el grafo inverso de un grafo G = (E,V) dado

    El grafo inverso G'= (V, E') donde E' contiene todos los lados (u,v) tal que (v,u) pertenece a E

*/
fun digrafoInverso(g: GrafoDirigido) : GrafoDirigido {
    var gInverso: GrafoDirigido = GrafoDirigido(g.obtenerNumeroDeVertices())
    var ladosG = g.iterator()

    for (i in ladosG) {
        gInverso.agregarArco(Arco(i.b, i.a))
    }

    return gInverso
}

fun merge(l: Array<AristaCosto>, inicio: Int, mid: Int, fin: Int) {
    var n1 = mid - inicio + 1
    var n2 = fin - mid

    var iz = LinkedList<AristaCosto>()
    var de = LinkedList<AristaCosto>()
    for (i in 0 until n1) {
        iz.add(l[inicio + i])
    }
    for (i in 0 until n2) {
        de.add(l[mid + 1 + i])
    }

    var i = 0
    var j = 0
    var k = inicio
    while (i < n1 && j < n2) {
        if (iz[i].costo <= de[j].costo) {
            l[k] = iz[i]
            i++
        }
        else {
            l[k] = de[j]
            j++
        }
        k++
    }

    while (i < n1) {
        l[k] = iz[i]
        i++
        k++
    }

    while (j < n2) {
        l[k] = de[j]
        j++
        k++
    }
}

fun mergeSortAristaCosto(l: Array<AristaCosto>, inicio: Int, fin: Int) {
    if (inicio >= fin) {
        return
    }    
    var mid = (inicio + fin) / 2
    mergeSortAristaCosto(l, inicio, mid)
    mergeSortAristaCosto(l, mid + 1, fin)
    merge(l, inicio, mid, fin)
}