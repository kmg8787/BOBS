package memoque.bobs.com.memoque.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import memoque.bobs.com.memoque.R
import memoque.bobs.com.memoque.appdata.AppData
import memoque.bobs.com.memoque.main.MemoQueManager
import memoque.bobs.com.memoque.main.adapter.MemoQueAdapter.ViewHolder
import memoque.bobs.com.memoque.main.memo.BSMemo
import memoque.bobs.com.memoque.main.memo.DetailMemoActivity

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
        val memo = BSMemoList!![i]

        val title = cardView.findViewById<TextView>(R.id.memo_title)
        title.text = memo.title

        val content = cardView.findViewById<TextView>(R.id.memo_content)
        content.text = memo.content

        val date = cardView.findViewById<TextView>(R.id.memo_date)
        date.text = memo.date

        cardView.setOnClickListener { run {
            val intent = Intent(AppData.mainActivity, DetailMemoActivity::class.java)
            intent.putExtra(DetailMemoActivity.MEMO_INDEX, memo.index)
            intent.putExtra(DetailMemoActivity.TAB_KEY, getAdapterKey())
            AppData.mainActivity.startActivity(intent)
        } }
    }

    open fun getAdapterKey() : MemoQueManager.Adapterkey{
        return MemoQueManager.Adapterkey.MEMO
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

    override fun searchMemos(BSMemos: List<BSMemo>?, searchtext: String) {
        // 검색탭 메모 리스트 갱신
        BSMemoList = BSMemos
        notifyDataSetChanged()
    }

    override fun clear() {
        if (BSMemoList == null || BSMemoList!!.isEmpty())
            return

        BSMemoList = mutableListOf()
        notifyDataSetChanged()
    }
}
