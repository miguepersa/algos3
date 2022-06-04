package ve.usb.libGrafo
import java.util.LinkedList

// Implementacion de algunas funciones que son usadas en los metodos de los grafos

/* 
    Las funciones obtenerArista, obtenerAristaCosto, obtenerArco y obtenerArcoCosto 
    transforman un string con el formato
        int int -> Si es arista o arco
        int int double -> Si es aristaCosto o arcoCosto
*/

/* 
    {P: linea debe estar en formato -> int int}
    {Q: La salida es una arista con extremos (int, int)}

    Input: linea -> String que contiene informacion sobre una arista.
    Output: La arista que representa la informacion de linea
*/
fun obtenerArista(linea: String): Arista {
    val infoLinea: List<String> = linea.split(" ")

    return Arista(Vertice(infoLinea[0].toInt()), Vertice(infoLinea[1].toInt()))
} 

/* 
    {P: linea debe estar en formato -> int int double}
    {Q: La salida es una aristaCosto con extremos (int, int, double)}

    Input: linea -> String que contiene informacion sobre una aristaCosto
    Output: La arista que representa la informacion de linea
*/
fun obtenerAristaCosto(linea: String): AristaCosto {
    val infoLinea: List<String> = linea.split(" ")

    return AristaCosto(Vertice(infoLinea[0].toInt()), Vertice(infoLinea[1].toInt()), infoLinea[2].toDouble())
}

/* 
    {P: linea debe estar en formato -> int int}
    {Q: La salida es un arco con extremos <int, int>}

    Input: linea -> String que contiene informacion sobre una arista
    Output: Un arco que representa la informacion de linea
*/
fun obtenerArco(linea: String): Arco {
    val infoLinea: List<String> = linea.split(" ")

    return Arco(Vertice(infoLinea[0].toInt()), Vertice(infoLinea[1].toInt()))
}

/* 
    {P: linea debe estar en formato -> int int double}
    {Q: La salida es un arcoCosto con extremos <int, int, double>}

    Input: linea -> String que contiene informacion sobre un arcoCosto
    Output: Un arcoCosto que representa la informacion de linea
*/
fun obtenerArcoCosto(linea: String): ArcoCosto {
    val infoLinea: List<String> = linea.split(" ")

    return ArcoCosto(Vertice(infoLinea[0].toInt()), Vertice(infoLinea[1].toInt()), infoLinea[2].toDouble())
}

/* 
    Determina si existe o no una arista en un grafo

    {P: arr.size != 0}
    {Q: true = si existe la arista en arr}

    Input: arr -> Array<LinkedList<Arista>> es la representaicion de un grafo
           arista -> Arista, arista a buscar si se encuentra en el grafo
    Output: true -> Si la arista se encuentra en el grafo
            false -> Caso contrario
*/
fun aristaEnGrafoNoDirigido(arr: Array<LinkedList<Vertice>>, arista: Arista): Boolean {
    for (i in arr[arista.u.n]) {
        if (i.n == arista.v.n) {
            return true
        }
    }
    return false
}
/* 
    Determina si existe o no un arco en un grafo

    {P: arr.size != 0}
    {Q: true = si exite el arco en arr}

    Input: arr -> Array<LinkedList<Arco>> es la representacion de un grafo
           arco -> Arco, arco a buscar si se encuentra en el grafo
    Output: true -> Si la arista se encuentra en el grafo
            false -> Caso contrario
*/
fun arcoEnGrafoDirigido(arr: Array<LinkedList<Vertice>>, arco: Arco): Boolean {
    for (i in arr[arco.inicio.n]) {
        if (i.n == arco.fin.n) {
            return true
        }
    }
    return false
}

/*
    Determina si una arista está en una lista enlazada

    {P: true}
    {Q: true si la arista esta en la lista, false si no}

    Input:  lista -> LinkedList<Arista> Lista enlazada de aristas
            arista -> Arista que va a ser buscada en la lista
    Output: true -> la arista esta en la lista
            false -> la arista no esta en la lista
 */

 fun aristaEnLista(lista: LinkedList<Arista>, arista: Arista): Boolean {
    for (i in lista) {
        if ((i.v.n == arista.v.n && i.u.n == arista.u.n) || i.u.n == arista.v.n && i.v.n == arista.u.n) {
            return true
        }
    }
    return false
}

/*
    Determina si una arista con costo está en una lista enlazada

    {P: true}
    {Q: true si la arista esta en la lista, false si no}

    Input:  lista -> LinkedList<AristaCosto> Lista enlazada de aristas
            arista -> Arista que va a ser buscada en la lista
    Output: true -> la arista esta en la lista
            false -> la arista no esta en la lista
 */
fun aristaCostoEnLista(lista: LinkedList<AristaCosto>, arista: AristaCosto): Boolean {
    for (i in lista) {
        if ((i.y.n == arista.y.n && i.x.n == arista.x.n) || i.x.n == arista.y.n && i.y.n == arista.x.n) {
            return true
        }
    }
    return false
}

/*
    Determina si un arco está en una lista enlazada

    {P: true}
    {Q: true si el arco esta en la lista, false si no}

    Input:  lista -> LinkedList<Arco> Lista enlazada de arcos
            arco -> Arco que va a ser buscado en la lista
    Output: true -> El arco esta en la lista
            false -> El arco no esta en la lista
*/

fun arcoEnLista(lista: LinkedList<Arco>, arco: Arco): Boolean {
    for (i in lista) {
        if (i.inicio.n == arco.inicio.n && i.fin.n == arco.fin.n) {
            return true
        }
    }
    return false
}

/*
    Determina si un arco con costo está en una lista enlazada

    {P: true}
    {Q: true si el arco esta en la lista, false si no}

    Input:  lista -> LinkedList<ArcoCosto> Lista enlazada de arcos
            arco -> Arco que va a ser buscado en la lista
    Output: true -> El arco esta en la lista
            false -> El arco no esta en la lista
*/
fun arcoCostoEnLista(lista: LinkedList<ArcoCosto>, arco: ArcoCosto): Boolean {
    for (i in lista) {
        if (i.inicio.n == arco.inicio.n && i.fin.n == arco.fin.n) {
            return true
        }
    }
    return false
}
