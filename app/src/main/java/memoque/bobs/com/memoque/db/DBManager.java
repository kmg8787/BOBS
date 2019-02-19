package memoque.bobs.com.memoque.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import memoque.bobs.com.memoque.main.memo.BSMemo;

public class DBManager
{
	public static final String DB_PATH = "memoque.db";

	public static final String TABLE_NAME     = "memos";
	public static final String COLUMN_ID      = "_id";
	public static final String COLUMN_TITLE   = "title";
	public static final String COLUMN_DATE    = "date";
	public static final String COLUMN_CONTENT = "content";
	public static final String COLUMN_INDEX   = "idx";

	public static final String DB_CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_CONTENT + " TEXT, " + COLUMN_INDEX + " INTEGER)";

	private SQLiteDatabase        database = null;
	private MemoQueDatabaseHelper helper   = null;
	private Cursor                cursor   = null;

	public DBManager( Context context )
	{
		helper = new MemoQueDatabaseHelper( context );
	}

	public List<BSMemo> getAllMemos()
	{
		List<BSMemo> list = new ArrayList<>();

		database = helper.getReadableDatabase();
		cursor = database.query( TABLE_NAME, null, null, null, null, null, null );
		while( cursor.moveToNext() ) {
			BSMemo memo = new BSMemo();
			memo.setId( cursor.getInt( cursor.getColumnIndex( COLUMN_ID ) ) );
			memo.setTitle( cursor.getString( cursor.getColumnIndex( COLUMN_TITLE ) ) );
			memo.setDate( cursor.getString( cursor.getColumnIndex( COLUMN_DATE ) ) );
			memo.setContent( cursor.getString( cursor.getColumnIndex( COLUMN_CONTENT ) ) );
			memo.setIndex( cursor.getInt( cursor.getColumnIndex( COLUMN_INDEX ) ) );
			memo.convertDateTime();

			list.add( memo );
		}

		return list;
	}

	public void insert( BSMemo insertmemo )
	{
		database = helper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put( COLUMN_TITLE, insertmemo.getTitle() );
		values.put( COLUMN_DATE, insertmemo.getDate() );
		values.put( COLUMN_CONTENT, insertmemo.getContent() );
		values.put( COLUMN_INDEX, insertmemo.getIndex() );

		database.insert( TABLE_NAME, null, values );
	}

	public void update( BSMemo updatememo )
	{
		database = helper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put( COLUMN_TITLE, updatememo.getTitle() );
		values.put( COLUMN_DATE, updatememo.getDate() );
		values.put( COLUMN_CONTENT, updatememo.getContent() );
		values.put( COLUMN_INDEX, updatememo.getIndex() );

		database.update( TABLE_NAME, values, COLUMN_ID + "=" + updatememo.getId(), null );
	}

	public void delete( BSMemo deletememo )
	{
		database = helper.getWritableDatabase();
		database.delete( TABLE_NAME, COLUMN_ID + "=" + deletememo.getId(), null );
	}
}
