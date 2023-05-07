package net.novauniverse.labymodapiplus.economydisplay;

import java.util.UUID;

import com.google.gson.JsonObject;

import net.labymod.serverapi.api.LabyAPI;
import net.labymod.serverapi.api.serverinteraction.economy.EconomyBalanceType;

public class EconomyDisplayWrapper {
	private final UUID player;

	private double cash;
	private double bank;

	private boolean cashVisible;
	private boolean bankVisible;

	private DecimalSettings cashDecimalSettings;
	private DecimalSettings bankDecimalSettings;

	private String cashIconURL;
	private String bankIconURL;

	public EconomyDisplayWrapper(UUID player) {
		if (player == null) {
			throw new IllegalArgumentException("player cant be null");
		}
		this.player = player;

		this.cash = 0;
		this.bank = 0;

		this.cashVisible = false;
		this.bankVisible = false;

		this.cashDecimalSettings = null;
		this.bankDecimalSettings = null;

		this.cashIconURL = null;
		this.bankIconURL = null;
	}

	public void setVisible(EconomyBalanceType type, boolean visible) {
		switch (type) {
		case BANK:
			bankVisible = visible;
			break;

		case CASH:
			cashVisible = visible;
			break;

		default:
			return;
		}
	}

	public boolean isVisible(EconomyBalanceType type) {
		switch (type) {
		case BANK:
			return bankVisible;

		case CASH:
			return cashVisible;

		default:
			return false;
		}
	}

	public void set(EconomyBalanceType type, double value) {
		switch (type) {
		case BANK:
			bank = value;
			break;

		case CASH:
			cash = value;
			break;

		default:
			return;
		}
	}

	public double get(EconomyBalanceType type) {
		switch (type) {
		case BANK:
			return bank;

		case CASH:
			return cash;

		default:
			return 0;
		}
	}

	public void setDecimalSettings(EconomyBalanceType type, DecimalSettings settings) {
		switch (type) {
		case BANK:
			bankDecimalSettings = settings;
			break;

		case CASH:
			cashDecimalSettings = settings;
			break;

		default:
			return;
		}
	}

	public void setIconURL(EconomyBalanceType type, String url) {
		switch (type) {
		case BANK:
			bankIconURL = url;
			break;

		case CASH:
			cashIconURL = url;
			break;

		default:
			return;
		}
	}

	public UUID getPlayer() {
		return player;
	}

	public void send() {
		JsonObject economyObject = new JsonObject();

		JsonObject cashObject = new JsonObject();
		cashObject.addProperty("visible", cashVisible);
		cashObject.addProperty("balance", cash);
		if (cashIconURL != null) {
			cashObject.addProperty("icon", cashIconURL);
		}

		if (cashDecimalSettings != null) {
			JsonObject decimalObject = new JsonObject();
			decimalObject.addProperty("format", cashDecimalSettings.getFormat());
			decimalObject.addProperty("divisor", cashDecimalSettings.getDivisor());
			cashObject.add("decimal", decimalObject);
		}

		JsonObject bankObject = new JsonObject();
		bankObject.addProperty("visible", bankVisible);
		bankObject.addProperty("balance", bank);
		if (bankIconURL != null) {
			bankObject.addProperty("icon", bankIconURL);
		}

		if (bankDecimalSettings != null) {
			JsonObject decimalObject = new JsonObject();
			decimalObject.addProperty("format", bankDecimalSettings.getFormat());
			decimalObject.addProperty("divisor", bankDecimalSettings.getDivisor());
			bankObject.add("decimal", decimalObject);
		}

		economyObject.add(EconomyBalanceType.CASH.getKey(), cashObject);
		economyObject.add(EconomyBalanceType.BANK.getKey(), bankObject);

		LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(player, "economy", economyObject);
	}
}