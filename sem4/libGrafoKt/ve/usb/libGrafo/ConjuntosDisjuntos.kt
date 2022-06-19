package ve.usb.libGrafo

/*
 Implementación de las estructuras de datos para conjuntos disjuntos.
 Los conjuntos disjuntos son representado como árboles.
 El constructor recibe como entrada en número elementos que van a conformar los cojuntos disjuntos. 
 Los elementos de los conjuntos disjuntos están identificados en el intervalo [0 .. n-1]. 
 Cuando se ejecuta el constructor, se crean n conjuntos disjuntos iniciales, es decir,
 se debe ejecutar make-set(i) para todo i en el intervalo [0 .. n-1]. 
*/
public class ConjuntosDisjuntos(val n: Int) {

    /*
     Realiza la unión de las dos componentes conexas conexas de las cuales pertenecen
     los elementos v y u. Retorna true si los dos vertices están en diferentes componentes conexas, 
     en caso contrario retorna falso. Si el algunos de los dos elementos de
     entrada, no pertenece a algún conjunto, entonces se lanza un RuntineException
     */
    fun union(v: Int, u: Int) : Boolean {
	
    }

    /*
     Retorna el elemento representante de un elemento en un conjunto disjunto.
     Se recibe como entrada al elemento que al que se le quiere determinar el representante.
     Este método corresponde a la función find-set de conjuntos disjuntos. 
     Si el identificador no pertenece a ningun elemento, entonces se lanza una RuntimeException.
     */
    fun encontrarConjunto(v: Int) : Int {
	
    }

    /* 
     Retorna el número de conjuntos disjuntos actuales que tiene la estructura de conjuntos disjuntos.
     */
    fun numConjuntosDisjuntos() : Int {

    }
}
