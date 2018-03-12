#!/bin/bash

application="one_time/March2018LabMeeting"
slide_location=~/Dropbox/March2018LabMeetingSlides/

temp=`echo $application | sed 's:/:.:g'`

javac -d DOOMED/ -cp src src/applications/${application}.java
java -cp DOOMED applications.$temp $slide_location
\rm -rf DOOMED
