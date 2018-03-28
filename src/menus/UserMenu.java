package menus;

import repositories.UserRepo;

public class UserMenu {
	
	public static boolean back;
	private static UserMenu menu = null;
	private static MainMenu mainMenu = MainMenu.getIntance();
	private static UserRepo userRepo = UserRepo.getInstance();
	
	private UserMenu() {
		
	}
	
	public static UserMenu getIntance() {
		if(menu==null) {
			menu = new UserMenu();
		}
		return menu;
	}
	
	//Infinite User Menu Cycle
	public void runMenu() {
		while(!back) {
			 printMenu();
			 String choice = MainMenu.getMenuChoice();
	         performAction(choice);
		 }
	}
	
	
	//Printing User Menu Options
	public void printMenu() {
        MainMenu.displayHeader("Please select a Menu: ");
        System.out.println("-> Register");
        System.out.println("-> Login");
        System.out.println("-> Logout");
        System.out.println("-> View All Vouchers");
        System.out.println("-> Cart View");
        System.out.println("-> Add Voucher");
        System.out.println("-> Remove Voucher");
        System.out.println("-> View All Destiantion Traders");
        System.out.println("-> Write Comment");
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
                userRepo.registerUser();
            break;
            case "login":
               userRepo.login();
               break;
            case "logout":
                userRepo.logOut();;
                break;
            case "view all vouchers":
                userRepo.viewAllVouchers();;
                break;  
            case "cart view":
                userRepo.viewCart();;
                break; 
            case "add voucher":
                userRepo.addVoucherToCart();
                break;
            case "remove voucher":
                userRepo.removeVoucherFromCart();
                break;
            case "view all destination traders":
            	userRepo.viewAllDestinationTraders();
            	break;
            case "write comment":
                userRepo.writeComment();
                break;
            
            default:
                System.out.println("Unknown error has occured.");
        }
    }
}
