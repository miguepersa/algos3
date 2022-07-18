package ve.usb.libGrafo

public open class Vertice(val n: Int): Comparable<Vertice> {

    var dist: Int = Int.MAX_VALUE
    var color: Color = Color.BLANCO
    var pred: Vertice? = null
    var d: Int = 0
    var f: Int = 0
    var cc: Int = 0
    var key: Double = Double.MAX_VALUE
    var name: String = ""
    var fHat: Double = 0.0

    override fun toString(): String {
        return n.toString()
    }

    override fun compareTo(other: Vertice): Int {
        if (this.key > other.key) {
            return 1
        }

        if (this.key < other.key) {
            return -1
        }

        return 0
    }

}