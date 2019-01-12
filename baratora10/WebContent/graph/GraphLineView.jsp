<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%String pageTitle = (String)request.getAttribute("pageTitle"); %>
	<%String graphTitle = (String)request.getAttribute("graphTitle"); %>
	<%String[] labels = (String[])request.getAttribute("labels"); %>
	<%String title = (String)request.getAttribute("title"); %>
	<%String color = (String)request.getAttribute("color"); %>
	<%double[] values = (double[])request.getAttribute("values"); %>
	<%String unit = (String)request.getAttribute("unit"); %>

<meta charset="UTF-8">
<title><%=pageTitle %>></title>
<script src="./graph/life.js"></script>
</head>

<body>
<div id="chart"></div>
<script>
<!--ラベルの数とバリューの数が一致している必要があるが、今回は確かめないものとする。-->
let data = {
	labels: [
	  <%for(String label : labels){%>
		  	"<%=label%>",
	  <%}%>
	],

  datasets: [
    {
      title: "<%=title%>",
      color: "<%=color%>",
      values: [
    	<%for(double value : values){%>
		  	"<%=value%>",
	  	<%}%>

      ]
    }
  ]
};

let chart = new Chart({
  parent: "#chart", // or a DOM element
  title: "<%=graphTitle%>",
  data: data,
  type: "line",
  height: 250,
  width: <%=50*labels.length%>,
  format_tooltip_x: d => (d + "").toUpperCase(),
  format_tooltip_y: d => d + " <%=unit%>"
});S

</script>


</body>
</html>