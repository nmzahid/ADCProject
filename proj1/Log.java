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

public class Log {
	private static String fileName;
	public static void setLogFile(String str){

	}
	
	public static void log(String data){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
		String formattedDate = sdf.format(date);
		
		System.out.println("[" + formattedDate + "] " + data);
	}
}

