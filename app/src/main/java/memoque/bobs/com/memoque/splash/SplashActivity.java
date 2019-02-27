package memoque.bobs.com.memoque.splash;

import android.Manifest.permission;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Window;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.appdata.AppData;
import memoque.bobs.com.memoque.appdata.NotiService;
import memoque.bobs.com.memoque.title.TitleActivity;

public class SplashActivity extends Activity
{
	private static int PERMISSIONS_REQUEST_CODE = 100;

	String[] requestPermissions = { permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE };

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		setContentView( R.layout.activity_splash );

//		startSplash();
		checkPermissions();
	}

	private void checkPermissions()
	{
		int writestoreage = ContextCompat.checkSelfPermission( this, permission.WRITE_EXTERNAL_STORAGE );
		int readstoreage = ContextCompat.checkSelfPermission( this, permission.READ_EXTERNAL_STORAGE );

		if(  writestoreage == PackageManager.PERMISSION_GRANTED && readstoreage == PackageManager.PERMISSION_GRANTED ) {
			startSplash();
		} else {
			if( ActivityCompat.shouldShowRequestPermissionRationale( this, requestPermissions[0] ) || ActivityCompat.shouldShowRequestPermissionRationale( this, requestPermissions[1] )  ) {
				new AlertDialog.Builder( this ).setTitle( R.string.permission_request_title )
																  .setMessage( R.string.permission_request_content )
																  .setCancelable( false )
																  .setPositiveButton( R.string.permission_request_error_positive, new OnClickListener()
																  {
																	  @Override
																	  public void onClick( DialogInterface dialog, int which )
																	  {
																		  ActivityCompat.requestPermissions( SplashActivity.this, requestPermissions, PERMISSIONS_REQUEST_CODE );
																	  }
																  } )
																  .show();
			} else
				ActivityCompat.requestPermissions( this, requestPermissions, PERMISSIONS_REQUEST_CODE );
		}
	}

	@Override
	public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults )
	{
		if( requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == requestPermissions.length ) {

			boolean check = true;

			for( int result : grantResults ) {
				if( result != PackageManager.PERMISSION_GRANTED ) {
					check = false;
					break;
				}
			}

			if( check )
				startSplash();
			else {
				new AlertDialog.Builder( this ).setTitle( R.string.permission_request_error_title )
																  .setMessage( R.string.permission_request_error_content )
																  .setCancelable( false )
																  .setPositiveButton( R.string.permission_request_error_positive, new OnClickListener()
																  {
																	  @Override
																	  public void onClick( DialogInterface dialog, int which )
																	  {
																		  finish();
																	  }
																  } )
																  .show();
			}
		}
	}

	private void startSplash()
	{
		// fabric 초기화
		Fabric.with( SplashActivity.this, new Crashlytics() );

		NotiService.serviceIntent = null;
		AppData.splashActivity = SplashActivity.this;

		Handler handler = new Handler();
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
