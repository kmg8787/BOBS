package memoque.bobs.com.memoque.main

import android.database.sqlite.SQLiteDatabase
import memoque.bobs.com.memoque.main.adapter.IAdapter
import memoque.bobs.com.memoque.main.memo.MemoModel
import java.util.*

class MemoQueManager private constructor() {

    enum class Adapterkey{
        MEMO,SEARCH
    }

    private val mainDB: SQLiteDatabase? = null
    private val dbHelper: MemoQueDatabaseHelper? = null

    private val memos = hashMapOf<Int, MemoModel>()
    private val adapterListeners = hashMapOf<Adapterkey, IAdapter>()

    companion object {
        val instance = MemoQueManager()
    }

    val memoList: List<MemoModel>
        get() {
            return listSort(null)
        }

    fun setAdapterListener(key: Adapterkey, listener:IAdapter)
    {
        adapterListeners[key] = listener
    }

    fun add(key: Adapterkey, memo: MemoModel) {
        memos[memo.index] = memo
        adapterListeners[key]?.addToIndex(memo.index + 1)
    }

    fun remove(key: Adapterkey, index: Int) {
        val memo = memos.remove(index)
        adapterListeners[key]?.removeToIndex(memo?.index)
    }

    fun memosSearch(key: Adapterkey, filterText: String) {
        val searchMemos = listSort(memos.filter { it.value.title.contains(filterText) || it.value.content.contains(filterText) || it.value.date.contains(filterText) })
        adapterListeners[key]?.searchMemos(searchMemos)
    }

    fun listSort(map: Map<Int, MemoModel>?): List<MemoModel> {
        val list = ArrayList(map?.values ?: memos.values)
        Collections.sort(list, Comparator { o1, o2 ->
            if (o1.index < o2.index)
                return@Comparator -1
            else if (o1.index > o2.index)
                return@Comparator 1
            0
        })

        return list
    }

    fun getMemoIndex(): Int {
        return if (memos.size == 0) 0 else memos.size
    }
}