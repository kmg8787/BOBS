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

import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.main.memo.DetailMemoActivity;
import memoque.bobs.com.memoque.main.memo.MemoFragment;
import memoque.bobs.com.memoque.main.search.SearchFragment;

public class MemoQueActivity extends AppCompatActivity
{
	private final long FINISH_INTERVAL_TIME = 2000;
	private       long backPressedTime      = 0;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		MemoQueManager.Companion.getInstance().setDatabase( this );

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
				TabLayout tabLayout = findViewById( R.id.tabLayout );
				if( tabLayout.getSelectedTabPosition() == 0 ) {
					Intent intent = new Intent( this, DetailMemoActivity.class );
					startActivity( intent );
				} else
					Toast.makeText( getApplicationContext(), getString( R.string.memo_add_button_warning ), Toast.LENGTH_SHORT ).show();
				break;
		}

		return super.onOptionsItemSelected( item );
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public void onBackPressed()
	{
		// 백키 두번 터치할경우 꺼지도록 한다
		long tempTime = System.currentTimeMillis();
		long intervalTime = tempTime - backPressedTime;

		// 첫 백키를 터치한지 2초 내에 백키를 터치하면 앱을 종료하고 아니면 토스트를 띄운다
		if( intervalTime >= 0 && intervalTime <= FINISH_INTERVAL_TIME )
			finish();
		else {
			backPressedTime = tempTime;
			Toast.makeText( this, getResources().getString( R.string.main_backpressed ), Toast.LENGTH_SHORT ).show();
		}
	}
}
