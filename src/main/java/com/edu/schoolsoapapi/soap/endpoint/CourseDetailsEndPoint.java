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

    private GetCourseDetailsResponse mapCourseDetails(Course course){
        CourseDetails courseDetails = mapCourse(course);

        GetCourseDetailsResponse courseDetailsResponse = new GetCourseDetailsResponse();

        courseDetailsResponse.setCourseDetails(courseDetails);
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
