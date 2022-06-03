package ve.usb.libGrafo

public open class Vertice(val etiqueta: Int) {

    var distancia: Int = -1
    var color: Color = Color.BLANCO
    var pred: Vertice? = null
    var ti: Int = 0
    var tf: Int = 0

    override fun toString(): String {
        return etiqueta.toString()
    }

}