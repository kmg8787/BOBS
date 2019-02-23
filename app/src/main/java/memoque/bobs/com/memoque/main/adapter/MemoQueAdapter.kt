package memoque.bobs.com.memoque.main.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import memoque.bobs.com.memoque.R
import memoque.bobs.com.memoque.main.MemoQueManager
import memoque.bobs.com.memoque.main.adapter.MemoQueAdapter.ViewHolder
import memoque.bobs.com.memoque.main.memo.BSMemo

open class MemoQueAdapter : RecyclerView.Adapter<ViewHolder>(), IAdapter {

    protected var BSMemoList: List<BSMemo>? = null

    open fun initData() {
        // 매니져에 어뎁터를 세팅하고 메모리스트를 가져온다
        MemoQueManager.instance.setAdapterListener(MemoQueManager.Adapterkey.MEMO, this)
        BSMemoList = MemoQueManager.instance.getMemos()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val cardView = LayoutInflater.from(viewGroup.context).inflate(R.layout.recycler_view_row, viewGroup, false) as CardView
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        // 리사이클러뷰 카드뷰별 세팅
        val cardView = viewHolder.cardView
        val (_, _,title1, content1, date1) = BSMemoList!![i]

        val title = cardView.findViewById<TextView>(R.id.memo_title)
        title.text = title1

        val content = cardView.findViewById<TextView>(R.id.memo_content)
        content.text = content1

        val date = cardView.findViewById<TextView>(R.id.memo_date)
        date.text = date1
    }

    override fun getItemCount(): Int {
        // 리사이클러뷰 카드뷰 개수 리턴
        return if (null == BSMemoList) 0 else BSMemoList!!.size

    }

    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun refreshAll() {
        // 전체 메모 리스트 카드뷰 갱신
        BSMemoList = MemoQueManager.instance.getMemos()
        notifyDataSetChanged()
    }
    override fun refreshToIndex(index: Int?) {
        // 해당 인덱스 카드뷰 갱신
        if (index != null)
            notifyItemChanged(index)
    }

    override fun addToIndex(index: Int?) {
        // 메모 추가
        BSMemoList = MemoQueManager.instance.getMemos()

        if (index != null)
            notifyItemInserted(index)
    }

    override fun removeToIndex(index: Int?) {
        // 메모 삭제
        BSMemoList = MemoQueManager.instance.getMemos()

        if (index != null)
            notifyItemRemoved(index)
    }

    override fun searchMemos(BSMemos: List<BSMemo>?) {
        // 검색탭 메모 리스트 갱신
        BSMemoList = BSMemos
        notifyDataSetChanged()
    }
}
