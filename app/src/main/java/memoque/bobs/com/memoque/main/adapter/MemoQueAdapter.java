package memoque.bobs.com.memoque.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.main.MemoQueManager;
import memoque.bobs.com.memoque.main.adapter.MemoQueAdapter.ViewHolder;
import memoque.bobs.com.memoque.main.memo.MemoModel;

public class MemoQueAdapter extends RecyclerView.Adapter<ViewHolder>
{
	private List<MemoModel> memoModelList;

	public MemoQueAdapter()
	{
		memoModelList = MemoQueManager.Companion.getInstance().getMemoList();
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder( @NonNull ViewGroup viewGroup, int i )
	{
		CardView cardView = (CardView)LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.recycler_view_row, viewGroup, false );
		return new ViewHolder( cardView );
	}

	@Override
	public void onBindViewHolder( @NonNull ViewHolder viewHolder, int i )
	{
		CardView cardView = viewHolder.cardView;
		MemoModel memoData = memoModelList.get( i );

		TextView title = cardView.findViewById( R.id.memo_title );
		title.setText( memoData.getTitle() );

		TextView content = cardView.findViewById( R.id.memo_content );
		content.setText( memoData.getContent() );

		TextView date = cardView.findViewById( R.id.memo_date );
		date.setText( memoData.getDate() );
	}

	@Override
	public int getItemCount()
	{
		if( null == memoModelList )
			return 0;

		return memoModelList.size();
	}

	static class ViewHolder extends RecyclerView.ViewHolder
	{
		private CardView cardView;

		ViewHolder( CardView v )
		{
			super( v );
			cardView = v;
		}
	}
}
