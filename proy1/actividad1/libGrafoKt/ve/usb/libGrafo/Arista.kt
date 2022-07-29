package ve.usb.libGrafo

public open class Arista(val v: Vertice, val u: Vertice) : Lado(v, u) {

    /* 
        Representación en string de la arista. La representacion escogida fue (v, u)

        {P: true}
        {Q: true}

        Input: ~~
        Output: La representacion del arco

        Tiempo de ejecucion O(1)
    */ 
    override fun toString() : String {
        return "($v, $u)"
    }

} 
