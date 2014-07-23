package studio.coldstream.stardate;

/*
* ToDo:
* When screen pressed in about activity - go back - check!
* Copy to clipboard - check!
* Share to whoever using whatever - check!
* Fix the widget since its not working anymore! - check!
*
* */


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_copy) {
            //Log.d(TAG,"Copy");

            // Gets a handle to the clipboard service.
            ClipboardManager clipboard = (ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);

            // Creates a new text clip to put on the clipboard
            ClipData clip = ClipData.newPlainText("Stardate",stardate[0]+stardate[1]+stardate[2]);

            clipboard.setPrimaryClip(clip);

            Toast.makeText(getApplicationContext(), "Copied to Clipboard",
                    Toast.LENGTH_LONG).show();

            return true;
        }
        if (id == R.id.action_share) {
            //Log.d(TAG,"Share");

            Intent i = new Intent(Intent.ACTION_SEND);
            String msg ="Captain's Log";

            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, msg);
            i.putExtra(Intent.EXTRA_TEXT, stardate[0]+stardate[1]+stardate[2]);

            startActivity(Intent.createChooser(i, "Select"));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showMain(){
        setContentView(R.layout.activity_main);

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
        ib.setOnClickListener(new View.OnClickListener()
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

                    if(DEBUG) Log.v(TAG, stardate[0] + stardate[1] + stardate[2]);

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

}
