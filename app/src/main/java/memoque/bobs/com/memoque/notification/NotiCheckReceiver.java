package memoque.bobs.com.memoque.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;

import memoque.bobs.com.memoque.main.MemoQueManager;
import memoque.bobs.com.memoque.main.memo.BSMemo;

import static memoque.bobs.com.memoque.notification.NotifyReceiver.NOTIMEMO_INDEX;

public class NotiCheckReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive( final Context context, final Intent intent )
	{
		// 메모들 알림 다 보냈으면 리시버를 실행하지 않는다
		if( MemoQueManager.Companion.getInstance().isMemosNotNoti() )
			return;

		Log.e( "BOBS", "노티체크 리시버 시작" );

		NotificationInfo notificationInfo = MemoQueManager.Companion.getInstance().getNextNotiInfo( );
		if( !notificationInfo.isEmpty() ) {
			BSMemo notimemo = notificationInfo.getNotiMemo();
			Intent notifyintent = new Intent( context, NotifyReceiver.class );
			notifyintent.putExtra( NOTIMEMO_INDEX, notimemo.getIndex() );

			Log.e( "BOBS", "노티 제목 : " + notimemo.getTitle() + " , 노티 인덱스 : " + notimemo.getIndex() + " , 노티 날짜 : " + Objects.requireNonNull( notimemo.getDateTime() ).toString()  );

			NotiService.startNotification( context, notifyintent, notificationInfo.getDelay() );
		}
	}
}
