package pl.mzuchnik.client.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.mzuchnik.client.jwt.model.Student;

import java.util.List;

@Service
public class StudentServiceJwtImpl implements StudentService {

    private RestTemplate restTemplate;
    private JwtGenerator jwtGenerator;
    private String token;

    @Autowired
    public StudentServiceJwtImpl(JwtGenerator jwtGenerator) {
        this.restTemplate = new RestTemplate();
        this.jwtGenerator = jwtGenerator;
        this.token = jwtGenerator.generateJWT();
    }

    @Override
    public List<Student> findAll() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity httpEntity = new HttpEntity(headers);

        Student[] students = restTemplate.exchange("http://localhost:8080/api/students",
                HttpMethod.GET,
                httpEntity,
                Student[].class).getBody();

        return List.of(students);
    }

    @Override
    public void save(Student student) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-Type","application/json");
        HttpEntity httpEntity = new HttpEntity(student, headers);

        int statusCodeValue = restTemplate.exchange("http://localhost:8080/api/students",
                HttpMethod.POST,
                httpEntity,
                String.class).getStatusCodeValue();
        System.out.println("status: " + statusCodeValue);
    }
}
