package ve.usb.libGrafo

public class ArcoCosto(val x: Vertice, val y: Vertice, 
                        val costo: Int, 
                        val horarios: MutableList<Pair<Int, Int>>) : Arco(x, y) {

    /* 
        Obtiene el costo asociado de cruzar la calle

        Tenemos dos casos:
            La calle esta siendo limpiada
            La calle no esta siendo limpiada

        Cuando la calle esta siendo limpieada:
            Tom tiene que esperar a que limpien la calle y luego cruzarla.
            El costo es la suma del tiempo que tiene que esperar tom a que
            limpien la calle mas el tiempo de cruzar la calle cuando esta limpia

        Cuando no esta siendo limpiada
            El tiempo es calculado usando la siguiente formula
            min(techo((1+T/100)*ti), 100500*ti) donde T es el tiempo que lleva la 
            calle recibiendo nieve y ti es el tiempo que toma cruzar la calle
            son nieve. T se obtiene restando el tiempo actual con el tiempo de la ultima
            limpieza 
                
        {P: t >= 0}
        {Q: true}

        Input: ~~
        Output: El costo asociado del arco

        Tiempo de ejecucion O(1)
    */ 
    fun obtenerCosto(t: Int) : Int {
        // Determinamos si la calle esta siendo limpiada.
        val limpieza: Pair<Boolean, Int> = estaSiendoLimpiada()
        
        if (limpieza.first) { // La calle se esta limpiando
            val tiempoEspera: Int = limpieza.second - t
            return tiempoEspera + costo
        
        } else { // La calle no se esta limpiando 
            // Determinamos cual fue la ultima limpieza
            var ultimaLimpieza: Int = obtenerUltimaLimpieza(t)

        }
    }

    /* 
        Determina si la calle esta siendo limpiada en el tiempo t

        En caso de estar limpie se devuelve el tiempo en el que termina
        la limpieza

        {P: t >= 0}
        {Q: true si existe un elemento en horarios tal que horarios.first <= t <= horarios.secons}

        Tiempo de ejecucion O(horarios.size)
    */
    private fun estaSiendoLimpiada(t: Int): Pair<Boolean, Int> {
        for (horario in horarios) {
            if (horario.first <= t && t <= horario.second) {
                return Pair(true, horario.second)
            }
        }

        return Pair(false, 0)
    }

    /* 
        Determinamos el tiempo de la ultima limpieza

        Para esto obtemelos los tiempos finales que son menores 
        a t.

        Sean tf_i los tiempos finales menores a t.


    */
    private fun obtenerUltimaLimpieza(t: Int): Int {
        var tiempo: Int = 0
        var ultimaLimpieza: Int = 0
        
    }

    /* 
        Representación del arco como String. Misma representacion del arco pero con una 
        tercera coordenada para el costo. Es decir <x, y, costo>

        {P: true}
        {Q: true}

        Input: ~~
        Output: Representacion del arco con costo como String
        Tiempo de ejecucion O(1)    
    */ 
    override fun toString() : String {
        return "<$x, $y, $costo>"
    }

    fun determinarUltimaLimpieza()
} 
