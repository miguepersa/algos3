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
		$(GLIB)/GrafoDirigidoCosto.class\
		$(GLIB)/GrafoNoDirigido.class\
		$(GLIB)/GrafoNoDirigidoCosto.class\
		$(GLIB)/BFS.class\
		$(GLIB)/DFS.class\
		$(GLIB)/Utilidades.class\
		$(GLIB)/CFC.class\
		$(GLIB)/OrdenamientoTopologico.class\
		$(GLIB)/ParticionNiveles.class\
		$(GLIB)/CicloDigrafo.class\
		$(GLIB)/ConjuntosDisjuntos.class\
		$(GLIB)/ComponentesConexasCD.class\
		$(GLIB)/ComponentesConexasDFS.class\
		$(GLIB)/ColaDePrioridad.class\
		$(GLIB)/ComponentesConexasCostoDFS.class\
		$(GLIB)/KruskalAMC.class\
		$(GLIB)/PrimAMC.class\
		$(GLIB)/TablaGrafoDirigido.class\
		$(GLIB)/TablaGrafoNoDirigido.class\
		$(GLIB)/VerticeBipartito.class\
		$(GLIB)/GrafoBipartito.class

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

$(GLIB)/GrafoDirigido.class: $(GLIB)/GrafoDirigido.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/FuncionesExtras.kt $(GLIB)/GrafoDirigido.kt

$(GLIB)/GrafoDirigidoCosto.class: $(GLIB)/GrafoDirigidoCosto.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/FuncionesExtras.kt $(GLIB)/GrafoDirigidoCosto.kt

$(GLIB)/GrafoNoDirigido.class: $(GLIB)/GrafoNoDirigido.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/FuncionesExtras.kt $(GLIB)/GrafoNoDirigido.kt

$(GLIB)/GrafoNoDirigidoCosto.class: $(GLIB)/GrafoNoDirigidoCosto.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/FuncionesExtras.kt $(GLIB)/GrafoNoDirigidoCosto.kt

$(GLIB)/BFS.class: $(GLIB)/BFS.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/BFS.kt

$(GLIB)/DFS.class: $(GLIB)/DFS.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/DFS.kt

$(GLIB)/Utilidades.class: $(GLIB)/Utilidades.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Utilidades.kt

$(GLIB)/OrdenamientoTopologico.class: $(GLIB)/OrdenamientoTopologico.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/OrdenamientoTopologico.kt

$(GLIB)/CFC.class: $(GLIB)/CFC.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/OrdenamientoTopologico.kt $(GLIB)/CFC.kt

$(GLIB)/ParticionNiveles.class: $(GLIB)/ParticionNiveles.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/ParticionNiveles.kt

$(GLIB)/CicloDigrafo.class: $(GLIB)/CicloDigrafo.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/CicloDigrafo.kt

$(GLIB)/ConjuntosDisjuntos.class: $(GLIB)/ConjuntosDisjuntos.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/ConjuntosDisjuntos.kt

$(GLIB)/ComponentesConexasCD.class: $(GLIB)/ComponentesConexasCD.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/ComponentesConexasCD.kt

$(GLIB)/ComponentesConexasDFS.class: $(GLIB)/ComponentesConexasDFS.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/ComponentesConexasDFS.kt

$(GLIB)/ColaDePrioridad.class: $(GLIB)/ColaDePrioridad.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/ColaDePrioridad.kt

$(GLIB)/ComponentesConexasCostoDFS.class: $(GLIB)/ComponentesConexasCostoDFS.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/ComponentesConexasCostoDFS.kt

$(GLIB)/KruskalAMC.class: $(GLIB)/KruskalAMC.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Utilidades.kt $(GLIB)/KruskalAMC.kt

$(GLIB)/PrimAMC.class: $(GLIB)/PrimAMC.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/ColaDePrioridad.kt $(GLIB)/PrimAMC.kt

$(GLIB)/TablaGrafoDirigido.class: $(GLIB)/TablaGrafoDirigido.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/TablaGrafoDirigido.kt

$(GLIB)/TablaGrafoNoDirigido.class: $(GLIB)/TablaGrafoNoDirigido.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/TablaGrafoNoDirigido.kt
	
$(GLIB)/VerticeBipartito.class: $(GLIB)/VerticeBipartito.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/VerticeBipartito.kt

$(GLIB)/GrafoBipartito.class: $(GLIB)/GrafoBipartito.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/GrafoBipartito.kt

clean:
	rm -rf libGrafoKt.jar META-INF $(GLIB)/*.class
