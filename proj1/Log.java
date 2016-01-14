import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	public static void log(String data){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
		String formattedDate = sdf.format(date);
		
		System.out.println("[" + formattedDate + "] " + data);
	}
}
