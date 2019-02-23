package memoque.bobs.com.memoque.title;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import memoque.bobs.com.memoque.main.MemoQueActivity;
import memoque.bobs.com.memoque.R;

public class TitleActivity extends Activity
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		setContentView( R.layout.activity_title );

		// 아래쪽 텍스트 깜빡임 애니메이션을 준다
		Animation blinkAnimation = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.blink );

		TextView blinkTextView = findViewById( R.id.titleblink );
		blinkTextView.startAnimation( blinkAnimation );
	}

	@Override
	public boolean onTouchEvent( MotionEvent event )
	{
		if( event.getAction() == MotionEvent.ACTION_DOWN ) {
			// 아무 화면 터치시 메모큐액티비티를 시작한다
			startActivity( new Intent( getApplicationContext(), MemoQueActivity.class ) );
			finish();
		}

		return false;
	}
}
