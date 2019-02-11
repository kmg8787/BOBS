package memoque.bobs.com.memoque.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoQueDatabaseHelper extends SQLiteOpenHelper
{
	private static final String DB_NAME = "memoque";
	private static int dbVersion = 1;

	public MemoQueDatabaseHelper( Context context )
	{
		super(context, DB_NAME, null, dbVersion);
	}
	@Override
	public void onCreate( SQLiteDatabase db )
	{

	}

	@Override
	public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
	{

	}
}
