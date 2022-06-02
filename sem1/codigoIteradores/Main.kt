fun main() {
    try {
	val miLista = ListaEnlazadaSimple<Int>()
	miLista.add(2)
	miLista.add(34)
	miLista.add(98)
	println(miLista.toString())
	println("\nIterando con un Iterable")
	for (e in miLista) {
	    println(e)
	}
	println("\nIterando con Iterator")
	val lit = miLista.iterator()
	while(lit.hasNext()) {
	    println(lit.next())
	}
	println(lit.next()) // Se lanza una excepcion
    } catch(e: Exception) {
	println("Error faltal, el programa se aborta")
	e.printStackTrace()
    }
}
