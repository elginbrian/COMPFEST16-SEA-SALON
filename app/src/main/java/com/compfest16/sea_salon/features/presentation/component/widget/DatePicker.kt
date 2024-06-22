import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DatePicker(onDateSelected: (String) -> Unit, onDismiss: () -> Unit, onDateChanged: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    LaunchedEffect(Unit) {
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                val format = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val selectedDate = format.format(calendar.time)
                onDateSelected(selectedDate)
                onDateChanged(selectedDate)
            },
            year, month, day
        ).apply {
            setOnDismissListener { onDismiss() }
            show()
        }
    }
}

@Composable
fun TimePicker(onTimeSelected: (String) -> Unit, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    LaunchedEffect(Unit) {
        android.app.TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                calendar.set(Calendar.MINUTE, selectedMinute)
                val format = SimpleDateFormat("(HH:mm)", Locale.getDefault())
                val selectedTime = format.format(calendar.time)
                onTimeSelected(selectedTime)
            },
            hour, minute, true
        ).apply {
            setOnDismissListener { onDismiss() }
            show()
        }
    }
}
