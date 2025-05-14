import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private String title;
    private LocalDateTime dateTime;
    private int appId;
    private static int nextId;

    public Appointment(String title, LocalDateTime dateTime) {
        this.title = title;
        this.dateTime = dateTime;
        this.appId = nextId++;
    }

    public Appointment(String title, LocalDateTime dateTime, int appId) {
        this.title = title;
        this.dateTime = dateTime;
        this.appId = appId;
        if (appId >= nextId) {
            nextId = appId + 1;
        }
    }

    public int getAppId() {
        return appId;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return String.format("agendamento: %s -- %s", title, dateTime.format(FORMATTER));
    }

    public String toLine() {
        return title + ";" + dateTime.format(FORMATTER) + ";" + appId;
    }

    public static Appointment fromAppointment(String line) {
        String[] dataAppointment = line.split(";");
        String title = dataAppointment[0];
        LocalDateTime dateTime = LocalDateTime.parse(dataAppointment[1], FORMATTER);
        int appId = Integer.parseInt(dataAppointment[2]);
        return new Appointment(title, dateTime, appId);
    }
}
