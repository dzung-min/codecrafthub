package io.dzung.codecrafthub.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import io.dzung.codecrafthub.model.Course;
import io.dzung.codecrafthub.service.CourseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    // Constructor Injection
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * POST /api/courses
     * Add a new course
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course createCourse(
            @Valid @RequestBody Course course
    ) {

        return courseService.addCourse(course);
    }

    /**
     * GET /api/courses
     * Get all courses
     */
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    /**
     * GET /api/courses/{id}
     * Get one course by ID
     */
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    /**
     * PUT /api/courses/{id}
     * Update a course
     */
    @PutMapping("/{id}")
    public Course updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody Course course
    ) {

        return courseService.updateCourse(id, course);
    }

    /**
     * DELETE /api/courses/{id}
     * Delete a course
     */
    @DeleteMapping("/{id}")
    public Map<String, String> deleteCourse(
            @PathVariable Long id
    ) {

        courseService.deleteCourse(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Course deleted successfully");

        return response;
    }

    /**
     * GET /api/courses/stats
     * Returns statistics about courses
     */
    @GetMapping("/stats")
    public Map<String, Object> getCourseStats() {

        // Get all courses from service
        List<Course> courses = courseService.getAllCourses();

        // Count total courses
        int totalCourses = courses.size();

        // Count courses by status
        long notStartedCount = courses.stream()
                .filter(course ->
                        "Not Started".equals(course.getStatus()))
                .count();

        long inProgressCount = courses.stream()
                .filter(course ->
                        "In Progress".equals(course.getStatus()))
                .count();

        long completedCount = courses.stream()
                .filter(course ->
                        "Completed".equals(course.getStatus()))
                .count();

        // Build response JSON
        Map<String, Object> stats = new HashMap<>();

        stats.put("total_courses", totalCourses);

        // Nested object for status statistics
        Map<String, Long> statusCounts = new HashMap<>();
        statusCounts.put("Not Started", notStartedCount);
        statusCounts.put("In Progress", inProgressCount);
        statusCounts.put("Completed", completedCount);

        stats.put("status_counts", statusCounts);

        return stats;
    }

    // =========================================================
    // EXCEPTION HANDLING
    // =========================================================

    /**
     * Handle validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return errors;
    }

    /**
     * Handle invalid status values
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgument(
            IllegalArgumentException ex
    ) {

        Map<String, String> error = new HashMap<>();

        error.put("error", ex.getMessage());

        return error;
    }

    /**
     * Handle general runtime errors
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleRuntimeException(
            RuntimeException ex
    ) {

        Map<String, String> error = new HashMap<>();

        error.put("error", ex.getMessage());

        return error;
    }
}