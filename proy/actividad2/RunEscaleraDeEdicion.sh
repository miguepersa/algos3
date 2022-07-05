#!/bin/bash
export JAVA_OPTS="-Xmx4g"
kotlin -cp libGrafoKt/libGrafoKt.jar:. EscaleraDeEdicionKt.class  $*