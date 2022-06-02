package ve.usb.libGrafo

import java.io.File

public class GrafoNoDirigidoCosto: Grafo {
    // Atributos de la clase
    val nVertices: Int
    val repGrafo: Array<LinkedList<Int>>
    var listaLados: LinkedList<AristaCosto>
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
        repGrafo = Array<LinkedList<Int>>(nVertices){LinkedList<Int>()}
        listaLados = LinkedList<AristaCosto>
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
        repGrafo = Array<Lista>(nVertices){LinkedList<Int>()}
        listaLados = LinkedList<AristaCosto>()
        nLados = fileContent[1].toInt()

        for (i in 2 until fileContent.size) {
            val arista. AristaCosto = obtenerAristaCosto(fileContent[i])

            repGrafo[arista.x].add(arista.y)
            repGrafo[arista.y].add(arista.x)
            this.agregarAristaCosto(arista)
        }
    }

    /* 
        Agregamos un lado al grafo. Si el lado tiene extremos iguales o invalidos
        se termina el prorama

        {P: Arsita cuyos extremos sean validos y no sean iguales}
        {Q: repGrafo[a.y].contains(a.x) && repGrafo[a.x].contains(a.y) && listaLados.contains(a)}

        Input: a -> Arista valida
        Output: true -> Si la arista se puedo agregar al grafo
                false -> Si la arista no se pudo añadir al grafo

        Tiempo de ejecucion O(|vertices adyacentes de a.x|)
    */
    fun agregarAristaCosto(a: AristaCosto) : Boolean {
        if (a.x < 0 || a.x >= nVertices || a.y < 0 || a.y >= nVertces) {
            throw RuntimeException("La arista a agregar tiene vertices invalidos")
        }

        if (a.x == a.y) {
            throw RuntimeException("La arista a agregar tiene vertices iguales")
        }

        if (aristaEnGrafoNoDirigido(repGrafo, a)) {
            return false
        }

        repGrafo[a.x].add(a.y)
        repGrafo[a.y].add(a.x)
        listaLados.add(a)
        nlados++

        return true
    }

    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados() : Int = nLados

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices() : Int = nVertces

    // Retorna los lados adyacentes al vértice v, es decir, los lados que contienen al vértice v
    override fun adyacentes(v: Int) : Iterable<AristaCosto> {
        var ady: LinkedList<AristaCosto> = LinkedList<AristaCosto>()
        for (i in listaLados){
            if (i.inicio == v || i.fin == v)
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
    fun ladosAdyacentes(l: AristaCosto) : Iterable<AristaCosto> {
        if (!aristaEnGrafoNoDirigido(repGrafo, l)) {
            throw RuntimeException("La arista no pertenece al grafo")
        }
        var ady: LinkedList<AristaCosto> = LinkedList<AristaCosto>()
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
     override operator fun iterator() : Iterator<AristaCosto> {
        return listaLados.iterator()
     }
    
    // Grado del grafo
    override fun grado(v: Int) : Int {
        if (v < 0 || v >= nVertces) {
            throw RuntimeException("El vertice dado es invalido")
        }

        return repGrafo[v].count()
    }

    // Retorna un string con una representación del grafo, en donde se nuestra todo su contenido
    override fun toString() : String {
        for (i in listaLados) {
            println(i)
        }
    }
}
