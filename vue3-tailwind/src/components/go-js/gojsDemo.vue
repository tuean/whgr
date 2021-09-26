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
          new go.Binding("fill", "color"),          
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
       new go.Binding('routing', 'routing'),
          $(go.Shape,  // the link shape
            // the first element is assumed to be main element: as if isPanelMain were true
            { stroke: "gray", strokeWidth: 2 }),
          $(go.Shape,  // the "from" arrowhead
            { fromArrow:'BackwardOpenTriangle', fill: null }),
          $(go.Shape,  // the "to" arrowhead
             { toArrow: 'OpenTriangle', fill: null }) ,
             
           new go.Binding("strokeDashArray", "dash")
        );


      // but use the default Link template, by not setting Diagram.linkTemplate

      // create the model data that will be represented by Nodes and Links
      myDiagram.model = new go.GraphLinksModel(
        [
          { key: "Alpha", color: "lightblue",},
          { key: "Beta", color: "orange"},
          { key: "Gamma", color: "lightgreen"},
          { key: "Delta", color: "pink"},
        ],
        [
          { from: "Alpha", to: "Beta", fromArrow:'BackwardOpenTriangle', toArrow: 'OpenTriangle', fill: null },
          { from: "Alpha", to: "Gamma" , fromArrow:'BackwardOpenTriangle', toArrow: 'OpenTriangle', fill: null },
          { from: "Beta", to: "Beta", fromArrow:'BackwardOpenTriangle', toArrow: 'OpenTriangle', fill: null },
          { from: "Gamma", to: "Delta", fromArrow:'BackwardOpenTriangle', toArrow: 'OpenTriangle', fill: null },
          { from: "Delta", to: "Alpha" , fromArrow:'BackwardOpenTriangle', toArrow: 'OpenTriangle', fill: null },
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