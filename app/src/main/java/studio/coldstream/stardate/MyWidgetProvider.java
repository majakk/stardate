package studio.coldstream.stardate;

import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import android.util.Log;

public class MyWidgetProvider extends AppWidgetProvider {

	private static boolean DEBUG = false;
	//StarDate myStarDate;
	//String[] stardate;
	
	private PendingIntent service = null;  
			
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		if(DEBUG) Log.v("MyWidgetProvider", "Called");
		
		// ------ This is alarmmanager code -------
		final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);  
  
        final Calendar TIME = Calendar.getInstance();  
        TIME.set(Calendar.MINUTE, 0);  
        TIME.set(Calendar.SECOND, 0);  
        TIME.set(Calendar.MILLISECOND, 0);  
  
        final Intent i = new Intent(context, UpdateWidgetService.class);  
  
        if (service == null)  
        {  
            service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);  
        }  
  
        m.setRepeating(AlarmManager.RTC, TIME.getTime().getTime(), 17280, service);  
        //m.setRepeating(AlarmManager.RTC, TIME.getTime().getTime(), 1800000, service);  
	    
        
		// ----- This is old ------
		// Build the intent to call the service
		/*RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_layout);
		Intent intent = new Intent(context.getApplicationContext(),
				UpdateWidgetService.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

		// To react to a click we have to use a pending intent as the
		// onClickListener is
		// excecuted by the homescreen application
		PendingIntent pendingIntent = PendingIntent.getService(
				context.getApplicationContext(), 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.layout, pendingIntent);

		// Finally update all widgets with the information about the click
		// listener
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

		// Update the widgets via the service
		context.startService(intent);*/
		
		
		// -------  This almost works  -----------
		/*myStarDate = new StarDate();
		stardate = new String[3];
		
		
		 Timer timer = new Timer();
         timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1, 16000);*/
	 }
	
	 @Override  
     public void onDisabled(Context context)  
     {  
		 if(DEBUG) Log.v("MyWidgetProvider", "Canceled");
         final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);  
         
         /*Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
         
         vib.vibrate(200);*/
         //Recreate Intent??
         //final Intent i = new Intent(context, UpdateWidgetService.class);
         //service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);  
         
         m.cancel(service);  
         super.onDisabled(context);
     }  
		
	   /* private class MyTime extends TimerTask {
	           RemoteViews remoteViews;
	           AppWidgetManager appWidgetManager;
	           ComponentName thisWidget;
	           public MyTime(Context context, AppWidgetManager appWidgetManager) {
		           this.appWidgetManager = appWidgetManager;
		           remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		           thisWidget = new ComponentName(context, MyWidgetProvider.class);
	           }
		           
	    @Override
	    public void run() {
              
               stardate = myStarDate.getStarDate();
               
               remoteViews.setTextViewText(R.id.textView01,"" + stardate[0]);
               remoteViews.setTextViewText(R.id.textView02,"" + stardate[1]);
               remoteViews.setTextViewText(R.id.textView03,"" + stardate[2]);
                            
               appWidgetManager.updateAppWidget(thisWidget, remoteViews);
               
               Log.v("Run", "Updated");
               
             
	    }
		
		
	}*/
	
}