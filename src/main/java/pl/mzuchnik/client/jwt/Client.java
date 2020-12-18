package pl.mzuchnik.client.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.mzuchnik.client.jwt.model.Student;
import pl.mzuchnik.client.jwt.service.StudentService;

import java.util.List;

@Component
public class Client {

    private StudentService studentService;

    @Autowired
    public Client(StudentService studentService) {
        this.studentService = studentService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printStudents()
    {
        System.out.println("Pobieram studentów");
        studentService.findAll().forEach(System.out::println);
        System.out.println("Zakończono pobieranie studentów");
        System.out.println("Dodaje studenta");
        studentService.save(new Student("Przemek","Byk",32));


    }
}
