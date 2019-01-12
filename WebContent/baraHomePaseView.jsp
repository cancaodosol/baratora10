<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%List<List<String>> itemList = (List<List<String>>)request.getAttribute("itemList"); %>
	<%List<String> records = itemList.get(0); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.css" type="text/css">
<title>バラ売り上げ管理画面ホーム</title>
</head>
<body>
	<p1>〇バラ売り上げ管理</p1>
	<ul>
		<div onmouseover="apper(this)" onmouseout="disapper(this)">
			<li>売上入力画面</li>
				<div style="display:none">
					<ol>
						<% for(int i=1;i<itemList.size();i++){%>
						<% records = itemList.get(i); %>
						<li><a href = "./InsertSalesResultServlet?itemid=<%=records.get(0)%>"><%=records.get(1) %></a></li>
						<%} %>
					</ol>
				</div>
		</div>
		<div onmouseover="apper(this)" onmouseout="disapper(this)">
			<li>売上参照画面(一覧表示)</li>
			<div style="display:none">
				<ol>
					<li><a href = "./SalesResultListServlet?mode=listall&itemid=0">全て</a></li>
					<% for(int i=1;i<itemList.size();i++){%>
					<% records = itemList.get(i); %>
					<li><a href = "./SalesResultListServlet?mode=listall&itemid=<%=records.get(0)%>"><%=records.get(1) %></a></li>
					<%} %>
				</ol>
			</div>
		</div>
		<div onmouseover="apper(this)" onmouseout="disapper(this)">
			<li>売上参照画面(月次)</li>
			<div style="display:none">
				<ol>
					<li><a href = "./SalesResultListServlet?mode=listmonth&itemid=0">全て</a></li>
					<% for(int i=1;i<itemList.size();i++){%>
					<% records = itemList.get(i); %>
					<li><a href = "./SalesResultListServlet?mode=listmonth&itemid=<%=records.get(0)%>"><%=records.get(1) %></a></li>
					<%} %>
				</ol>
			</div>
		</div>
		<div onmouseover="apper(this)" onmouseout="disapper(this)">
			<li>売上参照画面(週次)</a></li>
			<div style="display:none">
				<ol>
					<li><a href = "./SalesResultListServlet?mode=listweek&itemid=0">全て</a></li>
					<% for(int i=1;i<itemList.size();i++){%>
					<% records = itemList.get(i); %>
					<div onmouseover="apper(this)" onmouseout="disapper(this)">
						<li><a href = "./SalesResultListServlet?mode=listweek&itemid=<%=records.get(0)%>"><%=records.get(1) %></a></li>
						<div style="display:none">
							<ol>
								<li><a href = "./SalesResultListServlet?mode=rankweek&itemid=<%=records.get(0)%>">等階級集計</a></li>
							</ol>
						</div>
					</div>
					<%} %>
				</ol>
			</div>
		</div>
	</ul>

	<script>
			function apper(obj){
				var element = obj.firstElementChild.nextElementSibling;
				console.log(obj.firstElementChild);
				console.log(element);
				element.style.display = "block";
			}

			function disapper(obj){
				var element = obj.firstElementChild.nextElementSibling;
				console.log(obj.firstElementChild);
				console.log(element);
				element.style.display = "none";
			}
	</script>
</body>
</html>