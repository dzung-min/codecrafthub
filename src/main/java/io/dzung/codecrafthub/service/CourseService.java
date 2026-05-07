package io.dzung.codecrafthub.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.dzung.codecrafthub.model.Course;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    // JSON file name
    private static final String FILE_NAME = "courses.json";

    // Jackson ObjectMapper for JSON processing
    private final ObjectMapper objectMapper;

    public CourseService() {

        objectMapper = new ObjectMapper();

        // Support Java 8 date/time classes
        objectMapper.registerModule(new JavaTimeModule());

        // Create file automatically if it doesn't exist
        createFileIfNotExists();
    }

    /**
     * Creates courses.json if it does not exist
     */
    private void createFileIfNotExists() {

        File file = new File(FILE_NAME);

        try {

            if (!file.exists()) {

                // Create empty JSON array
                objectMapper.writeValue(file, new ArrayList<>());

                System.out.println("courses.json created successfully.");
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to create courses.json file."
            );
        }
    }

    /**
     * Reads all courses from JSON file
     */
    private List<Course> readCoursesFromFile() {

        try {

            File file = new File(FILE_NAME);

            return objectMapper.readValue(
                    file,
                    new TypeReference<List<Course>>() {}
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Error reading courses from file."
            );
        }
    }

    /**
     * Writes all courses to JSON file
     */
    private void writeCoursesToFile(List<Course> courses) {

        try {

            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_NAME), courses);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Error writing courses to file."
            );
        }
    }

    /**
     * Validate allowed status values
     */
    private void validateStatus(String status) {

        List<String> allowedStatuses = List.of(
                "Not Started",
                "In Progress",
                "Completed"
        );

        if (!allowedStatuses.contains(status)) {

            throw new IllegalArgumentException(
                    "Invalid status. Allowed values are: " +
                            "Not Started, In Progress, Completed"
            );
        }
    }

    /**
     * Get all courses
     */
    public List<Course> getAllCourses() {
        return readCoursesFromFile();
    }

    /**
     * Get course by ID
     */
    public Course getCourseById(Long id) {

        List<Course> courses = readCoursesFromFile();

        Optional<Course> foundCourse = courses.stream()
                .filter(course -> course.getId().equals(id))
                .findFirst();

        return foundCourse.orElseThrow(() ->
                new RuntimeException("Course not found with ID: " + id)
        );
    }

    /**
     * Add new course
     */
    public Course addCourse(Course course) {

        validateStatus(course.getStatus());

        List<Course> courses = readCoursesFromFile();

        // Generate next ID
        long nextId = courses.stream()
                .mapToLong(Course::getId)
                .max()
                .orElse(0) + 1;

        course.setId(nextId);

        // Auto-generate timestamp
        course.setCreatedAt(LocalDateTime.now());

        courses.add(course);

        writeCoursesToFile(courses);

        return course;
    }

    /**
     * Update existing course
     */
    public Course updateCourse(Long id, Course updatedCourse) {

        validateStatus(updatedCourse.getStatus());

        List<Course> courses = readCoursesFromFile();

        Course existingCourse = courses.stream()
                .filter(course -> course.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Course not found with ID: " + id
                        )
                );

        // Update fields
        existingCourse.setName(updatedCourse.getName());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setTargetDate(updatedCourse.getTargetDate());
        existingCourse.setStatus(updatedCourse.getStatus());

        writeCoursesToFile(courses);

        return existingCourse;
    }

    /**
     * Delete course by ID
     */
    public void deleteCourse(Long id) {

        List<Course> courses = readCoursesFromFile();

        boolean removed = courses.removeIf(
                course -> course.getId().equals(id)
        );

        if (!removed) {
            throw new RuntimeException(
                    "Course not found with ID: " + id
            );
        }

        writeCoursesToFile(courses);
    }
}