package memoque.bobs.com.memoque.main.memo;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import org.joda.time.DateTime;

import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.main.MemoQueManager;
import memoque.bobs.com.memoque.main.MemoQueManager.Adapterkey;

public class DetailMemoActivity extends AppCompatActivity
{
	DateTime resultDate = DateTime.now();
	EditText titleEdit;
	EditText contentEdit;
	TextView alarmDateTv;
	MemoModel memoModel = new MemoModel( );

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

		titleEdit = findViewById( R.id.edit_title );
		contentEdit = findViewById( R.id.edit_content );
		alarmDateTv = findViewById( R.id.text_alarm_date );
		alarmDateTv.setText( resultDate.toString( "yyyy/MM/dd HH:mm" ) );

		memoModel.setIndex( MemoQueManager.Companion.getInstance().getMemoIndex() );
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
		switch( item.getItemId() ) {
			case R.id.select_alarm_date:
				createPickersDialog();
				break;
			case R.id.menu_save:
				memoModel.setTitle( titleEdit.getText().toString() );
				memoModel.setDate( alarmDateTv.getText().toString() );
				memoModel.setContent( contentEdit.getText().toString() );

				MemoQueManager.Companion.getInstance().add( Adapterkey.MEMO, memoModel );

				finish();
				break;
			case R.id.menu_delete:
				MemoQueManager.Companion.getInstance().remove( Adapterkey.MEMO, memoModel.getIndex() );

				finish();
				break;
			case R.id.menu_reset:
				titleEdit.setText( "" );
				contentEdit.setText( "" );
				alarmDateTv.setText( "" );
				break;
		}

		return super.onOptionsItemSelected( item );
	}

	private void createPickersDialog()
	{
		final Builder builder = new Builder( this );
		LayoutInflater inflater = this.getLayoutInflater();

		View view = inflater.inflate( R.layout.dialog_time_picker, null );
		final TextView resultTv = view.findViewById( R.id.result_date );
		resultTv.setText( resultDate.toString( "yyyy/MM/dd HH:mm" ) );

		DatePicker datePicker = view.findViewById( R.id.date_picker );
		datePicker.init( datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new OnDateChangedListener()
		{
			@Override
			public void onDateChanged( DatePicker view, int year, int monthOfYear, int dayOfMonth )
			{
				resultDate = new DateTime( year, monthOfYear + 1, dayOfMonth, resultDate.getHourOfDay(), resultDate.getMinuteOfHour(), 0 );
				resultTv.setText( resultDate.toString( "yyyy/MM/dd HH:mm" ) );
			}
		} );

		TimePicker timePicker = view.findViewById( R.id.time_picker );
		timePicker.setOnTimeChangedListener( new OnTimeChangedListener()
		{
			@Override
			public void onTimeChanged( TimePicker view, int hourOfDay, int minute )
			{
				try {
					resultDate = new DateTime( resultDate.getYear(), resultDate.getMonthOfYear(), resultDate.getDayOfMonth(), hourOfDay, minute, 0 );
					resultTv.setText( resultDate.toString( "yyyy/MM/dd HH:mm" ) );
				} catch( NullPointerException e ) {
					Toast.makeText( DetailMemoActivity.this, R.string.select_alarmtime_warning_not_select_date, Toast.LENGTH_SHORT ).show();
				}
			}
		} );

		builder.setView( view ).setPositiveButton( R.string.select_alarmtime_positive, new OnClickListener()
		{
			@Override
			public void onClick( DialogInterface dialog, int which )
			{
				Builder alertDialog = new Builder(DetailMemoActivity.this)
						.setTitle( R.string.select_alarmtime_positive )
						.setMessage( resultDate.toString( "yyyy/MM/dd HH:mm" ) + "\n" + getString( R.string.select_alarmtime_decision ) )
						.setPositiveButton( R.string.select_alarmtime_positive, new OnClickListener()
						{
							@Override
							public void onClick( DialogInterface dialog, int which )
							{
								alarmDateTv.setText( resultDate.toString( "yyyy/MM/dd HH:mm" ) );
								dialog.cancel();
							}
						} )
						.setNegativeButton( R.string.select_alarmtime_negative, new OnClickListener()
						{
							@Override
							public void onClick( DialogInterface dialog, int which )
							{
								dialog.cancel();
							}
						} );

				alertDialog.show();
			}
		} ).setNegativeButton( R.string.select_alarmtime_negative, new OnClickListener()
		{
			@Override
			public void onClick( DialogInterface dialog, int which )
			{
				dialog.cancel();
			}
		} ).show();
	}
}
