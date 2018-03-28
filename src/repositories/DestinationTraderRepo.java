package repositories;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import dataclasses.DestinationTrader;
import exceptions.DestinationTraderException;



public class DestinationTraderRepo {
	
	private DestinationTrader destinationTrader;
	private Map<Integer, DestinationTrader> allDestinationTraders;
	private static DestinationTraderRepo destinationTraderRepo = null;
	private static final String DESTINATION_TRADER_JSON_FILE = ".//Json//destinationTraders.json";
	
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
		Map<Integer, DestinationTrader> map = null;
		try (Reader reader = new FileReader(DESTINATION_TRADER_JSON_FILE)) {
			JsonElement json = gson.fromJson(reader, JsonElement.class);
			String jsonInString = gson.toJson(json);

			map = gson.fromJson(jsonInString, new TypeToken<Map<Integer, DestinationTrader>>() {
			}.getType());

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
	
	public void addNewDestinationTrader (DestinationTrader destinationTrader) throws DestinationTraderException {
		if (destinationTrader == null)
			throw new DestinationTraderException("Invalid destination trader");
		allDestinationTraders.put(destinationTrader.getDestinationTraderID(), destinationTrader);
		this.writeDestinationTradersToJSONFile(allDestinationTraders);

	}
	public Map<Integer, DestinationTrader> getAllDestinationTraders() {
		return Collections.unmodifiableMap(allDestinationTraders);
	}


}
