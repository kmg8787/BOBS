package memoque.bobs.com.memoque.appdata;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

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
		// 노티리시버를 실행한다
		startNotification( NotiService.this, new Intent( NotiService.this, NotiReceiver.class ) );

		return super.onStartCommand( intent, flags, startId );
	}

	public static void startNotification( Context context, Intent intent )
	{
		// AlarmManager 호출
		AlarmManager manager = (AlarmManager)context.getSystemService( Context.ALARM_SERVICE );
		if( null == manager )
			return;

		PendingIntent pendingIntent = PendingIntent.getBroadcast( context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );

		// Receiver를 호출 한다.
		manager.set( AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent );
	}
}
