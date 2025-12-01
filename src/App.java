import java.time.LocalDate;
import java.time.LocalDateTime;

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
        System.out.println("Hello, World!");

        PatientRepository pr = new PatientRepository();
        DoctorRepository dr = new DoctorRepository();
        AppointmentRepository ar = new AppointmentRepository();

        PatientService ps = new PatientService(pr);
        DoctorService ds = new DoctorService(dr);
        AppointmentService as = new AppointmentService(ar);

        Patient p1 = new Patient("John", "john@example.com", LocalDate.of(2020, 5, 5));
        Patient p2 = new Patient("Joe", "ajohn@example.com", LocalDate.of(2020, 5, 5));

        Doctor d1 = new Doctor("house", "house@example.com", LocalDate.of(2000, 1, 1), Specialty.GENERAL_PRACTITIONER);
        Doctor d2 = new Doctor("house2", "house2@example.com", LocalDate.of(2000, 1, 1), Specialty.CARDIOLOGIST);

        Appointment a1 = new Appointment(p2, d1, null,
                LocalDateTime.of(2025, 1, 1, 10, 30),
                LocalDateTime.of(2025, 1, 1, 11, 30),
                100.00);

        Appointment a2 = new Appointment(p1, d2, null,
                LocalDateTime.of(2025, 1, 1, 11, 00),
                LocalDateTime.of(2025, 1, 1, 11, 30),
                919.99);

        ps.save(p1);
        ps.save(p2);

        // System.out.println(ps.getAll());
        // System.out.println(ps.findById(p2.getId()));

        // ps.delete(p2.getId());

        // System.out.println(ps.getAll());

        ds.save(d1);

        // System.out.println(ds.getAll());
        // System.out.println(ds.findById(d1.getId()));

        as.save(a1);
        as.save(a2);

        // System.out.println(as.getAll());

        System.out.println(as.findById(a2.getId()));

    }
}
