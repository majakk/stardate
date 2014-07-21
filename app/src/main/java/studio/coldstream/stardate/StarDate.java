package studio.coldstream.stardate;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.util.Log;

public class StarDate {
	private static boolean DEBUG = false;
	static final String TAG = "StarDate"; // for Log
	String[] stardate;	
	
	public String[] getStarDate(){
		stardate = new String[3];
		
		SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
        yyyy.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtYear =  yyyy.format(new Date());
        
        SimpleDateFormat ddd = new SimpleDateFormat("DDD");
        ddd.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtDays =  ddd.format(new Date());
        
        SimpleDateFormat kk = new SimpleDateFormat("kk");
        kk.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtHour =  kk.format(new Date());
        
        SimpleDateFormat mm = new SimpleDateFormat("mm");
        mm.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtMinute =  mm.format(new Date());
        
        SimpleDateFormat ss = new SimpleDateFormat("ss");
        ss.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtSecond =  ss.format(new Date());
        
        int delta_y = 0;
        long delta_d = 0;
        int delta_h = 0;
        int delta_m = 0;
        int delta_s = 0;
        double dec = 0.0;
        try {
        	delta_y = Integer.parseInt(gmtYear) - 2009;
        	delta_d = 5 * (114 + Math.round(((double)delta_y * 365.2422)) + Integer.parseInt(gmtDays));
        	
        	delta_h = Integer.parseInt(gmtHour);
        	delta_m = (delta_h * 60) + Integer.parseInt(gmtMinute);
        	delta_s = (delta_m * 60) + Integer.parseInt(gmtSecond);
        	
        	dec = -280000 - delta_d - (double)delta_s / 17280.0f;
        	//dec += 0.00000111f;
        } catch(NumberFormatException nfe) {
        	if(DEBUG) Log.v(TAG, "Could not parse " + nfe);
        } 
        if(DEBUG) Log.v(TAG, String.valueOf(dec));
        
        DecimalFormat maxDigitsFormatter = new DecimalFormat("#000000.0000");
               
        try {
	        stardate[0] = "[" + (maxDigitsFormatter.format(dec)).substring(0, 3) + "]";
	        stardate[1] = " 0" + (maxDigitsFormatter.format(dec)).substring(3, 9);
	        stardate[2] = "" + (maxDigitsFormatter.format(dec)).substring(9, 11);
        } catch(StringIndexOutOfBoundsException sbe) {
        	if(DEBUG) Log.v(TAG, "Could not format " + sbe);
        	if(DEBUG) Log.v(TAG, maxDigitsFormatter.format(dec));
        	stardate[2] = "--";        	 
        }
        return stardate;
	}
	
	/*
	 * TODO: It appears to work until we get close enough to the stardate [0] 00000.0, but then it goes all weird. 
	 * In fact, the diff calculation should be returned to "normal" at that point.
	 * 
	 * */
	
	public String[] getStarDate2(){
		stardate = new String[3];
		
		Calendar originaldate = Calendar.getInstance(TimeZone.getTimeZone("gmt"));
		Calendar instancedate = Calendar.getInstance(TimeZone.getTimeZone("gmt"));
				
		//originaldate.set(2008, 8, 8, 0, 0, 0);
		originaldate.set(2162, 0, 4, 0, 0, 0);
		
		/*instancedate.add(Calendar.HOUR, 7);
		instancedate.add(Calendar.MINUTE, 33);
		instancedate.add(Calendar.YEAR, 156);
		instancedate.add(Calendar.HOUR, -6*24);
		instancedate.add(Calendar.MONTH, -3);*/
		
		
		// Get the represented date in milliseconds
		long milis1 = originaldate.getTimeInMillis();
		long milis2 = instancedate.getTimeInMillis();
		
		// Calculate difference in milliseconds
		long diff = milis2 - milis1;
		 
		// Calculate difference in seconds
		long diffSeconds = diff / 1000;
		 
		// Calculate difference in minutes
		//long diffMinutes = diff / (60 * 1000);
		
		// Calculate difference in hours
		//long diffHours = diff / (60 * 60 * 1000);
		
		// Calculate difference in days
		//long diffDays = diff / (24 * 60 * 60 * 1000);
		
		//double dec = -280000 - ((double)diffSeconds / 17280.0f);
		double dec = ((double)diffSeconds / 17280.0f);
				
		int mantel = (int)Math.ceil(dec/10000.0f);
				
		double kropp = (dec + (-(mantel-1)*10000.0f));
		
		if(kropp >= 10000) mantel += 2; //Fixing rounding error
		
		double mantelkropp = ((mantel-1) * 10000.0f) - kropp;
		
		/*Log.v(TAG, "Diff: " + Long.toString(diff));
		
		Log.v(TAG, "Diff: " + Double.toString(dec));
		
		Log.v(TAG, "Diff: " + Integer.toString(mantel));
		
		Log.v(TAG, "Diff: " + Double.toString(kropp));
		
		Log.v(TAG, "Diff: " + Double.toString(mantelkropp));*/
		
		dec = mantelkropp;
		
		DecimalFormat maxDigitsFormatter;
		if(dec < 0)
			maxDigitsFormatter = new DecimalFormat("#000000.0000");
		else
			maxDigitsFormatter = new DecimalFormat("0000000.0000");
        
        try {
	        stardate[0] = "[" + (maxDigitsFormatter.format(dec)).substring(0, 3) + "]";
	        stardate[1] = " 0" + (maxDigitsFormatter.format(dec)).substring(3, 9);
	        stardate[2] = "" + (maxDigitsFormatter.format(dec)).substring(9, 11);
        } catch(StringIndexOutOfBoundsException sbe) {
        	if(DEBUG) Log.v(TAG, "Could not format " + sbe);
        	if(DEBUG) Log.v(TAG, maxDigitsFormatter.format(dec));
        	stardate[2] = "--";        	 
        }
        
        Log.v(TAG, "Stardate: " + stardate[0] + stardate[1] + stardate[2]);
        return stardate;
		
	}
	
	
}
