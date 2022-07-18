package ve.usb.libGrafo
import java.util.PriorityQueue

data class VerticeAEstrella(val n: Int) {
   var pred: VerticeAEstrella? = null
   var fHat: Double = Double.MAX_VALUE
}
/*
 Implementación del algoritmo de A* para encontrar un camino
 desde un vértice fuente s fijo hasta alguno de los vértices objetivos. 
 La implementación debe usar como cola de prioridad un heap.
 
 Los parármetros de entradas son los siguientes:
 g: Digrafo con los costos en los lados. 
 s: Vértice de partida.
 objs: Conjunto de vértices objetivos. 
 hHat: función estimativa de h llamada h sombrero. Recibe como entrada un vértice v y retorna 
    el costo estimado desde ese vértice v, hasta cualquiera de los vértices 
    de la meta en objs.

 Como precondiciones se tiene que:
    * Si el grafo de entrada tiene un lado con costo negativo,  entonces se retorna una RuntimeException.
    * Si el vértice s no pertenece al grafo, entonces se retorna una RuntimeException.
    * Si alguno de los vértices en objs no pertenece al grafo, entonces se retorna una RuntimeException.
    * Al menos uno de los vértices de objs debe ser alcanzable desde s. Esta precondición no se chequea por razones de eficiencia.
*/
public class AEstrella(val g: GrafoDirigidoCosto, 
		      val s: Int,
		      val objs: Set<Int>,
		      val hHat: (Int) -> Double) {
   
   var listaVertices: MutableList<VerticeAEstrella>
   var verticeAlcanzado: Int

   init {
      // Verificamos que el grafo no tenga lado con costo negativo
      for (l in g) {
         if (l.obtenerCosto() < 0) { 
            throw RuntimeException("AEstrella.init: El grafo dado tiene lado con costo negativo")
         }
      }

      // Verificamos que el vertice s pertenezca al grafo
      if (s < 0 || s >= g.obtenerNumeroDeVertices()) {
         throw RuntimeException("AEstrella.init: El vertice fuente $s no pertenece al grafo")
      }

      // Verificamos que los vertices objs pertenezcan al grafo
      for (v in objs) {
         if (v < 0 || v >= g.obtenerNumeroDeVertices()) {
            throw RuntimeException("AEstrella.init: El vertive objetivo $v no pertenece al grafo")
         }
      }

      var closed: MutableList<Int> = mutableListOf()
      var closedContains: MutableList<Boolean> = mutableListOf()
      for (i in 0 until g.obtenerNumeroDeVertices()) closedContains.add(false) // closed esta vacia
            
      var openQueue: PriorityQueue<VerticeAEstrella> = PriorityQueue(compareBy{ it.fHat })
      var openQueueContains: MutableList<Boolean> = mutableListOf() 
      for (i in 0 until g.obtenerNumeroDeVertices()) openQueueContains.add(false) // openQueue esta vacia


      listaVertices = mutableListOf()
      for (i in 0 until g.obtenerNumeroDeVertices()) {
         listaVertices.add(VerticeAEstrella(i))
      }

      listaVertices[s].fHat = hHat(s)
      listaVertices[s].pred = null

      openQueue.add(listaVertices[s])
      var u: Int = openQueue.remove().n

      // Arreglo que contiene información de elementos contenidos en obj
      val objContains: MutableList<Boolean> = mutableListOf()
      for (i in 0 until g.obtenerNumeroDeVertices()) objContains.add(false)
      for (i in objs) objContains[i] = true
      
      while (!objContains[u]) {
         closed.add(u)
         closedContains[u] = true

         for (v in g.adyacentes(u)) {
            if (!closedContains[v.y.n]) {
               val fHatNew = listaVertices[u].fHat - hHat(u) + v.obtenerCosto() + hHat(v.y.n)

               if (!openQueueContains[v.y.n]) { 

                  listaVertices[v.y.n].fHat = fHatNew
                  listaVertices[v.y.n].pred = listaVertices[u]

                  openQueueContains[v.y.n] = true
                  openQueue.add(listaVertices[v.y.n])

               } else {

                  if (fHatNew < listaVertices[v.y.n].fHat) {
                     openQueue.remove(listaVertices[v.y.n])
                     listaVertices[v.y.n].fHat = fHatNew
                     listaVertices[v.y.n].pred = listaVertices[u]
                     openQueue.add(listaVertices[v.y.n])
                  }

               }
            }
         }
         u = openQueue.remove().n
         openQueueContains[u] = false
      }

      verticeAlcanzado = u      
   }
   // Retorna el vértice meta del conjunto objs que fue alcanzado por el algoritmo.
   fun objetivoAlcanzado() : Int = verticeAlcanzado

   // Retorna el costo del camino desde s hasta el vértice objetivo alcanzado. 
   fun costo() : Double {
      return listaVertices[verticeAlcanzado].fHat
   }

   // Retorna los arcos del camino desde s hasta el vértice objetivo alcanzado. 
   fun obtenerCamino() : Iterable<ArcoCosto> {
      var v: VerticeAEstrella = listaVertices[verticeAlcanzado]
      var camino: MutableList<ArcoCosto> = mutableListOf<ArcoCosto>()
      while (v != null && v.n != s) {
         if (v.pred != null) {
            var u: VerticeAEstrella = v.pred!!
            camino.add(0, ArcoCosto(Vertice(u.n), Vertice(v.n), v.fHat - u.fHat))
            v = v.pred!!
         }
      }
      return camino.asIterable()
   }
}
