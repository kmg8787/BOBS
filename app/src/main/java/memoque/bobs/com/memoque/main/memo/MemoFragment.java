package memoque.bobs.com.memoque.main.memo;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
