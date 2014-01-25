package net.depotwarehouse.as1.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class File {
	
	// Our streams are static because in the instance where multiple classes
	// might be instantiated, we do not want to try to open two file handlers
	
	
	public static String readString(FileInputStream inStream) throws IOException{
		String ret;
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(inStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ( (receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append(receiveString);
            }
            
            ret = stringBuilder.toString();
		} catch (IOException e) {
			throw e;
		}
		return ret;
	}
	
	public static void writeString(FileOutputStream outStream, String json) {
		try {
			outStream.write(json.getBytes());
		} catch (Exception e) {
			System.err.println("Error writing file");
		}
	}

}
