package ve.usb.libGrafo

public class AristaCosto(val x: Int,
			 val y: Int,
			 val costo: Double) : Comparable<AristaCosto>, Arista(x, y) {

    /* 
          Retorna el costo del arco

          {P: true}
          {Q: true}

          Input: ~~
          Output: El Costo asociado a la arista.

          Tiempo de ejecucion O(1)
    */
    fun obtenerCosto() : Double {
         return costo
    }

    /* 
          Representación en string de la arista. La representacion escogida fue similar a la de
          arista con una coordenada adicional para el costo

          {P: true}
          {Q: true}

          Input: ~~
          Output: La representacion de la arista.

          Tiempo de ejecucion O(1)
    */
    override fun toString() : String {
         return "($x, $y, $costo)"
    }

    /* 
          Se comparan dos aristas con respecto a su costo. Una arista es mayor a otra
          si el costo es mayor.
          
          {P: other es una AristaCosto}
          {Q: Retorna el valor al evaluar la relacion de orden}

          Input: Otra AristaCosto
          Output: 1 -> Si this > other
                  -1 -> Si this < other
                  0 -> Si this == other

          Tiempo de ejecucion O(1)
     */
     override fun compareTo(other: AristaCosto): Int {
          if (this.obtenerCosto() > other.obtenerCosto()) {
               return 1
          }

          if (this.obtenerCosto() < other.obtenerCosto()) {
               return -1
          }

          return 0
     }
} 
