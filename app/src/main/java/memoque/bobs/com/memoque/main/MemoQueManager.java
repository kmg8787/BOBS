package memoque.bobs.com.memoque.main;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import memoque.bobs.com.memoque.main.memo.MemoModel;

public class MemoQueManager
{
	private static MemoQueManager instance = new MemoQueManager();

	public static MemoQueManager getInstance()
	{
		return instance;
	}

	private SQLiteDatabase        mainDB   = null;
	private MemoQueDatabaseHelper dbHelper = null;

	public static int INDEX = 0;

	private Map<Integer, MemoModel> memos = new HashMap<>();

	private MemoQueManager()
	{
		setTestMemos();
	}

	private void setTestMemos()
	{
		for(int i = 0; i < 100; ++i) {
			MemoModel memo = new MemoModel();
			memo.setTestData();

			memos.put( memo.getIndex(), memo );
		}
	}

	public List<MemoModel> getMemoList()
	{
		synchronized( this ) {
			List<MemoModel> list = new ArrayList<>( memos.values() );
			Collections.sort( list, new Comparator<MemoModel>()
			{
				@Override
				public int compare( MemoModel o1, MemoModel o2 )
				{
					if(o1.getIndex() < o2.getIndex())
						return -1;
					else if(o1.getIndex() > o2.getIndex())
						return 1;
					return 0;
				}
			} );
			return list;
		}
	}
}
