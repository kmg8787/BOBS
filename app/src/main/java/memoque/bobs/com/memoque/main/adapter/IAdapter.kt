package memoque.bobs.com.memoque.main.adapter

import memoque.bobs.com.memoque.main.memo.BSMemo

// 데이터 매니져 클래스에서 사용할 리사이클러뷰 어뎁터 인터페이스
interface IAdapter {
    fun refreshAll()
    fun refreshToIndex(index: Int?)
    fun addToIndex(index: Int?)
    fun removeToIndex(index: Int?)
    fun searchMemos(BSMemos : List<BSMemo>?)
}
