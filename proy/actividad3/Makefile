KC=		kotlinc
KFLAG=		-cp
LIBGRAPH=	libGrafoKt/
LIBJAR=		libGrafoKt/libGrafoKt.jar

all:	jarlib TwoSATSolver.class

jarlib:
	(cd $(LIBGRAPH); make)  

TwoSATSolver.class: TwoSATSolver.kt 
	$(KC) $(KFLAG) $(LIBJAR) TwoSATSolver.kt
clean:
	(cd $(LIBGRAPH); make clean)
	rm -rf *.class META-INF
