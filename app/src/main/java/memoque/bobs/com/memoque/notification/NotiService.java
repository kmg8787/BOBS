package memoque.bobs.com.memoque.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class NotiService extends Service
{
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
		startNotification( NotiService.this, new Intent( NotiService.this, NotiCheckReceiver.class ), 0 );

		return super.onStartCommand( intent, flags, startId );
	}

	public static void startNotification( Context context, Intent intent, long delay )
	{
		// AlarmManager 호출
		AlarmManager alramManager = (AlarmManager)context.getSystemService( Context.ALARM_SERVICE );

		PendingIntent pendingIntent = PendingIntent.getBroadcast( context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );

		// Receiver를 호출 한다.
		alramManager.setExact( AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent );
	}
}
