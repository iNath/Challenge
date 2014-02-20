package ups.ter.challenge;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity {

	Button takepicture;
	Button GoTo;
	Button About;
	Button Quitter;


	private Uri imageUri;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		takepicture = (Button) findViewById(R.id.button1);
		takepicture.setOnClickListener(takepictureEcouteur);

		GoTo = (Button) findViewById(R.id.button2);
		GoTo.setOnClickListener(GoToEcouteur);

		About = (Button) findViewById(R.id.button3);
		About.setOnClickListener(AboutEcouteur);

		Quitter = (Button) findViewById(R.id.button4);
		Quitter.setOnClickListener(QuitterEcouteur);

	}

	private OnClickListener takepictureEcouteur = new OnClickListener() {
		public void onClick(View arg0) {
			takePhoto(arg0);
		}
	};


	public void takePhoto(View view) {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		File photo = new File(Environment.getExternalStorageDirectory(),
				"Pic.jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		imageUri = Uri.fromFile(photo);
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	
	private OnClickListener GoToEcouteur = new OnClickListener() {
		public void onClick(View arg0) {
			Intent intent = new Intent(Home.this, GoTo.class);
			startActivity(intent);

		}
	};

	private OnClickListener AboutEcouteur = new OnClickListener() {
		public void onClick(View arg0) {
			Intent intent = new Intent(Home.this, About.class);
			startActivity(intent);

		}
	};

	private OnClickListener QuitterEcouteur = new OnClickListener() {
		public void onClick(View arg0) {
			System.exit(RESULT_OK);

		}
	};
}
