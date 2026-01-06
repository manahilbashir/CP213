package cp213;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Lab task class.
 *
 * @author David Brown
 * @version 2025-01-05
 */
public class ExceptionsTest {

    /**
     * @param args unused
     */
    public static void main(String[] args) {
	System.out.println("Test scannerTest");
	System.out.println();
	Scanner keyboard = new Scanner(System.in);
	int total = scannerTest(keyboard);
	keyboard.close();
	System.out.println("Total: " + total);
	System.out.println();
	System.out.println("-".repeat(40));
	System.out.println("Test stringPrinter");
	System.out.println();
	// provide the try/catch block to call:

	try {
	    String output = stringPrinter(5, "*");
	    System.out.println(output);
	    output = stringPrinter(-5, "*");
	    System.out.println(output);
	} catch (Exception e) {
	    System.out.println();
	    System.out.println("getMessage:");
	    System.out.println(e.getMessage());
	    System.out.println();
	    System.out.println("toString:");
	    System.out.println(e.toString());
	    System.out.println();
	    System.out.println("printStackTrace:");
	    e.printStackTrace();
	}
    }

    /**
     * Uses exceptions to capture bad input from a keyboard Scanner.
     *
     * @param keyboard for keyboard input
     * @return The total of all the integers entered.
     */
    public static int scannerTest(final Scanner keyboard) {
	int total = 0;

	while (true) {
	    System.out.print("Enter an integer (\"Quit\" to stop): ");
	    String input = keyboard.nextLine();

	    if (input.equalsIgnoreCase("Quit")) {
		break;
	    }

	    try {
		int number = Integer.parseInt(input); // triggers NumberFormatException
		total += number;
	    } catch (InputMismatchException e) {
		System.out.println("That is not an integer!");
		keyboard.nextLine(); // clear the bad token
	    } catch (NumberFormatException e) {
		System.out.println("That is not an integer!");
	    }
	}

	System.out.println("Total: " + total);
	return total;
    }

    /**
     * Repeats a string.
     *
     * @param n   Number of times to repeat a string.
     * @param str The string to print.
     * @return The repeated string.
     * @throws Exception If n is negative.
     */
    public static String stringPrinter(int n, String str) throws Exception {
	if (n < 0) {
	    throw new Exception("Please Enter a Positive Number!");
	}

	String result = "";
	for (int i = 0; i < n; i++) {
	    result += str;
	}

	return result;
    }

}
