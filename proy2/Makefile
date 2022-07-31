KC=		kotlinc
KFLAG=		-cp
LIBGRAPH=	libGrafoKt/
LIBJAR=		libGrafoKt/libGrafoKt.jar

all:	jarlib RutaEstadio.class

jarlib:
	(cd $(LIBGRAPH); make)  

RutaEstadio.class: RutaEstadio.kt 
	$(KC) $(KFLAG) $(LIBJAR) RutaEstadio.kt
clean:
	(cd $(LIBGRAPH); make clean)
	rm -rf *.class META-INF
