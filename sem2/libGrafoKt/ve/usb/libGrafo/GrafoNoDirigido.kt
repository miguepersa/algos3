package ve.usb.libGrafo

import java.io.File
import java.util.LinkedList

public class GrafoNoDirigido: Grafo {
    // Atribuos de la clase
    val nVertices: Int
    val repGrafo: Array<LinkedList<Vertice>>
    var listaLados: LinkedList<Arista>
    var arrVertices: Array<Vertice>
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
        nVertices = numDeVertices
        repGrafo = Array<LinkedList<Vertice>>(nVertices){LinkedList<Vertice>()}
        listaLados = LinkedList<Arista>()
        arrVertices = Array<Vertice>(nVertices, {i -> Vertice(i)})
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
        val fileContent: List<String> = File(nombreArchivo).readLines()
        nVertices = fileContent[0].toInt()
        repGrafo = Array<LinkedList<Vertice>>(nVertices){LinkedList<Vertice>()}
        nLados = 0
        listaLados = LinkedList<Arista>()

        for (i in 2 until fileContent.size) {
            val arista: Arista = obtenerArista(fileContent[i])

            this.agregarArista(arista)
        }

        arrVertices = Array<Vertice>(nVertices, {i -> Vertice(i)})
    }

    /* 
        Agregamos un lado al grafo. Si el  lado tiene extremos invalidos se termina el
        programa.

        {P: Arista cuyos extremos sean vertices validos y no se encuentre en el grafo}
        {Q: repGrafo[a.v.n].busqueda(a.u.n) && repGrafo[a.u.n].busqueda(a.v.n)

        Input: a -> Arista valida.
        Output: true -> Si la arista se pudo agregar al grafo
                false -> Si la arista no se pudo agregar al grafo

        Tiempo de ejecucion O(|vertices adyacentes de a.u.n|)
     */
    fun agregarArista(a: Arista) : Boolean {
        if (a.v.n < 0 || a.v.n >= nVertices || a.u.n < 0 || a.u.n >= nVertices) { // verificamos que los vertices sean validos
            throw RuntimeException("La arista a agregar tiene vertices invalidos")
        }
Int
        if (a.v.n == a.u.n) { // en los grafos no dirigidos no se permiten los lados (a, a)
            throw RuntimeException("La arista a agregar tiene vertices iguales")
        }

        if (aristaEnGrafoNoDirigido(repGrafo, a)) { // no podemos añadir el mismo lado dos veces
            return false
        }

        repGrafo[a.v.n].add(a.u)
        repGrafo[a.u.n].add(a.v)
        listaLados.add(a)
        nLados++

        return true
    }

    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados() : Int = nLados

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices() : Int = nVertices

    /* 
        Retorna los adyacentes de v, en este caso los lados que tienen como vértice a v. 
        Si el vértice no pertenece al grafo se lanza una RuntimeException

        {P: v pertenece al grafo}
        {Q: ady contiene los lados adyacentes a v}

        Input: v -> Vertice cuyos adyacentes se quieren hallar
        Output: ady -> Iterable que contiene los lados adyacentes a v

        O(nLados)
     */
    override fun adyacentes(v: Int) : Iterable<Arista> {
        var ady: LinkedList<Arista> = LinkedList<Arista>()
        for (i in listaLados){
            if (i.v.n == v || i.u.n == v)
            {
                ady.add(i)
            }
        }
        return ady.asIterable()
    }

    /* 
        Calcula el grado de un vertice dado del grafo

        {P: v es un vertice que pertenece al lado}
        {Q: devuelve el numero de lados incidentes del vertice}

        Input: v -> Entero, numero del vertice del cual se obtendra el vertice
        Output: grado del vertce
    */
    override fun grado(v: Int) : Int {
        if (v < 0 || v >= nVertices || v < 0 || v >= nVertices) {
            throw RuntimeException("El arco a agregar tiene extremos invalidos")
        }

        return repGrafo[v].count()
    }

    /* 
        Retorna los lados adyacentes de un lado l. 
        Se tiene que dos lados son iguales si tiene los mismos extremos. 
        Si un lado no pertenece al grafo se lanza una RuntimeException.

        {P: l pertenece al grafo}
        {Q: ady contiene los lados adyacentes a l}

        Input: l -> Lado cuyos lados adyacentes se quieren hallar
        Output: ady -> Iterable con los lados adyacentes a l

        O(nLados)
     */
    fun ladosAdyacentes(l: Arista) : Iterable<Arista> {
        if (!aristaEnGrafoNoDirigido(repGrafo, l)) {
            throw RuntimeException("La arista no pertenece al grafo")
        }
        var ady: LinkedList<Arista> = LinkedList<Arista>()
        for (i in listaLados) {
            if (!aristaEnLista(ady, i) && (i.v.n != l.v.n && i.u.n != l.u.n)) {
                if (i.v.n == l.v.n || i.v.n == l.u.n || i.u.n == l.v.n || i.u.n == l.u.n) {
                    ady.add(i)
                }
            }
        }
        return ady.asIterable()
    }
    
    /*
        Retorna todos los lados del grafo en un iterador

        {P: true}
        {Q: el iterador contiene todos los lados del grafo}

        Input:
        Output: Iterador con los lados del grafo

        O(nLados)
    */
    override operator fun iterator() : Iterator<Arista> {
        return listaLados.iterator()
    }

    /*
        Metodo para imprimir los elementos del grafo

        {P: true}
        {Q: true}

        Input:
        Output:

        O(nLados)
    */
    override fun toString() : String {
        var s: String = " "
        for (i in listaLados) {
            s += i.toString() + " "
        }

        return s
    }
}
