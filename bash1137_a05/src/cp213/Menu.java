package cp213;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private List<MenuItem> items = new ArrayList<>();

    public Menu(List<MenuItem> items) {
	this.items = new ArrayList<>(items);
    }

    public Menu(Scanner fileScanner) {
	while (fileScanner.hasNextLine()) {
	    String line = fileScanner.nextLine();
	    Scanner lineScan = new Scanner(line);
	    if (lineScan.hasNextBigDecimal() && lineScan.hasNext()) {
		BigDecimal price = lineScan.nextBigDecimal();
		String name = lineScan.nextLine().trim();
		MenuItem item = new MenuItem(name, price);
		items.add(item);
	    }
	    lineScan.close();
	}
    }

    public MenuItem getItem(int i) {
	return items.get(i);
    }

    public int size() {
	return items.size();
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < items.size(); i++) {
	    sb.append(String.format("%2d) %s\n", i + 1, items.get(i)));
	}
	return sb.toString();
    }
}
