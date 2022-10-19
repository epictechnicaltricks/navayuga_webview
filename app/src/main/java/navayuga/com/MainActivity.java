package navayuga.com;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends  AppCompatActivity  { 
	
	private final Timer _timer = new Timer();
	
	private LinearLayout linear1;
	private ImageView imageview1;
	
	private TimerTask time;
	private final Intent in = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = findViewById(R.id.linear1);
		imageview1 = findViewById(R.id.imageview1);
	}
	
	private void initializeLogic() {
		time = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						time.cancel();
						in.setClass(getApplicationContext(), HomeActivity.class);
						startActivity(in);
						finish();
					}
				});
			}
		};
		_timer.schedule(time, 3000);
	}
	


}
