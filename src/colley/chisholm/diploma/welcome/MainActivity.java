package colley.chisholm.diploma.welcome;



import colley.chisholm.diploma.welcome.utility.MenuMaker;
import colley.chisholm.diploma.welcome.utility.Print;
import colley.chisholm.diploma.welcome.settings.AppSettingz;
import colley.chisholm.diploma.welcome.settings.AppSettingz.PREF_KEY_NAMES;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private String className;
	
	private PlaceholderFragment dataFragment;

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	// Called when the user clicks the Send button
	public void sendMessage(View view) {
		Print.print(className, "sendMessage");
		
		// Find the EditText control in the layout.
		EditText editText = (EditText) findViewById(R.id.edit_message);
		// Get the text from the control.
		String name = editText.getText().toString();
		
		if (name.length() > 0) {
			
			((AppSettingz)this.getApplication()).setSettings(colley.chisholm.diploma.welcome.settings.AppSettingz.PREF_KEY_NAMES.USERNAME, name);
			
			// Create an Intent, passing along the current context and the
			// name of the next Activity we want to start.
			Intent intent = new Intent(this, UserHomeActivity.class);
			// Put the text into the Intent.
			intent.putExtra(EXTRA_MESSAGE, name);
			// Start the activity in the Intent.
			startActivity(intent);
		} else {
			Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
		}

		
	}


	// Subclasses of Activity must override onCreate.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		className = this.getClass().getSimpleName();
		((AppSettingz)getApplication()).setSettings(PREF_KEY_NAMES.PRINTLOG, "true");
		Print.print(className, "onCreate");
		
		// Must call super.onCreate before introducing new logic.
		super.onCreate(savedInstanceState);
		// Point to the xml layout to use for this activity.
		setContentView(R.layout.activity_main);
		
		// find the retained fragment on activity restarts
        FragmentManager fm = getFragmentManager();
        dataFragment = (PlaceholderFragment) fm.findFragmentByTag("data");

        // create the fragment and data the first time
        if (dataFragment == null) {
            // add the fragment
            dataFragment = new PlaceholderFragment();
            fm.beginTransaction().add(dataFragment, "data").commit();
            // load the data from the web
            dataFragment.doSomething();
        }
		
		

//		// Add internal fragment to fragment manager.
//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//			.add(R.id.container, new PlaceholderFragment()).commit();
//		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Print.print(className, "onCreateOptionsMenu");
		
		// Inflate the menu; this adds items to the action bar if it is present.
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
		

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			fragmentClassName = this.getActivity().getClass().getSimpleName() + "." + this.getClass().getSimpleName();
			Print.print(fragmentClassName, "onCreateView");
			
			setRetainInstance(true);

			//return new MyCanvas(getActivity());
			// Point to the xml layout to use for this fragment.
			View rootView = inflater.inflate(R.layout.fragment_main,
					container, false);
			doSomething();
			
			return rootView;
		}
		
		public void doSomething() {
			Print.print(fragmentClassName, "doSomething");
		}
	}

}
