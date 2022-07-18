package ve.usb.libGrafo


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
   
   var listaVertices: MutableList<Vertice>
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
      var inClosed: MutableList<Boolean> = mutableListOf() // para determinar si esta o no un elemento en closed
      
      // Inicializamos los arreglos inClosed y inOpenQueue
      for (i in 0 until g.obtenerNumeroDeVertices()) inClosed.add(false) // no hay ningun elemento en closed
            
      var openQueue: ColaDePrioridad = ColaDePrioridad()
      var inOpenQueue: MutableList<Boolean> = mutableListOf() // para determinar si esta o no un elemento en openQueue

      for (i in 0 until g.obtenerNumeroDeVertices()) inOpenQueue.add(false)
      inOpenQueue[s] = true

      listaVertices = mutableListOf()
      for (i in 0 until g.obtenerNumeroDeVertices()) {
         listaVertices.add(Vertice(i))
      }

      listaVertices[s].fHat = hHat(s)
      listaVertices[s].pred = null
      openQueue.add(listaVertices[s])

      var u: Int = openQueue.extraerMinimo()

      // Arreglo que contiene información de elementos contenidos en obj
      var objContains = Array<Boolean>(listaVertices.size, {i -> false})

      // Si i esta en objs, objContains[i] = true
      for (i in objs) {
         objContains[i] = true
      }

      while (!objContains[u]) {
         closed.add(u)

         for (v in g.adyacentes(u)) {
            if (!inClosed[v.y.n]) {
               val fHatNew = listaVertices[u].fHat + hHat(u) + v.obtenerCosto() + hHat(v.y.n)

               if (!inOpenQueue[v.y.n]) { // Acomodar lista de boolean
                  listaVertices[v.y.n].fHat = fHatNew
                  listaVertices[v.y.n].pred = listaVertices[u]
                  openQueue.add(listaVertices[v.y.n])
               } else {
                  if (fHatNew < listaVertices[v.y.n].fHat) {
                     listaVertices[v.y.n].fHat = fHatNew
                     listaVertices[v.y.n].pred = listaVertices[u]
                  }
               }
            }
         }

         u = openQueue.extraerMinimo()
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
      var v: Vertice? = listaVertices[verticeAlcanzado]
      var camino: MutableList<ArcoCosto> = mutableListOf<ArcoCosto>()
      while (v != null) {
         if (v.pred != null) {
            var u: Vertice = v.pred!!
            camino.add(ArcoCosto(u, v, v.fHat - u.fHat))
            v = v.pred
         }
      }
      return camino.asIterable()
   }
}
