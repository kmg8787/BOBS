package memoque.bobs.com.memoque.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoQueDatabaseHelper extends SQLiteOpenHelper
{
	private static final int DB_VERSION = 1;

	public MemoQueDatabaseHelper( Context context )
	{
		super(context, DBManager.DB_PATH, null, DB_VERSION);
	}

	@Override
	public void onCreate( SQLiteDatabase db )
	{
		db.execSQL( DBManager.DB_CREATE_QUERY );
	}

	@Override
	public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
	{
		db.execSQL( "DROP TABLE IF EXISTS " + DBManager.TABLE_NAME );
		onCreate( db );
	}
}
