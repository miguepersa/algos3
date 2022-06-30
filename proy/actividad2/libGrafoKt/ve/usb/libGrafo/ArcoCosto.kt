package ve.usb.libGrafo

public class ArcoCosto(val x: Vertice, val y: Vertice, val costo: Double) : Arco(x, y) {

    /* 
        Retorna el costo asociado del arco

        {P: true}
        {Q: true}

        Input: ~~
        Output: El costo asociado del arco

        Tiempo de ejecucion O(1)
    */ 
    fun obtenerCosto() : Double {
        return costo
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
