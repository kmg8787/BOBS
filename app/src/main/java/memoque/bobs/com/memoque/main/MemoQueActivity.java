package memoque.bobs.com.memoque.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import memoque.bobs.com.memoque.BuildConfig;
import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.appdata.AppData;
import memoque.bobs.com.memoque.appdata.NotiService;
import memoque.bobs.com.memoque.main.memo.DetailMemoActivity;
import memoque.bobs.com.memoque.main.memo.MemoFragment;
import memoque.bobs.com.memoque.main.search.SearchFragment;

public class MemoQueActivity extends AppCompatActivity
{
	private DoubleBackPressed doubleBackPressed = null;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		// free 버전이면 배너광고가 있는 레이아웃을 세팅한다
		setContentView( BuildConfig.IS_FREE_VERSION ? R.layout.activity_main : R.layout.activity_main_paid );

		// 백키 더블 처리 클래스 생성
		doubleBackPressed = new DoubleBackPressed( this );

		// 구글 애드몹 초기화
		MobileAds.initialize( this, BuildConfig.ADMOB_APP_ID );

		AppData.mainActivity = this;
		MemoQueManager.Companion.getInstance().setDatabase( this );

		// 툴바 세팅
		Toolbar toolbar = findViewById( R.id.toolbar_main );
		setSupportActionBar( toolbar );

		// 뷰 페이져 생성
		ViewPager viewPager = findViewById( R.id.viewPager );
		viewPager.setAdapter( new FragmentPagerAdapter( getSupportFragmentManager() )
		{
			@Override
			public int getCount()
			{
				String[] tabs = getResources().getStringArray( R.array.tabnames );
				return tabs.length;
			}

			@Override
			public Fragment getItem( int i )
			{
				switch( i ) {
					case 0:
						return new MemoFragment();
					case 1:
						return new SearchFragment();
				}
				return null;
			}

			@Nullable
			@Override
			public CharSequence getPageTitle( int position )
			{
				return getResources().getStringArray( R.array.tabnames )[position];
			}
		} );

		// 탭에 뷰 페이져 연결
		TabLayout tabLayout = findViewById( R.id.tabLayout );
		tabLayout.setupWithViewPager( viewPager );

		if( BuildConfig.IS_FREE_VERSION ) {
			// free 버전이면 배너광고를 로드한다
			AdView adView = findViewById( R.id.myAdView );
			AdRequest adRequest = new AdRequest.Builder().build();
			adView.loadAd( adRequest );
		}
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		stopService( new Intent( this, NotiService.class ) );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		getMenuInflater().inflate( R.menu.main_menu, menu );
		return true;
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		switch( item.getItemId() ) {
			case R.id.memo_add:
				// 메모 탭에서만 메모 추가가 가능하도록 한다
				TabLayout tabLayout = findViewById( R.id.tabLayout );
				if( tabLayout.getSelectedTabPosition() == 0 ) {
					Intent intent = new Intent( this, DetailMemoActivity.class );
					startActivity( intent );
				} else
					Toast.makeText( getApplicationContext(), getString( R.string.memo_add_button_warning ), Toast.LENGTH_SHORT ).show();
				break;

			case R.id.alram_on:
				// 알람을 켠다
				AppData.setEnableNotification( true );
				Toast.makeText( getApplicationContext(), R.string.memo_alram_on_message, Toast.LENGTH_SHORT ).show();
				break;

			case R.id.alram_off:
				// 알람을 끈다
				AppData.setEnableNotification( false );
				Toast.makeText( getApplicationContext(), R.string.memo_alram_off_message, Toast.LENGTH_SHORT ).show();
				break;
		}

		return super.onOptionsItemSelected( item );
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		if( AppData.isEnableNotification() ) {
			// 현재 시간보다 이전인 메모는 알림을 보낸걸로 처리한다(종료시 바로 알림이 가는걸 방지)
			MemoQueManager.Companion.getInstance().checkMemosDate();

			// 알람이 켜져있으면 서비스를 실행한다
			NotiService.serviceIntent = new Intent( this, NotiService.class );
			startService( NotiService.serviceIntent );
		}
	}

	@Override
	public void onBackPressed()
	{
		if( doubleBackPressed != null )
			doubleBackPressed.onBackPressed();
	}
}
