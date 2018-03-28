package dataclasses;
import java.time.Duration;
import java.time.LocalDateTime;


//Voucher class, info for voucher 
public class Voucher {
	
	private static Integer id = 0;
	private Integer voucherID;
	
	private String category;
	private Price price;
	private VoucherInformation vInfo;
	private int numberOfVouchers;
	private LocalDateTime date;
	private DestinationTrader destination;
//	private int periodOfVoucherInDays;
//	private LocalDateTime remainingTime;

	public Voucher(String category, Price price, VoucherInformation vInfo, int numberOfVouchers, DestinationTrader destination/*,
			int periodOfVoucherInDays*/) {
		setPrice(price);
		setvInfo(vInfo);
		setNumberOfVouchers(numberOfVouchers);
		setDestination(destination);
		this.date = LocalDateTime.now();
		this.voucherID = ++id;
//		setPeriodOfVoucherInDays(periodOfVoucherInDays);
	}

//	public int getPeriodOfVoucherInDays() {
//		return periodOfVoucherInDays;
//	}
//
//	public void setPeriodOfVoucherInDays(int periodOfVoucherInDays) {
//		if (periodOfVoucherInDays > 0)
//			this.periodOfVoucherInDays = periodOfVoucherInDays;
//	}
//
//	public LocalDateTime getRemainingTime() {
//		return this.date.plusDays(periodOfVoucherInDays);
//	}



	public String getCategory() {
		return category;
	}

	public Integer getVoucherID() {
		return voucherID;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		if (price != null)
			this.price = price;
	}

	public VoucherInformation getvInfo() {
		return vInfo;
	}

	public void setvInfo(VoucherInformation vInfo) {
		if (vInfo != null)
			this.vInfo = vInfo;
		else
			System.out.println("Enter Valid Voucher Info");
	}

	public int getNumberOfVouchers() {
		return numberOfVouchers;
	}

	public void setNumberOfVouchers(int numberOfVouchers) {
		if (numberOfVouchers > 0)
			this.numberOfVouchers = numberOfVouchers;
		else
			System.out.println("Invalid number Of vouchers");
	}

	public void setDestination(DestinationTrader destination) {
		if (destination != null)
			this.destination = destination;
		else
			System.out.println("Invalid destination");
	}

	public DestinationTrader getDestination() {
		return destination;
	}

	@Override
	public String toString() {
		return "Voucher [voucherID=" + voucherID + ", category=" + category + ", price=" + price + ", vInfo=" + vInfo
				+ ", date=" + date + ", destination=" + destination + "]";
	}

	
}
