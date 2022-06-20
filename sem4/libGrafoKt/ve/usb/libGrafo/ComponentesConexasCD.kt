package ve.usb.libGrafo
import java.util.LinkedList

/*
 Determina las componentes conexas de un grafo no dirigido usando
 conjuntos disjuntos, representados como árboles. 
 La componentes conexas se determinan cuando 
 se crea un nuevo objeto de esta clase.
*/
public class ComponentesConexasCD(val g: GrafoNoDirigido) {
    // creamos tantos conjuntos disjuntos como vertices tenga g
    var componentesConexas: ConjuntosDisjuntos = ConjuntosDisjuntos(g.obtenerNumeroDeVertices()) 
    
    init {
        for (i in g.iterator()) {
            if (componentesConexas.encontrarConjunto(i.v.n) != componentesConexas.encontrarConjunto(i.u.n)) {
                componentesConexas.union(i.v.n, i.u.n)
            }
        }
    }

    /*
        Retorna true si los dos vertices están en la misma componente conexa y
        falso en caso contrario.

        {P: v y u son vertices del grafo}
        {Q: true}

        Input: v -> Entero, un vertice del grafo
               u -> Entero, un vertice del grafo
        Output: true -> si u y v pertencen a la misma componente
                false -> caso contrario

        Tiempo de ejecucion O
     */
    fun mismaComponente(v: Int, u: Int) : Boolean {
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("ComponentesConexasCD.mismaComponente(): El vertice ${v} no pertenece al grafo")
        }

        if (u < 0 || u >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("ComponentesConexasCD.mismaComponente(): El vertice ${u} no pertenece al grafo")
        }

        return componentesConexas.encontrarConjunto(v) == componentesConexas.encontrarConjunto(u)
    }

    // Indica el número de componentes conexas
    fun nCC() : Int = componentesConexas.numConjuntosDisjuntos()

    /*    
        Retorna el identificador de la componente conexa donde está contenido 
        el vértice v. El identificador es un número en el intervalo [0 , nCC()-1].
        
        {P: v es un vertice del grafo}
        {Q: true}

        Input: v -> Entero, representa el vertie de un grafo
        Output: Entero que representa el numero de la componente en la que esta el vertice v

        Tiempo de ejecucion
    */
    fun obtenerComponente(v: Int) : Int {
        if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("ComponentesConexas.obtenerComponente(): El vertive ${v} no pertenece al grafo")
        }

        val ordenComp: Array<Int> = componentesConexas.obtenerOrdenConjuntos()

        return ordenComp[v]
    }

    /* 
        Retorna el número de vértices que conforman una componente conexa dada.
        Se recibe como entrada el identificado de la componente conexa.
        El identificador es un número en el intervalo [0 , nCC()-1].

        {P: compID es el identificador de una componente del grafo}
        {Q: true}

        Input: compID -> Entero, identificador de una componente
        Output: Entero, cantidad de vertices de la componente
    */
    fun numVerticesDeLaComponente(compID: Int) : Int {
        if (compID < 0 || compID >= this.nCC()) {
            throw RuntimeException("ComponentesConexasCD.numVerticesDeLaComponente(): El identificador ${compID} no pertenece a ninguna componente")
        }

        val ordenComp: Array<Int> = componentesConexas.obtenerOrdenConjuntos()
        var cont: Int = 0

        for (i in ordenComp) {
            if (i == compID) cont++
        }

        return cont
    }
}
