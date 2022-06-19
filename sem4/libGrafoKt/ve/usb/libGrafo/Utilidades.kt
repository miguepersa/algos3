package ve.usb.libGrafo

/* 
    Retorna el grafo inverso de un grafo G = (E,V) dado

    El grafo inverso G'= (V, E') donde E' contiene todos los lados (u,v) tal que (v,u) pertenece a E

*/
fun digrafoInverso(g: GrafoDirigido) : GrafoDirigido {
    var gInverso: GrafoDirigido = GrafoDirigido(g.obtenerNumeroDeVertices())
    var ladosG = g.iterator()

    for (i in ladosG) {
        gInverso.agregarArco(Arco(i.b, i.a))
    }

    return gInverso
}
