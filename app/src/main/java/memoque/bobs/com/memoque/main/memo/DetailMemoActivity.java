package memoque.bobs.com.memoque.main.memo;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import memoque.bobs.com.memoque.R;

public class DetailMemoActivity extends AppCompatActivity
{
	EditText titleEdit;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_detail_memo );

		Toolbar toolbar = findViewById( R.id.detail_memo_toolbar );
		setSupportActionBar( toolbar );

		ActionBar actionBar = getSupportActionBar();
		if( actionBar != null )
			actionBar.setDisplayHomeAsUpEnabled( true );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		getMenuInflater().inflate( R.menu.menu, menu );
		return true;
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		switch( item.getItemId() ){
			case R.id.menu_save:
				break;
			case R.id.menu_delete:
				break;
			case R.id.menu_reset:
				break;
		}

		return super.onOptionsItemSelected( item );
	}
}
