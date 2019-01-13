SELECT
	I.name AS 品目名,
	TO_CHAR(ship_date,'YYYY年') AS 年,
	EXTRACT(WEEK FROM ship_date) AS 集計週,
	sum(unit_case*num_case) AS 本数,
	sum(num_case) AS 箱数,
	to_char(sum(sales_amount*num_case), '99,999,999,990') AS 売上（税別）,
	to_char(sum(sales_amount*num_case)*1.08, '99,999,999,990') AS 売上（税込） ,
	to_char(sum(sales_amount*num_case)/sum(unit_case*num_case),'999,999,990.00') AS 単価（税別）,
	to_char(sum(sales_amount*num_case)/sum(unit_case*num_case) * 1.08,'999,999,990.00') AS 単価（税込）
FROM public.sales_results

LEFT JOIN m_item AS I ON itemid = I.id
LEFT JOIN t_score AS S ON S.id = rank

WHERE  itemid = ? and unit_case <> 0

GROUP BY itemid,I.name,TO_CHAR(ship_date,'YYYY年'),EXTRACT(WEEK FROM ship_date)
ORDER BY itemid,TO_CHAR(ship_date,'YYYY年'),EXTRACT(WEEK FROM ship_date);