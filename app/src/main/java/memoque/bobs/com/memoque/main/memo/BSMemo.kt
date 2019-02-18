package memoque.bobs.com.memoque.main.memo

import android.text.TextUtils
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

//class BSMemo {
//    var index: Int = 0
//    var title: String? = null
//    var content: String? = null
//    var date: String? = null
//    var isPush: Boolean = false
//    var memoBgColor: Int = 0
//
//    constructor()
//
//    constructor(index: Int, title: String, content: String, date: String, push: Boolean, bgcolor: Int) {
//        setAllData(index, title, content, date, push, bgcolor)
//    }
//
//    fun setAllData(index: Int, title: String, content: String, date: String, push: Boolean, bgcolor: Int) {
//        this.index = index
//        this.title = title
//        this.content = content
//        this.date = date
//        isPush = push
//        memoBgColor = bgcolor
//    }
//}

data class BSMemo(var id: Int = 0, var index: Int = 0, var title: String = "", var content: String = "", var date: String = "", var dateTime : DateTime? = null, var isCompleteNoti : Boolean = false){
    fun convertDateTime()
    {
        if(!TextUtils.isEmpty(date)) {
            dateTime = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm").parseDateTime(date)
        }
    }
}
