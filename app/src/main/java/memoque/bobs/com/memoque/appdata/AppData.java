package memoque.bobs.com.memoque.appdata;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.main.memo.BSMemo;

public class AppData
{
	public static final String PREFERENCE           = "com.bobs.memoque";
	public static final String NOTIFICATION_ENABLED = "notification_enabled";

	public static Context mainActivity = null;
	public static Context splashActivity = null;

	public static void sendNotification( BSMemo memo )
	{
		if( mainActivity == null || !isEnableNotification() )
			return;

		Log.e( "BOBS", "푸시 시작" );

		NotificationCompat.Builder builder = null;
		NotificationManager manager = (NotificationManager)mainActivity.getSystemService( Context.NOTIFICATION_SERVICE );
		if( null != manager ) {
			if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ) {
				NotificationChannel notificationChannel = new NotificationChannel( "memoque", "memoque notification", NotificationManager.IMPORTANCE_DEFAULT );
				manager.createNotificationChannel( notificationChannel );
				builder = new NotificationCompat.Builder( mainActivity, notificationChannel.getId() );
			} else {
				builder = new NotificationCompat.Builder( mainActivity, "memoque" );
			}
		}

		PendingIntent pendingIntent = PendingIntent.getActivity( splashActivity, 0, new Intent( splashActivity, splashActivity.getClass() ), PendingIntent.FLAG_UPDATE_CURRENT );

		if( null != builder ) {
			builder.setSmallIcon( R.drawable.baseline_note_24 );
			builder.setContentTitle( mainActivity.getString( R.string.notification_title ) );
			builder.setContentText( memo.getTitle() );
			builder.setDefaults( Notification.DEFAULT_VIBRATE );
			builder.setLargeIcon( BitmapFactory.decodeResource( mainActivity.getResources(), R.drawable.baseline_note_white_48 ) );
			builder.setPriority( NotificationCompat.PRIORITY_DEFAULT );
			builder.setAutoCancel( true );
			builder.setContentIntent( pendingIntent );
			builder.setCategory( Notification.CATEGORY_MESSAGE ).setVisibility( Notification.VISIBILITY_PUBLIC );

			manager.notify( 0, builder.build() );
		}

		memo.setCompleteNoti( true );

		Log.e( "BOBS", "푸시 성공" );
	}

	public static boolean isEnableNotification()
	{
		if( mainActivity == null )
			return true;

		SharedPreferences preferences = mainActivity.getSharedPreferences( PREFERENCE, Context.MODE_PRIVATE );
		if( preferences == null )
			return true;

		return preferences.getBoolean( NOTIFICATION_ENABLED, true );
	}

	public static void setEnableNotification( boolean value )
	{
		if( mainActivity == null )
			return;

		SharedPreferences preferences = mainActivity.getSharedPreferences( PREFERENCE, Context.MODE_PRIVATE );
		if( preferences == null )
			return;

		Editor editor = preferences.edit();
		if( editor != null ) {
			editor.putBoolean( NOTIFICATION_ENABLED, value );
			editor.apply();
		}
	}
}
