package colley.chisholm.diploma.welcome.utility;

import colley.chisholm.diploma.welcome.MapActivity;
import colley.chisholm.diploma.welcome.ParticleActivity;
import colley.chisholm.diploma.welcome.R;
import colley.chisholm.diploma.welcome.UserHomeActivity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

public class MenuMaker {

	public static Intent getAct(Context c, MenuItem mi) { // http://developer.android.com/guide/topics/resources/menu-resource.html
		int id = mi.getItemId();
		Intent i = null;
		if (id == R.id.menu_home_activity) {
			i = new Intent(c,UserHomeActivity.class);
		}
		if (id == R.id.menu_particles_activity) {
			i = new Intent(c,ParticleActivity.class);		
		}
		if (id == R.id.menu_map_activity) {
			i = new Intent(c, MapActivity.class);		
		}
		if (id == R.id.action_settings) {
			i = null;
		}
		
		return i;

	}

}
