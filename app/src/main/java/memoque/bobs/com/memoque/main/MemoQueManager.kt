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

    var memoQueActivity: Activity? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        val instance = MemoQueManager()
    }

    fun setDatabase(activity: Activity) {
        memoQueActivity = activity
        databaseManager = DBManager(activity)
        memos = databaseManager!!.allMemos
    }

    fun setAdapterListener(key: Adapterkey, listener: IAdapter) {
        adapterListeners[key] = listener
    }

    fun add(key: Adapterkey, BSMemo: BSMemo, index: Int) {
        memos.add(BSMemo)
        adapterListeners[key]?.addToIndex(index)
        databaseManager?.insert(BSMemo)
    }

    fun update(key: MemoQueManager.Adapterkey, index: Int) {
        when (key) {
            Adapterkey.MEMO -> adapterListeners[key]?.refreshToIndex(index)
            Adapterkey.SEARCH -> {
                adapterListeners[key]?.refreshToIndex(index)
                adapterListeners[Adapterkey.MEMO]?.refreshAll()
            }
        }

        databaseManager?.update(memos[index])
    }

    fun remove(key: MemoQueManager.Adapterkey, index: Int) {
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
        if (memos.size == 0)
            return false
        else {
            listSort()
            adapterListeners[key]?.searchMemos(memos.filter { it.title.contains(filterText) || it.content.contains(filterText) || it.date.contains(filterText) })
        }

        return true
    }

    fun listSort() {
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
        listSort()
        return memos[index]
    }

    fun getMemoIndex(): Int {
        var index = 0

        if (memos.size > 0) {
            listSort()
            index = (memos[memos.size - 1].index + 1)
        }

        return index
    }

    fun getMemos(): MutableList<BSMemo> {
        listSort()
        return memos
    }

    fun getMemosSize(): Int {
        return memos.size
    }
}