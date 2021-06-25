package com.edu.schoolsoapapi.soap.endpoint;

import com.edu.schoolsoapapi.soap.bean.Course;
import com.edu.schoolsoapapi.soap.bean.Student;
import com.edu.schoolsoapapi.soap.repository.ICourseRepository;
import com.edu.schoolsoapapi.soap.repository.IStudentRepository;
import com.edu.schoolsoapapi.students.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class StudentDetailsEndPoint {


    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private ICourseRepository courseRepository;

    @PayloadRoot(namespace = "http://schoolsoapapi.edu.com/students",localPart = "GetStudentDetails")
    @ResponsePayload
    public GetStudentDetailsResponse findStudent(@RequestPayload GetStudentDetailsRequest request)
    {
        Student student= studentRepository.findById(request.getId()).get();
        GetStudentDetailsResponse studentDetailsResponse = mapStudentDetails(student);
        return studentDetailsResponse;
    }



    @PayloadRoot(namespace = "http://schoolsoapapi.edu.com/students",localPart = "GetAllStudentDetailsRequest")
    @ResponsePayload

    public GetAllStudentDetailsResponse getAllStudents(@RequestPayload GetAllStudentDetailsRequest request)
    {
        GetAllStudentDetailsResponse studentResp = new GetAllStudentDetailsResponse();
        System.out.println("Reached here");
        List<Student> students = studentRepository.findAll();
        System.out.println("List: "+ students);

        for(Student student: students){
            GetStudentDetailsResponse studentDetailsResponse = mapStudentDetails(student);
            studentResp.getStudentDetails().add(studentDetailsResponse.getStudentDetails());
        }

        return  studentResp;
    }



    @PayloadRoot(namespace = "http://schoolsoapapi.edu.com/students", localPart = "CreateStudentDetailsRequest")
    @ResponsePayload
    public CreateStudentDetailsResponse save(@RequestPayload CreateStudentDetailsRequest request) {
        Course course = courseRepository.findById(request.getStudentDetails().getCourseId()).get();

        Student testStudent = studentRepository.save(new Student(
                request.getStudentDetails().getId(),
                request.getStudentDetails().getFirstName(),
                request.getStudentDetails().getLastName(),
                request.getStudentDetails().getEmail(),
                course
        ));

        System.out.println("Test: "+testStudent);

//        System.out.println("course details "+ course);
        CreateStudentDetailsResponse studentDetailsResponse = new CreateStudentDetailsResponse();
        studentDetailsResponse.setStudentDetails(request.getStudentDetails());
        studentDetailsResponse.setMessage("Created Successfully");
        return studentDetailsResponse;
    }


    @PayloadRoot(namespace = "http://schoolsoapapi.edu.com/students", localPart = "DeleteStudent")
    @ResponsePayload
    public DeleteStudentDetailsResponse delete(@RequestPayload DeleteStudentDetailsRequest request) {

        System.out.println("ID: "+request.getId());
        studentRepository.deleteById(request.getId());
        DeleteStudentDetailsResponse studentDetailsResponse = new DeleteStudentDetailsResponse();
        studentDetailsResponse.setMessage("Deleted Successfully");
        return studentDetailsResponse;
    }



    private GetStudentDetailsResponse mapStudentDetails(Student student){
        StudentDetails details = mapStudent(student);
        GetStudentDetailsResponse studentDetailsResponse = new GetStudentDetailsResponse();
        studentDetailsResponse.setStudentDetails(details);
        return studentDetailsResponse;
    }

    private UpdateStudentDetailsResponse mapUpdateStudentDetails(Student std, String message) {
        StudentDetails details = mapStudent(std);
        UpdateStudentDetailsResponse resp = new UpdateStudentDetailsResponse();
        resp.setStudentDetails(details);
        resp.setMessage(message);
        return resp;
    }

    private StudentDetails mapStudent(Student student){
        StudentDetails stdDetails = new StudentDetails();
        stdDetails.setFirstName(student.getFirstName());
        stdDetails.setId(student.getId());
        stdDetails.setLastName(student.getLastName());
        stdDetails.setEmail(student.getEmail());
        stdDetails.setCourseId(student.getCourse().getId());
        return stdDetails;
    }
}
