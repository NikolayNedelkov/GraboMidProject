package repositories;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import dataclasses.Comment;
import dataclasses.DestinationTrader;
import dataclasses.User;
import dataclasses.Voucher;
import exceptions.CommentException;
import exceptions.UserException;
import exceptions.VoucherException;
import menus.MainMenu;

public class UserRepo {

	private User user;
	private Map<String, User> users;
	private static UserRepo userRepo = null;
	private static final String USER_JSON_FILE = ".//Json//users.json";

	private UserRepo() {
		this.users = new TreeMap<String, User>();
		this.users = getUsersFromJSONFILE();
	}

	public static UserRepo getInstance() {
		if (userRepo == null) {
			userRepo = new UserRepo();

		}
		return userRepo;
	}

	// Login method
	public void login() {
		MainMenu.displayHeader("Login Form");
		String name = MainMenu.askQuestion("Please enter your username: ", null);
		String password = MainMenu.askQuestion("Please enter your password: ", null);
		for (User user : users.values()) {
			if (name.equals(user.getName()) && password.equals(user.getPassword())) {
				user.setLogin(true);
				MainMenu.displayHeader("Login Succesfull");
				this.user = users.get(user.getName());
				break;
			}
		}
		if (this.user == null)
			try {
				throw new UserException("Something went wrong");
			} catch (UserException e) {
				System.out.println(e.getMessage());
			}
	}

	// Method that loads all registered users from Json file
	private static Map<String, User> getUsersFromJSONFILE() {
		Gson gson = new Gson();
		Map<String, User> map = null;
		try (Reader reader = new FileReader(USER_JSON_FILE)) {
			JsonElement json = gson.fromJson(reader, JsonElement.class);
			String jsonInString = gson.toJson(json);

			map = gson.fromJson(jsonInString, new TypeToken<Map<String, User>>() {
			}.getType());

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (map == null) {
			map = new TreeMap<>();
		}
		return map;

	}

	// Method that writes all registered users to Json file
	private void writeUsersToJSONFile(Map<String, User> users) {
		Gson gson = new Gson();
		String json = gson.toJson(users);
		try (FileWriter writer = new FileWriter(USER_JSON_FILE)) {

			gson.toJson(users, writer);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Logout method
	public void logOut() {
		if (this.user!=null && this.user.isLogin()) {
			try {
				this.user.setLogin(false);
				MainMenu.displayHeader("Logout Successful");
				this.user = null;
			} catch (NullPointerException e) {
				MainMenu.displayHeader("No one is Logged in");
			}
		} else {
			MainMenu.displayHeader("No one is Logged in");
		}

	}

	// Method that registers user in Grabo
	public void registerUser() {
		MainMenu.displayHeader("Create user Account");
		String name = MainMenu.askQuestion("Please enter your account username: ", null);
		String password = MainMenu.askQuestion("Please enter your account password: ", null);
		String email = MainMenu.askQuestion("Please enter your account email: ", null);

		try {
			if (password.trim().length() >= 4 && password.trim().length() <= 19 && name.trim().length() > 3) {
				User user = new User(name, password, email);
				this.addNewUser(user);
				MainMenu.displayHeader("Registration Succesfull");
			} else {
				throw new UserException("Wrong username, password or email");
			}
		} catch (UserException e) {
			System.out.println(e.getMessage());
		}

	}

	// Method that adds new user in Grabo/Json file
	public void addNewUser(User user) throws UserException {
		if (user == null)
			throw new UserException("Invalid user");
		users.put(user.getName(), user);
		this.writeUsersToJSONFile(users);

	}

	// Viewing all vouchers
	public void viewAllVouchers() {
		for (Voucher v : VoucherRepo.getInstance().getAllVouchers().values()) {
			System.out.println(v.toString());
		}
	}

	// Viewing all vouchers in Cart
	public void viewCart() {
		if (this.user != null) {
			try {
				for (Voucher v : user.getCart().getMyVouchers().values()) {
					System.out.println(v.toString());
				}
			} catch (NullPointerException e) {
				MainMenu.displayHeader("Please log in first");
			}
		} else {
			MainMenu.displayHeader("Please log in first");
		}

	}

	// Adding voucher to cart
	public void addVoucherToCart() {
		if (this.user != null) {
			try {
				MainMenu.displayHeader("Enter Voucher ID ");
				String id = MainMenu.askQuestion("Please enter voucher id: ", null);
				Integer id2 = Integer.valueOf(id);
				try {
					for (Voucher v : VoucherRepo.getInstance().getAllVouchers().values()) {
						if (v.getVoucherID().equals(id2)) {
							this.user.getCart().addVoucher(v);
						}
					}
				} catch (VoucherException e) {
					System.out.println("Something went wrong !");
				}
			} catch (NumberFormatException e) {
				MainMenu.displayHeader("Please enter valid number");
			}
		} else {
			MainMenu.displayHeader("Please login first");
		}
	}

	// Removing voucher from cart
	public void removeVoucherFromCart() {
		if (this.user != null) {
			try {
				MainMenu.displayHeader("Enter Voucher ID ");
				String id = MainMenu.askQuestion("Please enter voucher id: ", null);
				Integer id2 = Integer.valueOf(id);
				try {
					for (Voucher v : this.user.getCart().getMyVouchers().values()) {
						if (v.getVoucherID().equals(id2))
							this.user.getCart().removeVoucher(v);
					}
				} catch (VoucherException e) {
					System.out.println("Something went wrong !");
				}
			} catch (Exception e) {
				MainMenu.displayHeader("Please enter valid number");
			}
		} else {
			MainMenu.displayHeader("Please login first");
		}
	}

	// Writing comment under Destination Trader wall
	public void writeComment() {
		if (this.user != null) {
			try {
				MainMenu.displayHeader("Enter Destonation Trader ID ");
				String id = MainMenu.askQuestion("Please enter Trader id: ", null);
				Integer id2 = Integer.valueOf(id);
				try {
					for (DestinationTrader destinationTrader : DestinationTraderRepo.getInstance()
							.getAllDestinationTraders().values()) {
						if (destinationTrader.getDestinationTraderID().equals(id2)) {
							MainMenu.displayHeader("Destination Trader Found");
							String comment = MainMenu.askQuestion("Enter your comment: ", null);
							String rating = MainMenu.askQuestion("Enter rating between 0 and 5: ", null);
							Double rating2 = Double.parseDouble(rating);
							DestinationTraderRepo.getInstance().addNewComment(new Comment(this.user, destinationTrader, comment, rating2));
//							destinationTrader.addComment(new Comment(this.user, destinationTrader, comment, rating2));
						}
					}
				} catch (Exception e) {
					System.out.println("Something went wrong");
				}
			} catch (Exception e) {
				System.out.println("Something went wrong");
			}
		} else {
			MainMenu.displayHeader("Please login first");
		}
	}

	// View all Destination Traders
	public void viewAllDestinationTraders() {
		for (DestinationTrader dest : DestinationTraderRepo.getInstance().getAllDestinationTraders().values()) {
			System.out.println(dest.toString());
		}
	}

}
