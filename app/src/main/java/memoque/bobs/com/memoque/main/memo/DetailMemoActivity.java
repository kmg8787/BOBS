package memoque.bobs.com.memoque.main.memo;

import android.annotation.SuppressLint;
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
	public static final String ADAPTER_INDEX = "adapterindex";
	public static final String TAB_KEY    = "tabkey";

	enum DetailMemoStatus
	{
		ADD_MEMO, EDIT_MEMO
	}


	EditText         titleEdit;
	EditText         contentEdit;
	TextView         alarmDateTv;

	BSMemo           BSMemo;
	DateTime         resultDate       = DateTime.now();
	DetailMemoStatus detailMemoStatus = DetailMemoStatus.ADD_MEMO;
	Adapterkey       currentTabkey    = Adapterkey.MEMO;

	int              currentPosition  = 0;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_detail_memo );

		// 툴바 세팅
		Toolbar toolbar = findViewById( R.id.detail_memo_toolbar );
		setSupportActionBar( toolbar );

		titleEdit = findViewById( R.id.edit_title );
		contentEdit = findViewById( R.id.edit_content );
		alarmDateTv = findViewById( R.id.text_alarm_date );
		alarmDateTv.setText( resultDate.toString( "yyyy/MM/dd HH:mm" ) );

		Bundle extras = getIntent().getExtras();
		if( null == extras ) {
			// 값이 없으면 메모 추가 상태이다
			setTitle( R.string.detail_memo_title_add );

			// 메모 데이터 기본 세팅
			BSMemo = new BSMemo();
			BSMemo.setIndex( MemoQueManager.Companion.getInstance().getMemoIndex() );
			// 추가할 리사이클러뷰 인덱스를 세팅
			currentPosition = MemoQueManager.Companion.getInstance().getMemosSize();
		} else {
			// 값이 있으므로 메모 수정 상태이다
			detailMemoStatus = DetailMemoStatus.EDIT_MEMO;
			setTitle( R.string.detail_memo_title_edit );

			currentPosition = extras.getInt( ADAPTER_INDEX );
			// 추가 되어있는 메모 데이터를 가져온다
			BSMemo = MemoQueManager.Companion.getInstance().getMemoToMemoIndex( extras.getInt( MEMO_INDEX ) );

			if( BSMemo != null ) {
				titleEdit.setText( BSMemo.getTitle() );
				contentEdit.setText( BSMemo.getContent() );
				alarmDateTv.setText( BSMemo.getDate() );
			}

			// 현재 탭을 세팅한다
			currentTabkey = (Adapterkey)extras.getSerializable( TAB_KEY );
		}
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		// 툴바 메뉴 세팅
		getMenuInflater().inflate( R.menu.menu, menu );
		return true;
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		switch( item.getItemId() ) {
			case R.id.select_alarm_date:
				// 캘린더버튼 터치하면 날짜 선택 다이얼로그를 띄운다
				createPickersDialog();
				break;

			case R.id.menu_save:
				// 메모를 저장한다
				String title = titleEdit.getText().toString();
				if( TextUtils.isEmpty( title ) ) {
					// 제목이 없으면 경고 토스트를 띄운다
					Toast.makeText( this, R.string.detail_memo_menu_save_empty_title, Toast.LENGTH_SHORT ).show();
					break;
				}

				String content = contentEdit.getText().toString();
				if( TextUtils.isEmpty( content ) ) {
					// 내용이 없으면 경고 토스트를 띄운다
					Toast.makeText( this, R.string.detail_memo_menu_save_empty_content, Toast.LENGTH_SHORT ).show();
					break;
				}

				// 메모 데이터에 작성한 내용을 세팅
				BSMemo.setTitle( title );
				BSMemo.checkAndSetDate( alarmDateTv.getText().toString() );
				BSMemo.setContent( content );
				BSMemo.convertDateTime();
				BSMemo.setCompleteNoti( false );

				switch( detailMemoStatus ) {
					case ADD_MEMO:
						// 메모 추가 상태면 디비에 추가한다
						MemoQueManager.Companion.getInstance().add( Adapterkey.MEMO, BSMemo, currentPosition );
						break;

					case EDIT_MEMO:
						// 메모 수정 상태면 디비를 업데이트한다
						MemoQueManager.Companion.getInstance().update( currentTabkey, BSMemo, currentPosition );
						break;
				}

				// 액티비티를 종료한다
				finish();
				break;

			case R.id.menu_delete:
				// 디비에서 메모를 삭제한다
				if( detailMemoStatus == DetailMemoStatus.ADD_MEMO || !MemoQueManager.Companion.getInstance().remove( currentTabkey, BSMemo.getIndex(), currentPosition )) {
					// 메모 추가 상태거나 삭제함수를 돌리지 못했으면 삭제하지 못한다
					Toast.makeText( this, R.string.detail_memo_menu_remove_warning, Toast.LENGTH_SHORT ).show();
					break;
				}

				// 액티비티를 종료한다
				finish();
				break;

			case R.id.menu_reset:
				// 제목,내용 에디트 텍스트를 초기화한다
				titleEdit.setText( "" );
				contentEdit.setText( "" );
				break;

			case R.id.menu_exit:
				// 액티비티를 종료한다
				finish();
				break;
		}

		return super.onOptionsItemSelected( item );
	}

	private void createPickersDialog()
	{
		// 다이얼로그 생성 및 날짜,시간 피커를 세팅한다
		final Builder builder = new Builder( this );
		LayoutInflater inflater = this.getLayoutInflater();

		@SuppressLint("InflateParams") View view = inflater.inflate( R.layout.dialog_time_picker, null );
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
