KC=	kotlinc
KFLAG=	-cp
GLIB=	ve/usb/libGrafo

all:	libGrafoKt.jar

libGrafoKt.jar: $(GLIB)/Color.class\
		$(GLIB)/Exceptions.class\
		$(GLIB)/Vertice.class\
		$(GLIB)/Lado.class\
		$(GLIB)/Arista.class\
		$(GLIB)/AristaCosto.class\
		$(GLIB)/FuncionesExtras.class\
		$(GLIB)/Grafo.class\
		$(GLIB)/GrafoNoDirigidoCosto.class\
		$(GLIB)/ConjuntosDisjuntos.class\
		$(GLIB)/ColaDePrioridad.class\
		$(GLIB)/ComponentesConexasCostoDFS.class\
		$(GLIB)/PrimUSBTours.class

	jar -cvf $@ $(GLIB)/*.class 

$(GLIB)/Color.class: $(GLIB)/Color.kt
	$(KC) $(GLIB)/Color.kt

$(GLIB)/Exceptions.class: $(GLIB)/Exceptions.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Exceptions.kt

$(GLIB)/Vertice.class: $(GLIB)/Vertice.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Vertice.kt

$(GLIB)/Lado.class: $(GLIB)/Lado.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Lado.kt

$(GLIB)/Arista.class: $(GLIB)/Arista.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Arista.kt

$(GLIB)/AristaCosto.class: $(GLIB)/AristaCosto.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/AristaCosto.kt

$(GLIB)/FuncionesExtras.class: $(GLIB)/FuncionesExtras.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/FuncionesExtras.kt

$(GLIB)/Grafo.class: $(GLIB)/Grafo.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Grafo.kt

$(GLIB)/GrafoNoDirigidoCosto.class: $(GLIB)/GrafoNoDirigidoCosto.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/FuncionesExtras.kt $(GLIB)/GrafoNoDirigidoCosto.kt

$(GLIB)/ConjuntosDisjuntos.class: $(GLIB)/ConjuntosDisjuntos.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/ConjuntosDisjuntos.kt

$(GLIB)/ColaDePrioridad.class: $(GLIB)/ColaDePrioridad.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/ColaDePrioridad.kt

$(GLIB)/ComponentesConexasCostoDFS.class: $(GLIB)/ComponentesConexasCostoDFS.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/ComponentesConexasCostoDFS.kt

$(GLIB)/PrimUSBTours.class: $(GLIB)/PrimUSBTours.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/ColaDePrioridad.kt $(GLIB)/PrimUSBTours.kt

clean:
	rm -rf libGrafoKt.jar META-INF $(GLIB)/*.class
