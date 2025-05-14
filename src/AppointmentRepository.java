import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {
    private static final File APPOINT_FILE = new File("appointments.txt");

    public void registerAppointment(Appointment appointment) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINT_FILE, true))) {
            bw.write(appointment.toLine());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("não foi possível registrar o agendamento");
        }
    }

    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                appointments.add(Appointment.fromAppointment(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println("arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("não foi possível listar agendamentos");        }
        return appointments;
    }

    public void updateAppointment(Appointment toUpdateAppointment) {
        List<Appointment> appointments = findAll();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINT_FILE))) {
            for (Appointment a : appointments) {
                if (a.getAppId() == toUpdateAppointment.getAppId()) {
                    bw.write(toUpdateAppointment.toLine());
                } else {
                    bw.write(a.toLine());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("não foi possível atualizar agendamento");
        }
    }

    public void deleteById(int toDeleteId) {
        List<Appointment> appointments = findAll();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINT_FILE))) {
            for (Appointment a : appointments) {
                if (a.getAppId() != toDeleteId) {
                    bw.write(a.toLine());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("não foi possível excluir agendamento");
        }
    }

    public Appointment findById(int id) {
        for (Appointment a : findAll()) {
            if (a.getAppId() == id) {
                return a;
            }
        }
        return null;
    }
}
