import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.jpdev.datastoretodoapp.presentation.features.screens.utils.convertToEndOfDayLocal
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
fun showDatePickerWrapper(context: Context, onDueDateChange: (Long) -> Unit) {
    showDatePicker(
        context = context,
        onDueDateChange = { year, month, day ->
            val endOfDayMillis = convertToEndOfDayLocal(year, month, day)
            onDueDateChange(endOfDayMillis)
        }
    )
}

private fun showDatePicker(
    context: Context,
    onDueDateChange: (year:Int, month:Int, day:Int) -> Unit,
) {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDueDateChange(year,month,dayOfMonth)
        },
        currentYear,
        currentMonth,
        currentDay
    ).apply {
        datePicker.minDate = System.currentTimeMillis() - 1000
    }.show()
}