package ups.ter.challenge;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class GoTo extends Activity implements LocationListener {

    private LocationManager locationManager;
    private GoogleMap gMap;
    private Marker markerTogo, markerMe;
    private LatLng coord;
    private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goto1);
		
		// Stub long and lat
		double longitude = 1.466765;
		double latitude = 43.562445;
		coord = new LatLng(latitude, longitude);
		
		System.out.println("R.id.map :: " + ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap());
		
		handler = new Handler();
		handler.postAtTime(waitReady, 10000);
        //gMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        //markerTogo = gMap.addMarker(new MarkerOptions().title("Point de vue ici").position(new LatLng(latitude, longitude)));
	}
	
	private Runnable waitReady = new Runnable() {
		public void run() {
	        gMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
	        if(gMap == null){
	    		handler.postAtTime(waitReady, 10000);
	        } else {
	        	// C'est ok
		        markerTogo = gMap.addMarker(new MarkerOptions().title("Point de vue ici").position(coord));
		        manageAbonnements();
	        }
		}
	};
	
    public void onResume() {
        super.onResume();
        System.out.println("On resemue reached");
        //Obtention de la référence du service
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
 
        manageAbonnements();
    }
 
    public void onPause() {
        super.onPause();
 
        stopAbonnements();
    }
 
    public void manageAbonnements() {
    	// On désactive tout
    	locationManager.removeUpdates(this);

    	LatLng latLng = null;
    	if(false && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    		System.out.println("GPS available");
    		System.out.println("obj:" + locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
    		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    		latLng = new LatLng(
    				locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(),
    				locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude()
    		);
        } else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
    		System.out.println("Network location available");
    		System.out.println("obj:" + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
        	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);
    		latLng = new LatLng(
    				locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude(),
    				locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude()
    		);
        } else {
        	System.out.println("No location provider available");
        }
    	
    	if(latLng != null)
    		reloadPosition(latLng);
    }
    
    public void stopAbonnements() {
    	locationManager.removeUpdates(this);
    }
    
    private void reloadPosition(LatLng latLng){
    	if(gMap == null) return;
    	
    	if(markerMe == null) 
    		markerMe = gMap.addMarker(new MarkerOptions().title("Vous êtes ici").position(latLng));
    	markerMe.setPosition(latLng);
    	
    	//LatLngBounds llb = new LatLngBounds(coord, latLng);

    	//gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(llb, 5));
    	gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
    	
        System.out.println("New location: " + latLng);

        checkProximity(latLng);
    }
    

    /**
     * Check proximity of given position with the 
     */
    private void checkProximity(LatLng latLng){
    	
    	float distance = distFrom((float)latLng.latitude, (float)latLng.longitude, (float)coord.latitude, (float)coord.longitude);
    	System.out.println("Distance entre les deux points: " + distance + "m");
    	Toast.makeText(this, "Distance: " + distance + "m", Toast.LENGTH_SHORT).show();
    	
    	if(distance <= 5){
    		// On est arrivé
    		Toast.makeText(this, "Vous êtes arrivééé!", Toast.LENGTH_SHORT).show();
    	}
    	
    }
 
    public void onLocationChanged(final Location location) {
    	System.out.println("Location changed");
    	if(gMap == null) return;
    	System.out.println("Location changed: 2");
    	
        final StringBuilder msg = new StringBuilder("lat : ");
        msg.append(location.getLatitude());
        msg.append( "; lng : ");
        msg.append(location.getLongitude());
 
        Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show();
 
        //Mise à jour des coordonnées
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());  
        reloadPosition(latLng);
        
    }
    
 
    public void onProviderDisabled(final String provider) {
    	manageAbonnements();
    }
    
    public void onProviderEnabled(final String provider) {
    	manageAbonnements();
    }
    
    public void onStatusChanged(final String provider, final int status, final Bundle extras) { }

    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private float distFrom(float lat1, float lng1, float lat2, float lng2) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		int meterConversion = 1609;

		return (float) (dist * meterConversion);
	}
	

}
