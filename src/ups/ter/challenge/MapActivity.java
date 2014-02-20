package ups.ter.challenge;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MapActivity extends Activity implements LocationListener {

    private LocationManager locationManager;
    private GoogleMap gMap;
    private Marker marker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
        gMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        marker = gMap.addMarker(new MarkerOptions().title("Vous êtes ici").position(new LatLng(0, 0)));
		
	}
	
    public void onResume() {
        super.onResume();
 
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

    	if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
        } else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
        	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);
        }
    }
    
    public void stopAbonnements() {
    	locationManager.removeUpdates(this);
    }
 
    public void onLocationChanged(final Location location) {
        //On affiche dans un Toat la nouvelle Localisation
        final StringBuilder msg = new StringBuilder("lat : ");
        msg.append(location.getLatitude());
        msg.append( "; lng : ");
        msg.append(location.getLongitude());
 
        Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show();
 
        //Mise à jour des coordonnées
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());     
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        marker.setPosition(latLng);
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
	
	

}
