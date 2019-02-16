package memoque.bobs.com.memoque.main.adapter

import memoque.bobs.com.memoque.main.MemoQueManager

class SearchAdapter : MemoQueAdapter(){
    override fun initData() {
        MemoQueManager.instance.setAdapterListener(MemoQueManager.Adapterkey.SEARCH, this)
    }
}