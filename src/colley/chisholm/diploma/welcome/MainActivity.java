package colley.chisholm.diploma.welcome;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	// Called when the user clicks the Send button
	public void sendMessage(View view) {
		// Create an Intent, passing along the current context and the
		// name of the next Activity we want to start.
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		// Find the EditText control in the layout.
		EditText editText = (EditText) findViewById(R.id.edit_message);
		// Get the text from the control.
		String message = editText.getText().toString();
		// Put the text into the Intent.
		intent.putExtra(EXTRA_MESSAGE, message);
		// Start the activity in the Intent.
		startActivity(intent);
	}

	// Subclasses of Activity must override onCreate.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Must call super.onCreate before introducing new logic.
		super.onCreate(savedInstanceState);
		// Point to the xml layout to use for this activity.
		setContentView(R.layout.activity_main);

		// Add internal fragment to fragment manager.
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		
		MenuMaker.makeMenu(menu); /////////////////////

		
		getMenuInflater().inflate(R.menu.main, menu);
		
//		menu.add("particles");
//		menu.add("home");
//		menu.add("map");
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Inline class for the internal fragment.
	public static class PlaceholderFragment extends Fragment {

		// Empty constructor.
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			return new MyCanvas(getActivity());
			
//			// Point to the xml layout to use for this fragment.
//			View rootView = inflater.inflate(R.layout.fragment_main, container,
//					false);
//			
//			return rootView;
		}
	}

}
