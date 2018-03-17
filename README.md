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

## Frame Graph ##

![FrameGraph](documentation/FrameGraph.png)

![Graphs](documentation/Graphs.png)
