package memoque.bobs.com.memoque.main.adapter

import memoque.bobs.com.memoque.main.MemoQueManager
import memoque.bobs.com.memoque.main.memo.BSMemo

class SearchAdapter : MemoQueAdapter() {
    var searchText = ""

    override fun initData() {
        // 매니져에 검색탭용 어뎁터를 세팅한다
        MemoQueManager.instance.setAdapterListener(MemoQueManager.Adapterkey.SEARCH, this)
    }

    override fun getAdapterKey(): MemoQueManager.Adapterkey {
        return MemoQueManager.Adapterkey.SEARCH
    }

    override fun searchMemos(BSMemos: List<BSMemo>?, searchtext: String) {
        super.searchMemos(BSMemos, searchtext)
        searchText = searchtext
    }

    override fun refreshAll() {
        BSMemoList = MemoQueManager.instance.filterMemos(searchText)
        notifyDataSetChanged()
    }
}