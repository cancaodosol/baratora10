SELECT
	I.name AS 品目名,
	to_char(ship_date,'YYYY/MM/DD(DY)') AS 出荷日,
	S.name AS 等階級,
	unit_case AS ケース本数,
	num_case AS 箱数,
	to_char(sales_amount, '99,999,990.00') AS 箱単価,
	area AS 坪数
FROM public.sales_results

LEFT JOIN m_item AS I ON itemid = I.id
LEFT JOIN t_score AS S ON S.id = rank

ORDER BY ship_date,itemid,rank;