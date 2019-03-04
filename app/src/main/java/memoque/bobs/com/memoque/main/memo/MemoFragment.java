package memoque.bobs.com.memoque.main.memo;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.appdata.AppData;
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
		View view = inflater.inflate( R.layout.fragment_memo, container, false );

		RecyclerView recyclerView = view.findViewById( R.id.memo_recyclerView );
		MemoQueAdapter memoQueAdapter = new MemoQueAdapter();
		memoQueAdapter.initData();
		recyclerView.setAdapter( memoQueAdapter );
		recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

		FloatingActionButton fb = view.findViewById( R.id.memo_add_button );
		fb.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				Intent intent = new Intent( AppData.mainActivity, DetailMemoActivity.class );
				startActivity( intent );
			}
		} );

		return view;
	}
}
