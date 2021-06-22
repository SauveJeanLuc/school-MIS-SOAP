package com.edu.schoolsoapapi.soap.endpoint;

import com.edu.schoolsoapapi.courses.*;
import com.edu.schoolsoapapi.soap.bean.Course;
import com.edu.schoolsoapapi.soap.repository.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.Optional;

@Endpoint
public class CourseDetailsEndPoint {

    @Autowired
    private ICourseRepository courseRepository;

    @PayloadRoot(namespace = "http://schoolsoapapi.edu.com/courses", localPart = "GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse findById(@RequestPayload GetCourseDetailsRequest request) {

        Course course = courseRepository.findById(request.getId()).get();

        GetCourseDetailsResponse courseDetailsResponse = mapCourseDetails(course);
        return  courseDetailsResponse;
    }

    @PayloadRoot(namespace = "http://schoolsoapapi.edu.com/courses", localPart = "GetAllCourseDetailsRequest")
    @ResponsePayload
    public GetAllCourseDetailsResponse findAll(@RequestPayload GetAllCourseDetailsRequest request){
        GetAllCourseDetailsResponse allCourseDetailsResponse = new GetAllCourseDetailsResponse();

        List<Course> courses = courseRepository.findAll();
        for (Course course: courses){
            GetCourseDetailsResponse courseDetailsResponse = mapCourseDetails(course);
            allCourseDetailsResponse.getCourseDetails().add(courseDetailsResponse.getCourseDetails());
        }
        return allCourseDetailsResponse;
    }


    @PayloadRoot(namespace = "http://schoolsoapapi.edu.com/courses", localPart = "CreateCourseDetailsRequest")
    @ResponsePayload
    public CreateCourseDetailsResponse save(@RequestPayload CreateCourseDetailsRequest request) {
        courseRepository.save(new Course(request.getCourseDetails().getId(),
                request.getCourseDetails().getName(),
                request.getCourseDetails().getDescription()
        ));

        CreateCourseDetailsResponse courseDetailsResponse = new CreateCourseDetailsResponse();
        courseDetailsResponse.setCourseDetails(request.getCourseDetails());
        courseDetailsResponse.setMessage("Created Successfully");
        return courseDetailsResponse;
    }

    @PayloadRoot(namespace = "http://schoolsoapapi.edu.com/courses", localPart = "UpdateCourseDetailsRequest")
    @ResponsePayload
    public UpdateCourseDetailsResponse update(@RequestPayload UpdateCourseDetailsRequest request) {
        UpdateCourseDetailsResponse courseDetailsResponse = null;
        Optional<Course> existingCourse = this.courseRepository.findById(request.getCourseDetails().getId());
        if(existingCourse.isEmpty() || existingCourse == null) {
            courseDetailsResponse = mapCourseDetail(null, "Id not found");
        }
        if(existingCourse.isPresent()) {

            Course _course = existingCourse.get();
            _course.setName(request.getCourseDetails().getName());
            _course.setDescription(request.getCourseDetails().getDescription());
            courseRepository.save(_course);
            courseDetailsResponse = mapCourseDetail(_course, "Updated successfully");

        }
        return courseDetailsResponse;
    }

    @PayloadRoot(namespace = "http://schoolsoapapi.edu.com/courses", localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteCourseDetailsResponse save(@RequestPayload DeleteCourseDetailsRequest request) {

        System.out.println("ID: "+request.getId());
        courseRepository.deleteById(request.getId());

        DeleteCourseDetailsResponse courseDetailsResponse = new DeleteCourseDetailsResponse();
        courseDetailsResponse.setMessage("Deleted Successfully");
        return courseDetailsResponse;
    }

    private GetCourseDetailsResponse mapCourseDetails(Course course){
        CourseDetails courseDetails = mapCourse(course);

        GetCourseDetailsResponse courseDetailsResponse = new GetCourseDetailsResponse();

        courseDetailsResponse.setCourseDetails(courseDetails);
        return courseDetailsResponse;
    }

    private UpdateCourseDetailsResponse mapCourseDetail(Course course, String message) {
        CourseDetails courseDetails = mapCourse(course);
        UpdateCourseDetailsResponse courseDetailsResponse = new UpdateCourseDetailsResponse();

        courseDetailsResponse.setCourseDetails(courseDetails);
        courseDetailsResponse.setMessage(message);
        return courseDetailsResponse;
    }

    private CourseDetails mapCourse(Course course){
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setDescription(course.getDescription());
        courseDetails.setId(course.getId());
        courseDetails.setName(course.getName());
        return courseDetails;
    }
}
