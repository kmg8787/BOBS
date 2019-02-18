package memoque.bobs.com.memoque.appdata;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class NotiService extends Service
{
	public static Intent serviceIntent = null;

	@Nullable
	@Override
	public IBinder onBind( Intent intent )
	{
		return null;
	}

	@Override
	public int onStartCommand( final Intent intent, int flags, int startId )
	{
		Log.e( "BOBS", "서비스 시작" );

		new Thread( new Runnable()
		{
			@Override
			public void run()
			{
				startNotification( NotiService.this, new Intent( NotiService.this, NotiReceiver.class ) );
			}
		}, "Noti Thread" ).start();

		return super.onStartCommand( intent, flags, startId );
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Log.e( "BOBS", "서비스 종료" );
	}

	public static void startNotification( Context context, Intent intent )
	{
		// AlarmManager 호출
		AlarmManager manager = (AlarmManager)context.getSystemService( Context.ALARM_SERVICE );
		if( null == manager )
			return;

		PendingIntent pendingIntent = PendingIntent.getBroadcast( context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );

		// Receiver 호출 한다.
		manager.set( AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent );
	}
}
