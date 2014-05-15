package colley.chisholm.diploma.welcome.settings;

import android.app.Application;
import android.content.SharedPreferences;

public class AppSettingz extends Application {

	public static final String PREFS_NAME = "myprefs";
	private SharedPreferences myprefs;

	private static String PREF_NAME_USERNAME = "username";
	private static String PREF_PRINT_LOG = "printlog";

	public static boolean printLogs;

	public AppSettingz() {
		
//		setSettings(PREF_KEY_NAMES.PRINTLOG,false);
//		printLogs = (boolean)(getSettings(PREF_KEY_NAMES.PRINTLOG,true));
	}

	public enum PREF_KEY_NAMES {
		USERNAME, PRINTLOG
	}

	public void setSettings(PREF_KEY_NAMES prefKeyName, Object prefKeyValue) {

		if (prefKeyValue != null) {
			myprefs = getSharedPreferences(PREFS_NAME,0);
			SharedPreferences.Editor editor = myprefs.edit();
		

			if (prefKeyName == PREF_KEY_NAMES.USERNAME) {
				editor.putString(PREF_NAME_USERNAME, prefKeyValue.toString());
			}

			if (prefKeyName == PREF_KEY_NAMES.PRINTLOG) {
				editor.putString(PREF_PRINT_LOG, prefKeyValue.toString());
				printLogs = Boolean.parseBoolean(prefKeyValue.toString());
			}

			editor.commit();
		}

	}

	public Object getSettings(PREF_KEY_NAMES prefKeyName, Object defaultValue) {

		if (prefKeyName != null) {
			myprefs = getSharedPreferences(PREFS_NAME,0);

			if (prefKeyName == PREF_KEY_NAMES.USERNAME) {
				return myprefs.getString(PREF_NAME_USERNAME, defaultValue.toString());
			}


			if (prefKeyName == PREF_KEY_NAMES.PRINTLOG) {
				return myprefs.getString(PREF_PRINT_LOG, defaultValue.toString());
			}

		}
		return null;
	}

}
