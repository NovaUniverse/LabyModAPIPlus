package net.novauniverse.labymodapiplus;

import java.util.UUID;

import com.google.gson.JsonObject;

import net.labymod.serverapi.api.LabyAPI;

public class LabyModAPIPlus {
	public static final void sendTabListBanner(UUID receiver, String url) {
		JsonObject object = new JsonObject();
		object.addProperty("url", url);
		LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(receiver, "server_banner", object);
	}
}