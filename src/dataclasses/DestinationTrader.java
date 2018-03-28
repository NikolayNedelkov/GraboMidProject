package dataclasses;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.VoucherException;
import repositories.VoucherRepo;


//DestinationTrader class
public class DestinationTrader {
	
	private static Integer id = 0;
	private Integer destinationTraderID;
	
	private Map<Integer, Voucher> vouchers;
	private String information;
	private String address;
	private double rating;
	private List<Comment> comments;

	public DestinationTrader(String information, String address) {
		setInformation(information);
		setAddress(address);
		this.vouchers = new HashMap<Integer, Voucher>();
		this.comments = new ArrayList<Comment>();
		this.destinationTraderID = ++id;
	}

	public void setRating() {
		double rating = 0;
		for (Comment comment : comments) {
			rating += comment.getRating();
		}
		this.rating = rating / comments.size();
	}

	public void addComment(Comment c) {
		if( c!= null) {
			this.comments.add(c);
		}
	}
	public void addVoucher(Voucher v) throws VoucherException {
		if (v != null) {
			this.vouchers.put(v.getVoucherID(), v);
			VoucherRepo.getInstance().addNewVoucher(v);
		} else {
			System.out.println("Add existing voucher");
		}
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

	public Integer getDestinationTraderID() {
		return destinationTraderID;
	}

	@Override
	public String toString() {
		return "DestinationTrader [destinationTraderID=" + destinationTraderID + ", information=" + information
				+ ", address=" + address + ", rating=" + rating + "]";
	}



}