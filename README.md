# FancySlideShow
Play slides with more dynamic behavior and interactivity

## Table of Contents ##

[Overview](#overview) 

[Classes](#classes) 

[Usage](#usage) 
- [Control Panel](#control_panel) 
- [Controls](#controls) 

[FrameScripts](#fs)
- [XML Interface](#xml) 
- [Running FrameScripts](#running) 

<a name="overview"/>

# Overview #

This program allows you to arrange a collection of pictures into a presentation.
The advantage of this program over other PowerPoint-like software is that
you can have non-linear flow between slides.
This also provides a seamless interface between movies and slides.

For example, say I have an animation comprised of 1000 rendered pictures and
I want frames 1, 250, and 1000 to be slides.
This program can let you define these slides
then use frames 2-249 as a transition between the first two slides
and frames 251-999 as a transition between the final two slides.
This prevents the awkward slide-to-movie-to-slide artifacts that PowerPoint gives.

See the demo directory for a simple example.

<a name="classes"/>

# Classes #

![ClassRelationships](documentation/ClassRelationships.png)

### Application ###

This is where `main()` lives.
`main()` is responsible for generating a `ConceptualGraph`
and giving it to a `SlideShow`.
`SlideShow.run()` takes care of the rest.
The primary application is called FrameScripts,
and is described [below](#fs).

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

<a name="def_conceptual_graph"/>

### Conceptual Graph ###

![ConGraph](documentation/ConceptualGraph.png)

This graph is meant to be the user-facing representation of the slide show.
Each node in the graph represents a slide and each edge represents a connection from one slide to another.

<a name="def_conceptual_node"/>

#### Conceptual Node ####

The Conceptual Graph has one Conceptual Node for every slide.
At any point, a Conceptual Node can be "hard" or "soft".
Hard Conceptual Nodes pause the engine when they are reached,
while soft nodes are not stopped at for longer than a single frame.

The order that edges are added is very [important](TODO).
The ordering here matches the order that is defined in the XML
and will be preserved when the [Frame Graph](#def_frame_graph)
is created.

Every Conceptual Node has:

|       Type       | Description |
| ---------------- | ----------- |
| String           | Slide Name  |
| String           | Slide Notes |
| Integer          | Index       |
| Integer          | FrameSpace (a.k.a. Subgraph) |
| Boolean          | Stores if the slide is currently "hard" |
| ConceptualEdge[] | Stores downstream edges |
| ConceptualEdge[] | Stores upstream edges |
| String           | Filename for slide's image |
| BufferedImage    | Scaled-down version of image for this slide (for control panel preview) |
| [FrameNode](#def_frame_node) | Link to corresponding FrameNode |

<a name="def_conceptual_edge"/>

#### Conceptual Edge ####

A Conceptual Edge defines a directed link between two Conceptual Nodes.
If you provide an edge with images, these images will be displayed as
a transition between those two nodes.
When moving backwards, the transition will also be played backwards.

Every Conceptual Edge has:

|       Type       | Description                                                |
| ---------------- | ---------------------------------------------------------- |
| String           | Name for control panel display                             |
| Integer          | Index of incoming Conceptual Node                          |
| Integer          | Index of outgoing Conceptual Node                          |
| Integer          | Edge Index (not sure if this is used, might get rid of it) |
| String[]         | filenames for transition frames, if any                    |

<a name="def_frame_graph"/>

### Frame Graph ###

![FrameGraph](documentation/FrameGraph.png)

The Frame Graph is the machine-facing representation of the slide show.
This graph contains a node for every frame in the entire presentation (slides and transitions).
Every node is either primary (shown in blue) or secondary (shown in gray).
Primary nodes map to ConceptualNodes and secondary nodes map to frames in ConceptualEdges, as shown here:

![Graphs](documentation/Graphs.png)

<a name="def_frame_node"/>

#### Frame Node ####

TODO: Primary/Secondary

Every Frame Node has:

|       Type       | Description                                                |
| ---------------- | ---------------------------------------------------------- |
| TODO             | TODO                             |


<a name="def_slide_show"/>

### Slide Show ###

This class constructs many of the lower-level classes and starts the engine.
It's primary function is `run()`, which usually occurs at the end of a main method.

<a name="def_slide_show_panel"/>

### Slide Show Panel ###

This is the view that you want to show the audience.

<a name="def_engine"/>

### Engine ###

The engine traverses the [Frame Graph](#def_frame_graph)
and updates the [Slide Show Panel](#def_slide_show_panel).
The engine has a built-in timer and procedes to the next
FrameNode at every tick of the timer
(unless it is waiting at a hard primary node).

<a name="def_control_panel"/>

### Control Panel ###

This is the display that shows up on the second monitor
(only the presenter will want to see this).
It is full of bells and whistles that allows the
presenter to control the presentation and
read/take notes about the material.

A deeper look into the control panel can be found [here](#control_panel).

<a name="def_control_panel_updater"/>

### Control Panel Updater ###

On occasion, the engine will need to send information to the control panel.
The control panel is complicated and ever-changing,
so this can be a complicated flow of information.
To make things easier, I created this updater class that
is called by the engine and knows which parts of the control
panel needs the information being provided.

<a name="usage"/>

# Usage #

<a name="control_panel"/>

## Control Panel ##

This is the section that is most likely to be out of date by the time you read this.
The control panel is being tinkered with a lot,
but the general format will stay the same.

![ControlPanel](documentation/ControlPanel.png)

#### (1) Current Slide ####

Shows a miniature version of the current slide.

#### (2) Preview of Next Slide ####

If it exists, this shows a miniature version of the next slide.
If the current slide has multiple downstream slides,
only the active one is shown.

#### (3) Selected Slide ####

Shows a miniature version of the current slide.

#### (4) FrameSpace Menu ####

It can be messy to have the graph of the entire presentation shown at once,
so the user can break it up into FrameSpaces.
(11) will only show one FrameSpace at a time and
the user can use this menu to toggle between them.

#### (5) Next Slide Menu ####

This menu lets you scroll over the selected slide's downstream edges and select the active one
(i.e., decide the one that will actually be shown after the selected node).

#### (6) Previous Slide Menu ####

Similar to (5), this menu lets you scroll over the
selected slide's upstream edges and select the active one.

#### (7) Garbage Collection ####

(This is a development feature that will almost certainly be removed)

Java performs garbage collection when this button is pushed.

#### (8) Calc Mem Usage ####

(This is a development feature that will almost certainly be removed)

Java measures the amount of memory it is using
when this button is pushed. The result will be
displayed to the right of the button.

#### (9) Clear Focus ####

(This is a development feature that will almost certainly be removed)

This is not as useful as it used to be.
There used to be some bugs where you could not
click on the control panel because the computer's
focus would be stuck on one of the dropdown menus.
If that happens to you, just push this button and
the focus will go back to its neutral state.

Now that I think about it,
this still might be required after you type in (12).

#### (10) Title of Selected Slide ####

#### (11) Conceptual Graph of Selected FrameSpace ####

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

#### (12) Runtime Notes ####

This is a text field for you to take notes in.
At the end of the presentation,
these notes will be printed to the console so that you do not lose them.

A possible future feature is for these notes to be emailed to you.

<a name="notes_for_current_slide"/>

#### (13) Notes for Current Slide ####

Each slide has the ability to hold notes.
If the current slide has notes in it,
they will be displayed here.

<a name="controls"/>

## Controls ##

#### Space Bar ####

Pauses the slide show by stopping the timer.
Every element is still active,
but the engine will stop traversing the
FrameGraph and updating the SlideShowPanel.

If already paused, the space bar resumes
the slide show by starting the timer again.

#### Left Mouse Button (on the ConceptualGraph) ####

Selects a ConceptualNode.

#### Right Mouse Button (on the ConceptualGraph) ####

Toggles the hardness of a ConceptualNode.

#### Left Arrow Key + Shift (optional) ####

Okay this one is tricky due to a few conditionals,
but I assure you that the behavior is reasonable.
In general, the left arrow key means "go backwards".
The details of this depend on the state of the engine.

It's hard to explain in English,
maybe the code can help make it clear:

```c++
private void leftButton( boolean shift ) {
    final boolean was_reversed = engine_.reverse();
    engine_.setReverse( true );

    if( !engine_.isWaitingAtHardNode() && !was_reversed ) {
        //If we were running forward, then all we want to do is turn on reverse
        return;
    }

    if( engine_.getTimer().isRunning() ) {
	//If we are waiting at a hard node, override the hardness
        if( engine_.isWaitingAtHardNode() ) {
            engine_.goAtNextTick();
        }

	//If shift is selected, do not proceed to the active FrameNode
	//Instead, take the highest-ranking non-active FrameNode
        engine_.takeNextSecondaryOption( shift );
    } else {
        //If the timer is stopped, just go back to the previous FrameNode.
        engine_.goBackOneImage();
    }
}
```

#### Right Arrow Key + Shift (optional) ####

The exact symmetric opposite of the left arrow key.
In general, this key means "go forward".

<a name="fs"/>

# FrameScripts #

<a name="xml"/>

## XML Interface ##

At the moment, there are two primary XML sections:
`<Graph>` and `<Settings>`.

- [`<Graph>`](#xml_graph)
..- [`<FrameSpace>`](#xml_frame_space)
...- [`<Nodes>`](#xml_nodes)
...- [`<Edges>`](#xml_edges)
..- [`<GlobalEdges>`](#xml_global_edges)
- [`<Settings>`](#xml_settings)
..- [`<ControlPanel>`](#xml_control_panel)
..- [`<SlideShowPanel>`](#xml_slide_show_panel)
..- [`<FrameCache>`](#xml_frame_cache)


<a name="xml_graph"/>

### Graph ###

TODO

<a name="xml_frame_space"/>

#### FrameSpace ####

TODO

<a name="xml_nodes"/>

#### Nodes ####

This is the section where you define
[Conceptual Nodes](#def_conceptual_node)
for the graph. `<FrameSpace>` can contain
one `<Nodes>` section, which will contain
a `<Node>` tag for every ConceptualNode
as shown below.

```xml
<Node title="name_of_node" filename="Track1/0250.png" hard="true" notes="Good luck!"/>
```
or
```xml
<Node title="name_of_node" filename="Track1/0250.png" hard="true">
      <Notes line="Good"/>
      <Notes line="Luck!"/>
</Node>
```

| Option   | Type    | Mandatory              | Usage |
| -------- | ------- | ---------------------- | ----- |
| title    | String  | Yes                    | Name given to node so that it can be referenced by edges later on. If the name does not start with an underscore then it will appear as a label in the control panel for this node ("_my_node" will not be unlabeled, for example). |
| filename | String  | Yes                    | Gives the path to the picture for this slide. |
| hard     | boolean | No (default is "true") | Sets [hardness](#def_conceptual_node) of this node. |
| notes    | String  | No                     | You can leave notes for yourself. [These will appear in the control panel when you present this slide.](#notes_for_current_slide) |

Notes can span multiple lines, as shown in the second example above.
The two examples are not identical,
the first will display
```
Good luck!
```
and the second will show
```
Good

luck!
```

<a name="xml_edges"/>

#### Edges ####

TODO

<a name="xml_global_edges"/>

#### GlobalEdges ####

TODO

<a name="xml_settings"/>

### Settings ###

TODO

<a name="xml_control_panel"/>

#### ControlPanel ####

TODO

<a name="xml_slide_show_panel"/>

#### SlideShowPanel ####

TODO

<a name="xml_frame_cache"/>

#### FrameCache ####

TODO

<a name="full_skeleton"/>

### Full Skeleton ###

```xml
<FrameScript>
	<Graph>
		<FrameSpace name="Skeleton">
			<Nodes>
				<Node title="TODO" filename="TODO"/>
				<Node title="TODO2" filename="TODO"/>
			</Nodes>
			<Edges>
				<Edge title="_TODO" origin_node="TODO" destination_node="TODO2"/>
			</Edges>
		</FrameSpace>

		<GlobalEdges>
			<Edge title="_global" origin_frame_space="space1" origin_node="node1"
			      destination_frame_space="space2" destination_node="node2"/>
		</GlobalEdges>
	</Graph>

	<Settings>
		<FrameCache load_caches="false" delete_caches="false">
			<PrimaryNodeCache ratio="1.0" max_width="1920" max_height="1080"/>
    			<SecondaryNodeCache ratio="1.0" max_width="1920" max_height="1080"/>
		</FrameCache>

  		<SlideShowPanel monitor="1" fps="24">
    			<Background r="0" g="0" b="0"/>
  		</SlideShowPanel>

		<ControlPanel monitor="0" width="1440" height="880" east_width="250" west_width="250"/>
	</Settings>
</FrameScript>
```

<a name="running"/>

## Running FrameScripts ##

From the top-level directory of this repo:

```sh
run/frame_scripts.sh <xml files>
```

You can provide as many xml files as you wish;
they will all be parsed in the order in which you provide them.
If you have multiple scripts that change the same setting,
the setting will match the last script that changed it.

You technically can provide multiple `<Graph>` blocks
but only the final one will be parsed.
A possible future feature is the ability to import
`<FrameSpace>` blocks from several different scripts.