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
import memoque.bobs.com.memoque.splash.SplashActivity;

public class AppData
{
	public static final String PREFERENCE           = "com.bobs.memoque";
	public static final String NOTIFICATION_ENABLED = "notification_enabled";
	public static final String FIRST_HELP_DIALOG = "first_help_dialog";

	public static Context mainActivity   = null;
	public static Context splashActivity = null;

	public static void sendNotification( BSMemo memo, Context context )
	{
		// 액티비티가 널이거나 알림이 꺼져있으면 보내지 않는다
		if( !isEnableNotification(context) || memo == null) {
			Log.e( "BOBS", "노티 발송 실패" );
			return;
		}

		NotificationCompat.Builder builder = null;
		NotificationManager manager = (NotificationManager)context.getSystemService( Context.NOTIFICATION_SERVICE );
		if( null != manager ) {
			// 채널ID 값이 필수로 들어가야한다
			if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ) {
				NotificationChannel notificationChannel = new NotificationChannel( "memoque", "memoque notification", NotificationManager.IMPORTANCE_DEFAULT );
				manager.createNotificationChannel( notificationChannel );
				builder = new NotificationCompat.Builder( context, notificationChannel.getId() );
			} else {
				builder = new NotificationCompat.Builder( context, "memoque" );
			}
		}

		// 알람을 터치했을때 앱이 실행되도록 스플래쉬 액티비티를 펜딩인텐트에 세팅한다
		Intent intent = new Intent( context, SplashActivity.class );
		intent.setAction( Intent.ACTION_MAIN );
		intent.addCategory( Intent.CATEGORY_LAUNCHER );
		intent.addFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP  );
		PendingIntent pendingIntent = PendingIntent.getActivity( context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );

		if( null != builder ) {
			// 알람에 대한 데이터들을 세팅한다
			builder.setSmallIcon( R.drawable.baseline_note_24 );
			builder.setContentTitle( context.getString( R.string.notification_title ) );
			builder.setContentText( memo.getTitle() );
			builder.setDefaults( Notification.DEFAULT_VIBRATE );
			builder.setLargeIcon( BitmapFactory.decodeResource( context.getResources(), R.drawable.baseline_note_white_48 ) );
			builder.setPriority( NotificationCompat.PRIORITY_DEFAULT );
			builder.setAutoCancel( true );
			builder.setContentIntent( pendingIntent );
			builder.setCategory( Notification.CATEGORY_MESSAGE ).setVisibility( Notification.VISIBILITY_PUBLIC );

			manager.notify( 0, builder.build() );
		}

		// 해당 메모는 알람을 보낸것으로 처리한다
		memo.setCompleteNoti( true );

		Log.e( "BOBS", "노티 발송 완료");
	}

	public static boolean isEnableNotification(Context context)
	{
		if( context == null )
			return true;

		SharedPreferences preferences = context.getSharedPreferences( PREFERENCE, Context.MODE_PRIVATE );
		if( preferences == null )
			return true;

		// 알람 여부를 가져온다
		return preferences.getBoolean( NOTIFICATION_ENABLED, true );
	}

	public static void setEnableNotification( boolean value )
	{
		if( mainActivity == null )
			return;

		SharedPreferences preferences = mainActivity.getSharedPreferences( PREFERENCE, Context.MODE_PRIVATE );
		if( preferences == null )
			return;

		// 알람 여부를 세팅한다
		Editor editor = preferences.edit();
		if( editor != null ) {
			editor.putBoolean( NOTIFICATION_ENABLED, value );
			editor.apply();
		}
	}

	public static boolean isEnableFirstHelpDialog()
	{
		if( mainActivity == null )
			return true;

		SharedPreferences preferences = mainActivity.getSharedPreferences( PREFERENCE, Context.MODE_PRIVATE );
		if( preferences == null )
			return true;

		// 알람 여부를 가져온다
		return preferences.getBoolean( FIRST_HELP_DIALOG, true );
	}

	public static void setEnableFirstHelpDialog( boolean value )
	{
		if( mainActivity == null )
			return;

		SharedPreferences preferences = mainActivity.getSharedPreferences( PREFERENCE, Context.MODE_PRIVATE );
		if( preferences == null )
			return;

		// 알람 여부를 세팅한다
		Editor editor = preferences.edit();
		if( editor != null ) {
			editor.putBoolean( FIRST_HELP_DIALOG, value );
			editor.apply();
		}
	}
}
