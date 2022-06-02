//import ve.usb.libGrafo.*
import java.io.File
/* 
    Input: Arreglo de string que contiene los parametros con los cuales se ejecuto el programa
    Output: true -> Si el primer elemento esta en formato correcto.
            false -> Caso contrario

    El formato correcto es que sea de la gorma -g[d|n|c|p] 
*/
fun verificarTipoGrafoEntrada(args: Array<String>): Boolean {
    val par: String = args[0]

    if (par.length != 3) {
        return false
    }

    if (par[0] != '-' || par [1] != 'g') {
        return false
    }

    if (par[2] != 'd' && par[2] != 'n' && par[2] != 'c' && par[2] != 'p') {
        return false
    }

    return true
}


fun main(args: Array<String>) {
    try {

        if(args.size != 2) {
            throw InvalidArgumentException("Cantidad de parametros erronea.")
        }

        if (!verificarTipoGrafoEntrada(args)) {
            throw InvalidArgumentException("Error el primer argumento. Formato correcto -g[d|n|c|p]")
        }

    } catch (e: InvalidArgumentException) {
        println(e)

    } catch (e: java.io.FileNotFoundException) {
        println(e)

    }
}