package ups.ter.challenge;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class About  extends Activity {
	 
	Button Acceuil;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.about);
	        Acceuil = (Button) findViewById(R.id.button1);
	        Acceuil.setOnClickListener(AcceuilEcouteur);
	    }
	 
	 private OnClickListener AcceuilEcouteur = new OnClickListener() {
	        public void onClick(View arg0) {
//	        Intent intent = new Intent(Apropos.this,Acceuil.class);
//	        startActivity(intent);
//	        	Activity.this.finish();
	        	About.this.finish();
	                 
	                    
	        }
	};

}