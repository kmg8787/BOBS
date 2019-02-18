package memoque.bobs.com.memoque.main.memo;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
	public static final String MEMO_INDEX = "memoindex";
	public static final String TAB_KEY    = "tabkey";

	enum DetailMemoStatus
	{
		ADD_MEMO, EDIT_MEMO
	}

	DateTime         resultDate       = DateTime.now();
	EditText         titleEdit;
	EditText         contentEdit;
	TextView         alarmDateTv;
	BSMemo           BSMemo;
	int              currentPosition  = 0;
	DetailMemoStatus detailMemoStatus = DetailMemoStatus.ADD_MEMO;
	Adapterkey       currentTabkey    = Adapterkey.MEMO;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_detail_memo );

		Toolbar toolbar = findViewById( R.id.detail_memo_toolbar );
		setSupportActionBar( toolbar );

		titleEdit = findViewById( R.id.edit_title );
		contentEdit = findViewById( R.id.edit_content );
		alarmDateTv = findViewById( R.id.text_alarm_date );
		alarmDateTv.setText( resultDate.toString( "yyyy/MM/dd HH:mm" ) );

		Bundle extras = getIntent().getExtras();
		if( null == extras ) {
			setTitle( R.string.detail_memo_title_add );

			BSMemo = new BSMemo();
			BSMemo.setIndex( MemoQueManager.Companion.getInstance().getMemoIndex() );
			currentPosition = MemoQueManager.Companion.getInstance().getMemosSize();
		} else {
			detailMemoStatus = DetailMemoStatus.EDIT_MEMO;
			setTitle( R.string.detail_memo_title_edit );

			currentPosition = extras.getInt( MEMO_INDEX );
			BSMemo = MemoQueManager.Companion.getInstance().getMemo( currentPosition );

			if( BSMemo != null ) {
				titleEdit.setText( BSMemo.getTitle() );
				contentEdit.setText( BSMemo.getContent() );
				alarmDateTv.setText( BSMemo.getDate() );
			}

			currentTabkey = (Adapterkey)extras.getSerializable( TAB_KEY );
		}
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
				String title = titleEdit.getText().toString();
				if( TextUtils.isEmpty( title ) ) {
					Toast.makeText( this, R.string.detail_memo_menu_save_empty_title, Toast.LENGTH_SHORT ).show();
					break;
				}

				String content = contentEdit.getText().toString();
				if( TextUtils.isEmpty( content ) ) {
					Toast.makeText( this, R.string.detail_memo_menu_save_empty_content, Toast.LENGTH_SHORT ).show();
					break;
				}

				BSMemo.setTitle( title );
				BSMemo.setDate( alarmDateTv.getText().toString() );
				BSMemo.setContent( content );
				BSMemo.convertDateTime();
				BSMemo.setCompleteNoti( false );

				switch( detailMemoStatus ) {
					case ADD_MEMO:
						MemoQueManager.Companion.getInstance().add( Adapterkey.MEMO, BSMemo, currentPosition );
						break;

					case EDIT_MEMO:
						MemoQueManager.Companion.getInstance().update( currentTabkey, currentPosition );
						break;
				}

				finish();
				break;
			case R.id.menu_delete:
				MemoQueManager.Companion.getInstance().remove( currentTabkey, currentPosition );

				finish();
				break;
			case R.id.menu_reset:
				titleEdit.setText( "" );
				contentEdit.setText( "" );
				break;
			case R.id.menu_exit:
				finish();
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
				Builder alertDialog = new Builder( DetailMemoActivity.this ).setTitle( R.string.select_alarmtime_positive )
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
