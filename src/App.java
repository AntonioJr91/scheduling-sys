import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import UI.MainMenu;
import controllers.AppointmentController;
import controllers.DoctorController;
import controllers.PatientController;
import enums.Specialty;
import model.Appointment;
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
        Patient p2 = new Patient("joe", "joe@example.com", LocalDate.of(1995, 2, 2));
        patientService.save(p1);
        patientService.save(p2);

        DoctorRepository doctorRepository = new DoctorRepository();
        DoctorService doctorService = new DoctorService(doctorRepository);
        DoctorController doctorController = new DoctorController(doctorService);
        Doctor d1 = new Doctor("house", "house@example.com", LocalDate.of(1900, 1, 1), Specialty.GENERAL_PRACTITIONER);
        Doctor d2 = new Doctor("stevan", "stevan@example.com", LocalDate.of(1900, 5, 5), Specialty.DERMATOLOGIST);
        doctorService.save(d1);
        doctorService.save(d2);

        AppointmentRepository appointmentRepository = new AppointmentRepository();
        AppointmentService appointmentService = new AppointmentService(appointmentRepository);
        AppointmentController appointmentController = new AppointmentController(appointmentService, patientService,
                doctorService);
        Appointment a1 = new Appointment(p1, d1, null,
                LocalDateTime.of(2025, 1, 1, 10, 30), LocalDateTime.of(2025, 1, 1, 10, 40), 150.00);
        appointmentService.save(a1);

        List<String> options = Arrays.asList(
                "All Patients", "Find Patient By Name", "Add New Patient", "Update Patient", "Delete Patient",
                "All Doctors", "Find Doctor By Name", "Add New Doctors", "Update Doctors", "Delete Doctors",
                "All Appointments", "Find Appointment By ID", "Add New Appointment", "Delete Appointment");

        List<Runnable> actions = Arrays.asList(
                // -----------PATIENT-------------------//
                () -> patientController.getAll(),
                () -> patientController.getByName(),
                () -> patientController.add(),
                () -> patientController.update(),
                () -> patientController.delete(),
                // ----------DOCTOR---------------------//
                () -> doctorController.getAll(),
                () -> doctorController.getByName(),
                () -> doctorController.add(),
                () -> doctorController.update(),
                () -> doctorController.delete(),
                // -----------APPOINTMENT--------------------//
                () -> appointmentController.getAll(),
                () -> appointmentController.getByPatient(),
                () -> appointmentController.add(),
                () -> appointmentController.delete());

        MainMenu.menu("Scheduling System", options, actions);
    }
}
