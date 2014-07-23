package studio.coldstream.stardate;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class AboutActivity extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ScrollView sv = (ScrollView)findViewById(R.id.scrollView);
        sv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onBackPressed();
                return false;
            }
        });
    }
	
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Intent mainIntent = new Intent(AboutActivity.this,AboutActivity.class);
		AboutActivity.this.startActivity(mainIntent);
		AboutActivity.this.finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (  Integer.valueOf(android.os.Build.VERSION.SDK) < 7 //Instead use android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
	            && keyCode == KeyEvent.KEYCODE_BACK
	            && event.getRepeatCount() == 0) {
	        // Take care of calling this method on earlier versions of
	        // the platform where it doesn't exist.
	        onBackPressed();
	    }
	    return super.onKeyDown(keyCode, event);
	}



	//@Override
	public void onBackPressed() {
	    // This will be called either automatically for you on 2.0
	    // or later, or by the code above on earlier versions of the
	    // platform.
		
		Intent mainIntent = new Intent(AboutActivity.this,MainActivity.class);
		AboutActivity.this.startActivity(mainIntent);
		AboutActivity.this.finish();
		
	}
	
}
