import ve.usb.libGrafo.*
import java.util.HashMap
import java.util.LinkedList
import java.io.File

fun main(args: Array<String>) {
    try {

        if(args.size != 1) {
            throw InvalidArgumentException("Cantidad de parametros erronea.")
        }

        val fileContent: List<String> = File(args[0]).readLines()   // Contenido del archivo
        var map: HashMap<String, Int> = HashMap<String, Int>()      // Mapa con indices de las variables para el grafo
        var n: Int = 0      // Indices para las variables en el HashMap

        for (i in fileContent) {    // Se recorre todo el archivo de entrada para conocer las variables que seran evaluadas
            var line = i.split(" ")
            if (map[line[0]] == null) {         // Si la variable no tiene indice asignado, se le asigna n como indice y a su negado n+1
                if (line[0].toInt() == 0) {     // Si la variable es "0" o "-0"
                    map.put("0", n)
                    n++
                    map.put("-0", n)
                    n++
                } else {                        // Si la variable no es "0" ni "-0"
                    var k = line[0].toInt()
                    map.put(k.toString(), n)
                    n++
                    map.put((k*-1).toString(), n)
                    n++
                }
            }
            if (map[line[1]] == null) {         // Si la variable no tiene indice asignado, se le asigna n como indice y a su negado n+1
                if (line[1].toInt() == 0) {     // Si la variable es "0" o "-0"
                    map.put("0", n)
                    n++
                    map.put("-0", n)
                    n++
                } else {                        // Si la variable no es "0" ni "-0"
                    var k = line[1].toInt()
                    map.put(k.toString(), n)
                    n++
                    map.put((k*-1).toString(), n)
                    n++
                }
            }
        }

        var g: GrafoDirigido = GrafoDirigido(map.size)  // Grafo con las expresiones
        
        for (i in map) {
            g.arrVertices[map[i.key]!!].name = i.key       // Se le asignan los identificadores a los vertices
        }

        for (i in fileContent) {        // Ciclo que recorre el archivo de entrada y agrega los lados pertinentes
            var line = i.split(" ")
            if (line[0].toInt() != 0 && line[1].toInt() != 0) {
                var j = line[0].toInt()
                var k = line[1].toInt()
                g.agregarArco(Arco(g.arrVertices[map[(-1*j).toString()]!!], g.arrVertices[map[k.toString()]!!]))    // ¬x -> y
                g.agregarArco(Arco(g.arrVertices[map[(-1*k).toString()]!!], g.arrVertices[map[j.toString()]!!]))    // ¬y -> x
            } else {        // Caso: alguna de las variables es "0" o "-0"
                if (line[0].toInt() == 0 && line[1].toInt() != 0) { // La primera variable es "0" o "-0"
                    if (line[0].length == 1) {      // La variable es "0"
                        var k = line[1].toInt()
                        g.agregarArco(Arco(g.arrVertices[map["-0"]!!], g.arrVertices[map[k.toString()]!!]))
                        g.agregarArco(Arco(g.arrVertices[map[(-1*k).toString()]!!], g.arrVertices[map["0"]!!]))
                    } else {    // La variable es "-0"
                        var k = line[1].toInt()
                        g.agregarArco(Arco(g.arrVertices[map["0"]!!], g.arrVertices[map[k.toString()]!!]))
                        g.agregarArco(Arco(g.arrVertices[map[(-1*k).toString()]!!], g.arrVertices[map["-0"]!!]))
                    }
                } else if (line[0].toInt() != 0 && line[1].toInt() == 0) {  // La segunda variable es "0" o "-0"
                    if (line[1].length == 1) { // La variable es "0"
                        var j = line[0].toInt()
                        g.agregarArco(Arco(g.arrVertices[map[(-1*j).toString()]!!], g.arrVertices[map["0"]!!]))
                        g.agregarArco(Arco(g.arrVertices[map["-0"]!!], g.arrVertices[map[j.toString()]!!]))
                    } else {     // La variable es "-0"
                        var j = line[0].toInt()
                        g.agregarArco(Arco(g.arrVertices[map[(-1*j).toString()]!!], g.arrVertices[map["-0"]!!]))
                        g.agregarArco(Arco(g.arrVertices[map["0"]!!], g.arrVertices[map[j.toString()]!!]))
                    }
                } else {    // Ambas variables son "0" o "-0"
                    if (line[0] == "0" && line[1] == "0") {
                        g.agregarArco(Arco(g.arrVertices[map["-0"]!!], g.arrVertices[map["0"]!!]))
                    } else if ((line[0] == "0" && line[1] == "-0") || (line[0] == "-0" && line[1] == "0")) {
                        g.agregarArco(Arco(g.arrVertices[map["-0"]!!], g.arrVertices[map["-0"]!!]))
                        g.agregarArco(Arco(g.arrVertices[map["0"]!!], g.arrVertices[map["0"]!!]))
                    } else if (line[0] == "-0" && line[1] == "-0") {
                        g.agregarArco(Arco(g.arrVertices[map["0"]!!], g.arrVertices[map["-0"]!!]))
                    }
                }
            }
        }

        var cfc = CFC(g)    // Componentes fuertemente conexas del grafo de la expresión

        if (map["0"] != null) {
            if (cfc.estanEnLaMismaCFC(map["0"]!!, map["-0"]!!)) {   // "0" y "-0" estan en la misma CFC
                println("No hay asignación válida")
                return
            } else {
                for (i in 1 until g.nVertices/2) {  // Verifica si una variable y su negado estan en la misma CFC
                    if (cfc.estanEnLaMismaCFC(map[i.toString()]!!, map[(-1*i).toString()]!!)) {
                        println("No hay asignación válida")
                        return
                    }
                }
            }
        }
    

        var solucion: LinkedList<String> = LinkedList<String>() // Lista de strings con las asignaciones para que la expresion sea verdadera
        var grafoComp: GrafoDirigido = cfc.obtenerGrafoComponente() // Grafo de CFC
        
        if (CicloDigrafo(grafoComp).hayCiclo) {
            println("el grafo de CFC no es DAG")
        }

        var ordenTop: LinkedList<Int> = OrdenamientoTopologico(grafoComp).ordenTopologico  // Orden topologico de grafoComp
        var mOrden: HashMap<Int, Int> = HashMap<Int, Int>() // Mapa con el orden topologico y un entero asignado para cada nivel

        for (i in 0 until ordenTop.size) {  // Asignando los enteros al orden topológico
            mOrden.put(ordenTop[i], i)
        }

        if (map["0"] != null) {
            if (mOrden[cfc.obtenerIdentificadorCFC(map["-0"]!!)]!! < mOrden[cfc.obtenerIdentificadorCFC(map["0"]!!)]!!) { // Existe el lado -0 -> 0
                solucion.add("TRUE")
            } else {    // Existe el lado 0 -> -0
                solucion.add("FALSE")
            }
        }

        for (i in 1 until g.nVertices/2) {
            // Existe el lado ¬x -> x
            var x = mOrden[cfc.obtenerIdentificadorCFC(map[(-1*i).toString()]!!)] // ¬x
            var y = mOrden[cfc.obtenerIdentificadorCFC(map[i.toString()]!!)] // x
            if (x!! < y!!) {
                solucion.add("TRUE")
            } else { // Existe el lado x -> ¬x
                solucion.add("FALSE")
            }
        }

        var s: String = ""  // String con la solución del problema
        for (i in solucion) {
            s = s + "$i "
        }

        println(s)  // Se imprime la solución

    } catch (e: InvalidArgumentException) {
        println(e)

    } catch (e: java.io.FileNotFoundException) {
        println(e)

    } catch (e: RuntimeException) {
        println(e)

    }


    // Fin del programa
}