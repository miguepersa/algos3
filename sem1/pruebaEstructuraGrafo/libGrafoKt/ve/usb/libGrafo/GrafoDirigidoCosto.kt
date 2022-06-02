package ve.usb.libGrafo

import java.io.File
import java.util.LinkedList

public class GrafoDirigidoCosto : Grafo {
    //Atributos de la clase
    val nVertices: Int
    val repGrafo: Array<LinkedList<Int>
    var listaLados: LinkedList<ArcoCosto>
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
        repGrafo = Array<LinkedList<Int>>(nVertices){LinkedList()}
        listaLados = LinkedList<ArcoCosto>()
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
    constructor(nombreArchivo: String)  {
        val fileContent: List<String> = File(nombreArchivo).readLines()
        nVertices = fileContent[0].toInt()
        repGrafo = Array<Lista>(nVertices){Lista()}
        listaLados = ListaArcoCosto()
        nLados = fileContent[1].toInt()

        for (i in 2 until fileContent.size) {
            val arco: ArcoCosto = obtenerArcoCosto(fileContent[i])

            repGrafo[arco.inicio].add(arco.sumidero)
            agregarArcoCosto(arco)
        }
    }

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
        if (a.x < 0 || a.x >= nVertices || a.x < 0 || a.v >= nVertices) {
            throw RuntimeException("El ArcoCosto a agregar tiene extremos invalidos")
        }

        if (arcoEnGrafoDirgido(repGrafo, a)) {
            return false
        }

        repGrafo[a.x].add(a.y)
        listaLados.add(a)
        nLados++

        return true
    }


    // Retorna el grado exterior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoExterior(v: Int) : Int {
        if (v.x < 0 || v.y >= nVertices) {
            throw RuntimeException("El vertce dado es invalido")
        }

        return repGrafo[v].count()
    }

    // Retorna el grado interior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoInterior(v: Int) : Int {
        if (v.x < 0 || v.y >= nVertices) {
            throw RuntimeException("El vertice dado es invaldo")
        }

        var cont: Int = 0

        for (i in 0 until nVertices) {
            if (repGrafo[i].contains(v)) {
                cont++
            }
        }

        return cont
    }

    // Retorna el grado del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    override fun grado(v: Int) : Int {
        if (v.x < 0 || v.y >= nVertices) {
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

     g[v] -> -> -> 
     */
    override fun adyacentes(v: Int) : Iterable<ArcoCosto> {
        var ady: LinkedList<ArcoCosto> = LinkedList<ArcoCosto>()
        for (i in listaLados){
            if (i.inicio == v)
            {
                ady.add(i)
            }
        }
        return ady.asIterable()
    }

    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    fun ladosAdyacentes(l: ArcoCosto) : Iterable<ArcoCosto> {
        if (!arcoEnGrafoDirigido(repGrafo, l)) {
            throw RuntimeException("La arista no pertenece al grafo")
        }
        var ady: LinkedList<ArcoCosto> = LinkedList<ArcoCosto>()
        for (i in listaLados) {
            if (!arcoEnLista(ady, i) && !arcoEnLista(ady, ArcoCosto(l.fin, l.inicio)) && (i.v != l.v && i.u != l.u)) {
                if (i.v == l.v || i.v == l.u || i.u == l.v || i.u == l.u) {
                    ady.add(i)
                }
            }
        }
        return ady.asIterable()
    }

    // Retorna todos los lados del digrafo con costo
    // g[i], g[i].nodo
    // g[i], g[i].nodo.next
    // g[i + 1]
    override operator fun iterator() : Iterator<ArcoCosto> {
        return listaLados.iterator()
    }

    
    // String que muestra el contenido del grafo
    // println(grafo)
    override fun toString() : String {
        for (i in listaLados) {
            println(i)
        }
    }

}
