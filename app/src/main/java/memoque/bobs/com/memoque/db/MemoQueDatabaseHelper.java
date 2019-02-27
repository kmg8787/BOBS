package memoque.bobs.com.memoque.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoQueDatabaseHelper extends SQLiteOpenHelper
{
	public MemoQueDatabaseHelper( Context context )
	{
		super(context, DBManager.DB_PATH, null, 1);
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
