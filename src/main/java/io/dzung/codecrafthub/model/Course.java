package io.dzung.codecrafthub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class Course {

    // Auto-generated course ID
    private Long id;

    // Course name is required
    @NotBlank(message = "Course name is required")
    private String name;

    // Description is required
    @NotBlank(message = "Course description is required")
    private String description;

    // Maps Java field "targetDate" to JSON field "target_date"
    @NotNull(message = "Target date is required")
    @JsonProperty("target_date")

    // Format for JSON requests/responses
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

    // Status is required
    @NotBlank(message = "Status is required")
    private String status;

    // Auto-generated timestamp
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}