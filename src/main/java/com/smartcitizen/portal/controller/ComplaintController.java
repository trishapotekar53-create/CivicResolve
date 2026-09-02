package com.smartcitizen.portal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartcitizen.portal.dto.ComplaintDto;
import com.smartcitizen.portal.dto.ComplaintStatusDto;
import com.smartcitizen.portal.service.ComplaintService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ComplaintController {

    private final ComplaintService complaintService;


    @PostMapping
    public ResponseEntity<ComplaintDto> createComplaint(
            @Valid @RequestBody ComplaintDto complaintDto) {

        return new ResponseEntity<>(
                complaintService.createComplaint(
                        complaintDto
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<ComplaintDto>> getAllComplaints() {

        return ResponseEntity.ok(
                complaintService.getAllComplaints()
        );
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ComplaintDto>> getUserComplaints(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                complaintService.getComplaintsByUser(
                        userId
                )
        );
    }



    @GetMapping("/{id}")
    public ResponseEntity<ComplaintDto> getComplaintById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                complaintService.getComplaintById(id)
        );
    }


   
    @PutMapping("/{id}")
    public ResponseEntity<ComplaintDto> updateComplaint(
            @PathVariable Long id,
            @RequestBody ComplaintDto complaintDto) {

        return ResponseEntity.ok(
                complaintService.updateComplaint(
                        id,
                        complaintDto
                )
        );
    }



    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @RequestBody ComplaintStatusDto statusDto) {

        complaintService.updateComplaintStatus(
                id,
                statusDto
        );

        return ResponseEntity.ok(
                "Complaint status updated successfully"
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComplaint(
            @PathVariable Long id) {

        complaintService.deleteComplaint(id);

        return ResponseEntity.ok(
                "Complaint deleted successfully"
        );
    }
}