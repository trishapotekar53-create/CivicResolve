package com.smartcitizen.portal.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintDto {

    private Long complaintId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Location is required")
    private String location;

    private String attachmentUrl;

    private String attachmentType;

    private String status;

    private String remarks;

    private LocalDateTime createdAt;

    @NotNull(message = "User ID is required")
    private Long userId;
}