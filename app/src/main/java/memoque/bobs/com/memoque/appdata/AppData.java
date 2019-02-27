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
import memoque.bobs.com.memoque.main.MemoQueManager;
import memoque.bobs.com.memoque.main.memo.BSMemo;

public class AppData
{
	public static final String PREFERENCE           = "com.bobs.memoque";
	public static final String NOTIFICATION_ENABLED = "notification_enabled";
	public static final String FIRST_HELP_DIALOG = "first_help_dialog";

	public static Context mainActivity   = null;
	public static Context splashActivity = null;

	public static void sendNotification( BSMemo memo )
	{
		// 액티비티가 널이거나 알림이 꺼져있으면 보내지 않는다
		if( mainActivity == null || !isEnableNotification() )
			return;

		NotificationCompat.Builder builder = null;
		NotificationManager manager = (NotificationManager)mainActivity.getSystemService( Context.NOTIFICATION_SERVICE );
		if( null != manager ) {
			// 채널ID 값이 필수로 들어가야한다
			if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ) {
				NotificationChannel notificationChannel = new NotificationChannel( "memoque", "memoque notification", NotificationManager.IMPORTANCE_DEFAULT );
				manager.createNotificationChannel( notificationChannel );
				builder = new NotificationCompat.Builder( mainActivity, notificationChannel.getId() );
			} else {
				builder = new NotificationCompat.Builder( mainActivity, "memoque" );
			}
		}

		// 알람을 터치했을때 앱이 실행되도록 스플래쉬 액티비티를 펜딩인텐트에 세팅한다
		Intent intent = new Intent( splashActivity, splashActivity.getClass() );
		intent.setAction( Intent.ACTION_MAIN );
		intent.addCategory( Intent.CATEGORY_LAUNCHER );
		intent.addFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP  );
		PendingIntent pendingIntent = PendingIntent.getActivity( splashActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );

		if( null != builder ) {
			// 알람에 대한 데이터들을 세팅한다
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

		// 해당 메모는 알람을 보낸것으로 처리한다
		memo.setCompleteNoti( true );
		MemoQueManager.Companion.getInstance().update( memo );

		Log.e( "BOBS", "푸시 발송 완료");
	}

	public static boolean isEnableNotification()
	{
		if( mainActivity == null )
			return true;

		SharedPreferences preferences = mainActivity.getSharedPreferences( PREFERENCE, Context.MODE_PRIVATE );
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
