package ve.usb.libGrafo

/* 
   Determina la existencia o no de un ciclo en un digrafo.
   En el momento de la creación de un objeto de este tipo,
   se ejecuta una versión de  DFS que determina la existencia 
   o no de un ciclo. Es decir, el algoritmo de detección de
   ciclo e ejecuta en el constructor. 
*/
public class CicloDigrafo(val g: GrafoDirigido) {

    // Retorna true si el digrafo G tiene un ciclo, false en caso contrario
    fun existeUnCiclo() : Boolean {

    }

    // Si el grafo tiene un ciclo, entonces retorna la secuencia de vértices del ciclo,
    // y en caso contrario retorna una RuntineException.
    // El primer y último vértice es el mismo en el objeto iterable, por ejemplo
    // puede contener una secuencia como <2,5,4,3,1,2>
    fun cicloEncontrado() : Iterable<Int> {

    }
} 
