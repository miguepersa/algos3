KC=	kotlinc
KFLAG=	-cp

all:	\
	Nodo.class \
	ListaEnlazadaSimple.class \
	MainKt.class

Nodo.class: Nodo.kt
	$(KC) Nodo.kt

ListaEnlazadaSimple.class: ListaEnlazadaSimple.kt
	$(KC) $(KFLAG) . ListaEnlazadaSimple.kt

MainKt.class: Main.kt 
	$(KC) $(KFLAG) . Main.kt

clean:
	rm -rf *.class META-INF
