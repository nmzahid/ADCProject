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
