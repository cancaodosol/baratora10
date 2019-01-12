package baratora10;

import java.sql.Date;

public class SalesResult {

	private int itemId= 0;
	private String itemName = null;
	private Date ship_date = null;
	private int rank = 0;
	private String rankname = null;
	private int unit_case = 0;
	private int num_case = 0;
	private double sales_amount = 0;
	private int area = 0;
	private int totalCase = 0;
	private int totalSales = 0;


	public int getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(int totalSales) {
		this.totalSales = totalSales;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getTotalCase() {
		return totalCase;
	}
	public void setTotalCase(int totalCase) {
		this.totalCase = totalCase;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Date getShip_date() {
		return ship_date;
	}
	public void setShip_date(Date ship_date) {
		this.ship_date = ship_date;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getRankname() {
		return rankname;
	}
	public void setRankname(String rankname) {
		this.rankname = rankname;
	}
	public int getUnit_case() {
		return unit_case;
	}
	public void setUnit_case(int unit_case) {
		this.unit_case = unit_case;
	}
	public int getNum_case() {
		return num_case;
	}
	public void setNum_case(int num_case) {
		this.num_case = num_case;
	}
	public double getSales_amount() {
		return sales_amount;
	}
	public void setSales_amount(double sales_amount) {
		this.sales_amount = sales_amount;
	}

}
