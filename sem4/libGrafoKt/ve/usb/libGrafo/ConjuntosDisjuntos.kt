package ve.usb.libGrafo
import java.util.LinkedList

// Implementacion del nodo del arbol, el rep es el pred del nodo. Si el rep == n el nodo es una raiz
data class Nodo(val n: Int, var rep: Int)

/*
 Implementación de las estructuras de datos para conjuntos disjuntos.
 Los conjuntos disjuntos son representado como árboles.
 El constructor recibe como entrada en número elementos que van a conformar los cojuntos disjuntos. 
 Los elementos de los conjuntos disjuntos están identificados en el intervalo [0 .. n-1]. 
 Cuando se ejecuta el constructor, se crean n conjuntos disjuntos iniciales, es decir,
 se debe ejecutar make-set(i) para todo i en el intervalo [0 .. n-1]. 
*/
public class ConjuntosDisjuntos(val n: Int) {
    var arrConjuntosDisjuntos: Array<Nodo> = Array<Nodo>(n, {i -> Nodo(i, i)}) // se crean n arboles de un elemento cuyo es el mismo
    var numConjuntos: Int = n

    /*
        Realiza la union de dos conjuntos, la union la hace cambiando el representante de 
        cada elemento del conjunto u al representante del conjunto v.

        {P: v y u son vertices existentes en la estructura}
        {Q: numConjuntos' = numConjuntos - 1}

        Input: v -> Entero, elemento de algun conjunto
               u -> Entero, elemento de algun conjunto

        Output: true -> Si v y u pertenecen a conjuntos distintos, luego se unen los conjunos
                false -> Caso contrario, no se unen los conjuntos

        Tiempo de ejecucion O(n)
     */
    fun union(v: Int, u: Int) : Boolean {
        if (v < 0 || v >= n) {
            throw RuntimeException("ConjuntosDisjuntos.union(): El vertice ${v} no pertenece a ningun conjunto")
        }

        if (u < 0 || u >= n) {
            throw RuntimeException("ConjuntosDisjuntos.union(): El vertice ${u} no pertenece a ningun conjunto")
        }

        if (this.encontrarConjunto(v) == this.encontrarConjunto(u)) { 
            return false // pertenecen al mismo conjunto
        }

        val nuevoRep: Int = this.encontrarConjunto(v)
        val viejoRep: Int = this.encontrarConjunto(u)

        for (i in 0 until n) {
            if (arrConjuntosDisjuntos[i].rep == viejoRep) {
                arrConjuntosDisjuntos[i].rep = nuevoRep
            }
        }

        numConjuntos--

        return true // no pertenecen al mismo conjunto

    }

    /*
        Retorna el elemento representante de un elemento en un conjunto disjunto.

        {P: v es un elemento de algun conjunto}
        {Q: true}

        Input: v -> Entero, un elemento de algun conjunto
        Output: El representante del conjunto que contiene el elemento v 

        Tiempo de ejecucion O(1)
    */
    fun encontrarConjunto(v: Int) : Int {
        if (v < 0 || v >= n) {
            throw RuntimeException("ConjuntosDisjuntos.encontrarConjunto(): El vertice ${v} no pertenece a ningun conjunto")
        }

        return arrConjuntosDisjuntos[v].rep
    }

    /* 
        Retorna el número de conjuntos disjuntos actuales que tiene la estructura de conjuntos disjuntos.
    
        {P: true}
        {Q: true}

        Input: ~~
        Output: el numero de conjutnso en la estructura

        Tiempo de ejecucion O(1) 
    */

    fun numConjuntosDisjuntos(): Int = numConjuntos

    fun obtenerOrdenConjuntos(): Array<Int> {
        var listaConj: LinkedList<Int> = LinkedList<Int>()

        for (i in 0 until n) {
            if (listaConj.contains(arrConjuntosDisjuntos[i].rep)) {
                /* 
                    La lista contiene los representantes en el 
                    el orden que aparecen en el arreglo
                */   
                listaConj.addLast(i) //listaConj tiene tantos elementos como rep distintos tengamos, es decir tantos conjuntos tenga la estructur
            }
        }

        val arrConj = listaConj.toArray()

        var arrOrden: Array<Int> = Array<Int>(0, {i -> i})

        for (i in 0 until n) {
            var j = 0
            while (arrConj[j] != arrConjuntosDisjuntos[i].rep) {
                j++
            }

            arrOrden[i] = j // el elemento i pertenece al conjunto j, con j en [0, numConj)
        }

        return arrOrden
    }

}
