import java.time.LocalDate;

import model.Patient;
import repositories.PatientRepository;
import services.PatientService;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        
        PatientRepository pr = new PatientRepository();
        PatientService ps = new PatientService(pr);

        Patient p1 = new Patient("John", "john@example.com", LocalDate.of(2020, 5, 5));
        Patient p2 = new Patient("Joe", "john@example.com", LocalDate.of(2020, 5, 5));

        pr.save(p1);

        ps.save(p2);

        ps.getAll();
        ps.findById(p2.getId());
        ps.delete(p1.getId());
    }
}
