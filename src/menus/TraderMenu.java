package menus;

import dataclasses.DestinationTrader;
import repositories.DestinationTraderRepo;
import repositories.UserRepo;

public class TraderMenu {
	
	public static boolean back;
	private static TraderMenu menu = null;
	private static MainMenu mainMenu = MainMenu.getIntance();
	private static DestinationTraderRepo destinationTraderRepo = DestinationTraderRepo.getInstance();
	
	
	public static TraderMenu getIntance() {
		if	(menu==null) {
			menu = new TraderMenu();
		}
		return menu;
	}
	
	//Infinite DestinationTrader Menu Cycle
	public void runMenu() {
		while(!back) {
			 printMenu();
			 String choice = MainMenu.getMenuChoice();
	         performAction(choice);
		 }
	}
	//Printing DestinationTrader Menu Options
	public void printMenu() {
        MainMenu.displayHeader("Please select an Option from Trader Menu: ");
        System.out.println("-> Register");
        System.out.println("-> Login");
        System.out.println("-> Logout");
        System.out.println("-> View Your Vouchers");
        System.out.println("-> Add Voucher");
//      System.out.println("-> Remove Voucher");
        System.out.println("-> View all comments");
        System.out.println("-> View your rating");
        System.out.println("-> Back");
    }
	
	//Performing action , choosing option from UserMenu with string input
		private void performAction(String choice) {
	        switch (choice) {
	            case "back":
	                System.out.println("Going Back.");
	                UserMenu.back = true;
	                MainMenu.exit = false;
	                mainMenu.runMenu();
	                break;
	            case "register": 
	            	destinationTraderRepo.registerTrader();
	            break;
	            case "login":
	            	destinationTraderRepo.login();
	               break;
	            case "logout":
	            	destinationTraderRepo.logOut();;
	                break;
	            case "view your vouchers":
	            	destinationTraderRepo.viewAllVouchers();;
	                break;  
	            case "add voucher":
	            	destinationTraderRepo.addVoucher();;
	                break; 
//	            case "remove voucher":
//	            	destinationTraderRepo.removeVoucher();
//	                break;
	            case "view all comments":
	            	destinationTraderRepo.viewAllComments();
	                break;
	            case "view your rating":
	            	destinationTraderRepo.viewYourRating();
	            	break;	            
	            default:
	                System.out.println("Unknown error has occured.");
	        }
	    }
}
