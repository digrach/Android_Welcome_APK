package colley.chisholm.diploma.welcome.settings;

import android.app.Application;
import android.content.SharedPreferences;

public class AppSettingz extends Application {
	
	public static final String PREFS_NAME = "myprefs";
	private SharedPreferences myprefs;
	
	private static String PREF_NAME_USERNAME = "username";
	
	public enum PREF_KEY_NAMES {
		USERNAME
	}
	
	public void setSettings(PREF_KEY_NAMES prefKeyName, Object prefKeyValue) {

		if (prefKeyValue != null) {
			myprefs = getSharedPreferences(PREFS_NAME,0);
			SharedPreferences.Editor editor = myprefs.edit();

			if (prefKeyName == PREF_KEY_NAMES.USERNAME) {
				editor.putString(PREF_NAME_USERNAME, prefKeyValue.toString());
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

		}
		return null;
	}

}
