package memoque.bobs.com.memoque.main.memo;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.main.MemoQueManager.Adapterkey;
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
		recyclerView.addOnItemTouchListener( new OnItemTouchListener()
		{
			@Override
			public boolean onInterceptTouchEvent( @NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent )
			{
				if( motionEvent.getAction() == MotionEvent.ACTION_DOWN ) {
					// 터치한 메모 수정 액티비티를 띄운다
					View childview = recyclerView.findChildViewUnder( motionEvent.getX(), motionEvent.getY() );
					if( childview != null ) {
						int currentPosition = recyclerView.getChildAdapterPosition( childview );

						Intent intent = new Intent( getContext(), DetailMemoActivity.class );
						intent.putExtra( DetailMemoActivity.MEMO_INDEX, currentPosition );
						intent.putExtra( DetailMemoActivity.TAB_KEY, Adapterkey.MEMO );
						startActivity( intent );
					}
			}
				return false;
			}

			@Override
			public void onTouchEvent( @NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent )
			{

			}

			@Override
			public void onRequestDisallowInterceptTouchEvent( boolean b )
			{

			}
		} );

		return recyclerView;
	}
}
