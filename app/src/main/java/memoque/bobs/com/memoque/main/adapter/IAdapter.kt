package memoque.bobs.com.memoque.main.adapter

import memoque.bobs.com.memoque.main.memo.BSMemo

interface IAdapter {
    fun refreshAll()
    fun refreshToIndex(index: Int?)
    fun addToIndex(index: Int?)
    fun removeToIndex(index: Int?)
    fun searchMemos(BSMemos : List<BSMemo>?)
}
