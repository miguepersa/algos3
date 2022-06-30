KC=		kotlinc
KFLAG=		-cp
LIBGRAPH=	libGrafoKt/
LIBJAR=		libGrafoKt/libGrafoKt.jar

all:	jarlib EscaleraDeEdicion.class

jarlib:
	(cd $(LIBGRAPH); make)  

EscaleraDeEdicion.class: EscaleraDeEdicion.kt 
	$(KC) $(KFLAG) $(LIBJAR) EscaleraDeEdicion.kt
clean:
	(cd $(LIBGRAPH); make clean)
	rm -rf *.class META-INF
