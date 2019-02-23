package memoque.bobs.com.memoque.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.appdata.AppData;
import memoque.bobs.com.memoque.appdata.NotiService;
import memoque.bobs.com.memoque.title.TitleActivity;

public class SplashActivity extends Activity
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		setContentView( R.layout.activity_splash );

		// fabric 초기화
		Fabric.with(this, new Crashlytics());

		NotiService.serviceIntent = null;
		AppData.splashActivity = this;

		Handler handler = new Handler(  );
		handler.postDelayed( new Runnable()
		{
			@Override
			public void run()
			{
				// 1초 뒤 타이틀 액티비티 슬라이드 애니메이션 효과를 준다
				startActivity( new Intent( SplashActivity.this, TitleActivity.class ) );
				overridePendingTransition( R.anim.slide_in, R.anim.slide_out );

				finish();
				overridePendingTransition( R.anim.slide_enter, R.anim.slide_exit );
			}
		}, 1000 );
	}
}
