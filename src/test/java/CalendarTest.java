import java.util.Calendar;

public class CalendarTest {
    public static void main(String[] args) {
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        int second = Calendar.getInstance().get(Calendar.SECOND);
        System.out.println(minute + ", " + second);
    }
}
