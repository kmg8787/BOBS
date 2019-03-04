package memoque.bobs.com.memoque.main.memo

import android.text.TextUtils
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

// 메모 데이터 클래스
data class BSMemo(var index: Int = 0, var title: String = "", var content: String = "", var date: String = "", var dateTime: DateTime? = null, var isCompleteNoti: Boolean = false)  {
    fun checkAndSetDate(newdate: String) {
        if (!TextUtils.isEmpty(date)) {
            val curdateTime = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm").parseDateTime(date)
            val newdateTime = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm").parseDateTime(newdate)

            if (!curdateTime.isEqual(newdateTime))
                isCompleteNoti = false
        }

        date = newdate
    }

    fun convertDateTime() {
        // 조다타임 클래스를 생성한다
        if (!TextUtils.isEmpty(date)) {
            dateTime = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm").parseDateTime(date)
        }
    }
}
