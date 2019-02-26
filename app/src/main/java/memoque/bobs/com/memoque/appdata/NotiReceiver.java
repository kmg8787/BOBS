package memoque.bobs.com.memoque.appdata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import org.joda.time.DateTime;

import java.util.List;

import memoque.bobs.com.memoque.main.MemoQueManager;
import memoque.bobs.com.memoque.main.memo.BSMemo;

public class NotiReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive( final Context context, Intent intent )
	{
		// 앱이 실행되면 리시버를 더이상 실행하지 않는다
		if( NotiService.serviceIntent == null )
			return;

		// 메인액티비티가 특정상황에서 null일 경우가 있으므로 세팅해준다
		AppData.mainActivity = context.getApplicationContext();

		// 현재 시간 가져온다
		DateTime now = new DateTime();

		// 메모들 중 현재시간 이상이면 알람을 보낸다(메모가 없으면 리시버를 돌리지 않는다)
		List<BSMemo> memos = MemoQueManager.Companion.getInstance().getMemos();
		if( memos.size() == 0 )
			return;

		for( BSMemo memo : memos ) {
			if( memo == null || memo.getDateTime() == null )
				continue;

			if( !now.isBefore( memo.getDateTime() ) && !memo.isCompleteNoti() )
				AppData.sendNotification( memo );
		}

		// 30초 딜레이를 준다(Thread.sleep은 쓰지않는다)
		Handler handler = new Handler();
		handler.postDelayed( new Runnable()
		{
			@Override
			public void run()
			{
				// 리시버를 계속 돌린다
				NotiService.startNotification( context, new Intent( context, NotiReceiver.class ) );
			}
		}, 30000 );
	}
}
