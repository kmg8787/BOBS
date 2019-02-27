package memoque.bobs.com.memoque.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import memoque.bobs.com.memoque.main.memo.BSMemo;

public class DBManager
{
	public static final String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "memoque/databases/memoque.db";
	//		public static final String DB_PATH = "memoque.db";

	public static final String TABLE_NAME          = "memos";
	public static final String COLUMN_ID           = "number_id";
	public static final String COLUMN_TITLE        = "title";
	public static final String COLUMN_DATE         = "date";
	public static final String COLUMN_CONTENT      = "content";
	public static final String COLUMN_INDEX        = "idx";
	public static final String COLUMN_COMPLETENOTI = "completenoti";

	public static final String DB_CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_CONTENT + " TEXT, " + COLUMN_INDEX + " INTEGER, " + COLUMN_COMPLETENOTI + " TEXT)";

	private SQLiteDatabase        database = null;
	private MemoQueDatabaseHelper helper;
	private Cursor                cursor   = null;

	public DBManager( Context context )
	{
		DBContext dbContext = new DBContext( context );
		database = dbContext.openOrCreateDatabase( "memoque", Context.MODE_PRIVATE, null );
		if( database.getVersion() == 0 )
			database.execSQL( DB_CREATE_QUERY );
		//		helper = new MemoQueDatabaseHelper( context );
	}

	public List<BSMemo> getAllMemos()
	{
		//		if( helper == null )
		//			return null;
		//
		//		// 디비에 저장되어있는 메모 리스트 전체를 가져온다
		//		List<BSMemo> list = new ArrayList<>();
		//
		//		database = helper.getReadableDatabase();
		//		if( database == null )
		//			return null;

		// 디비에 저장되어있는 메모 리스트 전체를 가져온다
		List<BSMemo> list = new ArrayList<>();
		if( database == null )
			return null;

		cursor = database.query( TABLE_NAME, null, null, null, null, null, null );
		while( cursor.moveToNext() ) {
			BSMemo memo = new BSMemo();
			memo.setTitle( cursor.getString( cursor.getColumnIndex( COLUMN_TITLE ) ) );
			memo.setDate( cursor.getString( cursor.getColumnIndex( COLUMN_DATE ) ) );
			memo.setContent( cursor.getString( cursor.getColumnIndex( COLUMN_CONTENT ) ) );
			memo.setIndex( cursor.getInt( cursor.getColumnIndex( COLUMN_INDEX ) ) );
			memo.setCompleteNoti( cursor.getString( cursor.getColumnIndex( COLUMN_COMPLETENOTI ) ).equals( "Y" ) );
			memo.convertDateTime();

			list.add( memo );
		}

		return list;
	}

	public void insert( BSMemo insertmemo )
	{
		//		if( helper == null )
		//			return;
		//
		//		// 디비에 해당 메모를 저장한다
		//		database = helper.getWritableDatabase();
		if( database == null )
			return;

		ContentValues values = new ContentValues();
		values.put( COLUMN_TITLE, insertmemo.getTitle() );
		values.put( COLUMN_DATE, insertmemo.getDate() );
		values.put( COLUMN_CONTENT, insertmemo.getContent() );
		values.put( COLUMN_INDEX, insertmemo.getIndex() );
		values.put( COLUMN_COMPLETENOTI, insertmemo.isCompleteNoti() ? "Y" : "N" );

		database.insert( TABLE_NAME, null, values );

		database.setVersion( 1 );
	}

	public void update( BSMemo updatememo )
	{
		//		if( helper == null )
		//			return;
		//
		//		// 디비에 해당 메모내용을 갱신한다
		//		database = helper.getWritableDatabase();
		if( database == null )
			return;

		ContentValues values = new ContentValues();
		values.put( COLUMN_TITLE, updatememo.getTitle() );
		values.put( COLUMN_DATE, updatememo.getDate() );
		values.put( COLUMN_CONTENT, updatememo.getContent() );
		values.put( COLUMN_INDEX, updatememo.getIndex() );
		values.put( COLUMN_COMPLETENOTI, updatememo.isCompleteNoti() ? "Y" : "N" );

		database.update( TABLE_NAME, values, COLUMN_INDEX + "=" + updatememo.getIndex(), null );
	}

	public void delete( BSMemo deletememo )
	{
		//		if( helper == null )
		//			return;
		//
		//		// 디비에서 해당 메모를 삭제한다
		//		database = helper.getWritableDatabase();
		if( database == null )
			return;

		database.delete( TABLE_NAME, COLUMN_INDEX + "=" + deletememo.getIndex(), null );
	}
}
