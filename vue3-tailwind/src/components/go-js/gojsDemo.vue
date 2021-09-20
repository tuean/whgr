<template>
  <div
    id="myDiagramDiv"
    style="width: 2000px; height: 1000px; background-color: #dae4e4"
  ></div>
</template>

<script>
// import go from "go.js";
import { nextTick } from "vue";
export default {
  setup() {
    const init = async () => {
      await nextTick();
      var $ = go.GraphObject.make; // for conciseness in defining templates

      var myDiagram = $(
        go.Diagram,
        "myDiagramDiv", // create a Diagram for the DIV HTML element
        {
          // enable undo & redo
          "undoManager.isEnabled": true,
        }
      );

      // define a simple Node template
      myDiagram.nodeTemplate = $(
        go.Node,
        "Auto", // the Shape will go around the TextBlock
        $(
          go.Shape,
          "RoundedRectangle",
          { strokeWidth: 0, fill: "white" }, // default fill is white
          // Shape.fill is bound to Node.data.color
          new go.Binding("fill", "color")
        ),
        $(
          go.TextBlock,
          { margin: 8 }, // some room around the text
          // TextBlock.text is bound to Node.data.key
          new go.Binding("text", "key")
        )
      );

       myDiagram.linkTemplate =
        $(go.Link,  // the whole link panel
          { routing: go.Link.Normal },
          $(go.Shape,  // the link shape
            // the first element is assumed to be main element: as if isPanelMain were true
            { stroke: "gray", strokeWidth: 2 }),
          $(go.Shape,  // the "from" arrowhead
            { fromArrow:'BackwardOpenTriangle', fill: null }),
          $(go.Shape,  // the "to" arrowhead
             { toArrow: 'OpenTriangle', fill: null })
        );


      // but use the default Link template, by not setting Diagram.linkTemplate

      // create the model data that will be represented by Nodes and Links
      myDiagram.model = new go.GraphLinksModel(
        [
          { key: "Alpha", color: "lightblue", fromArrow: "BackwardOpenTriangle", toArrow: "BackwardOpenTriangle" },
          { key: "Beta", color: "orange", fromArrow: "BackwardOpenTriangle", toArrow: "BackwardOpenTriangle" },
          { key: "Gamma", color: "lightgreen", fromArrow: "BackwardOpenTriangle", toArrow: "BackwardOpenTriangle" },
          { key: "Delta", color: "pink", fromArrow: "BackwardOpenTriangle", toArrow: "BackwardOpenTriangle" },
        ],
        [
          { from: "Alpha", to: "Beta" },
          { from: "Alpha", to: "Gamma" },
          { from: "Beta", to: "Beta" },
          { from: "Gamma", to: "Delta" },
          { from: "Delta", to: "Alpha" },
        ]
      );
    };

    init();
    return {
      init,
    };
  },
};
</script>