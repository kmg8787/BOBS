package memoque.bobs.com.memoque.main.memo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.main.adapter.MemoQueAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoFragment extends Fragment
{
	@Override
	public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		// Inflate the layout for this fragment
		RecyclerView recyclerView = (RecyclerView)inflater.inflate( R.layout.fragment_memo, container, false );

		MemoQueAdapter memoQueAdapter = new MemoQueAdapter();
		memoQueAdapter.initData();
		recyclerView.setAdapter( memoQueAdapter );
		recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

		return recyclerView;
	}
}
