package ve.usb.libGrafo
import java.util.LinkedList
import java.util.ArrayDeque

/*
  Determina las componentes conexas de un grafo no dirigido usando DFS. 
  La componentes conexas se determinan cuando 
  se crea un nuevo objeto de esta clase.
*/
public class ComponentesConexasDFS(val g: GrafoNoDirigido) {

    var tiempo: Int
    var arrVertices: Array<Vertice> = Array<Vertice>(g.obtenerNumeroDeVertices(), {i -> Vertice(i)}) // color blanco y dist 0 y pred nulo
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

    private fun dfsVisit(g: GrafoNoDirigido, u: Int) {
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

    private fun primerVecinoBlanco(g: GrafoNoDirigido, u: Int): Int {
      for (i in g.repGrafo[u]) {
        if (arrVertices[i.n].color == Color.BLANCO) {
          return i.n
        }
      }
      return -1
    }


    /*
     Retorna true si los dos vertices están en la misma componente conexa y
     falso en caso contrario. Si el algunos de los dos vértices de
     entrada, no pertenece al grafo, entonces se lanza un RuntineException
     */
    fun estanMismaComponente(v: Int, u: Int) : Boolean = arrVertices[v].cc == arrVertices[u].cc

    // Indica el número de componentes conexas
    fun nCC() : Int = contCC

    /*
     Retorna el identificador de la componente conexa donde está contenido 
     el vértice v. El identificador es un número en el intervalo [0 , nCC()-1].
     Si el vértice v no pertenece al grafo g se lanza una RuntimeException
     */
    fun obtenerComponente(v: Int) : Int = arrVertices[v].cc

    /* Retorna el número de vértices que conforman una componente conexa dada.
     Se recibe como entrada el identificado de la componente conexa.
     El identificador es un número en el intervalo [0 , nCC()-1].
     Si el identificador no pertenece a ninguna componente conexa, entonces se lanza una RuntimeException
     */
    fun numVerticesDeLaComponente(compID: Int) : Int {
      var cont = 0
      for (i in arrVertices) {
        if (i.cc == compID) {
          cont++
        }
      }
      return cont
    }

}
