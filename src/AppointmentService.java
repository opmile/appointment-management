import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppointmentService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final AppointmentRepository repository = new AppointmentRepository();

    public boolean saveAppointment(Appointment appointment) {
        if (repository.findById(appointment.getAppId()) != null) {
            System.out.println("agendamento já cadastrado no sistema");
            return false;
        }
        repository.registerAppointment(appointment);
        return true;
    }

    public boolean updateAppointment(Appointment appointment) {
        if (repository.findById(appointment.getAppId()) == null) {
            System.out.println("agendamento não encontrado no sistema");
            return false;
        }
        repository.updateAppointment(appointment);
        return true;
    }

    public boolean deleteAppointment(int toDeleteId) {
        if (repository.findById(toDeleteId) == null) {
            System.out.println("agendamento não encontrado no sistema");
            return false;
        }
        repository.deleteById(toDeleteId);
        return true;
    }

    public void listAppointments() {
        for (Appointment a : repository.findAll()) {
            System.out.println(a.getTitle() + " -- " + a.getDateTime().format(FORMATTER) + " id: " + a.getAppId());
        }
    }

    public void showDeadlines() {
        if (repository.findAll().isEmpty()) {
            System.out.println("nenhum agendamento previsto");
        }

        LocalDateTime now = LocalDateTime.now();
        for (Appointment a : repository.findAll()) {
            Duration duration = Duration.between(now, a.getDateTime());
            long days = duration.toDays();
            long hours = duration.toHours() % 24;
            System.out.printf("%s -- %d dias e %d horas", a.getTitle(), days, hours);
            System.out.println(" ");
        }
    }
}
