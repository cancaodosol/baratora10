<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="style.css" type="text/css">
	<title>販売代金請求通知書の登録</title>
</head>


<body>
	<div id = "itemname">品目ID：　　品名　：　</div>
	<button id="addButton">行追加</button>

	<form action="InsertSalesResultServlet" method= "POST">
		<div id = "date">出荷日　：　
			<input type = "date" name="date" value = "2018-12-13"><br>
		</div>

		<table>
			<thead>
				<th></th>
				<th>等階級</th>
				<th>1ケース本数</th>
				<th>数量</th>
				<th>単価</th>
				<th>金額</th>
				<th>累計数量</th>
				<th>累計金額</th>
			</thead>
			<tbody id="tbody"></tbody>
			<input type="hidden" name="itemid" value="1">
			<input type="submit" value="登録する">
		</table>
	</form>

	<table style="display: none;">
	  <tbody>
	    <tr id="template">
	      <td>
					<button class="js-removeButton">削除</button>
				</td>
	      <td>
					<input class="js-rank" />
				</td>
				<td>
					<input class="js-uca" />
				</td>
				<td>
					<input class="js-qty" />
				</td>
				<td>
					<input class="js-uslr" />
				</td>
				<td>
					<input class="js-tslr" />
				</td>
				<td>

				</td>
				<td>

				</td>
	    </tr>
	  </tbody>
  </table>


	<script>

		var addButton = document.getElementById('addButton');
		var tbody = document.getElementById('tbody');
		var template = document.getElementById('template');

		//明細項目
		var rank = template.querySelector('.js-rank');
		var uca = template.querySelector('.js-uca');
		var qty = template.querySelector('.js-qty');
		var uslr = template.querySelector('.js-uslr');

		addButton.addEventListener('click',addRow);

		tbody.addEventListener('click',function(event){
			var target = event.target;

			if(target.className === 'js-removeButton'){
				var row = getRowFromParents(target)
				removeElement(row)

			}
		});

		var nextRowIndex = 0;

		//行追加処理
		function addRow(){
			nextRowIndex++;
			var rowIndex = nextRowIndex;

			rank.name = 'rank'+ rowIndex;
			rank.type = 'hidden';
			rank.innerHTML = "ランク名が入ります";
			uca.name = 'data['+ rowIndex +'].uca';
			qty.name = 'data['+ rowIndex +'].qty';
			uslr.name = 'data['+ rowIndex +'].uslr';

			var row = template.cloneNode(true);
			console.log(row);

			tbody.appendChild(row);
		}

		function removeElement(element){
			element.parentNode.removeChild(element);
		}

		function getRowFromParents(element){
			for(var target = element;target != null; target = target.parentNode){
				if(target.nodeName === 'TR'){
					return target;
				}
			}

			return null;
		}

		addRow();

	</script>


</body>
</html>