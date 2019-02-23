package memoque.bobs.com.memoque.main.adapter

import memoque.bobs.com.memoque.main.MemoQueManager

class SearchAdapter : MemoQueAdapter(){
    override fun initData() {
        // 매니져에 검색탭용 어뎁터를 세팅한다
        MemoQueManager.instance.setAdapterListener(MemoQueManager.Adapterkey.SEARCH, this)
    }
}