package repositories;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import dataclasses.User;
import dataclasses.Voucher;
import exceptions.VoucherException;

public class VoucherRepo {
	private Voucher voucher;
	private Map<Integer, Voucher> allVouchers;
	private static VoucherRepo voucherRepo = null;
	private static final String VOUCHER_JSON_FILE = ".//Json//vouchers.json";

	private VoucherRepo() {
		this.allVouchers = new HashMap<Integer, Voucher>();
		this.allVouchers = getVochersFromJSONFILE();
	}

	public static VoucherRepo getInstance() {
		if (voucherRepo == null) {
			voucherRepo = new VoucherRepo();
		}
		return voucherRepo;
	}

	public Map<Integer, Voucher> getAllVouchers() {
		return Collections.unmodifiableMap(allVouchers);
	}

	// Loading all vouchers from Json file
	private Map<Integer, Voucher> getVochersFromJSONFILE() {
		Gson gson = new Gson();
		TreeMap<Integer, Voucher> map = null;
		try (Reader reader = new FileReader(VOUCHER_JSON_FILE)) {
			JsonElement json = gson.fromJson(reader, JsonElement.class);
			String jsonInString = gson.toJson(json);

			map = gson.fromJson(jsonInString, new TypeToken<TreeMap<String, User>>() {
			}.getType());
			if (map!=null) {
				Voucher.id = map.lastKey();
			} else {
				Voucher.id = 0;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (map == null) {
			map = new TreeMap<>();
		}
		return map;

	}

	// Writing all vouchers to Json file
	private void writeUsersToJSONFile(Map<Integer, Voucher> allVouchers) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter writer = new FileWriter(VOUCHER_JSON_FILE)) {

			gson.toJson(allVouchers, writer);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Adding new Voucher to VoucherRepo
	public void addNewVoucher(Voucher v) throws VoucherException {
		if (v == null)
			throw new VoucherException("Invalid voucher to add");
		allVouchers.put(v.getVoucherID(), v);
		this.writeUsersToJSONFile(allVouchers);
	}

	// Removing voucher from VoucherRepo
	public void removeVoucher(Voucher v) throws VoucherException {
		if (v == null)
			throw new VoucherException("Invalid voucher to remove");
		for (Voucher voucher : allVouchers.values()) {
			if (v.getVoucherID().equals(voucher.getVoucherID())) {
				allVouchers.remove(v.getVoucherID());
			}
		}
	}
}
