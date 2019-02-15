package memoque.bobs.com.memoque.main

import android.database.sqlite.SQLiteDatabase
import memoque.bobs.com.memoque.main.memo.MemoModel
import java.util.*

class MemoQueManager private constructor() {

    private val mainDB: SQLiteDatabase? = null
    private val dbHelper: MemoQueDatabaseHelper? = null

    private val memos = hashMapOf<Int, MemoModel>()

    companion object {
        val instance = MemoQueManager()
    }

    val memoList: List<MemoModel>
        get() {
            return listSort(null)
        }

    fun add(memo: MemoModel) {
        memos[memo.index] = memo
    }

    fun remove(key: Int) {
        memos.remove(key)
    }

    fun memosSearch(filterText: String): List<MemoModel> {
        return listSort(memos.filter { it.value.title.contains(filterText) || it.value.content.contains(filterText) || it.value.date.contains(filterText) })
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