package studio.coldstream.stardate;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	private static boolean DEBUG = false;
	static final String TAG = "Main"; // for Log
	
	protected static final int ABOUT = 0x8008;
	protected static final int TICK = 0x9001;
	
	TextView tv0;
	TextView tv1;
	TextView tv2;
	TextView tv3;
	ImageButton ib;
	
	Thread thread1;
	
	StarDate myStarDate;
	
	boolean main_flag = false;
	
	String[] stardate = new String[3];
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myStarDate = new StarDate();
        myTimer();
        showMain();        
    }
    
    public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Intent mainIntent = new Intent(MainActivity.this,MainActivity.class);
		MainActivity.this.startActivity(mainIntent);
		MainActivity.this.finish();
	}
    
    public void showMain(){
    	setContentView(R.layout.main);
        
        tv0 = (TextView)findViewById(R.id.textViewMain);
        Typeface font1 = Typeface.createFromAsset(getAssets(), "next.ttf");
	    tv0.setTypeface(font1);
		tv0.setText(" StarDate ");
		
		tv1 = (TextView)findViewById(R.id.textView1);
		tv1.setText("");
		tv2 = (TextView)findViewById(R.id.textView2);
		tv2.setText("");
		tv3 = (TextView)findViewById(R.id.textView3);
		tv3.setText("");
		
		ib = (ImageButton)findViewById(R.id.imageView2);
		ib.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View v) 
            {                
    			Message m1 = new Message();
    			m1.what = ABOUT;                            
	        	messageHandler.sendMessage(m1);
            }
        });
		
		
		
		main_flag = true;			
    }
    
    public void myTimer(){
		thread1 = new Thread()
	    {
	        public void run() {
	            try {	            	
	                while(!Thread.interrupted()) {	 	                	
	                	sleep(1000);
	        			Message m = new Message();
                    	m.what = TICK;                            
                    	messageHandler.sendMessage(m); 
	                }
	            } catch (InterruptedException e) {
	                e.printStackTrace();                	
	            }
	            if ( Thread.interrupted() ){
            	    return;
            	}
	        }
	    };
	    thread1.start();
	}
    
    private Handler messageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg){
			switch(msg.what){
			//handle messages
			
			case TICK:		        
		        stardate = myStarDate.getStarDate2();

		        //myStarDate.getStarDate2();
		        
		        if(DEBUG) Log.v(TAG, stardate[0] + stardate[1]+ stardate[2] );
		        		        
		        tv1 = (TextView)findViewById(R.id.textView1);
		        Typeface font2 = Typeface.createFromAsset(getAssets(), "tkgenti1.ttf");
			    tv1.setTypeface(font2);
				tv1.setText(stardate[0]);
				
		        tv2 = (TextView)findViewById(R.id.textView2);
		       
			    tv2.setTypeface(font2);
				tv2.setText(stardate[1]);
				
				tv3 = (TextView)findViewById(R.id.textView3);
		      
			    tv3.setTypeface(font2);
				tv3.setText(stardate[2]);
				break;
			case ABOUT:				
				Intent mainIntent2 = new Intent(MainActivity.this, AboutActivity.class);
				MainActivity.this.startActivity(mainIntent2);
				MainActivity.this.finish();
				break;		
			default:
				//break;
			}
		}
	};
	
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
		/*if(main_flag){			
			MainActivity.this.finish();
		}
		else
			showMain();	*/	
		MainActivity.this.finish();
	    return;
	}
    
    @Override
	protected void onResume() {
		/*
		 * onResume is is always called after onStart, even if the app hasn't been
		 * paused
		 */
		
		super.onResume();
	}
    
    @Override
	protected void onStop() {	
    	try{		
			if (thread1.isAlive()){				
				thread1.interrupt();				
			}
		}
		catch (NullPointerException e) {
            e.printStackTrace();
        }
		
		finish();
		super.onStop();
	}
}

