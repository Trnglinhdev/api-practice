package com.example.crs.model;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.crs.model.Student;
import com.example.crs.model.StudentRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")

public class StudentController {
    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents(){
        try{
            List<Student> students=new ArrayList<Student>();
            studentRepository.findAll().forEach(students::add);

            if(students.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(students,HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById (@PathVariable("id") Long id){
        Optional<Student> studentData=studentRepository.findById(id);
        if(studentData.isPresent()){
            return new ResponseEntity<>(studentData.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/students")
    public ResponseEntity<Object> createStudent(@Valid @RequestBody Student student, BindingResult result) {
        // if(result.hasErrors()){
        //     return new ResponseEntity<>(getErrorMessages(result),HttpStatus.BAD_REQUEST);
        // }
        try{
            Student stu= studentRepository.save(new Student(student.getName(),student.getEmail(),student.getDepartment()));
            return new ResponseEntity<>(stu,HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student,@PathVariable Long id){
        Optional <Student> studentData=studentRepository.findById(id);

        if(studentData.isPresent()){
            Student stu = studentData.get();
            stu.setName(student.getName());
            stu.setEmail(student.getEmail());
            stu.setDepartment(student.getDepartment());
            return new ResponseEntity<>(studentRepository.save(stu),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable Long id){
        Optional<Student> studentData=studentRepository.findById(id);

        try{
            if(studentData.isPresent()){
                studentRepository.deleteById(id);

                Map<String, Object> response= new HashMap<>();
                response.put("id",studentData.get().getId());
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // private List<String> getErrorMessages(BindingResult result){
    //      return result.getAllErrors().stream()
    //             .map(error -> {
    //                 if (error instanceof FieldError) {
    //                     return ((FieldError) error).getField() + ": " + error.getDefaultMessage();
    //                 }
    //                 return error.getDefaultMessage();
    //             })
    //             .collect(Collectors.toList());
    // }

}
