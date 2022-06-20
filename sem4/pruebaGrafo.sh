#!/bin/bash

kotlin -cp libGrafoKt/libGrafoKt.jar:. EvaluacionCCKt ./grafoEjemplos/pequenio.txt
kotlin -cp libGrafoKt/libGrafoKt.jar:. EvaluacionCCKt ./grafoEjemplos/mediano.txt
export JAVA_OPTS="-Xmx3g"
kotlin -cp libGrafoKt/libGrafoKt.jar:. EvaluacionCCKt ./grafoEjemplos/grande.txt