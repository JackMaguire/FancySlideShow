#!/bin/bash

application="FrameScript"
script="demo/script.xml"

temp=`echo $application | sed 's:/:.:g'`

compilation_flags=""
#compilation_flags="-Xverify:none" #VisualVM

mkdir DOOMED
javac -d DOOMED/ -cp src src/applications/${application}.java
java $compilation_flags -cp DOOMED applications.$temp -script $script
\rm -rf DOOMED
