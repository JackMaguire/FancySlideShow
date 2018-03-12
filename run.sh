#!/bin/bash

application="one_time/March2018LabMeeting"
slide_location=~/Dropbox/March2018LabMeetingSlides/

temp=`echo $application | sed 's:/:.:g'`

#compilation_flags=""
compilation_flags="-Xverify:none" #VisualVM

mkdir DOOMED
javac -d DOOMED/ -cp src src/applications/${application}.java
java $compilation_flags= -cp DOOMED applications.$temp $slide_location
\rm -rf DOOMED
