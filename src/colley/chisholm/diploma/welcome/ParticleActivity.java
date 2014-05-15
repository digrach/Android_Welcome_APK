package colley.chisholm.diploma.welcome;

import colley.chisholm.diploma.welcome.drawingsurface.MyCanvas;
import colley.chisholm.diploma.welcome.utility.MenuMaker;
import colley.chisholm.diploma.welcome.utility.Print;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class ParticleActivity extends Activity {
	
	private String className;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		className = this.getClass().getSimpleName();
		Print.print(className, "onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_particle);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		
		private String fragmentClassName;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			fragmentClassName = this.getActivity().getClass().getSimpleName() + "." + this.getClass().getSimpleName();
			Print.print(fragmentClassName, "onCreateView");
//			View rootView = inflater.inflate(R.layout.fragment_particle,
//					container, false);
//			return rootView;
			return new MyCanvas(getActivity());
		}
	}

}
