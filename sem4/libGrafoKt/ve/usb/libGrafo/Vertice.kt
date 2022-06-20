package ve.usb.libGrafo

public open class Vertice(val n: Int) {

    var dist: Int = Int.MAX_VALUE
    var color: Color = Color.BLANCO
    var pred: Vertice? = null
    var d: Int = 0
    var f: Int = 0
    var cc: Int = 0

    override fun toString(): String {
        return n.toString()
    }

}