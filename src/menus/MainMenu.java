package menus;

import java.util.List;
import java.util.Scanner;

import dataclasses.DestinationTrader;

public class MainMenu {

	public static boolean exit;
	private static MainMenu menu = null;
	private UserMenu userMenu = UserMenu.getIntance();
	private TraderMenu traderMenu = TraderMenu.getIntance();

	private MainMenu() {

	}

	public static MainMenu getIntance() {
		if (menu == null) {
			menu = new MainMenu();
		}
		return menu;
	}

	public void printHeader() {
		System.out.println("+---------------------------------+");
		System.out.println("|        Welcome to Grabo         |");
		System.out.println("|                                 |");
		System.out.println("+---------------------------------+");
	}

	// Infinite while cycle of Main menu
	public void runMenu() {
		printHeader();
		while (!exit) {
			printMenu();
			String choice = getMenuChoice();
			performAction(choice);
		}
	}

	// Printing main Menu
	public void printMenu() {
		displayHeader("Please select a Menu: ");
		System.out.println("-> User menu");
		System.out.println("-> Trader menu");
		System.out.println("-> Exit");
	}

	// Entering choice for menu option (String)
	public static String getMenuChoice() {
		Scanner sc = new Scanner(System.in);
		String choice = null;
		System.out.print("Enter your choice: ");
		choice = sc.nextLine().toLowerCase();
		return choice;
	}

	// Performing action, choosing subMenu
	private void performAction(String choice) {
		switch (choice) {
		case "exit":
			System.out.println("Thank you for using Grabo.");
			MainMenu.exit = true;
			break;
		case "user menu":
			UserMenu.back = false;
			userMenu.runMenu();
			break;
		case "trader menu":
			TraderMenu.back = false;
			traderMenu.runMenu();
			break;
		default:
			System.out.println("Unknown error has occured.");
		}
	}

	// Method that display headers in Console
	public static void displayHeader(String message) {
		System.out.println();
		int width = message.length() + 6;
		StringBuilder sb = new StringBuilder();
		sb.append("+");
		for (int i = 0; i < width; ++i) {
			sb.append("-");
		}
		sb.append("+");
		System.out.println(sb.toString());
		System.out.println("|   " + message + "   |");
		System.out.println(sb.toString());
	}

	// Method that asks questions in Console
	public static String askQuestion(String question, List<String> answers) {
		String response = "";
		Scanner keyboard = new Scanner(System.in);
		boolean choices = ((answers == null) || answers.size() == 0) ? false : true;
		boolean firstRun = true;
		do {
			if (!firstRun) {
				System.out.println("Invalid selection. Please try again.");
			}
			System.out.print(question);
			if (choices) {
				System.out.print("(");
				for (int i = 0; i < answers.size() - 1; ++i) {
					System.out.print(answers.get(i) + "/");
				}
				System.out.print(answers.get(answers.size() - 1));
				System.out.print("): ");
			}
			response = keyboard.nextLine();
			firstRun = false;
			if (!choices) {
				break;
			}
		} while (!answers.contains(response));
		return response;
	}
}
