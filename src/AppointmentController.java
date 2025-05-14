import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AppointmentController {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final Scanner scanner = new Scanner(System.in);
    private static final AppointmentService service = new AppointmentService();
    private static final String ADMIN_PASSCODE = "milena27";

    public void run() {
        int option = 6;
        do {
            try {
                System.out.println("""
                    --- sistema de agendamentos ---
                    
                    1 - novo agendamento
                    2 - listar agendamentos
                    3 - exibir prazos
                    4 - atualizar agendamento
                    5 - excluir agendamento
                    0 - sair
                    
                    -------------------------------
                    """);
                option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1 -> create();
                    case 2 -> list();
                    case 3 -> deadlines();
                    case 4 -> update();
                    case 5 -> delete();
                }
            } catch (NumberFormatException e) {
                System.out.println("entrada não permitida");
            }
        } while (option != 0);
    }

    public static void create() {
        System.out.println("nome do compromisso: ");
        String title = scanner.nextLine();
        System.out.println("digite a data e hora para o compromisso: (pattern: dd/MM/yyyy HH:mm)");
        String dateTimeStr = scanner.nextLine();


        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, FORMATTER);
            service.saveAppointment(new Appointment(title, dateTime));
            System.out.println("novo agendamento criado com sucesso!");
        } catch (DateTimeParseException e) {
            System.out.println("erro de formatação de data e hora");
        }
    }

    public static void list() {
        System.out.println("--- agendamentos cadastrados ---");
        service.listAppointments();
        System.out.println("-------------------------------");
    }

    public static void deadlines() {
        System.out.println("---------- deadlines ----------");
        service.showDeadlines();
        System.out.println("-------------------------------");
    }

    public static void update() {
        try {
            list();
            System.out.println("digite a id do agendamento a ser atualizado: ");
            int updateId =  Integer.parseInt(scanner.nextLine());

            System.out.println("novo nome do agendamento: ");
            String title = scanner.nextLine();
            System.out.println("data e hora do novo agendameto (pattern: dd-MM-yyyy HH:mm)");
            String dateTimeStr = scanner.nextLine();

            try {
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, FORMATTER);
                service.updateAppointment(new Appointment(title, dateTime, updateId));
                System.out.println("agendamento atualizado com sucesso");
            } catch (DateTimeParseException e) {
                System.out.println("erro de formatação de data e hora");
            }

        } catch (NumberFormatException e) {
            System.out.println("entrada não permitida");
        }
    }

    public static void delete() {
        try {
            list();
            System.out.println("digite a id do agendamento a ser deletado: ");
            int deleteId = Integer.parseInt(scanner.nextLine());
            System.out.println("digite sua senha de administrador: ");
            String authPasscode = scanner.nextLine();
            if (authPasscode.equals(ADMIN_PASSCODE)) {
                service.deleteAppointment(deleteId);
                System.out.println("agendamento deletado com sucesso");
            } else {
                System.out.println("erro de autenticação! senha inválida");
            }
        } catch (NumberFormatException e) {
            System.out.println("entrada não permitida");
        }
    }
}
