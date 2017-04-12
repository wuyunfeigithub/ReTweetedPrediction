package data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class readFile {
	/**
	 * 
	 * @param Path
	 * @param type 0表示读用户属性（添加[]）,1表示读推文属性
	 * @return
	 */
	public String ReadFile(String Path, int type) {
		BufferedReader reader = null;
		String data = "";
		if(type == 0)  data = "[";
		try {
			FileInputStream fileInputStream = new FileInputStream(Path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");//UTF-8
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				data += tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if(type == 0)  return data += "]";
		return data;
	}
	
	public static BufferedReader createStream(String path){
		BufferedReader reader = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");//UTF-8
			reader = new BufferedReader(inputStreamReader);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return reader;
	}
	
	public static void closeStream(BufferedReader stream){
		try {
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> readNetByLine(String Path){
		
		BufferedReader reader = null;
		
		ArrayList<String> dataSet =  new ArrayList<String>();
		try {
			FileInputStream fileInputStream = new FileInputStream(Path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");//UTF-8
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				dataSet.add(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return dataSet;
	}
}