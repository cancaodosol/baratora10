<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.css" type="text/css">
<title>リストビュー</title>
	<%List<List<String>> SqlResults = (List<List<String>>)request.getAttribute("SqlResults"); %>
	<%List<String> fields = SqlResults.get(0); %>
	<%String title = (String)request.getAttribute("title"); %>
	<%String sql = (String)request.getAttribute("sql"); %>
</head>
<body>
	<table border = 1 >
		<caption><%=title %></caption>
		<thead>
			<%for(String field : fields){%><th><%=field%></th><%}%>
		</thead>
		<tbody style="text-align: right;">
			<%for(int i=1;i<SqlResults.size();i++){ %>
			<%fields = SqlResults.get(i); %>
			<tr>
				<%for(String field : fields){%><td><%=field%></td><%}%>
			</tr>
			<%} %>
		</tbody>
	</table>
	<form action="SalesResultListServlet" method= "POST">
		<p>SQL入力　<input type="submit" value="実行"></p>
		<textarea name="sql" rows="20" cols="150"><%=sql %></textarea>
	</form>
</body>
</html>