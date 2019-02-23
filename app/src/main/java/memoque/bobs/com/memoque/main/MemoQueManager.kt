package memoque.bobs.com.memoque.main

import android.annotation.SuppressLint
import android.app.Activity
import memoque.bobs.com.memoque.db.DBManager
import memoque.bobs.com.memoque.main.adapter.IAdapter
import memoque.bobs.com.memoque.main.memo.BSMemo
import java.util.*

class MemoQueManager private constructor() {

    enum class Adapterkey {
        MEMO, SEARCH
    }

    var databaseManager: DBManager? = null
    private var memos = mutableListOf<BSMemo>()
    private val adapterListeners = hashMapOf<Adapterkey, IAdapter>()

    companion object {
        @SuppressLint("StaticFieldLeak")
        val instance = MemoQueManager()
    }

    fun setDatabase(activity: Activity) {
        // 디비를 세팅하고 디비에 저장되어있는 메모리스트를 가져온다
        databaseManager = DBManager(activity)
        memos = databaseManager!!.allMemos
    }

    fun setAdapterListener(key: Adapterkey, listener: IAdapter) {
        // 탭별 리사이클러뷰 어뎁터를 세팅한다
        adapterListeners[key] = listener
    }

    fun add(key: Adapterkey, BSMemo: BSMemo, index: Int) {
        // 메모 추가
        memos.add(BSMemo)
        adapterListeners[key]?.addToIndex(index)
        databaseManager?.insert(BSMemo)
    }

    fun update(key: MemoQueManager.Adapterkey, index: Int) {
        // 메모 업데이트
        when (key) {
            Adapterkey.MEMO -> adapterListeners[key]?.refreshToIndex(index)
            Adapterkey.SEARCH -> {
                adapterListeners[key]?.refreshToIndex(index)
                adapterListeners[Adapterkey.MEMO]?.refreshAll()
            }
        }

        databaseManager?.update(memos[index])
    }

    fun update(memo: BSMemo){
        databaseManager?.update(memo)
    }

    fun remove(key: MemoQueManager.Adapterkey, index: Int) {
        // 메모 삭제
        databaseManager?.delete(memos.removeAt(index))

        when (key) {
            Adapterkey.MEMO ->
                adapterListeners[key]?.removeToIndex(index)
            Adapterkey.SEARCH -> {
                adapterListeners[key]?.removeToIndex(index)
                adapterListeners[Adapterkey.MEMO]?.refreshAll()
            }
        }
    }

    fun memosSearch(key: MemoQueManager.Adapterkey, filterText: String): Boolean {
        // 메모 검색
        if (memos.size == 0)
            return false
        else {
            listSort()
            adapterListeners[key]?.searchMemos(memos.filter { it.title.contains(filterText) || it.content.contains(filterText) || it.date.contains(filterText) })
        }

        return true
    }

    private fun listSort() {
        // 메모 리스트를 인덱스값 기준으로 오름차순 정렬한다
        if (memos.isEmpty())
            return

        Collections.sort(memos, Comparator { o1, o2 ->
            if (o1.index < o2.index)
                return@Comparator -1
            else if (o1.index > o2.index)
                return@Comparator 1
            0
        })
    }

    fun getMemo(index: Int): BSMemo? {
        // 메모 리스트 정렬 후 리턴한다
        listSort()
        return memos[index]
    }

    fun getMemoIndex(): Int {
        // 다음 메모 인덱스를 리턴해야하므로 마지막 메모의 인덱스 +1 값을 리턴한다
        var index = 0

        if (memos.size > 0) {
            listSort()
            index = (memos[memos.size - 1].index + 1)
        }

        return index
    }

    fun getMemos(): MutableList<BSMemo> {
        // 메모리스트 리턴
        listSort()
        return memos
    }

    fun getMemosSize(): Int {
        // 메모리스트 크기 리턴
        return memos.size
    }
}