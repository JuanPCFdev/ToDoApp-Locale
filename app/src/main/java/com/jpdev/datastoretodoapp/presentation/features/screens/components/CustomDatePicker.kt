import android.content.Context
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

fun showDatePickerWrapper(context: Context, onDueDateChange: (Long) -> Unit) {
    showDatePicker(
        context = context,
        onDueDateChange = { dateString ->
            val timestamp = dateStringToTimestamp(dateString)
            onDueDateChange(timestamp)
        }
    )
}

private fun showDatePicker(
    context: Context,
    onDueDateChange: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    val minDate = Calendar.getInstance().timeInMillis

    android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth + 1)
            onDueDateChange(formattedDate)
        },
        currentYear,
        currentMonth,
        currentDay
    ).apply {
        datePicker.minDate = minDate
    }.show()
}

private fun dateStringToTimestamp(dateString: String): Long {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        sdf.parse(dateString)?.time ?: 0L
    } catch (e: ParseException) {
        0L
    }
}