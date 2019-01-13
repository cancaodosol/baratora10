SELECT
	I.name AS 品目名,
	area AS 坪数,
	to_char(ship_date,'YYYY/MM/DD(DY)') AS 出荷日,
	S.name AS 等階級,
	unit_case AS ケース本数,
	num_case AS 箱数,
	to_char(sales_amount, '99,999,990.00') AS 箱単価,
	to_char(sales_amount*num_case,'999,999,990.00') AS 金額（税別）,
	to_char(sales_amount*num_case * 1.08,'999,999,990.00') AS 金額（税込）,
	to_char(sales_amount/(unit_case*num_case),'999,999,990.00') AS 単価（税別）,
	to_char(sales_amount/(unit_case*num_case) * 1.08,'999,999,990.00') AS 単価（税込）
FROM public.sales_results

LEFT JOIN m_item AS I ON itemid = I.id
LEFT JOIN t_score AS S ON S.id = rank

WHERE itemid = ? and unit_case <> 0

ORDER BY ship_date,rank;