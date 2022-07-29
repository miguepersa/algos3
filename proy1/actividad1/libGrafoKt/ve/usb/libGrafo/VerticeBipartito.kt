package ve.usb.libGrafo

/* 
    Tipo de vertice usado en la clase GrafoBipartito
*/
data class VerticeBipartito(val n: Int) {
    var color: Color = Color.BLANCO
    var kcolor: Int = 1
}