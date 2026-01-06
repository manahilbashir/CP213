package cp213;

import java.util.Scanner;

public class Cashier {

    private Menu menu = null;

    public Cashier(Menu menu) {
	this.menu = menu;
    }

    private int askForQuantity(Scanner scan) {
	int quantity = 0;
	System.out.print("How many do you want? ");
	try {
	    String line = scan.nextLine();
	    quantity = Integer.parseInt(line);
	} catch (NumberFormatException nfex) {
	    System.out.println("Not a valid number");
	}
	return quantity;
    }

    private void printCommands() {
	System.out.println("\nMenu:");
	System.out.println(menu.toString());
	System.out.println("Press 0 when done.");
	System.out.println("Press any other key to see the menu again.\n");
    }

    public Order takeOrder() {
	Scanner input = new Scanner(System.in);
	Order order = new Order();

	System.out.println("Welcome to WLU Foodorama!");
	printCommands();

	while (true) {
	    System.out.print("\nCommand: ");
	    String commandStr = input.nextLine();

	    int command;
	    try {
		command = Integer.parseInt(commandStr);
	    } catch (NumberFormatException e) {
		System.out.println("Not a valid number");
		printCommands();
		continue;
	    }

	    if (command == 0) {
		break;
	    }

	    if (command < 1 || command > menu.size()) {
		System.out.println("Not a valid number");
		printCommands();
		continue;
	    }

	    MenuItem item = menu.getItem(command - 1);
	    int quantity = askForQuantity(input);
	    if (quantity > 0) {
		order.add(item, quantity);
	    }
	}

	System.out.println("----------------------------------------");
	System.out.println("Receipt");
	System.out.print(order);

	return order;
    }
}
