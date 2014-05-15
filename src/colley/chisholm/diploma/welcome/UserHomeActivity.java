package colley.chisholm.diploma.welcome;

import colley.chisholm.diploma.welcome.settings.AppSettingz;
import colley.chisholm.diploma.welcome.utility.MenuMaker;
import colley.chisholm.diploma.welcome.utility.Print;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserHomeActivity extends Activity {
	
	private String className;


	// All subclasses must override onCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		className = this.getClass().getSimpleName();
		Print.print(className, "onCreate");
		
		// Must always call super.onCreate.
		super.onCreate(savedInstanceState);

		// Point to the xml layout to use for this activity.
		setContentView(R.layout.activity_display_message);

		// Add internal fragment to fragment manager.
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}

		// ActionBar settings.
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	// All subclasses must override onCreateOptionsMenu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Print.print(className, "onCreateOptionsMenu");

		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Print.print(className, "onOptionsItemSelected");

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		Intent i =	MenuMaker.getAct(this, item);
		if (i != null) {
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	// Inline class for the internal fragment.
	public static class PlaceholderFragment extends Fragment {
		private String fragmentClassName;
		
		// Empty constructor.
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			fragmentClassName = this.getActivity().getClass().getSimpleName() + "." + this.getClass().getSimpleName();
			Print.print(fragmentClassName, "onCreateView");
			
			// Point to the xml layout to use for this fragment.
			View rootView = inflater.inflate(R.layout.fragment_display_message,
					container, false);

			// Get the message from the intent that started the activity.
			Intent intent = getActivity().getIntent();
			String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
			
			String name = (String) ((AppSettingz)this.getActivity().getApplication()).getSettings(colley.chisholm.diploma.welcome.settings.AppSettingz.PREF_KEY_NAMES.USERNAME, "");

			// Find the TextView control in the layout and set it's text.
			TextView txtName = (TextView) rootView.findViewById(R.id.txtName);
//			txtName.setText(message);
			txtName.setText(name);

			return rootView;
		}

		
	}

}
