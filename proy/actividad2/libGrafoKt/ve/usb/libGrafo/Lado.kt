package ve.usb.libGrafo

abstract class Lado(val a: Vertice, val b: Vertice) {

    /* 
        Retorna uno de los extremos del lado
        {P: true}
        {Q: Sea x el valor que retorne se cumple que x == a || x == b}

        Input: ~~
        Output: opt es par -> a
                opt es impar -> b

        Se decide cual de los dos lados se devuelve de manera aleatoria. Cada lado
        tiene la misma probabilidad de ser escogido

        Tiempo de ejecucion O(1)
    */
    fun cualquieraDeLosVertices() : Vertice {
        val opt: Boolean = (0..100).random() % 2 == 0

        if (opt) {
            return a
        }

        return b
    }

    /* 
        Dado un vertice w, si w == a entonces retorna b, de lo contrario si w == b  entonces 
        retorna a,  y si w no es igual a a ni a b, entonces se lanza una RuntimeExpception
    
        {P: true}
        {Q: Sea x el valor que retorne se cumple que x = a == w = a || x = b == w = b}
        
        Tiempo de ejecucion O(1)
    */
    fun elOtroVertice(w: Int) : Vertice {
        if (w != a.n && w != b.n) {
            throw RuntimeException("Error en elOtroVertice($w). \n Ninguno de los extremos del lado es $w")

        }

        if (w == a.n) {
            return b
        }

        return a
    }
}
