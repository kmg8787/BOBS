package memoque.bobs.com.memoque.main.search;


import android.os.Bundle;
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
public class SearchFragment extends Fragment
{
	public SearchFragment()
	{
		// Required empty public constructor
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		// Inflate the layout for this fragment
		View view = inflater.inflate( R.layout.fragment_search, container, false );
		if( null == view )
			return null;
		RecyclerView recyclerView = view.findViewById( R.id.search_recyclerView );
		recyclerView.setAdapter( new MemoQueAdapter() );
		recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

		return view;
	}

}
