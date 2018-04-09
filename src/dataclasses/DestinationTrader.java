package dataclasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.DestinationTraderException;
import exceptions.VoucherException;
import repositories.VoucherRepo;

//DestinationTrader class
public class DestinationTrader {

	private static final String EMAIL_PATTERN =

			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"

					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static Integer id = 0;
	private Integer destinationTraderID;

	private final String traderName;
	private String traderPassword;
	private final String email;
	private Map<Integer, Voucher> vouchers;
	private String information;
	private String address;
	private double rating;
	private List<Comment> comments;
	private boolean isLogin;

	public DestinationTrader(String traderName, String traderPassword, String email, String information, String address)
			throws DestinationTraderException {
		setInformation(information);
		setAddress(address);
		if (traderName.trim().length() > 3)
			this.traderName = traderName;
		else
			throw new DestinationTraderException("Invalid traderName");
		setTraderPassword(traderPassword);
		if (checkForPattern(email))
			this.email = email;
		else
			throw new DestinationTraderException("Invalid email");
		this.vouchers = new HashMap<Integer, Voucher>();
		this.comments = new ArrayList<Comment>();
		this.destinationTraderID = ++id;
	}

	public void setTraderPassword(String traderPassword) {
		if (traderPassword.trim().length() >= 4 && traderPassword.trim().length() <= 19)
			this.traderPassword = traderPassword;
	}

	public void setRating() {
		double rating = 0;
		for (Comment comment : comments) {
			rating += comment.getRating();
		}
		this.rating = rating / comments.size();
	}

	public void addComment(Comment c) {
		if (c != null) {
			this.comments.add(c);
		}
		c = null;
	}

	public void addVoucher(Voucher v) throws VoucherException {
		if (v == null)
			throw new VoucherException("Invalid voucher");
		VoucherRepo.getInstance().addNewVoucher(v);
		this.vouchers.put(v.getVoucherID(), v);
	}

	public void removeVoucher(Voucher v) {
		if (v != null) {
			this.vouchers.remove(v.getVoucherID());
		} else {
			System.out.println("Add existing voucher");
		}
	}

	public Map<Integer, Voucher> getVouchers() {
		return Collections.unmodifiableMap(vouchers);
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		if (information.trim().length() > 0)
			this.information = information;
		else
			System.out.println("Not valid information, try again");
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		if (address.trim().length() > 0)
			this.address = address;
		else
			System.out.println("Not valid address, try again");
	}

	public double getRating() {
		return rating;
	}

	public String getTraderName() {
		return traderName;
	}

	public String getTraderPassword() {
		return traderPassword;
	}

	public Integer getDestinationTraderID() {
		return destinationTraderID;
	}

	// wrong encapsulation
	public List<Comment> getComments() {
		return Collections.unmodifiableList(comments);
	}

	private boolean checkForPattern(String url) {
		if (url != null) {
			try {
				Pattern patt = Pattern.compile(EMAIL_PATTERN);
				Matcher matcher = patt.matcher(url);
				return matcher.matches();
			} catch (RuntimeException e) {
				return false;
			}

		}
		return false;
	}

	@Override
	public String toString() {
		return "DestinationTrader [destinationTraderID=" + destinationTraderID + ", traderName=" + traderName
				+ ", email=" + email + ", address=" + address + ", rating=" + rating
				+ "]";
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

}