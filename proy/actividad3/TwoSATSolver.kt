import ve.usb.libGrafo.*
import java.util.HashMap
import java.util.LinkedList
import java.io.File

fun main(args: Array<String>) {
    val fileContent: List<String> = File(args[0]).readLines()
    var map: HashMap<String, Int> = HashMap<String, Int>()
    var vertices: LinkedList<String> = LinkedList<String>()
    var n: Int = 0
    for (i in fileContent) {
        var line = i.split(" ")
        if (map[line[0]] == null) {
            if (line[0].toInt() == 0) {
                map.put("0", n)
                vertices.add("0")
                n++
                map.put("-0", n)
                vertices.add("-0")
                n++
            } else {
                var k = line[0].toInt()
                map.put(k.toString(), n)
                vertices.add(k.toString())
                n++
                map.put((k*-1).toString(), n)
                vertices.add(k.toString())
                n++
            }
        }
        if (map[line[1]] == null) {
            if (line[1].toInt() == 0) {
                map.put("0", n)
                vertices.add("0")
                n++
                map.put("-0", n)
                vertices.add("-0")
                n++
            } else {
                var k = line[1].toInt()
                map.put(k.toString(), n)
                vertices.add(k.toString())
                n++
                map.put((k*-1).toString(), n)
                vertices.add(k.toString())
                n++
            }
        }
    }

    var g: GrafoDirigido = GrafoDirigido(map.size)
    for (i in map) {
        g.arrVertices[map[i.key]!!].name = i.key
    }

    for (i in fileContent) {
        var line = i.split(" ")
        if (line[0].toInt() != 0 && line[1].toInt() != 0) {
            var j = line[0].toInt()
            var k = line[1].toInt()
            g.agregarArco(Arco(g.arrVertices[map[(-1*j).toString()]!!], g.arrVertices[map[k.toString()]!!]))
            g.agregarArco(Arco(g.arrVertices[map[(-1*k).toString()]!!], g.arrVertices[map[j.toString()]!!]))
        } else {
            if (line[0].toInt() == 0 && line[1].toInt() != 0) {
                if (line[0].length == 1) {
                    var k = line[1].toInt()
                    g.agregarArco(Arco(g.arrVertices[map["-0"]!!], g.arrVertices[map[k.toString()]!!]))
                    g.agregarArco(Arco(g.arrVertices[map[(-1*k).toString()]!!], g.arrVertices[map["0"]!!]))
                } else {
                    var k = line[1].toInt()
                    g.agregarArco(Arco(g.arrVertices[map["0"]!!], g.arrVertices[map[k.toString()]!!]))
                    g.agregarArco(Arco(g.arrVertices[map[(-1*k).toString()]!!], g.arrVertices[map["-0"]!!]))
                }
            } else if (line[0].toInt() != 0 && line[1].toInt() == 0) {
                if (line[1].length == 1) {
                    var j = line[0].toInt()
                    g.agregarArco(Arco(g.arrVertices[map[(-1*j).toString()]!!], g.arrVertices[map["0"]!!]))
                    g.agregarArco(Arco(g.arrVertices[map["-0"]!!], g.arrVertices[map[j.toString()]!!]))
                } else {
                    var j = line[0].toInt()
                    g.agregarArco(Arco(g.arrVertices[map[(-1*j).toString()]!!], g.arrVertices[map["-0"]!!]))
                    g.agregarArco(Arco(g.arrVertices[map["0"]!!], g.arrVertices[map[j.toString()]!!]))
                }
            } else {
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

    var cfc = CFC(g)

    if (cfc.estanEnLaMismaCFC(map["0"]!!, map["-0"]!!)) {
        println("No hay asignación válida")
        return
    } else {
        for (i in 1 until g.nVertices/2) {
            if (cfc.estanEnLaMismaCFC(map[i.toString()]!!, map[(-1*i).toString()]!!)) {
                println("No hay asignación válida")
                return
            }
        }
    }

    var solucion: LinkedList<String> = LinkedList<String>()
    var grafoComp: GrafoDirigido = cfc.obtenerGrafoComponente()
    var ordenTop: LinkedList<Int> = OrdenamientoTopologico(grafoComp).ordenTopologico
    var mOrden: HashMap<Int, Int> = HashMap<Int, Int>()

    for (i in 0 until ordenTop.size) {
        mOrden.put(ordenTop[i], i)
    }

    if (mOrden[map["-0"]!!]!! < mOrden[map["0"]!!]!!) {
        solucion.add("TRUE")
    } else {
        solucion.add("FALSE")
    }

    for (i in 1 until g.nVertices/2) {
        if (mOrden[cfc.obtenerIdentificadorCFC(map[(-1*i).toString()]!!)]!! < mOrden[cfc.obtenerIdentificadorCFC(map[i.toString()]!!)]!!) {
            solucion.add("TRUE")
        } else {
            solucion.add("FALSE")
        }
    }

    var s: String = ""
    for (i in solucion) {
        s = s + "$i "
    }

    println(s)
}