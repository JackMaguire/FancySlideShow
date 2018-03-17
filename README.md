# FancySlideShow
Play slides with more dynamic behavior and interactivity

# Classes #

![ClassRelationships](documentation/ClassRelationships.png)

## Application ##

This is where `main()` lives.
`main()` is responsible for generating a `ConceptualGraph`
and giving it to a `SlideShow`.
`SlideShow.run()` takes care of the rest.

Some applications are tailor-made for specific slide shows.
This got tedious, so I made the FrameScript application that
takes in an XML script and parses it into a `ConceptualGraph`
(there is more info on the XML interface below).
Thanks to this, you do not need to write a new application
every time you want to make a new presentation.

A typical main method would look something like:
```c++
ConceptualGraph graph = myGraphCreationProcess();//create this graph however you want
SlideShow ss = new SlideShow( graph );
ss.run();
```

## Conceptual Graph ##

![ConGraph](documentation/ConceptualGraph.png)

This graph is meant to be the user-facing representation of the slide show.
Each node in the graph represents a slide and each edge represents a connection from one slide to another.

### Conceptual Node ###

TODO

### Conceptual Edge ###

TODO

## Frame Graph ##

![FrameGraph](documentation/FrameGraph.png)

The Frame Graph is the machine-facing representation of the slide show.
This graph contains a node for every frame in the entire presentation (slides and transitions).
Every node is either primary (shown in blue) or secondary (shown in gray).
Primary nodes map to ConceptualNodes and secondary nodes map to frames in ConceptualEdges, as shown here:

![Graphs](documentation/Graphs.png)

### Conceptual Node ###

TODO

### Conceptual Edge ###

TODO

## SlideShow ##

TODO

## SlideShowPanel ##

TODO

## Engine ##

TODO

## Control Panel ##

TODO

## Control Panel Updater ##

TODO


# Usage #

## Control Panel ##

This is the section that is most likely to be out of date by the time you read this.
The control panel is being tinkered with a lot,
but the general format will stay the same.

![ControlPanel](documentation/ControlPanel.png)

### (1) Current Slide ###

.

### (2) Preview of Next Slide ###

.

### (3) Selected Slide ###

.

### (4) FrameSpace Menu ###

.

### (5) Next Slide Menu ###

.

### (6) Previous Slide Menu ###

.

### (7) Garbage Collection ###

.

### (8) Calc Mem Usage ###

.

### (9) Clear Focus ###

.

### (10) Title of Selected Slide ###

.

### (11) Conceptual Graph of Selected FrameSpace ###

.

### (12) Runtime Notes ###

.


### (13) Notes for Current Slide ###

.