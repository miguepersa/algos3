class ListaEnlazadaSimple<T>  : Iterable<T> {
    private var cabeza: Nodo<T>? = null
    private var tamanio = 0

    fun esVacia(): Boolean {
	return tamanio == 0
    }

    fun numElementos() : Int {
	return tamanio
    }
    
    fun add(valor: T): ListaEnlazadaSimple<T> {
	val nuevo = Nodo(valor)
	if (cabeza == null) {
	    cabeza = nuevo
	} else {
	    nuevo.proximo = cabeza
	    cabeza = nuevo
	}
	tamanio++
	return this
    }

    override fun toString(): String {
	var s: String
	if (esVacia()) {
	   s = "Lista vacia"
	} else {
	    var actual: Nodo<T>? = cabeza
	    s = actual.toString()
	    actual = actual?.proximo
	    while(actual != null) {
		s = "$s -> ${actual.toString()}"
		actual = actual.proximo
	    }
	}
	return s
    }

     inner class ListaIterato<T>(l: ListaEnlazadaSimple<T>) : Iterator<T> {
	var actual = l.cabeza
	override fun hasNext(): Boolean = (actual != null) 
	override fun next(): T {
	    if (actual == null) {
		throw NoSuchElementException("Error, no hay mas elementos que iterar")
	    }
	    val valor = actual!!.valor
	    actual = actual?.proximo
	    return valor
	}
    }

    override operator fun iterator() : Iterator<T> = ListaIterato(this)
}
