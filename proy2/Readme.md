Explicacion de la solucion

Se decidio usar un grafo no dirigido para tener ambas direcciones de la calle.
Creamos un arreglo de horarios de limpieza. A cada lado le agregamos su
horario de limpieza.

Cada lado contiene sus horarios de limpiezas.

El camino que se debe seguir es un camino de costo minimo y se decidio usar el algoritmo
de dijkstra. Para este problema, el primer tiempo (tf) que demoramos en cruzar la calle (u, v)
depende del tiempo en el que llegamos al vertice u (u.d). 

Tenemos dos casos.

En el instante u.d la calle (u,v) esta siendo limpiada. Esto existe un horario de limpieza
programado para la calle (u,v) tal que t_inicial <= u.d < t_final. No consideramos la igualdad
en t_final pues en ese caso llegamos a la calle justo en el instante en el que termina la 
ecuacion. En este caso el tf es igual al tiempo que se demora esperando a que termine la limpieza
mas el tiempo en cruzar la calle sin nieve

En el instante u.d la calle (u,v) no esta siento limpiada. Obtenemo la ultima limpieza y 
el tiempo que ha transcurrido desde la ultima limpieza hasta u.d, luego el tiempo en cruzar la 
calle la obtenemos con la formula dada.

Ahora, bastaria con revisar si cruzando la calle no ocurre una limpieza. Para resolver esto
revisamos los horario y vemos si sus tiempo de limpieza estan dentro del intervalo [u.d, tf+u.d].
Por cada horario dentro de ese intervalo volvemos a calcular un nuevo tf que contiene la espera
desde u.d hasta ese tiempo de limpieza dentro del horario. Hacemos esto hasta que tf contenga
el tiempo de todas las esperas necesarias para cruzar la calle y que no empieze una limpieza a 'mitad
de camino'.

Este ultimo tf es el costo de cruzar la calle (u, v) en el instante en el que llegamos a u.

Luego, modificamos dijkstra para que se detuviera el ciclo while cuando el minimo de la cola 
sea el estadio.

Luego, en el main, mostramos el camino solucion y el tiempo total