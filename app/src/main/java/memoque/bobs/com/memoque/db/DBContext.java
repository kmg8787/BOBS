package memoque.bobs.com.memoque.db;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

public class DBContext extends ContextWrapper
{
	public DBContext( Context base )
	{
		super( base );
	}

	@Override
	public File getDatabasePath( String name )
	{
		File sdcard = Environment.getExternalStorageDirectory();
		String dbfile = sdcard.getAbsolutePath() + File.separator + "memoque/databases" + File.separator + name;
		if( !dbfile.endsWith( ".db" ) ) {
			dbfile += ".db";
		}

		File result = new File( dbfile );

		if( !result.getParentFile().exists() ) {
			result.getParentFile().mkdirs();
		}

		return result;
	}

	/* this version is called for android devices >= api-11. thank to @damccull for fixing this. */
	@Override
	public SQLiteDatabase openOrCreateDatabase( String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler )
	{
		return openOrCreateDatabase( name, mode, factory );
	}

	/* this version is called for android devices < api-11 */
	@Override
	public SQLiteDatabase openOrCreateDatabase( String name, int mode, SQLiteDatabase.CursorFactory factory )
	{
		// SQLiteDatabase result = super.openOrCreateDatabase(name, mode, factory);
		return SQLiteDatabase.openOrCreateDatabase( getDatabasePath( name ), null );
	}
}
