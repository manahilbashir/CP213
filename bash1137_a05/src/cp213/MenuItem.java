package cp213;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MenuItem {

    private static final String itemFormat = "%-12s $%5.2f";
    private String descript = null;
    private BigDecimal cost = null;

    public MenuItem(final String descript, final BigDecimal cost) {
	this.descript = descript;
	this.cost = cost.setScale(2, RoundingMode.HALF_UP);
    }

    public MenuItem(final String descript, final double cost) {
	this(descript, BigDecimal.valueOf(cost));
    }

    public String getDescript() {
	return this.descript;
    }

    public BigDecimal getCost() {
	return this.cost;
    }

    @Override
    public String toString() {
	return String.format(itemFormat, this.descript, this.cost.doubleValue());
    }
}
