package ve.usb.libGrafo
import kotlin.math.ceil

public class ArcoCosto(val x: Vertice, val y: Vertice, 
                        val costo: Int, 
                        val horarios: MutableList<Pair<Int, Int>>) : Arco(x, y) {
    
    
    /*  
        Obtenemos el costo de cruzar el camino, el costo de cruzar
        depende del tiempo en el que llegamos al vertice x

        Para determiar este costo tenemos dos casos

        El lado se esta limpiando cuando llegamos al vertice x
            esperamos a que termine la limpieza y cruzamos

        El lado no se esta limpiando cuando llegamos al vertice x
            calculamos el coste de cruzar usando la formula dada

        Con estos primeros costos procedemos a determinar si el tiempo final
        es un tiempo valido.

        Esto es que no se crucemos la calle mientras ocurre un mantenimiento
        y que el tiempo final no termine entre un mantenimiento
                
        {P: t >= 0}
        {Q: el tiempo que demoramos cruzando el lado}

        Input: ~~
        Output: El tiempo que demoramos cruzando el lado, este tiempo contempla
                las esperas necesarias

        Tiempo de ejecucion O(horarios.size)
    */ 
    fun obtenerCosto(t: Int) : Int {
        // Determinamos si la calle esta siendo limpiada.
        val limpieza: Pair<Boolean, Int> = estaSiendoLimpiada(t)
        val tiempoEspera: Int
        val tiempoCosto: Int
        
        if (limpieza.first) { // La calle se esta limpiando
            tiempoEspera = limpieza.second - t
            tiempoCosto = tiempoEspera + costo
        
        } else { // La calle no se esta limpiando 
            // Determinamos cual fue la ultima limpieza
            val ultimaLimpieza: Int = obtenerUltimaLimpieza(t)
            tiempoCosto = minOf(ceil((1.0 + (ultimaLimpieza.toDouble() / 100.0))*costo.toDouble()), (100500*costo).toDouble()).toInt()
        }

        return obtenerTiempoFinal(t, tiempoCosto)
    }

    /* 
        Determina si la calle esta siendo limpiada en el tiempo t

        En caso de estar limpie se devuelve el tiempo en el que termina
        la limpieza

        {P: t >= 0}
        {Q: true si existe un elemento en horarios tal que horarios.first <= t <= horarios.second
            o false y 0 caso contrarior}

        Input: t: Int tiempo en que llegamos al vertice x

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
        Determina el tiempo final revisando si este no se solapa con algun
        otro horario.

        Revisamos los horarios tales que esten despues de ti 
        tales que tf solape con algun programa de limpieza

        en caso de que solape, se espera el tiepmo necesari y reasignamos
        el tiempo final

        este proceso se vuelve a repetir hasta que tf contenga las esperas necesarias

        {P: 0 <= ti <= tf}
        {Q: true si solo hay una sola limpieza entre ti y tf y en tf no hay limpieza}

        Input: ti y tf es un entero
        Output: true si no hay limpieza entre ti y tf

        Tiempo de ejecucion O(horarios.size)
    */
    private fun obtenerTiempoFinal(ti: Int, tf: Int): Int {
        var tfAux: Int = tf

        for (horario in horarios) {
            // verificamos los horarios que ocurren despues del tiempo inicial 
            if (ti < horario.first) {
                //verificamos si luego luego de cruzar
                // esta luego de un horario o si esta justo en el horario
                if (horario.second + ti <= tfAux || horario.first < tfAux + ti) {
                    tfAux = horario.second - ti + costo // tiempo de espera + costo sin nieve, todavia no cruzamos
                }
            }
        }

        return tfAux
    }  
    
    /* 
        Determinamos el que momento ocurrio la ultima limpieza
        de la calle.

        para esto revisamos todos las limpiezas que esten antes de t
        y vamos guardando la mas reciente

        {P: t>= 0}
        {Q: se duelve el tiepmo final de la limpieza mas cercana a t}

        Inputt: t: Int tiempo en el que llegamos al vertice x
        Output: Tiempo de la ultima limpieza

        Tiempo de ejecucion O(horarios.size)
    */
    private fun obtenerUltimaLimpieza(t: Int): Int {
        var ultimaLimpieza: Int = 0
        
        for (horario in horarios) {
            if (horario.first < t) {
                if (horario.second > ultimaLimpieza) ultimaLimpieza = horario.second
            }
        }

        return ultimaLimpieza
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
} 
