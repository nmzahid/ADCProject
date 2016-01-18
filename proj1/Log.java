/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anamzahid
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

public class Log {
	private String fileName;
	Log(String str){
		fileName = str;
	}
	
	public void log(String data){
		try{
			FileWriter fileWritter = new FileWriter(fileName,true);
		    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String logString = "[" + sdf.format(date) + "] " + data;
			
			System.out.println(logString);
			bufferWritter.write(logString);
			bufferWritter.newLine();
		    bufferWritter.close();
		}
		catch(IOException e){
    		e.printStackTrace();
    	}
	}
}

