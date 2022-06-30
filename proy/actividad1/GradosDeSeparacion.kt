import ve.usb.libGrafo.*
import java.io.FileNotFoundException
import java.util.LinkedList

/* 
    Cliente que implementa la solucion al problema de los grados de separacion
    en un grafo no dirigido.

    Sobre la solucion al problema
    
    Para solucionar el problema usamos la clase TablaGrafoNoDirigido. Con esto
    obtenemos el grafo no dirigido asociado al grafo.

    Luego, obtenemos los vertices asociados a cada ente.
    Con el grafo y los vertices ejecutamos BFS desde el vertice asociado a 
    ente1. Esto nos data como resultado que ente2.d es el camino la distancia
    mas corte entre ente1 y ente2. En base a esto ente2 es el grado de separacion
    entre ente1 y ente2.

    En caso de que se indique que se esta subiendo un grafo bipartito obtenemos
    el grafo no dirigido y los vertices asociados como en el caso anterior.
    Luego, creamos un objeto de la clase GrafoBipartito para determinar si el
    grado dado es o no un grafo bipartito. Ademas, en caso que el grafo sea
    bipartito, se verifica que ente1 y ente2 esten en el mismo conjunto de veritces.
    En caso de ser bipartito y analogamente que en caso anterior, ejecutamos 
    BFS desde el ente1. El grado de separacion entre ambos entes, seria ente2/2

    Sobre el tiempo de ejecucion

    Obtener la tabla toma O(|V|)
    Obtener el grafo asociado O(|E|)
    Determinar si el grafo es bipartito O(|V| + |E|)
    Ejecutar BFS O(|V| + |E|)
    Obtener el camino O(|V|)
    
    Demas operaciones como detemrinar si dos entes estan en el mismo conjunto de vertices,
    y obtener la distancia de un vertice toman O(1)

    En conclusion, obtener el grado de separacion entre dos vertices tiene un tiempo de ejecucion
    O(|V| + |R|)
*/
fun main(args: Array<String>) {
    try {
        if (!entradaCorrecta(args)) {
            throw RuntimeException("main: Los parametros de entrada son invalidos")
        }

        // Obtenemos los parametros 
        // Ya se verifico que si se indico -b args tiene tama;o 4 y si no 
        // args tiene tama;o 3
        val parametroBipartito: Boolean = usarGrafoBipartito(args)
        val archivoGrafo: String = if (parametroBipartito) args[1] else args[0]
        val ente1: String = if (parametroBipartito) args[2] else args[1]
        val ente2: String = if (parametroBipartito) args[3] else args[2]

        // En caso de un error en el orden de los parametros lanza FileNotFoundException
        val tablaGrafo: TablaGrafoNoDirigido = TablaGrafoNoDirigido(archivoGrafo) 
        val grafoNoDirigidoAsociado: GrafoNoDirigido = tablaGrafo.obtenerGrafoNoDirigido()
        val grafoBipartitoAsociado: GrafoBipartito = GrafoBipartito(grafoNoDirigidoAsociado)
        
        val verticeEnte1: Int = tablaGrafo.indiceVertice(ente1)
        val verticeEnte2: Int = tablaGrafo.indiceVertice(ente2)

        // Verificamos el grafo y los entes en caso de ser necesario.
        // Es necesario si se indico el parametro -b
        if (parametroBipartito) {
            
            // Verificamos si el grafoAsociado es bipartito
            if (!grafoBipartitoAsociado.esGrafoBipartito()) {
                throw RuntimeException("main: El grafo bipartito dado no es un grafo bipartito")
            }
            
            // Verificamos si los entes estan en el mismo conjunto de vertices
            if (!grafoBipartitoAsociado.estanEnElMismoConjunto(verticeEnte1, verticeEnte2)) {
                throw RuntimeException("main: Los entes $ente1 y $ente2 no estan en el mismo conjunto de vertices")
            }
        }

        // Ejecutamos BFS desde el vertice asociado a ente1
        val grafoBFS: BFS = BFS(grafoNoDirigidoAsociado, verticeEnte1)
        var gradoDeSeparacion: Int = if (parametroBipartito) grafoBFS.obtenerDistancia(verticeEnte2) / 2 else grafoBFS.obtenerDistancia(verticeEnte2)
        
        if (gradoDeSeparacion == -1) gradoDeSeparacion == 0// Si no existe camino entre ente1 y ente2
        
        // Mostrar los resultado en pantalla
        println(gradoDeSeparacion)
        if (gradoDeSeparacion != 0) {
            println(obtenerCaminoEntreEntes(tablaGrafo, grafoBFS, verticeEnte2))
        }

    } catch(e: RuntimeException) {
        println(e)
    
    } catch(e: java.io.FileNotFoundException) {
        println("main: El archivo especificado para cargar el grafo no existe")
    
    }
}

/* 
    Verificamos que el comando de entrada este correcto.

    El formato esta correcto si es de la siguiente forma

    [-b] <archivoGrafo> <ente1> <ente2>

    Esto es que (args.size == 3) o (args.size == 4 y args[0] == "-b")

    {P: args contiene los parametros con lo cual fue ejecutado el programa}
    {Q: true si los parametros estan correctos, false caso contrario}

    Input: args -> Array<String> en donde args[i] es un parametro usado en 
                    la ejecucion del programa
    Output: true -> Si los parametros estan correctos.
            false -> caso contrario

    Tiempo de ejecucion O(1) 
*/
fun entradaCorrecta(args: Array<String>): Boolean = (args.size == 3) || (args.size == 4 && args[0] == "-b")

/* 
    Determina si el programa se esta ejecutando para un grafo bipartito.
    Esto es que args.size == 4 y args[0] == "-b"

    {P: args contiene los parametros con los cuales fue ejecutado el programa}
    {Q: true si los parametros indican que se esta usando un grafo bipartito}

    Input: args -> Array<String> en donde args[i] es un parametro usado en
                    la ejecucion del programa
    Output: true -> args.size == 4 && args[0] =+ "-b"
            false -> caso contrario

    Tiempo de ejecucion O(1)
*/
fun usarGrafoBipartito(args: Array<String>): Boolean = args.size == 4 && args[0] == "-b"

/* 
    Obtiene el string que representa el camino entre ente1 y ente2.

    La idea es obtener el camino usando el metodo caminoHasta de BFS.
    Luego, se recorre el camino y se crea uno nuevo pero cambiando
    los vertice por su nombre correspondiente en la tabla

    {P: }
    {Q: Se obtiene el camino entre ente1 y ente2 con los nombres 
        correspondientes de los vertices}

    Input: tabla -> TablaGrafoNoDirigino para obtener el nombre del vertice dado un entero
            grafo -> BFS para obtener el camino desde ente1 hasta ente2
            fin -> Vertice final del camino que se obtendra

    Output: Representacion como string del camino desde ente1 hasta ente2

    Tiempo de ejecucion O(|V|) 
*/
fun obtenerCaminoEntreEntes(tabla: TablaGrafoNoDirigido, grafo: BFS, fin: Int): String {
    val camino: Iterable<Int> = grafo.caminoHasta(fin)
    val nuevoCamino: Iterable<String> = obtenerCaminoString(tabla, camino)

    return iterableString(nuevoCamino)
}

/* 
    Dado un camino en un grafo representado con enteros, se obtiene el
    camino representado con strings.

    {P: p contiene un camino entre dos vertices del grafo}
    {Q: Se obtiene el camino en donde cada elemento es el string 
        correspondiente del vertice de p}

    Input: tabla -> tablaGrafoNoDirigido para obtener el nombre del vertice dado un entero
            p -> Iterable<Int> contiene un camino entre dos vertices de un grafo

    Tiempo de ejecucion O(Largo del camino)
*/
fun obtenerCaminoString(tabla: TablaGrafoNoDirigido, p: Iterable<Int>): Iterable<String> {
    var camino: LinkedList<String> = LinkedList<String>()

    // Recorremos p y transformamos cada entero a su string correspondiente
    for (i in p) {
        camino.add(tabla.nombreVertice(i))
    }

    return camino.asIterable()
}

/* 
    Dado un iterable obtener su representacion como string

    Se desea devolver un string de la forma
    "ite_1 - ite_2 - ... - ite_n"

    {P: ite es un iterable de string}
    {Q: Un string que es la representacin del iterable y entre cada par
        de elementos hay un guion}

    Input: 
*/
fun iterableString(ite: Iterable<String>): String {
    var iteString: String = ""
    val ultimo: String = ite.last()

    for (i in ite) { 
        iteString += i

        if (i != ultimo) {
            iteString += " - "
        }
    }

    return iteString
}