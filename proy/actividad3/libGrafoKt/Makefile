KC=	kotlinc
KFLAG=	-cp
GLIB=	ve/usb/libGrafo

all:	libGrafoKt.jar

libGrafoKt.jar: $(GLIB)/Color.class\
		$(GLIB)/Exceptions.class\
		$(GLIB)/Vertice.class\
		$(GLIB)/Lado.class\
		$(GLIB)/Arco.class\
		$(GLIB)/FuncionesExtras.class\
		$(GLIB)/Grafo.class\
		$(GLIB)/GrafoDirigido.class\
		$(GLIB)/DFS.class\
		$(GLIB)/Utilidades.class\
		$(GLIB)/CFC.class\
		$(GLIB)/OrdenamientoTopologico.class

	jar -cvf $@ $(GLIB)/*.class 

$(GLIB)/Color.class: $(GLIB)/Color.kt
	$(KC) $(GLIB)/Color.kt

$(GLIB)/Exceptions.class: $(GLIB)/Exceptions.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Exceptions.kt

$(GLIB)/Vertice.class: $(GLIB)/Vertice.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Vertice.kt

$(GLIB)/Lado.class: $(GLIB)/Lado.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Lado.kt

$(GLIB)/Arco.class: $(GLIB)/Arco.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Arco.kt

$(GLIB)/FuncionesExtras.class: $(GLIB)/FuncionesExtras.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/FuncionesExtras.kt

$(GLIB)/Grafo.class: $(GLIB)/Grafo.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Grafo.kt

$(GLIB)/GrafoDirigido.class: $(GLIB)/GrafoDirigido.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/FuncionesExtras.kt $(GLIB)/GrafoDirigido.kt

$(GLIB)/DFS.class: $(GLIB)/DFS.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/DFS.kt

$(GLIB)/Utilidades.class: $(GLIB)/Utilidades.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Utilidades.kt

$(GLIB)/OrdenamientoTopologico.class: $(GLIB)/OrdenamientoTopologico.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/OrdenamientoTopologico.kt

$(GLIB)/CFC.class: $(GLIB)/CFC.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/OrdenamientoTopologico.kt $(GLIB)/CFC.kt

clean:
	rm -rf libGrafoKt.jar META-INF $(GLIB)/*.class
