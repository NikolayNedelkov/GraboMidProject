package dataclasses;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import exceptions.VoucherException;

//Cart of User
public class Cart {
	private Map<Integer, Voucher> myVouchers;

	public Cart() {
		myVouchers = new TreeMap<Integer, Voucher>();
	}

	public Map<Integer, Voucher> getMyVouchers() {
		return Collections.unmodifiableMap(myVouchers);
	}

	public void addVoucher(Voucher v) throws VoucherException {
		if (v == null)
			throw new VoucherException("Invalid voucher");
		myVouchers.put(v.getVoucherID(), v);
	}

	public void removeVoucher(Voucher v) throws VoucherException  {
		if (v == null) {
			throw new VoucherException("Invalid voucher");
		} else {
			for (Voucher voucher : myVouchers.values()) {
				if (v.getVoucherID().equals(voucher.getVoucherID())) {
					myVouchers.remove(v.getVoucherID());
					
				}
			}
		}
	}

}
