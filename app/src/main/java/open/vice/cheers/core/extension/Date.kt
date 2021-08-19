package open.vice.cheers.core.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.toDate(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return LocalDate.parse("01/$this", formatter)
}

fun LocalDate.toDateFormatted(): String {
    return this.format(DateTimeFormatter.ofPattern("MM/yyyy"))
}

fun String.fillDateWithSlash(): String = "${this.substring(0, 2)}/${this.substring(2, this.length)}"