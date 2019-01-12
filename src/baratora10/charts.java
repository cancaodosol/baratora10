package baratora10;

import java.util.List;

public class charts {
	private String[] x_label;
	private List<charts_y> y_label;
	private String title;
	private String unit;
	public String[] getX_label() {
		return x_label;
	}
	public void setX_label(String[] x_label) {
		this.x_label = x_label;
	}
	public List<charts_y> getY_label() {
		return y_label;
	}
	public void setY_label(List<charts_y> y_label) {
		this.y_label = y_label;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

}
