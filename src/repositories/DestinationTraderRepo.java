package repositories;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import dataclasses.Comment;
import dataclasses.DestinationTrader;
import dataclasses.Price;
import dataclasses.Voucher;
import dataclasses.VoucherInformation;
import exceptions.DestinationTraderException;
import exceptions.VoucherException;
import menus.MainMenu;

public class DestinationTraderRepo {

	private DestinationTrader destinationTrader;
	private Map<Integer, DestinationTrader> allDestinationTraders;
	private static DestinationTraderRepo destinationTraderRepo = null;
	private static final String DESTINATION_TRADER_JSON_FILE = ".//Json//destinationTraders.json";
//	private static File f;

	private DestinationTraderRepo() {
		this.allDestinationTraders = new TreeMap<Integer, DestinationTrader>();
		this.allDestinationTraders = getDestinationTraderFromJSONFILE();
	}

	public static DestinationTraderRepo getInstance() {
		if (destinationTraderRepo == null) {
			destinationTraderRepo = new DestinationTraderRepo();
		}
		return destinationTraderRepo;
	}

	private static Map<Integer, DestinationTrader> getDestinationTraderFromJSONFILE() {
		Gson gson = new Gson();
		TreeMap<Integer, DestinationTrader> map = null;
		try (Reader reader = new FileReader(DESTINATION_TRADER_JSON_FILE)) {
			JsonElement json = gson.fromJson(reader, JsonElement.class);
			String jsonInString = gson.toJson(json);

			map = gson.fromJson(jsonInString, new TypeToken<TreeMap<Integer, DestinationTrader>>() {
			}.getType());
			DestinationTrader.id =  map.lastKey();

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (map == null) {
			map = new TreeMap<>();
		}
		return map;

	}

	private void writeDestinationTradersToJSONFile(Map<Integer, DestinationTrader> allDestinationTraders) {
		Gson gson = new Gson();
		String json = gson.toJson(allDestinationTraders);
		try (FileWriter writer = new FileWriter(DESTINATION_TRADER_JSON_FILE)) {

			gson.toJson(allDestinationTraders, writer);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public static List<Comment> getDestinationTradersCommentsToJSONFile() {
//		Gson gson = new Gson();
//		List<Comment> list = null;
//		try (Reader reader = new FileReader(f)) {
//			JsonElement json = gson.fromJson(reader, JsonElement.class);
//			String jsonInString = gson.toJson(json);
//
//			list = gson.fromJson(jsonInString, new TypeToken<List<Comment>>() {
//			}.getType());
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		if (list == null) {
//			list = new ArrayList<>();
//		}
//		return list;
//
//	}
//
//	private void writeDestinationTradersCommentsToJSONFile(List<Comment> comments) {
//		Gson gson = new Gson();
//		String json = gson.toJson(comments);
//		try (FileWriter writer = new FileWriter(f)) {
//			gson.toJson(comments, writer);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public void addNewDestinationTrader(DestinationTrader destinationTrader) throws DestinationTraderException {
		if (destinationTrader == null)
			throw new DestinationTraderException("Invalid destination trader");
		allDestinationTraders.put(destinationTrader.getDestinationTraderID(), destinationTrader);
		this.writeDestinationTradersToJSONFile(allDestinationTraders);

	}

	public Map<Integer, DestinationTrader> getAllDestinationTraders() {
		return Collections.unmodifiableMap(allDestinationTraders);
	}

	public void registerTrader() {
		MainMenu.displayHeader("Create trader Account");
		String name = MainMenu.askQuestion("Please enter your trader account username: ", null);
		String password = MainMenu.askQuestion("Please enter your trader account password: ", null);
		String email = MainMenu.askQuestion("Please enter your trader account email: ", null);
		String information = MainMenu.askQuestion("Please enter your trader account information: ", null);
		String address = MainMenu.askQuestion("Please enter your trader account physical address: ", null);

		try {
			if (password.trim().length() >= 4 && password.trim().length() <= 19 && name.trim().length() > 3) {
				DestinationTrader dt = new DestinationTrader(name, password, email, information, address);
				this.addNewTrader(dt);
				MainMenu.displayHeader("Registration Succesfull");
			} else {
				throw new DestinationTraderException("Wrong trader information");
			}
		} catch (DestinationTraderException e) {
			System.out.println(e.getMessage());
		}

	}

//	public void addNewComment(Comment c) throws CommentException, IOException {
//		if (c == null)
//			throw new CommentException("Invalid comment");
//		DestinationTraderRepo.f = new File(
//				"Json" + File.separator + ("comments" + c.getDestination().getDestinationTraderID()) + ".json");
//		if (!DestinationTraderRepo.f.exists()) {
//			DestinationTraderRepo.f.createNewFile();
//		}
//		this.destinationTrader = c.getDestination();
//		destinationTrader.addComment(c);
//		this.writeDestinationTradersCommentsToJSONFile(destinationTrader.getComments());
//
//	}

	// Method that adds new user in Grabo/Json file
	public void addNewTrader(DestinationTrader dt) throws DestinationTraderException {
		if (dt == null)
			throw new DestinationTraderException("Invalid trader");
		allDestinationTraders.put(dt.getDestinationTraderID(), dt);
		this.writeDestinationTradersToJSONFile(allDestinationTraders);

	}

	public void login() {
		MainMenu.displayHeader("Login Form");
		String name = MainMenu.askQuestion("Please enter trader your username: ", null);
		String password = MainMenu.askQuestion("Please enter your trader password: ", null);
		for (DestinationTrader dt : allDestinationTraders.values()) {
			if (name.equals(dt.getTraderName()) && password.equals(dt.getTraderPassword())) {
				dt.setLogin(true);
				MainMenu.displayHeader("Login Succesfull");
				this.destinationTrader = allDestinationTraders.get(dt.getDestinationTraderID());
				break;
			}
		}
		if (this.destinationTrader == null)
			try {
				throw new DestinationTraderException("Something went wrong");
			} catch (DestinationTraderException e) {
				System.out.println(e.getMessage());
			}

	}

	public void logOut() {
		if (this.destinationTrader != null && this.destinationTrader.isLogin()) {
			try {
				this.destinationTrader.setLogin(false);
				MainMenu.displayHeader("Logout Successful");
				this.destinationTrader = null;
			} catch (NullPointerException e) {
				MainMenu.displayHeader("No one is Logged in");
			}
		} else {
			MainMenu.displayHeader("No one is Logged in");
		}

	}

	public void viewAllVouchers() {
		try {
			if (this.destinationTrader.getVouchers().isEmpty()) {
				MainMenu.displayHeader("You don't have Vouchers");
			} else {
				for (Voucher v : this.destinationTrader.getVouchers().values()) {
					System.out.println(v.toString());
				}
			}
		} catch (NullPointerException e) {
			MainMenu.displayHeader("Plese Login as Trader");
		}

	}

	public void addVoucher() {
		if (this.destinationTrader != null) {

			MainMenu.displayHeader("Enter Voucher Information: ");
			String category = MainMenu.askQuestion("Please enter voucher category: ", null);
			String price = MainMenu.askQuestion("Please enter voucher oldPrice: ", null);
			String disc = MainMenu.askQuestion("Please enter voucher discount between 0 and 100%: ", null);
			Integer oldPrice = Integer.valueOf(price);
			Integer discount = Integer.valueOf(disc);
			String information = MainMenu.askQuestion("Please enter voucher information: ", null);
			String conditions = MainMenu.askQuestion("Plese enter voucher conditons: ", null);
			try {
				Voucher v = new Voucher(category, new Price(oldPrice, discount),
						new VoucherInformation(information, conditions, destinationTrader.getTraderName()),
						this.destinationTrader);
				this.destinationTrader.addVoucher(v);
			} catch (VoucherException e) {
				MainMenu.displayHeader("Please add valid voucher information");
			}
		} else {
			MainMenu.displayHeader("Please login as trader first");
		}

	}
	// // Method that adds new vouchers in Grabo/Json file
	// public void addNewVoucher(Voucher v) throws VoucherException {
	// if (v== null)
	// throw new VoucherException("Invalid vouhcer");
	// destinationTrader.getVouchers().put(v.getVoucherID(), value);
	// this.writeDestinationTradersToJSONFile(allDestinationTraders);
	//
	// }

	public void removeVoucher() {
		// TODO Auto-generated method stub

	}

	public void viewAllComments() {
		try {
			if (this.destinationTrader.getComments().isEmpty()) {
				MainMenu.displayHeader("There are no Comments !");
			}
			try {
				for (Comment c : this.destinationTrader.getComments()) {
					System.out.println(c.toString());
				}
			} catch (NullPointerException e) {
				MainMenu.displayHeader("Plese Login as Trader");
			}
		} catch (NullPointerException e) {
			MainMenu.displayHeader("Plese Login as Trader");
		}

	}

	public void viewYourRating() {
		try {
			MainMenu.displayHeader("Your rating is " + this.destinationTrader.getRating());
		} catch (NullPointerException e) {
			MainMenu.displayHeader("Plese Login as Trader");
		}

	}

}
