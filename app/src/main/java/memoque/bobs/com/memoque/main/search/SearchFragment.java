package memoque.bobs.com.memoque.main.search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.main.MemoQueManager;
import memoque.bobs.com.memoque.main.MemoQueManager.Adapterkey;
import memoque.bobs.com.memoque.main.adapter.SearchAdapter;

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

		final EditText searchEdit = view.findViewById( R.id.search_editText );

		Button searchbutton = view.findViewById( R.id.btn_search );
		searchbutton.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				String searchText = searchEdit.getText().toString();

				if( TextUtils.isEmpty( searchText) )
					Toast.makeText( getContext(), R.string.search_empty_text, Toast.LENGTH_SHORT ).show();
				else
					MemoQueManager.Companion.getInstance().memosSearch( Adapterkey.SEARCH, searchText );
			}
		} );

		RecyclerView recyclerView = view.findViewById( R.id.search_recyclerView );

		SearchAdapter searchAdapter = new SearchAdapter();
		searchAdapter.initData();
		recyclerView.setAdapter( searchAdapter );
		recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

		return view;
	}

}
