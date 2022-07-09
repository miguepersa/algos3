KC=		kotlinc
KFLAG=		-cp
LIBGRAPH=	libGrafoKt/
LIBJAR=		libGrafoKt/libGrafoKt.jar

all:	jarlib Main.class

jarlib:
	(cd $(LIBGRAPH); make)  

Main.class: Main.kt 
	$(KC) $(KFLAG) $(LIBJAR) Main.kt
clean:
	(cd $(LIBGRAPH); make clean)
	rm -rf *.class META-INF
