package ve.usb.libGrafo

import java.io.File
import java.util.LinkedList

public class GrafoNoDirigido: Grafo {
    // Atribuos de la clase
    val nVertices: Int
    val repGrafo: Array<LinkedList<Int>>
    var listaLados: LinkedList<Arista>
    var nLados: Int

    /* 
        Crea un instancia de GrafoNoDirigido

        {P: numDeVertices >= 1}
        {Q: nVertices == numDeVertices && repGrafo contiene la representacion 
            de un grafo sin lados.}

        Input: numDeVertces -> Entero que indica la cantidad de vertices del grafo
        Output: ~~

        Tiempo de ejecicion O(numDeVertices)
    */
    constructor(numDeVertices: Int) {
        nVertices = numDeVertces
        repGrafo = Array<LinkedList<Int>>(nVertices){LinkedList<Int>()}
        listaLados = LinkedList<Arista>()
        nLados = 0
    }

    /*
        Se crea un instancia de GrafoNoDirigio desde un archivo
        Se asuma que el contenido del archivo esta correcto, no es necesario verificarlo 

        {P: nombreArchivo es un archivo que contiene informacion de un grafo}
        {Q: numDeVertices y repGrafo contienen la informacion que esta en el archivo}

        Input: nombreArchivo -> String que contiene la direccion de un archivo
        Output: ~~

        Tiempo de ejecucion O(lineas del archivo)
     
    */  
    constructor(nombreArchivo: String) {
        val fileContent: Lista<String> = File(nombreArchivo).readLines()
        nVertices = fileContent[0].toInt()
        nLados = fileContent[1].toInt()
        listaLados = LinkedList<Arista>()

        for (i in 2 until fileContent.size) {
            val arista: Arista = obtenerArista(fileContent[2])

            repGrafo[arista.v].add(arista.u)
            repGrafo[arista.u].add(arista.v)
            this.agregarArista(arista)
        }
    }

    /* 
        Agregamos un lado al grafo. Si el  lado tiene extremos invalidos se termina el
        programa.

        {P: Arista cuyos extremos sean vertices validos y no se encuentre en el grafo}
        {Q: repGrafo[a.v].busqueda(a.u) && repGrafo[a.u].busqueda(a.v)

        Input: a -> Arista valida.
        Output: true -> Si la arista se pudo agregar al grafo
                false -> Si la arista no se pudo agregar al grafo

        Tiempo de ejecucion O(|vertices adyacentes de a.u|)
     */
    fun agregarArista(a: Arista) : Boolean {
        if (a.v < 0 || a.v >= nVertices || a.u < 0 || a.u >= nVertices) { // verificamos que los vertices sean validos
            throw RuntimeException("La arista a agregar tiene vertices invalidos")
        }

        if (a.v == a.u) { // en los grafos no dirigidos no se permiten los lados (a, a)
            throw RuntimeException("La arista a agregar tiene vertices iguales")
        }

        if (aristaEnGrafoNoDirigido(repGrafo, a)) { // no podemos añadir el mismo lado dos veces
            return false
        }

        repGrafo[a.v].add(a.u)
        repGrafo[a.u].add(a.v)
        listaLados.add(a)
        nLados++

        return true
    }

    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados() : Int = nLados

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices() : Int = nVertices

    // Retorna los lados adyacentes al vértice v, es decir, los lados que contienen al vértice v
    override fun adyacentes(v: Int) : Iterable<Arista> {
        var ady: LinkedList<Arista> = LinkedList<Arista>()
        for (i in listaLados){
            if (i.inicio == v || i.fin == v)
            {
                ady.add(i)
            }
        }
        return ady.asIterable()
    }

    /* 
        Calcula el grado de un vertice dado del grafo

        {P: v es un vaertice que pertenece al lado}
        {Q: devuelve el numero de ladosa incidentes del vertice}

        Input: v -> Entero, numero del vertice del cual se obtendra el vertice
        Outpu: grado del vertce
    */
    override fun grado(v: Int) : Int {
        if (a.inicio < 0 || a.inicio >= nVertices || a.final < 0 || a.final >= nVertices) {
            throw RuntimeException("El arco a agregar tiene extremos invalidos")
        }

        return repGrafo[v].count()
    }

    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    fun ladosAdyacentes(l: Arista) : Iterable<Arista> {
        if (!aristaEnGrafoNoDirigido(repGrafo, l)) {
            throw RuntimeException("La arista no pertenece al grafo")
        }
        var ady: LinkedList<Arista> = LinkedList<Arista>()
        for (i in listaLados) {
            if (!aristaEnLista(ady, i) && (i.v != l.v && i.u != l.u)) {
                if (i.v == l.v || i.v == l.u || i.u == l.v || i.u == l.u) {
                    ady.add(i)
                }
            }
        }
        return ady.asIterable()
    }
    
    // Retorna todos los lados del grafo no dirigido
    override operator fun iterator() : Iterator<Arista> {
        return listaLados.iterator()
    }

    // Retorna un string con una representación del grafo, en donde se nuestra todo su contenido
    override fun toString() : String {
        for (i in listaLados) {
            println(i)
        }
    }
}
