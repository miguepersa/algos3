KC=		kotlinc
KFLAG=		-cp
LIBGRAPH=	libGrafoKt/
LIBJAR=		libGrafoKt/libGrafoKt.jar

all:	jarlib MainKt.class

jarlib:
	(cd $(LIBGRAPH); make)  

MainKt.class: Main.kt 
	$(KC) $(KFLAG) $(LIBJAR) Main.kt
clean:
	(cd $(LIBGRAPH); make clean)
	rm -rf *.class META-INF
