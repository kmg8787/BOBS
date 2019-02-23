package memoque.bobs.com.memoque.main;

import android.app.Activity;
import android.widget.Toast;

import memoque.bobs.com.memoque.R;

public class DoubleBackPressed
{
	private final long FINISH_INTERVAL_TIME = 2000;
	private       long backPressedTime      = 0;
	private Activity activity;

	public DoubleBackPressed(Activity activity)
	{
		this.activity = activity;
	}

	public void onBackPressed()
	{
		// 백키 두번 터치할경우 꺼지도록 한다
		long tempTime = System.currentTimeMillis();
		long intervalTime = tempTime - backPressedTime;

		// 첫 백키를 터치한지 2초 내에 백키를 터치하면 앱을 종료하고 아니면 토스트를 띄운다
		if( intervalTime >= 0 && intervalTime <= FINISH_INTERVAL_TIME )
			activity.finish();
		else {
			backPressedTime = tempTime;
			Toast.makeText( activity, activity.getResources().getString( R.string.main_backpressed ), Toast.LENGTH_SHORT ).show();
		}
	}
}
