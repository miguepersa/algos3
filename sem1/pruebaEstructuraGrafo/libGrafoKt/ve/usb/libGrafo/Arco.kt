package ve.usb.libGrafo

public open class Arco(val inicio: Int, val fin: Int) : Lado(inicio, fin) {

    /*
        Retorna el vértice inicial del arco
        
        {P: true}
        {Q: Sea x el valor que retorna, se cumple que x = Arco.inicio}

        Input: ~~
        Output: El extremo inicial del Arco

        Tiempo de ejecucion O(1)
    */
    fun fuente() : Int {
        return inicio
    }

    /*
        Retorna el vértice final del arco

        {P: true}
        {Q: Sea x el valor que retorna, se cumple que x = Arco.fin}

        Input: ~~
        Output: El extremo final del Arco

        Tiempo de ejecucion O(1)
    */ 
    fun sumidero() : Int {
        return fin
    }

    /* 
        Representacion del arco. La representacion escogida fue <inicio, fin>

        {P: true}
        {Q: Rtrue}

        Input: ~~
        Output: La representacion del arco

        Tiempo de ejecucion O(1)
    */
    override fun toString() : String {
        return "<$inicio, $fin>"
    }
} 
