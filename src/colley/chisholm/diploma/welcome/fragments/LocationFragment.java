package colley.chisholm.diploma.welcome.fragments;

import colley.chisholm.diploma.welcome.R;
import colley.chisholm.diploma.welcome.R.id;
import colley.chisholm.diploma.welcome.R.layout;
import colley.chisholm.diploma.welcome.utility.Print;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class LocationFragment extends Fragment implements LocationListener {

	static LocationManager lm;
	static TextView txtSats;
	static TextView txtaltitude;
	static TextView txtbearing;
	static TextView txtaccuracy;
	static TextView txtelapsedtime;
	static TextView txtutctime;
	static TextView txtspeed;
	static TextView txtdistancetomelbourne;
	static TextView txtdistancebetweenmelbournesanfran;
	static Activity currentActivity;

	double lat;
	static double lon;
	static double altitude;
	static double bearing;
	static double accuracy;
	static long elapsedTime;
	static long UTCTime;
	static float speed;

	private String fragmentClassName;

	public LocationFragment(Activity c) {
		currentActivity = c;
		fragmentClassName = currentActivity.getClass().getSimpleName() + "." + this.getClass().getSimpleName();
		Print.print(fragmentClassName, "LocationFragment");
	}

	//	@Override
	//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	//			Bundle savedInstanceState) {
	//		Print.print(fragmentClassName, "onCreateView");
	//		
	//		setRetainInstance(true);
	//		
	//		View rootView = inflater.inflate(R.layout.fragment_map, container,
	//				false);
	//		
	//		
	//
	//		txtSats = (TextView) rootView.findViewById(R.id.txtStats);
	//		txtaltitude = (TextView) rootView.findViewById(R.id.txtaltitude);
	//		txtbearing = (TextView) rootView.findViewById(R.id.txtbearing);
	//		txtaccuracy = (TextView) rootView.findViewById(R.id.txtaccuracy);
	//		txtelapsedtime = (TextView) rootView.findViewById(R.id.txtelapsedtime);
	//		txtutctime = (TextView) rootView.findViewById(R.id.txtutctime);
	//		txtspeed = (TextView) rootView.findViewById(R.id.txtspeed);
	//		txtdistancetomelbourne = (TextView) rootView.findViewById(R.id.txtdistancetomelbourne);
	//		txtdistancebetweenmelbournesanfran = (TextView) rootView.findViewById(R.id.txtdistancebetweenmelbournesanfran);
	//
	//
	//		populateLocationData();
	//
	//		return rootView;
	//	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//super.onCreateView(inflater, container, savedInstanceState);
		Print.print(fragmentClassName, "onCreateView");

		View rootView = inflater.inflate(R.layout.fragment_map, container,
				false);

		txtSats = (TextView) rootView.findViewById(R.id.txtStats);
		txtaltitude = (TextView) rootView.findViewById(R.id.txtaltitude);
		txtbearing = (TextView) rootView.findViewById(R.id.txtbearing);
		txtaccuracy = (TextView) rootView.findViewById(R.id.txtaccuracy);
		txtelapsedtime = (TextView) rootView.findViewById(R.id.txtelapsedtime);
		txtutctime = (TextView) rootView.findViewById(R.id.txtutctime);
		txtspeed = (TextView) rootView.findViewById(R.id.txtspeed);
		txtdistancetomelbourne = (TextView) rootView.findViewById(R.id.txtdistancetomelbourne);
		txtdistancebetweenmelbournesanfran = (TextView) rootView.findViewById(R.id.txtdistancebetweenmelbournesanfran);

		populateLocationData();

		return rootView;
	}


	public void populateLocationData() {
		Print.print(fragmentClassName, "populateLocationData");

		lm = (LocationManager) currentActivity.getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
				1, this);

		if (lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {


			lat = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
			lon = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
			altitude = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getAltitude();
			bearing = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getBearing();
			accuracy = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getAccuracy();
			elapsedTime = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getElapsedRealtimeNanos();
			UTCTime = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getTime();
			speed = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getSpeed();

			Location locMelbourne = new Location(LocationManager.GPS_PROVIDER);
			locMelbourne.setLatitude(-37.8602828);
			locMelbourne.setLongitude(145.079616);
			float distanceToMelbourne = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).distanceTo(locMelbourne);

			Location locSanFran = new Location(LocationManager.GPS_PROVIDER);
			locSanFran.setLatitude(37.7577);
			locSanFran.setLongitude(-122.4376);
			float distanceBetweenMelbourneSanFran = locMelbourne.distanceTo(locSanFran);


			txtSats.setText(Double.toString(lat) + " " + Double.toString(lon));		
			txtaltitude.setText((Double.toString(altitude)));
			txtbearing.setText((Double.toString(bearing)));
			txtaccuracy.setText((Double.toString(accuracy)));
			txtelapsedtime.setText((Double.toString(elapsedTime)));
			txtutctime.setText((Double.toString(UTCTime)));
			txtspeed.setText((Float.toString(speed)));
			txtdistancetomelbourne.setText((Float.toString(distanceToMelbourne)));
			txtdistancebetweenmelbournesanfran.setText((Float.toString(distanceBetweenMelbourneSanFran)));

		}
	}

	@Override
	public void onLocationChanged(Location location) {
		Print.print(fragmentClassName, "onLocationChanged");

		populateLocationData();
		Toast.makeText(currentActivity, "Your location has changed", Toast.LENGTH_SHORT).show();

		//		Intent i = new Intent(currentActivity,ParticleActivity.class);
		//		PendingIntent pi = PendingIntent.getActivity(currentActivity, 0, i, 0);
		//		lm.addProximityAlert(lat+ 0.0001, lon+ 0.0001, 2, -1, pi);
	}

	@Override
	public void onStatusChanged(String provider, int status,
			Bundle extras) {
		Print.print(fragmentClassName, "onStatusChanged");

	}

	@Override
	public void onProviderEnabled(String provider) {
		Print.print(fragmentClassName, "onProviderEnabled");

	}

	@Override
	public void onProviderDisabled(String provider) {
		Print.print(fragmentClassName, "onProviderDisabled");

	}

}
