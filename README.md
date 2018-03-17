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

Shows a miniature version of the current slide.

### (2) Preview of Next Slide ###

If it exists, this shows a miniature version of the next slide.
If the current slide has multiple downstream slides,
only the active one is shown.

### (3) Selected Slide ###

Shows a miniature version of the current slide.

### (4) FrameSpace Menu ###

It can be messy to have the graph of the entire presentation shown at once,
so the user can break it up into FrameSpaces.
(11) will only show one FrameSpace at a time and
the user can use this menu to toggle between them.

### (5) Next Slide Menu ###

This menu lets you scroll over the selected slide's downstream edges and select the active one
(i.e., decide the one that will actually be shown after the selected node).

### (6) Previous Slide Menu ###

Similar to (5), this menu lets you scroll over the
selected slide's upstream edges and select the active one.

### (7) Garbage Collection ###

(This is a development feature that will almost certainly be removed)

Java performs garbage collection when this button is pushed.

### (8) Calc Mem Usage ###

(This is a development feature that will almost certainly be removed)

Java measures the amount of memory it is using
when this button is pushed. The result will be
displayed to the right of the button.

### (9) Clear Focus ###

(This is a development feature that will almost certainly be removed)

This is not as useful as it used to be.
There used to be some bugs where you could not
click on the control panel because the computer's
focus would be stuck on one of the dropdown menus.
If that happens to you, just push this button and
the focus will go back to its neutral state.

Now that I think about it,
this still might be required after you type in (12).

### (10) Title of Selected Slide ###

### (11) Conceptual Graph of Selected FrameSpace ###

A stunningly beautiful illustration of the
FrameSpace selected by (4).

The first slide is at the 12:00 position
and each successive slide (ConceptualNode) in the FrameSpace
is laid out in a clockwise pattern with lines
representing ConceptualEdges.
ConceptualEdges that span two different FrameSpaces
are not shown at this time; I am currently 
debating how I want to portray them.

If provided, names are shown for each
ConceptualNode and ConceptualEdges.
Soft ConceptualNodes are shown as circles
and hard ones are shown as squares.

This graph is interactive,
and the controls are listed below.

### (12) Runtime Notes ###

This is a text field for you to take notes in.
At the end of the presentation,
these notes will be printed to the console so that you do not lose them.

A possible future feature is for these notes to be emailed to you.

### (13) Notes for Current Slide ###

Each slide has the ability to hold notes.
If the current slide has notes in it,
they will be displayed here.

## Controls ##

### Space Bar ###

Pauses the slide show by stopping the timer.
Every element is still active,
but the engine will stop traversing the
FrameGraph and updating the SlideShowPanel.

If already paused, the space bar resumes
the slide show by starting the timer again.

### Left Mouse Button ###

### Right Mouse Button ###

### Left Arrow Key ###

### Right Arrow Key ###