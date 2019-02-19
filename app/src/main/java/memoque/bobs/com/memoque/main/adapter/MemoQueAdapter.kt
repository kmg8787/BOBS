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
        MemoQueManager.instance.setAdapterListener(MemoQueManager.Adapterkey.MEMO, this)
        BSMemoList = MemoQueManager.instance.getMemos()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val cardView = LayoutInflater.from(viewGroup.context).inflate(R.layout.recycler_view_row, viewGroup, false) as CardView
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
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
        return if (null == BSMemoList) 0 else BSMemoList!!.size

    }

    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun refreshAll() {
        BSMemoList = MemoQueManager.instance.getMemos()
        notifyDataSetChanged()
    }
    override fun refreshToIndex(index: Int?) {
        if (index != null)
            notifyItemChanged(index)
    }

    override fun addToIndex(index: Int?) {
        BSMemoList = MemoQueManager.instance.getMemos()

        if (index != null)
            notifyItemInserted(index)
    }

    override fun removeToIndex(index: Int?) {
        BSMemoList = MemoQueManager.instance.getMemos()

        if (index != null)
            notifyItemRemoved(index)
    }

    override fun searchMemos(BSMemos: List<BSMemo>?) {
        BSMemoList = BSMemos
        notifyDataSetChanged()
    }
}
