package pl.mzuchnik.client.jwt.service;


import pl.mzuchnik.client.jwt.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> findAll();

    void save(Student student);


}
