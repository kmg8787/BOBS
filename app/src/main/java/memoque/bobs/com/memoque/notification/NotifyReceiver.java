package memoque.bobs.com.memoque.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import memoque.bobs.com.memoque.appdata.AppData;
import memoque.bobs.com.memoque.db.DBManager;
import memoque.bobs.com.memoque.main.memo.BSMemo;

public class NotifyReceiver extends BroadcastReceiver
{
	public static final String NOTIMEMO_INDEX = "notimemo_index";

	private DBManager dbManager = null;

	@Override
	public void onReceive( final Context context, final Intent intent )
	{
		Log.e( "BOBS", "노티 발송 리시버 시작" );

		int notiIndex = Objects.requireNonNull( intent.getExtras() ).getInt( NOTIMEMO_INDEX );

		// 디비를 불러온다
		dbManager = new DBManager( context );

		// 노티를 보낼 메모를 가져온다
		BSMemo notimemo = dbManager.getMemo( notiIndex );

		Log.e( "BOBS", "노티 제목 : " + notimemo.getTitle() + " , 노티 인덱스 : " + notimemo.getIndex() + " , 노티 날짜 : " + Objects.requireNonNull( notimemo.getDateTime() ).toString()  );

		// 노티 발송
		AppData.sendNotification( notimemo , context);

		// 발송했으므로 메모를 업데이트한다
		dbManager.update( notimemo );

		// 메모 전체리스트를 가져온다.
		List<BSMemo> memos = dbManager.getAllMemos();
		Collections.sort( memos, new Comparator<BSMemo>()
		{
			@Override
			public int compare( BSMemo o1, BSMemo o2 )
			{
				if( o1.getDateTime() == null || o2.getDateTime() == null )
					return 0;

				DateTime now = new DateTime();

				long o1Millisecond = o1.getDateTime().getMillis() - now.getMillis();
				long o2Millisecond = o2.getDateTime().getMillis() - now.getMillis();

				if( o1Millisecond < o2Millisecond )
					return -1;
				else if( o1Millisecond > o2Millisecond )
					return 1;
				return 0;
			}
		} );

		// 다음 노티를 보낼 메모가 있는지 체크한다.
		int nextIndex = -100;
		long nextDelay = 0;
		DateTime now = new DateTime();
		BSMemo nextnotimemo = null;

		for( BSMemo memo : memos ) {
			if( memo == null || memo.getDateTime() == null || memo.isCompleteNoti())
				continue;

			long mill = memo.getDateTime().getMillis() - now.getMillis();
			if( mill < 0 )
				continue;

			// 위에서 시간정렬을 했으므로 for문에서 보낼메모를 저장하고 바로 빠져나간다.
			nextDelay = mill;
			nextIndex = memo.getIndex();
			nextnotimemo = memo;
			break;
		}

		if( nextnotimemo != null ) {
			Log.e( "BOBS", "노티 제목 : " + nextnotimemo.getTitle() + " , 노티 인덱스 : " + nextnotimemo.getIndex() + " , 노티 날짜 : " + Objects.requireNonNull( nextnotimemo.getDateTime() ).toString()  );
		}

		// -100 이면 보낼 메모가 없는것이다
		if( nextIndex > -100 ) {
			// 다음 메모를 보낼 리시버를 등록한다
			Intent notifyintent = new Intent( context, NotifyReceiver.class );
			notifyintent.putExtra( NOTIMEMO_INDEX, nextIndex );

			NotiService.startNotification( context, notifyintent, nextDelay );
		}
	}
}
