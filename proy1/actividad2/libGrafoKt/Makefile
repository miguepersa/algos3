KC=	kotlinc
KFLAG=	-cp
GLIB=	ve/usb/libGrafo

all:	libGrafoKt.jar

libGrafoKt.jar: $(GLIB)/Color.class\
		$(GLIB)/Exceptions.class\
		$(GLIB)/Vertice.class\
		$(GLIB)/Lado.class\
		$(GLIB)/Arco.class\
		$(GLIB)/ArcoCosto.class\
		$(GLIB)/Arista.class\
		$(GLIB)/AristaCosto.class\
		$(GLIB)/FuncionesExtras.class\
		$(GLIB)/Grafo.class\
		$(GLIB)/GrafoDirigido.class\
		$(GLIB)/DFSCaminos.class

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

$(GLIB)/ArcoCosto.class: $(GLIB)/ArcoCosto.kt 
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/ArcoCosto.kt

$(GLIB)/Arista.class: $(GLIB)/Arista.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Arista.kt

$(GLIB)/AristaCosto.class: $(GLIB)/AristaCosto.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/AristaCosto.kt

$(GLIB)/FuncionesExtras.class: $(GLIB)/FuncionesExtras.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/FuncionesExtras.kt

$(GLIB)/Grafo.class: $(GLIB)/Grafo.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Grafo.kt

$(GLIB)/GrafoNoDirigido.class: $(GLIB)/GrafoNoDirigido.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/FuncionesExtras.kt $(GLIB)/GrafoNoDirigido.kt

$(GLIB)/GrafoDirigido.class: $(GLIB)/GrafoDirigido.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/FuncionesExtras.kt $(GLIB)/GrafoDirigido.kt

$(GLIB)/BFS.class: $(GLIB)/BFS.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/BFS.kt

$(GLIB)/DFS.class: $(GLIB)/DFS.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/DFS.kt

$(GLIB)/TablaGrafoNoDirigido.class: $(GLIB)/TablaGrafoNoDirigido.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/TablaGrafoNoDirigido.kt
	
$(GLIB)/VerticeBipartito.class: $(GLIB)/VerticeBipartito.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/VerticeBipartito.kt

$(GLIB)/GrafoBipartito.class: $(GLIB)/GrafoBipartito.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/GrafoBipartito.kt

$(GLIB)/DFSCaminos.class: $(GLIB)/DFSCaminos.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/DFSCaminos.kt

clean:
	rm -rf libGrafoKt.jar META-INF $(GLIB)/*.class
