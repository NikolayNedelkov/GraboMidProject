package dataclasses;

//Price of single Voucher
public class Price {

	private static final int MAX_PERCENT = 100;
	private double oldPrice;
	private int discount;
	private double newPrice;
	private double savedMoney; 

	public Price(double oldPrice, int discount) {
		setOldPrice(oldPrice);
		setDiscount(discount);
		this.newPrice = (getOldPrice() - ((getDiscount() / 100) * getOldPrice()));
		this.savedMoney = ((getDiscount() / MAX_PERCENT) * getOldPrice());
	}

	public double getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(double oldPrice) {
		if (oldPrice > 0)
			this.oldPrice = oldPrice;
		else
			System.out.println("Invaldid price");
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		if (discount > 0 || discount < MAX_PERCENT)
			this.discount = discount;
		else
			System.out.println("Invalid percent");

	}

	public double getNewPrice() {
		return newPrice;
	}

	public double getSavadMoney() {
		return savedMoney;
	}

}
