#!/bin/bash
export JAVA_OPTS="-Xmx2g"
kotlin -cp libGrafoKt/libGrafoKt.jar:. EscaleraDeEdicionKt.class  $*
