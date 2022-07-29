package ve.usb.libGrafo

import java.io.File
import java.util.LinkedList

public class GrafoNoDirigidoCosto: Grafo {
    // Atributos de la clase
    val nVertices: Int
    val repGrafo: Array<LinkedList<Vertice>>
    var listaLados: LinkedList<AristaCosto>
    var listaLadosAdyacentes: Array<MutableList<AristaCosto>>
    var arrVertices: Array<Vertice>
    var nLados: Int

    /* :
        Crea una instancia de GrafoNoDirigidoCosto

        {P: numDeVertices >= 1}
        {Q: nVertices == numDeVertices && repGrafo contiene la representacion
            de un grafo sin ladoso && listaLados.head == null}

        Input: numDeVertices -> Entero que indica la cantidad de vertices del grafo
        Output: ~~

        Tiempo de ejecucion O(numDeVertices)
    */
    constructor(numDeVertices: Int) {
        nVertices = numDeVertices
        repGrafo = Array<LinkedList<Vertice>>(nVertices){LinkedList<Vertice>()}
        listaLados = LinkedList<AristaCosto>()
        listaLadosAdyacentes = Array<MutableList<AristaCosto>>(nVertices){mutableListOf<AristaCosto>()}
        arrVertices = Array<Vertice>(nVertices, {i -> Vertice(i)})
        nLados = 0
    }

    /*
        Se crea una instanca de GrafoNoDirigidoCosto desde un archivo
        Se asume que el contenido del archivo esta correcto, no es necesario verificarlo

        {P: nombreArchivo es un archivo existente que tiene informacin de un grafo}
        {Q: nVertices, repGrafo y listaLados contienen la informacin del archivo dado}

        Input: nombreArchivo -> String que contiene la direccion a un archivo
        Output: ~~

        Tiempo de ejecucion O(lineas del archivo)
    */ 
    constructor(nombreArchivo: String) {
        val fileContent: List<String> = File(nombreArchivo).readLines()
        nVertices = fileContent[0].toInt()
        repGrafo = Array<LinkedList<Vertice>>(nVertices){LinkedList<Vertice>()}
        listaLados = LinkedList<AristaCosto>()
        listaLadosAdyacentes = Array<MutableList<AristaCosto>>(nVertices){mutableListOf<AristaCosto>()}
        nLados = 0
        arrVertices = Array<Vertice>(nVertices, {i -> Vertice(i)})

        for (i in 2 until fileContent.size) {
            val arista: AristaCosto = obtenerAristaCosto(fileContent[i], arrVertices)
            this.agregarAristaCosto(arista)
        }
        
        
    }

    /* 
        Agregamos un lado al grafo. Si el lado tiene extremos iguales o invalidos
        se termina el prorama

        {P: Arsita cuyos extremos sean validos y no sean iguales}
        {Q: repGrafo[a.y.n].contains(a.x.n) && repGrafo[a.x.n].contains(a.y.n) && listaLados.contains(a)}

        Input: a -> Arista valida
        Output: true -> Si la arista se puedo agregar al grafo
                false -> Si la arista no se pudo añadir al grafo

        Tiempo de ejecucion O(|vertices adyacentes de a.x.n|)
    */
    fun agregarAristaCosto(a: AristaCosto) : Boolean {
        if (a.x.n < 0 || a.x.n >= nVertices || a.y.n < 0 || a.y.n >= nVertices) {
            throw RuntimeException("La arista a agregar tiene vertices invalidos")
        }

        if (a.x.n == a.y.n) {
            throw RuntimeException("La arista a agregar tiene vertices iguales")
        }

        if (aristaEnGrafoNoDirigido(repGrafo, a)) {
            return false
        }

        repGrafo[a.x.n].add(a.y)
        repGrafo[a.y.n].add(a.x)
        listaLados.add(a)
        listaLadosAdyacentes[a.x.n].add(a)
        listaLadosAdyacentes[a.y.n].add(a)
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
    override fun adyacentes(v: Int) : Iterable<AristaCosto> {
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
    fun ladosAdyacentes(l: AristaCosto) : Iterable<AristaCosto> {
        if (!aristaEnGrafoNoDirigido(repGrafo, l)) {
            throw RuntimeException("La arista no pertenece al grafo")
        }
        var ady: LinkedList<AristaCosto> = LinkedList<AristaCosto>()
        for (i in listaLados) {
            if (!aristaCostoEnLista(ady, i) && (i.y.n != l.y.n && i.x.n != l.x.n)) {
                if (i.y.n == l.y.n || i.y.n == l.x.n || i.x.n == l.y.n || i.x.n == l.x.n) {
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
    override operator fun iterator() : Iterator<AristaCosto> {
        return listaLados.iterator()
     }
    
    /* 
        Calcula el grado de un vertice dado del grafo

        {P: v es un vertice que pertenece al lado}
        {Q: devuelve el numero de lados incidentes del vertice}

        Input: v -> Entero, numero del vertice del cual se obtendra el vertice
        Output: grado del vertce
    */
    override fun grado(v: Int) : Int {
        if (v < 0 || v >= nVertices) {
            throw RuntimeException("El vertice dado es invalido")
        }

        return repGrafo[v].count()
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
