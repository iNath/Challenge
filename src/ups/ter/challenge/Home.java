package ups.ter.challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class Home extends Activity {

	Button takepicture;
	Button GoTo;
	Button About;
	Button Quitter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		takepicture = (Button) findViewById(R.id.button1);
		takepicture.setOnClickListener(takepictureEcouteur);
		
		GoTo = (Button) findViewById(R.id.button2);
		GoTo.setOnClickListener(GoToEcouteur);
		
	/*	About = (Button) findViewById(R.id.button3);
		About.setOnClickListener(AboutEcouteur);*/
		
		Quitter = (Button) findViewById(R.id.button4);
		Quitter.setOnClickListener(QuitterEcouteur);

	}

	private OnClickListener takepictureEcouteur = new OnClickListener() {
		public void onClick(View arg0) {
			Intent intent = new Intent(Home.this, TakePicture.class);
			startActivity(intent);

		}
	};
	
	private OnClickListener GoToEcouteur = new OnClickListener() {
		public void onClick(View arg0) {
			Intent intent = new Intent(Home.this, GoTo.class);
			startActivity(intent);

		}
	};

	/*private OnClickListener AboutEcouteur = new OnClickListener() {
		public void onClick(View arg0) {
			Intent intent = new Intent(Home.this, About.class);
			startActivity(intent);

		}
	};
*/
	private OnClickListener QuitterEcouteur = new OnClickListener() {
		public void onClick(View arg0) {
			System.exit(RESULT_OK);

		}
	};
}
