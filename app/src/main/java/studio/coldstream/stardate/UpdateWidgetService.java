package studio.coldstream.stardate;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class UpdateWidgetService extends Service {
	
	final boolean DEBUG = false;
	StarDate myStarDate;
	String[] stardate;
		
 	@Override  
    public void onCreate()  
    {  
         super.onCreate();  
    }  
  
    @Override  
    public int onStartCommand(Intent intent, int flags,int startId)
    {  
        buildUpdate();  
  
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }  
  
    private void buildUpdate()  
    {  
    	
    	if(DEBUG) Log.v("UpdateWidgetService", "Called");
        //String lastUpdated = DateFormat.format("MMMM dd, yyyy h:mmaa", new Date()).toString();  
    	myStarDate = new StarDate();
		stardate = new String[3];

		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
				.getApplicationContext());
		
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_layout);
	    stardate = myStarDate.getStarDate2();
       
        remoteViews.setTextViewText(R.id.textView01,"" + stardate[0]);
        remoteViews.setTextViewText(R.id.textView02,"" + stardate[1]);
        remoteViews.setTextViewText(R.id.textView03,"" + stardate[2]);
  
        // Push update for this widget to the home screen  
        ComponentName widgetId = new ComponentName(this, MyWidgetProvider.class);  
        
        appWidgetManager.updateAppWidget(widgetId, remoteViews);
        
    }  
  
    @Override
	public IBinder onBind(Intent intent) {
		return null;
	}
    
    
	   
}