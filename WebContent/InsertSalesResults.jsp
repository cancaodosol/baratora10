<%@page import="baratora10.SalesResult"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="style.css" type="text/css">
	<title>販売代金請求通知書の登録</title>
	<%int itemid = (Integer)request.getAttribute("itemid"); %>
	<%String itemName = (String)request.getAttribute("itemName"); %>
	<%HashMap<Integer,String> rankMap = (HashMap<Integer,String>)request.getAttribute("rankMap"); %>
	<%List<SalesResult> LastSales = (List<SalesResult>)request.getAttribute("LastSalesResults"); %>
	<%int index = 0; %>
	<%SalesResult sales = LastSales.get(index); %>>
</head>


<body>

	<form action="InsertSalesResultServlet" method= "POST">
		<table>
			<tr>
				<td><div id = "itemname">品目ID：</td><td><%=itemid %><td>品名：</td><td><%=itemName %></div></td>
			</tr>
			<tr>
				<td><div id = "date">出荷日：</div></td><td><input type = "date" name="date" value = "<%=sales.getShip_date() %>" style="text-align: right;"></td>
				<td><div id = "area">品種坪数：</div></td><td><input type = "number" name="area" value = "<%=sales.getArea() %>" style="text-align: right;"></td>
			</tr>
		</table>

		<table>
			<thead>
				<th>等階級</th>
				<th>1ケース本数</th>
				<th>　数　　量　</th>
				<th>　単　　価　</th>
				<th>　金　　額　</th>
				<th>　累計数量　</th>
				<th>　累計金額　</th>
			</thead>
			<tbody id="tbody">
				<%for(int i=1; i<11; i++){ %>
					<%boolean chk = false;%>
					<%if(sales.getRank() == i){ chk = true;}%>
			    	<tr>
						<td>
							<%=rankMap.get(i)%>
							<input type="hidden" name = "rank<%=i %>" value = "<%=i %>"/>
						</td>
						<td>
							<input class="js-uca" id = "uca<%=i %>" type="number" name = "uca<%=i %>"
							 value = "<%=sales.getUnit_case()%>"
							 style="text-align: right;" onchange="copyUCA(<%=i %>)"/>
						</td>
						<td>
							<input class="js-qty" id = "qty<%=i %>" type="number" name = "qty<%=i %>" value = "0" style="text-align: right;" onchange="calTOTAL(<%=i %>)"/>
						</td>
						<td>
							<input class="js-uslr" id = "uslr<%=i %>" type="number" name = "uslr<%=i %>" value = "0" style="text-align: right;" onchange="calTOTAL(<%=i %>)"/>
						</td>
						<td>
							<div class="js-tslr" id = "tslr<%=i %>" style="text-align: right;"></div>
						</td>
						<td>
							<div class="js-ttqty" id = "ttqty<%=i %>" style="text-align: right;"></div>
						</td>
						<td>
							<div class="js-ttslr" id = "ttslr<%=i %>" style="text-align: right;"></div>
						</td>
			    	</tr>
			    	<%if(chk && index<LastSales.size()-1)index++; sales = LastSales.get(index); %>
				 <%} %>
				 <tr>

				 </tr>
				 <tr style="text-align: right;">
					 <td></td>
					 <td>計</td><td><div id = "t_case"></div></td>
					 <td></td><td><div id = "t_tslr"></div></td>
					 <td><div id = "t_ttqty"></div></td><td><div id = "t_ttslr"></div></td>
				 </tr>
				 <tr style="text-align: right;">
					 <td></td>
					 <td>本数</td><td><div id = "t_qty"></div></td>
					 <td>控除金額</td>
					 <td>
							<input type="hidden" name = "rank11" value = "99"/>
							<input type="hidden" name = "uca11" value = "0"/>
							<input type="hidden" name = "qty11" value = "0"/>
							<input class="js-uslr" id = "uslr11" type="number" name = "uslr11" value = "0" style="text-align: right;" onchange="checkEX()"/>
					</td>
					 <td>精算金額</td><td><div id = "t_slr_amt"></div></td>
				 </tr>
				 <tr style="text-align: right;">
						<td></td>
						<td>累計本数</td><td></td>
						<td>平均単価</td><td></div></td>
						<td>累計金額</td><td></div></td>
				</tr>
				<tr style="text-align: right;">
					   <td></td>
					   <td>坪本数</td><td></td>
					   <td>累計単価</td><td></div></td>
					   <td>坪金額</td><td></div></td>
			   </tr>


			</tbody>
		</table>
		<input type="hidden" name="itemid" value="<%=itemid %>">
		<input type="submit" value="登録する">
	</form>

	<script>
		//1ケース数量が入力された時に、その値を同じ長さのものにコピーする。
		function copyUCA(index){
			var ele_uca_x = document.getElementById("uca"+index);

			//数字以外の排他チェック
			if(!(parseInt(ele_uca_x.value,10)))ele_uca_x.value = "0";

			//1ケース数量のコピー
			if(index <= 5){
				var i = parseInt(index,10) + 5;
			}else{
				var i = parseInt(index,10) - 5;
			}
			ele_uca_y = document.getElementById("uca"+i);
			ele_uca_y.value = ele_uca_x.value;

			//合計ケース数・合計本数の計算
			calSumQTY_CASE();
		}

		//各種合計値を算出する。
		function calTOTAL(index){

			//金額の計算
			calTSLR(index);

			//合計金額・精算金額の計算
			calSumTSLR();

			//合計ケース数・合計本数の計算
			calSumQTY_CASE();

		}

		//数量、単価が入力された時に金額を出力する。
		function calTSLR(index){
			var ele_qty = document.getElementById("qty"+index);
			var ele_uslr = document.getElementById("uslr"+index);
			var ele_tslr = document.getElementById("tslr"+index);

			//数字以外の排他チェック
			if(!(parseInt(ele_qty.value,10))) ele_qty.value = 0;
			if(!(parseInt(ele_uslr.value,10))) ele_uslr.value = 0;

			ele_tslr.innerText = parseInt(ele_qty.value,10) * parseInt(ele_uslr.value,10);

			console.log(parseInt(ele_tslr.innerText,10));
		}

		//合計金額・精算金額の計算
		function calSumTSLR(){

			//合計金額の計算
			var sumTslr = 0;
			for(var i=1;i<=10;i++){
				var tslr = document.getElementById("tslr"+i).innerText;
				if(parseInt(tslr,10)){
					sumTslr += parseInt(tslr);
				}
			}

			var t_tslr = document.getElementById("t_tslr");
			t_tslr.innerText = sumTslr + " * 1.08 = " + sumTslr*1.08;

			//精算金額の計算
			var exslr = document.getElementById("uslr11").value;
			var t_slr_amt = document.getElementById("t_slr_amt");
			t_slr_amt.innerText = parseInt(sumTslr,10)*1.08 - parseInt(exslr,10);
		}


		//数量の合計値と本数の合計値を計算する。
		function calSumQTY_CASE(){
			var sumQTY = 0;
			var sumCASE = 0;

			for(var i=1;i<=10;i++){
				var qty = document.getElementById("qty"+i).value;
				var uca = document.getElementById("uca"+i).value;

				sumQTY += parseInt(qty,10) * parseInt(uca,10);
				sumCASE += parseInt(qty,10);
			}

			var t_case = document.getElementById("t_case");
			var t_qty = document.getElementById("t_qty");

			t_case.innerText = sumCASE;
			t_qty.innerText = sumQTY;
		}

		//控除金額入力時に数字以外の排他チェックと精算金額の算出を行う。
		function checkEX(){

			//排他チェック
			var exslr = document.getElementById("uslr11").value;
			if(!(parseInt(exslr,10)))exslr = 0;

			//精算金額の計算
			calSumTSLR();
		}


	</script>

</body>
</html>