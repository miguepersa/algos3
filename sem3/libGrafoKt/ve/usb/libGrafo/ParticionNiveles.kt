package ve.usb.libGrafo

/*
 Esta clase al inicializarse obtiene una partición por niveles, de los vértices
 de un digrafo acíclico (DAG). Si el grafo de entrada es un DAG, el algoritmo encuentra
 para cada nivel un conjunto de vértices y se indica que no hay ciclo. Si el grafo
 de entrada no es un DAG, entonces se indica que hay ciclo. 
 */
public class ParticionNiveles(val g: GrafoDirigido) {
    var particiones: Array<MutableSet<Int>>
    var gradoInterior: Array<Int>
    var nvert: Int
    var nivel: Int

    init {
        particiones = Array<MutableSet<Int>>(g.obtenerNumeroDeVertices(), {mutableSetOf<Int>()})
        gradoInterior = Array<Int>(g.obtenerNumeroDeVertices(), {i -> g.gradoInterior(i)})
        nvert = 0
        nivel = 0

        for (i in 0 until g.obtenerNumeroDeVertices()) {
            if (gradoInterior[i] == 0) {
                particiones[nivel].add(i)
                nvert++
            }
        }

        while (nvert < g.obtenerNumeroDeVertices() && particiones[nivel].isNotEmpty()) {
            for (u in particiones[nivel]) {
                for (v in g.adyacentes(u)) {
                    gradoInterior[v.fin.n]--
                    if (gradoInterior[v.fin.n] == 0) {
                        particiones[nivel + 1].add(v.fin.n)
                        nvert++
                    }
                }
            }
            nivel++
        }

        if (nvert < g.obtenerNumeroDeVertices()) {
            println("El grafo dado no es un DAG, no se tienen particiones de nivel")
        }
    }

    // Retorna las particiones de niveles de los vértices del grafo de entrada
    // Si el digrafo de entrada no es DAG, entonces se lanza una RuntineException()
    fun obtenerParticiones() : Array<MutableSet<Int>> {
        return particiones    
    }
    
    // Retorna true si el digrafo tiene un ciclo, y false en caso contrario.
    fun hayCiclo() : Boolean {
        return nvert < g.obtenerNumeroDeVertices()
    }
    
}
