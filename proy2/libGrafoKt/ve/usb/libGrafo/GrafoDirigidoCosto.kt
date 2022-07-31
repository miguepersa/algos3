package ve.usb.libGrafo

import java.io.File
import java.util.LinkedList

public class GrafoDirigidoCosto : Grafo {
    //Atributos de la clase
    val nVertices: Int
    val repGrafo: Array<LinkedList<Vertice>>
    var listaLados: LinkedList<ArcoCosto>
    var listaLadosAdyacentes: Array<MutableList<ArcoCosto>>
    var arrVertices: Array<Vertice>
    var nLados: Int

    /* 
        Crea una instancia de GrafoDirigidoCosto

        {P: numDeVertices >= 1}
        {Q: nVertices == numDeVertices && repGrafo contiene la representacion
            de un grafo sin lados && listaLados.head == null}

        Input: numDeVertices -> Entero que indica la cantidad de vertices del grafo
        Output: ~~

        Tiempo de ejecucion O(numDeVertices)
    */
    constructor(numDeVertices: Int) {
        nVertices = numDeVertices
        repGrafo = Array<LinkedList<Vertice>>(nVertices){LinkedList<Vertice>()}
        listaLados = LinkedList<ArcoCosto>()
        listaLadosAdyacentes = Array<MutableList<ArcoCosto>>(nVertices){mutableListOf<ArcoCosto>()}
        arrVertices = Array<Vertice>(nVertices, {i -> Vertice(i)})
        nLados = 0
    }

    /*
        Se crea una instancia de GrafoDirigidoCosto desde un archivo
        Se asume que el contenido de larchivo esta correcto, no es necesario verificarlo

        {P: numbreArchivo es un archivo existente ue tiene informacion de un grafo}
        {Q: nVertices, repGrafo y listaLados contiene la informacion del archivo dado}

        Input: nombreArchivo -> String que contiene la direccion a un archivo
        Output: ~~

        Tiempo de ejecucion O(lineas del archivo)
    */
    /*
    constructor(nombreArchivo: String)  {
        val fileContent: List<String> = File(nombreArchivo).readLines()
        nVertices = fileContent[0].toInt()
        repGrafo = Array<LinkedList<Vertice>>(nVertices){LinkedList<Vertice>()}
        listaLados = LinkedList<ArcoCosto>()
        listaLadosAdyacentes = Array<MutableList<ArcoCosto>>(nVertices){mutableListOf<ArcoCosto>()}
        nLados = 0
        arrVertices = Array<Vertice>(nVertices, {i -> Vertice(i)})

        for (i in 2 until fileContent.size) {
            val arco: ArcoCosto = obtenerArcoCosto(fileContent[i], arrVertices)

            this.agregarArcoCosto(arco)
        }

    }
    */

    /* 
        Agregamos un lado al grafo. Si el lado tiene extremos invalidos
        se termina el programa

        {P: Arco cuyos extremos son validos}
        {Q: repGrafo[a.inicio].contains(a.final)}

        Input: -> a -> ArcoCosto valido
        Output: true -> Si el arco se pudo agregar al grafo
                false -> Si el arco no se pudo agregar al arco

        Tiempo de ejecucion o(|vertices adyacentes de a.inicio|)
     */
    fun agregarArcoCosto(a: ArcoCosto) : Boolean {
        if (a.x.n < 0 || a.x.n >= nVertices || a.y.n < 0 || a.y.n >= nVertices) {
            throw RuntimeException("El ArcoCosto a agregar tiene extremos invalidos")
        }

        if (arcoEnGrafoDirigido(repGrafo, a)) {
            return false
        }

        repGrafo[a.x.n].add(a.y)
        listaLados.add(a)
        listaLadosAdyacentes[a.x.n].add(a)
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
            throw RuntimeException("El vertce dado es invalido")
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
            throw RuntimeException("El vertice dado es invaldo")
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
            throw RuntimeException("El vertce dado es invalido")
        }

        return this.gradoInterior(v) + this.gradoExterior(v)
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
    override fun adyacentes(v: Int) : Iterable<ArcoCosto> {
        return listaLadosAdyacentes[v].asIterable()
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
    /*
    fun ladosAdyacentes(l: ArcoCosto) : Iterable<ArcoCosto> {
        if (!arcoEnGrafoDirigido(repGrafo, l)) {
            throw RuntimeException("La arista no pertenece al grafo")
        }

        var ady: LinkedList<ArcoCosto> = LinkedList<ArcoCosto>()
        for (i in listaLados) {
            if (!arcoCostoEnLista(ady, i) && !arcoCostoEnLista(ady, ArcoCosto(l.y, l.x, 1.0)) && (i.y.n != l.y.n && i.x.n != l.x.n)) {
                if (i.y.n == l.y.n || i.y.n == l.x.n || i.x.n == l.y.n || i.x.n == l.x.n) {
                    ady.add(i)
                }
            }
        }
        return ady.asIterable()
    }
    */

    /*
        Retorna todos los lados del grafo en un iterador

        {P: true}
        {Q: el iterador contiene todos los lados del grafo}

        Input:
        Output: Iterador con los lados del grafo

        O(nLados)
    */
    override operator fun iterator() : Iterator<ArcoCosto> {
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
