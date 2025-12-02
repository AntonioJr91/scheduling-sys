import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import UI.MainMenu;
import controllers.AppointmentController;
import controllers.DoctorController;
import controllers.PatientController;
import enums.Specialty;
import model.Doctor;
import model.Patient;
import repositories.AppointmentRepository;
import repositories.DoctorRepository;
import repositories.PatientRepository;
import services.AppointmentService;
import services.DoctorService;
import services.PatientService;

public class App {
    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);

        PatientRepository patientRepository = new PatientRepository();
        PatientService patientService = new PatientService(patientRepository);
        PatientController patientController = new PatientController(patientService);
        Patient p1 = new Patient("john", "john@example.com", LocalDate.of(1995, 2, 2));
        Patient p2 = new Patient("john2", "john2@example.com", LocalDate.of(1995, 2, 2));
        patientService.save(p1);
        patientService.save(p2);

        DoctorRepository doctorRepository = new DoctorRepository();
        DoctorService doctorService = new DoctorService(doctorRepository);
        DoctorController doctorController = new DoctorController(doctorService);
        Doctor d1 = new Doctor("house", "house@example.com", LocalDate.of(1900, 1, 1), Specialty.GENERAL_PRACTITIONER);
        doctorService.save(d1);

        AppointmentRepository appointmentRepository = new AppointmentRepository();
        AppointmentService appointmentService = new AppointmentService(appointmentRepository);
        AppointmentController appointmentController = new AppointmentController(appointmentService, patientService,
                doctorService);

        List<String> options = Arrays.asList(
                "All Patients", "Find Patient By Name", "Add New Patient", "Update Patient", "Delete Patient"
            );

        List<Runnable> actions = Arrays.asList(
                () -> patientController.getAll(),
                () -> patientController.getByName(),
                () -> patientController.add(),
                () -> patientController.update(),
                () -> patientController.delete());

        MainMenu.menu("Scheduling System", options, actions);
    }
}
