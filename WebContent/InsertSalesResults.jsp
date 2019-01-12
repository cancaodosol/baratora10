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
	<%List<SalesResult> LSR = (List<SalesResult>)request.getAttribute("LSR"); %>
	<%int index = 0; %>
	<%SalesResult sales = LSR.get(index); %>
</head>


<body>

	<form action="InsertSalesResultServlet" method= "POST">
		<table>
			<tr>
				<td><div id = "itemname">品目ID：</td><td><%=sales.getItemId() %><td>品名：</td><td><%=sales.getItemName() %></div></td>
			</tr>
			<tr>
				<td><div>出荷日：</div></td><td><input id="date" type = "date" name="date" value = "<%=sales.getShip_date() %>" style="text-align: right;"　onchange="getDayOfWeek()"/><div id="dayOfWeek"></div></td>
				<td><div>品種坪数：</div></td><td><input id = "area" type = "number" name="area" value = "<%=sales.getArea() %>" style="text-align: right;"/></td>
			</tr>
		</table>

		<table class = summarySheet>
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
					<%sales = LSR.get(i-1);%>
			    	<tr>
						<td>
							<%=sales.getRankname()%>
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
							<div class="js-tqty" id = "tqty<%=i %>" style="text-align: right;"><%=sales.getTotalCase() %></div>
						</td>
						<td>
							<div class="js-ttslr" id = "ttslr<%=i %>" style="text-align: right;"><%=sales.getTotalSales() %></div>
						</td>
			    	</tr>
			    	<%} %>
				 <tr>

				 </tr>
				 <tr style="text-align: right;">
					 <td></td>
					 <td>計</td><td><div id = "t_case">0</div></td>
					 <td></td><td><div id = "t_tslr">0</div></td>
					 <td><div id = "t_tqty">0</div></td><td><div id = "t_ttslr">0</div></td>
				 </tr>
				 <tr style="text-align: right;">
					 <td></td>
					 <td>本数</td><td><div id = "t_qty">0</div></td>
					 <td>控除金額</td>
					 <td>
							<input type="hidden" name = "rank11" value = "99"/>
							<input type="hidden" name = "uca11" value = "0"/>
							<input type="hidden" name = "qty11" value = "-1"/>
							<input class="js-uslr" id = "uslr11" type="number" name = "uslr11" value = "0" style="text-align: right;" onchange="checkEX()"/>
					</td>
					 <td>精算金額</td><td><div id = "t_slr_amt">0</div></td>
				 </tr>
				 <tr style="text-align: right;">
						<td></td>
						<td>累計本数</td><td><div id="tt_qty"><%=LSR.get(11).getUnit_case()%></div></td>
						<td>平均単価</td><td><div id="av_tslr">0</div></td>
						<td>累計金額</td><td><div id="tt_slr_amt">0</div></td>
				</tr>
				<tr style="text-align: right;">
					   <td></td>
					   <td>坪本数</td><td><div id="ava_qty">0</div></td>
					   <td>累計単価</td><td><div id="av_ttslr">0</div></td>
					   <td>坪金額</td><td><div id="ava_ttslr">0</div></td>
			   </tr>


			</tbody>
		</table>
		<input type="hidden" name="itemid" value="<%=sales.getItemId()%>"/>
		<input type="submit" value="登録する"/>
	</form>

	<script>

		//累計値保存用配列
		//累計数量
		var ARY_t_qty = [
			<%for(int i=0;i<9;i++){%><%=LSR.get(i).getTotalCase()%>,<%}%><%=LSR.get(9).getTotalCase()%>
		];

		//累計金額
		var ARY_ttslr = [
			<%for(int i=0;i<9;i++){%><%=LSR.get(i).getTotalSales()%>,<%}%><%=LSR.get(9).getTotalSales()%>
		];

		//累計本数
		var ott_qty = <%=LSR.get(11).getUnit_case()%>;


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

			//累計数量・累計金額の計算
			calTTSLR(index);

			//合計金額・精算金額の計算
			calSumTSLR();

			//合計ケース数・合計本数の計算
			calSumQTY_CASE();

			//累計本数の計算
			calTQTY();

			//累計数量・累計金額の合計値の計算
			calSumTTSLR();

			//坪本数・平均単価・累計単価・坪金額の計算
			calAve();

		}

		//金額の計算
		//数量、単価が入力された時に金額を出力する。
		function calTSLR(index){
			var ele_qty = document.getElementById("qty"+index);
			var ele_uslr = document.getElementById("uslr"+index);
			var ele_tslr = document.getElementById("tslr"+index);

			//数字以外の排他チェック
			if(!(parseInt(ele_qty.value,10))) ele_qty.value = 0;
			if(!(parseInt(ele_uslr.value,10))) ele_uslr.value = 0;

			ele_tslr.innerText = parseInt(ele_qty.value,10) * parseInt(ele_uslr.value,10);
		}

		//累計数量・累計金額の計算
		function calTTSLR(index){

			//累計数量の保存
			var tqty = document.getElementById("tqty"+index);
			var qty = document.getElementById("qty"+index).value;
			var tmp = parseInt(ARY_t_qty[index-1],10) + parseInt(qty,10);
			tqty.innerText = tmp;

			//累計金額の保存
			var ttslr = document.getElementById("ttslr"+index);
			var tslr = document.getElementById("tslr"+index).innerText;
			tmp = parseInt(ARY_ttslr[index-1],10) + parseInt(tslr,10);
			ttslr.innerText = tmp;
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
			t_tslr.innerText = sumTslr;

			//精算金額の計算
			var exslr = document.getElementById("uslr11").value;
			var t_slr_amt = document.getElementById("t_slr_amt");
			t_slr_amt.innerText = parseInt(sumTslr,10) - parseInt(exslr,10);
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

		//累計本数の計算
		function calTQTY(){
			var tt_qty = document.getElementById("tt_qty");
			var t_qty = document.getElementById("t_qty").innerText;
			tmp = parseInt(ott_qty,10) + parseInt(t_qty,10);
			tt_qty.innerText = tmp;
		}

		//累計数量・累計金額の合計値の計算
		function calSumTTSLR(){
			var tmp_tqty = 0;
			var tmp_ttslr = 0;

			for(var i=1;i<=10;i++){
				var tqty = document.getElementById("tqty"+i).innerText;
				var ttslr = document.getElementById("ttslr"+i).innerText;

				tmp_tqty += parseInt(tqty,10);
				tmp_ttslr += parseInt(ttslr,10);
			}

			//どうなってる？
			var t_tqty = document.getElementById("t_tqty").innerText = tmp_tqty;
			var t_ttslr = document.getElementById("t_ttslr").innerText = tmp_ttslr;
		}

		//坪本数・平均単価・累計単価・坪金額の計算
		function calAve(){

			//坪本数計算
			var area = document.getElementById("area").value;
			var tt_qty = document.getElementById("tt_qty").innerText;
			var ava_qty = document.getElementById("ava_qty");

			ava_qty.innerText = (parseFloat(tt_qty,10) / parseFloat(area,10)).toFixed(2);

			//平均単価
			var t_tslr = document.getElementById("t_tslr").innerText;
			var t_qty = document.getElementById("t_qty").innerText;
			var av_tslr = document.getElementById("av_tslr");

			av_tslr.innerText = (parseFloat(t_tslr,10)/parseFloat(t_qty,10)).toFixed(2);

			//累計単価
			var t_ttslr = document.getElementById("t_ttslr").innerText;
			var av_ttslr = document.getElementById("av_ttslr");

			av_ttslr.innerText = (parseFloat(t_ttslr,10)/parseFloat(tt_qty,10)).toFixed(2);

			//坪金額
			var ava_ttslr = document.getElementById("ava_ttslr");
			ava_ttslr.innerText = (parseFloat(t_ttslr,10)/parseFloat(area,10)).toFixed(2);

		}

		//曜日取得
		function getDayOfWeek(){
			var date = document.getElementById("date");
			var dateFormat = new Date();
			dateFormat.setDate(date.value);
			var dayOfWeekStr = [ "日", "月", "火", "水", "木", "金", "土" ][dateFormat.getDay()];

			console.log(dateFormat);
			console.log(dayOfWeekStr);

			var dayOfWeek = document.getElementById("dayOfWeek");
			dayOfWeek.innerText = dayOfWeekStr;
		}


		//控除金額入力時に数字以外の排他チェックと精算金額の算出を行う。
		function checkEX(){

			//排他チェック
			var exslr = document.getElementById("uslr11").value;
			if(!(parseInt(exslr,10)))exslr = 0;

			//精算金額の計算
			calSumTSLR();
		}

		//曜日取得
		getDayOfWeek();
		//累計数量・累計金額の合計値の計算
		calSumTTSLR();
		//坪本数・平均単価・累計単価・坪金額の計算
		calAve();

	</script>

</body>
</html>