package colley.chisholm.diploma.welcome;

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

public class DisplayMessageActivity extends Activity {

	// All subclasses must override onCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_search:
			//openSearch();
			return true;
		case R.id.action_settings:
			//openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// Inline class for the internal fragment.
	public static class PlaceholderFragment extends Fragment {

		// Empty constructor.
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Point to the xml layout to use for this fragment.
			View rootView = inflater.inflate(R.layout.fragment_display_message,
					container, false);

			// Get the message from the intent that started the activity.
			Intent intent = getActivity().getIntent();
			String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

			// Find the TextView control in the layout and set it's text.
			TextView txtName = (TextView) rootView.findViewById(R.id.txtName);
			txtName.setText(message);

			return rootView;
		}

		
	}

}
