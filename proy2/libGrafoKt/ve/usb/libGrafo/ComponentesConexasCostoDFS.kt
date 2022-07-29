package ve.usb.libGrafo
import java.util.LinkedList
import java.util.ArrayDeque

/* 
    Determina las componentes conexas de un grafo no dirigido costo usando DFS.
    Las componentes conexas se determinan cuando se crea un nuevo objeto de la clase
*/
public class ComponentesConexasCostoDFS(val g: GrafoNoDirigidoCosto) {
    var tiempo: Int
    var arrVertices: Array<Vertice> = Array<Vertice>(g.obtenerNumeroDeVertices(), {i -> Vertice(i)})
    var contCC: Int


    init {
        tiempo = 0
        contCC = 0
        for (v in arrVertices) {
            if (v.color == Color.BLANCO) {
                contCC++
                dfsVisit(g, v.n)
            }
        }
    }

    private fun dfsVisit(g: GrafoNoDirigidoCosto, u: Int) {
        tiempo++
        var stack = ArrayDeque<Int>()
        stack.push(arrVertices[u].n)
        arrVertices[u].d = tiempo
        arrVertices[u].color = Color.GRIS

        while (stack.size > 0) {
          var v1 = stack.peek()
          arrVertices[v1].cc = contCC
          var v2 = primerVecinoBlanco(g,v1)
          if (v2 == -1) {
            stack.pop()
            tiempo++
            arrVertices[v1].f = tiempo
            arrVertices[v1].color = Color.NEGRO
          }
          else {
            arrVertices[v2].pred = arrVertices[u]
            tiempo++
            arrVertices[v2].d = tiempo
            arrVertices[v2].color = Color.GRIS
            stack.push(v2)
          }
        }
    }

    private fun primerVecinoBlanco(g: GrafoNoDirigidoCosto, u: Int): Int {
      for (i in g.repGrafo[u]) {
        if (arrVertices[i.n].color == Color.BLANCO) {
          return i.n
        }
      }
      return -1
    }

    // Indica el numero de componentes conexas
    fun nCC(): Int = contCC

}