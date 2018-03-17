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

## Conceptual Graph ##

TODO