package ve.usb.libGrafo

/*
  Obtiene las componentes fuertementes conexas de un grafo 
  La componentes fuertementes conexas se determinan cuando 
  se crea un nuevo objeto de esta clase, es decir, en el constructor.
*/
public class CFC(val g: GrafoDirigido) {
    
    /*
     Retorna true si dos vértices están en la misma CFC y
     falso en caso contrario. Si el algunos de los dos vértices de
     entrada, no pertenece al grafo, entonces se lanza un RuntineException
     */
    //fun estanEnLaMismaCFC(v: Int, u: Int) : Boolean {
    //}

    // Indica el número de componentes fuertemente conexas del digrafo.
    //fun numeroDeCFC() : Int {
	
    //}

    /*
     Retorna el identificador de la componente fuertemente conexa donde está contenido 
     el vértice v. El identificador es un número en el intervalo [0 , numeroDeCFC()-1].
     Si el vértice v no pertenece al grafo g se lanza una RuntimeException
     */
    //fun obtenerIdentificadorCFC(v: Int) : Int {
	
    //}
    
    /*
     Retorna un objeto iterable, el cual contiene CFCs. Cada CFC esta representada
     como un  conjunto de vértices. El orden de las CFCs en el objeto iterable,
     debe corresponder al que se indica en el método obtenerIdentificadorCFC
     */
    //fun  obternerCFC() : Iterable<MutableSet<Int>> {
	
    //}

    /*
     Retorna el grafo componente asociado a las componentes fuertemente conexas. 
     El identificador de los vértices del grafo componente está asociado 
     con los orden de la CFC en el objeto iterable que se obtiene del método obtenerCFC. 
     Es decir, si por ejemplo si los vértices 5,6 y 8 de G pertenecen a la
     componentemente fuertemente conexa cero, entonces 
     obtenerIdentificadorCFC(5) = obtenerIdentificadorCFC(6) = obtenerIdentificadorCFC(8) = 0
     y se va a tener que el en grafo componente el vértice 0 
     representa a la CFC que contiene a los vértices 5,6 y 8 de G.
     */
    //fun obtenerGrafoComponente() : GrafoNoDirigido {
	
    //}

}
