class Nodo<T>(valor: T){
    var valor: T = valor
    var proximo: Nodo<T>? = null

    override fun toString(): String {
	return "${valor}"
    }
}
