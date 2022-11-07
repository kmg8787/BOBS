package memoque.bobs.com.memoque.title;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.main.MemoQueActivity;

public class TitleActivity extends AppCompatActivity
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		setContentView( R.layout.activity_title );

		Animation blinkAnimation = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.blink );

		TextView blinkTextView = findViewById( R.id.titleblink );
		blinkTextView.startAnimation( blinkAnimation );
	}

	@Override
	public boolean onTouchEvent( MotionEvent event )
	{
		if( event.getAction() == MotionEvent.ACTION_DOWN ) {
			startActivity( new Intent( getApplicationContext(), MemoQueActivity.class ) );
			finish();
		}

		return false;
	}
}
