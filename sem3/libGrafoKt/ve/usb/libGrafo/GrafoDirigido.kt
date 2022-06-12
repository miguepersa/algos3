package ve.usb.libGrafo

import java.util.LinkedList
import java.io.File

public class GrafoDirigido : Grafo {
    //Atributos de la clase
    val nVertices: Int
    val repGrafo: Array<LinkedList<Vertice>>
    var listaLados: LinkedList<Arco>
    var arrVertices: Array<Vertice>
    var nLados: Int

    /*
        Crea una instancia de GrafoDirigido.

        {P: numDeVertices >= 1}
        {Q: nVertices == numDeVertices && repGrafo contiene la represenacion
            de un grafo sin lados}

        Input: numDeVertices -> Entero que indica la cantidad de vertices del grafo
        Output: ~~

        Tiempo de ejecucion O(numDeVertices)
    */
    constructor(numDeVertices: Int) {
        nVertices = numDeVertices
        repGrafo = Array<LinkedList<Vertice>>(nVertices){LinkedList<Vertice>()}
        listaLados = LinkedList<Arco>()
        arrVertices = Array<Vertice>(nVertices, {i -> Vertice(i)})
        nLados = 0
    }

    /*
        Se crea una instancia de GrafoDirigido desde un archivo
        Se asume que el contenido del archivo esta correcto, no es necesario verificarlo

        {P: nombreArchivo es un archivo existente que contiene informacion de un grafo}
        {Q: numDeVertices y repGrafo contienen la informacion que esta en el archivo dado}

        Input: nombreArchivo -> String que contiene la direcion a un archivo.
        Output: ~~

        Tiempo de ejecutio O(nLados + nVertices)
     */
    constructor(nombreArchivo: String) {
        val fileContent: List<String> = File(nombreArchivo).readLines()
        nVertices = fileContent[0].toInt()
        repGrafo = Array<LinkedList<Vertice>>(nVertices){LinkedList<Vertice>()}
        listaLados = LinkedList<Arco>()
        nLados = 0      
        arrVertices = Array<Vertice>(nVertices, {i -> Vertice(i)})

        for (i in 2 until fileContent.size) {
            val arco: Arco = obtenerArco(fileContent[i], arrVertices)

            this.agregarArco(arco)
        }

    }

    /*
        Agregamos un lado al grafo. Si el lado tiene extremos invalidos se termina el programa.

        {P: Arco cuyos extremos sean vertices validos y no se encuentre en el grafo}
        {Q: repGrafo[a.inicio].busqueda(a.final)}

        Input: a -> Arco valido
        Output: true -> Si la arista se puedo agregar al grafo
                false -> si la arista no se pudo agregar al grafo
    */
    fun agregarArco(a: Arco) : Boolean {
        if (a.inicio.n < 0 || a.inicio.n >= nVertices || a.fin.n < 0 || a.fin.n >= nVertices) {
            throw RuntimeException("El arco a agregar tiene extremos invalidos")
        }

        if (arcoEnGrafoDirigido(repGrafo, a)) {
            return false
        }

        repGrafo[a.inicio.n].add(a.fin)
        listaLados.add(a)
        nLados++

        return true
    }

    /* 
        Calcula la cantidad de lados tales que (v, w), con v fijo

        {P: v es un vertices valido}
        {Q: n == gradoExterior}

        Input: v -> Entero, numero del vertice del cual se obtendra el grado exterior
        Output: grado exterior del vertice v

        Tiempo de ejecucion O(1)
    */
    fun gradoExterior(v: Int) : Int {
        if (v < 0 || v >= nVertices) {
            throw RuntimeException("El arco a agregar tiene extremos invalidos")
        }

        return repGrafo[v].count()
    } 

    /* 
        Calcula la cantidad de lados tales que (w, v) con v fijo

        {P: v es un vertice valido}
        {Q: n == gradoInterior}

        Input: v -> Entero, numero del vertie del cual se obtendra el grado interior
        Output: grado interior del vertice v

        Tiempo de ejecucion O(repGrafo.size + cantidad de lados)
    */ 
    fun gradoInterior(v: Int) : Int {
        if (v < 0 || v >= nVertices) {
            throw RuntimeException("El vertce dado es invalido")
        }

        var cont: Int = 0

        for (i in 0 until nVertices) {
            for (j in repGrafo[i]) {
                if (j.n == v) {
                    cont++
                }
            }
        }

        return cont
    }

    /*
        Retorna el grado del vertice solicitado

        {P: v pertenece al grafo}
        {Q: grado(v) == gradoExterior(v) + gradoInterior(v)}

        Input: v -> Vertice cuyo grado se quiere calcular
        Output: grado del vertice v

        O(1)
     */
    override fun grado(v: Int) : Int {
        if (v < 0 || v >= nVertices) {
            throw RuntimeException("El vertice dado es invalido")
        }

        return this.gradoExterior(v) + this.gradoInterior(v)
    }

    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados() : Int = nLados

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices() : Int = nVertices

    /* 
        Retorna los adyacentes de v, en este caso los lados que tienen como vértice inicial a v. 
        Si el vértice no pertenece al grafo se lanza una RuntimeException

        {P: v pertenece al grafo}
        {Q: ady contiene los lados adyacentes a v}

        Input: v -> Vertice cuyos adyacentes se quieren hallar
        Output: ady -> Iterable que contiene los lados adyacentes a v

        O(nLados)
     */
    override fun adyacentes(v: Int) : Iterable<Arco> {
        var ady: LinkedList<Arco> = LinkedList<Arco>()
        for (i in listaLados){
            if (i.inicio.n == v)
            {
                ady.add(i)
            }
        }
        return ady.asIterable()
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
    fun ladosAdyacentes(l: Arco) : Iterable<Arco> {
        if (!arcoEnGrafoDirigido(repGrafo, l)) {
            throw RuntimeException("La arista no pertenece al grafo")
        }
        var ady: LinkedList<Arco> = LinkedList<Arco>()
        for (i in listaLados) {
            if (!arcoEnLista(ady, i) && !arcoEnLista(ady, Arco(l.fin, l.inicio)) && (i.inicio.n != l.inicio.n && i.fin.n != l.fin.n)) {
                if (i.fin.n == l.fin.n || i.fin.n == l.inicio.n || i.inicio.n == l.fin.n || i.inicio.n == l.inicio.n) {
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
    override operator fun iterator() : Iterator<Arco> {
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
