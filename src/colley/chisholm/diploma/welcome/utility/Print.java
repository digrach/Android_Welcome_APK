package colley.chisholm.diploma.welcome.utility;

import android.util.Log;
import colley.chisholm.diploma.welcome.settings.AppSettingz;

public class Print {

	public static void print(String originName, String message) {		
		if(AppSettingz.printLogs) {
			Log.d("!*!*!*!*!*!*!",originName + " " +  message);
			
		}
	}

}
