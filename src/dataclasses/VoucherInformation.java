package dataclasses;

//Voucher information class
public class VoucherInformation {
	private String information;
	private String conditions;
	private String providedFrom;
	private Voucher voucher;

	public VoucherInformation(String information, String conditions, Voucher v) {
		this.information = information;
		this.conditions = conditions;
		this.providedFrom = v.getDestination().getInformation();
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		if (information.trim().length() > 0)
			this.information = information;
		else
			System.out.println("Enter valid Information");
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		if (conditions.trim().length() > 0)
			this.conditions = conditions;
		else
			System.out.println("Enter valid Conditions");
	}

	public String getProvidedFrom() {
		return providedFrom;
	}

}