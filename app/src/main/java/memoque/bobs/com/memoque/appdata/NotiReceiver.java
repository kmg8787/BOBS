package memoque.bobs.com.memoque.appdata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.List;

import memoque.bobs.com.memoque.main.MemoQueManager;
import memoque.bobs.com.memoque.main.memo.BSMemo;

public class NotiReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive( Context context, Intent intent )
	{
		if( NotiService.serviceIntent == null )
			return;

		AppData.mainActivity = context.getApplicationContext();

		DateTime now = new DateTime();

		List<BSMemo> memos = MemoQueManager.Companion.getInstance().getMemos();
		for( BSMemo memo : memos ) {
			if( !now.isBefore( memo.getDateTime() ) )
				if( !memo.isCompleteNoti() )
					AppData.sendNotification( memo );
		}

		Log.e( "BOBS", "리시버 시간 : " + now.getHourOfDay() + "시 " + now.getMinuteOfHour() + "분" );

		try {
			Thread.sleep( 1000 );
		} catch( Exception e ) {

		}

		NotiService.startNotification( context, new Intent( context, NotiReceiver.class ) );
	}
}
