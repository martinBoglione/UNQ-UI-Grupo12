package api

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FormatTime {

    fun format(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyy - hh:mm")
        return date.format(formatter)
    }

}