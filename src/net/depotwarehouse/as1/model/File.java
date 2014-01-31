package net.depotwarehouse.as1.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A static class encapsulating File reading behaviour. Not to be confused with java.io.File
 * this one is namespaced to my package.
 * @author tpavlek
 *
 */
public class File {
	
	/**
	 * 
	 * @param inStream FileInputStream pointing to file, created with Activity's openFileInput()
	 * @return JSON string from disk.
	 * @throws IOException
	 */
	public static String readString(FileInputStream inStream) throws IOException{
		String ret;
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(inStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();
            
            // Iterate through the bufferedReader and append lines.
            while ( (receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append(receiveString);
            }
            // throw the string representation of the stringbuilder into a return variable
            ret = stringBuilder.toString();
		} catch (IOException e) {
			throw e;
		}
		// we're done
		return ret;
	}
	
	/**
	 * 
	 * @param outStream FileOutputStream opened by Activity's openFileOutput()
	 * @param json JSON string to write to disk.
	 */
	public static void writeString(FileOutputStream outStream, String json) {
		try {
			outStream.write(json.getBytes());
		} catch (Exception e) {
			System.err.println("Error writing file");
		}
	}

}
