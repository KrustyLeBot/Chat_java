package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Save_msg {
	public static Map<User,Boolean> open_conection = new HashMap<>();
	public static Map<String,String> conversations = new HashMap<>();
	
	public Save_msg() {
		//Get all the conversations from local file
		Retrieve_messages();
		System.out.println("Messages saved in properties:");
		System.out.println(conversations);
	}
	
	public static void Retrieve_messages() {
		//Get from local file
		Properties properties = new Properties();
		
		try {
			properties.load(new FileInputStream("data.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String key : properties.stringPropertyNames()) {
			conversations.put(key, properties.get(key).toString());
		}
	}
	
	public static void Save_messages() {
		//Save to local file
		Properties properties = new Properties();

		properties.putAll(conversations);
		try {
			properties.store(new FileOutputStream("data.properties"), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
