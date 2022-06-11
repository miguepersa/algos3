package ve.usb.libGrafo

/*
 Esta clase al inicializarse obtiene una partición por niveles, de los vértices
 de un digrafo acíclico (DAG). Si el grafo de entrada es un DAG, el algoritmo encuentra
 para cada nivel un conjunto de vértices y se indica que no hay ciclo. Si el grafo
 de entrada no es un DAG, entonces se indica que hay ciclo. 
 */
public class ParticionNiveles(val g: GrafoDirigido) {

    // Retorna las particiones de niveles de los vértices del grafo de entrada
    // Si el digrafo de entrada no es DAG, entonces se lanza una RuntineException()
    fun obtenerParticiones() : Array<MutableSet<Int>> {	
    }
    
    // Retorna true si el digrafo tiene un ciclo, y false en caso contrario.
    fun hayCiclo() : Boolean {
    }
}
