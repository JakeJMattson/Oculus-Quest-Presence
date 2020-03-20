package com.madmagic.oqrpc;

import java.io.File;
import java.io.FileWriter;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class Config {
	
	private static File configFile;
	
	public static void init() {
		try {
			String jarName = new File(jarPath()).getName();
			
			String mapPath = jarPath().replace(jarName, "");
			File parrentMap = new File(mapPath);
			
			configFile = new File(parrentMap.getAbsolutePath() + "/config.json");
			if (!configFile.exists()) configFile.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String jarPath() {
		try {
			String path = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	       	return URLDecoder.decode(path, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private static JSONObject read() {
		try {
			String text = new String(Files.readAllBytes(Paths.get(configFile.getAbsolutePath())));
			if (text.equals("")) {
				return write(new JSONObject());
			} else return new JSONObject(text);
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONObject();
		}
	}

	public static String getAddress() {
		JSONObject main = read();
		if (main.has("address")) return main.getString("address");
		else return "";
	}
	
	public static JSONObject write(JSONObject obj) {
		System.out.println("write to config");
		try {
			if (!configFile.exists()) configFile.createNewFile();
			FileWriter fw = new FileWriter(configFile);
			fw.write(obj.toString(4));
			fw.close();
			return new JSONObject();
		} catch (Exception ignored) {
			return new JSONObject();
		}
	}
}