package com.smartcitizen.portal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.smartcitizen.portal.dto.ComplaintDto;
import com.smartcitizen.portal.dto.ComplaintStatusDto;
import com.smartcitizen.portal.model.Complaint;
import com.smartcitizen.portal.model.User;
import com.smartcitizen.portal.repository.ComplaintRepository;
import com.smartcitizen.portal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;



    @Override
    public ComplaintDto createComplaint(ComplaintDto complaintDto) {

        // Check user ID
        if (complaintDto.getUserId() == null) {
            throw new RuntimeException("User ID is required");
        }

        // Find user
        User user = userRepository.findById(
                complaintDto.getUserId()
        ).orElseThrow(
                () -> new RuntimeException("User not found")
        );


        // Create complaint
        Complaint complaint = new Complaint();

        complaint.setTitle(
                complaintDto.getTitle()
        );

        complaint.setDescription(
                complaintDto.getDescription()
        );

        complaint.setCategory(
                complaintDto.getCategory()
        );

        complaint.setLocation(
                complaintDto.getLocation()
        );

        complaint.setAttachmentUrl(
                complaintDto.getAttachmentUrl()
        );

        complaint.setAttachmentType(
                complaintDto.getAttachmentType()
        );

        complaint.setStatus("PENDING");

        complaint.setRemarks(
                complaintDto.getRemarks()
        );

        complaint.setCreatedAt(
                LocalDateTime.now()
        );


        // IMPORTANT
        // Associate complaint with logged-in user
        complaint.setUser(user);


        // Save
        Complaint saved = complaintRepository.save(
                complaint
        );


        return convertToDto(saved);
    }

    @Override
    public List<ComplaintDto> getAllComplaints() {

        return complaintRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ComplaintDto getComplaintById(Long complaintId) {

        Complaint complaint =
                complaintRepository.findById(complaintId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Complaint not found"
                        )
                );

        return convertToDto(complaint);
    }

    @Override
    public List<ComplaintDto> getComplaintsByUser(Long userId) {

        return complaintRepository
                .findByUserUserid(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public ComplaintDto updateComplaint(
            Long complaintId,
            ComplaintDto complaintDto) {

        Complaint complaint =
                complaintRepository.findById(complaintId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Complaint not found"
                        )
                );


        complaint.setTitle(
                complaintDto.getTitle()
        );

        complaint.setDescription(
                complaintDto.getDescription()
        );

        complaint.setCategory(
                complaintDto.getCategory()
        );

        complaint.setLocation(
                complaintDto.getLocation()
        );

        complaint.setAttachmentUrl(
                complaintDto.getAttachmentUrl()
        );

        complaint.setAttachmentType(
                complaintDto.getAttachmentType()
        );

        if (complaintDto.getStatus() != null) {

            complaint.setStatus(
                    complaintDto.getStatus()
            );
        }

        complaint.setRemarks(
                complaintDto.getRemarks()
        );


        Complaint updated =
                complaintRepository.save(
                        complaint
                );


        return convertToDto(updated);
    }


    @Override
    public void updateComplaintStatus(
            Long complaintId,
            ComplaintStatusDto statusDto) {

        Complaint complaint =
                complaintRepository.findById(complaintId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Complaint not found"
                        )
                );


        complaint.setStatus(
                statusDto.getStatus()
        );

        complaint.setRemarks(
                statusDto.getRemarks()
        );


        complaintRepository.save(complaint);
    }


    @Override
    public void deleteComplaint(Long complaintId) {

        Complaint complaint =
                complaintRepository.findById(complaintId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Complaint not found"
                        )
                );

        complaintRepository.delete(complaint);
    }

    private ComplaintDto convertToDto(
            Complaint complaint) {

        ComplaintDto dto =
                new ComplaintDto();

        dto.setComplaintId(
                complaint.getComplaintId()
        );

        dto.setTitle(
                complaint.getTitle()
        );

        dto.setDescription(
                complaint.getDescription()
        );

        dto.setCategory(
                complaint.getCategory()
        );

        dto.setLocation(
                complaint.getLocation()
        );

        dto.setAttachmentUrl(
                complaint.getAttachmentUrl()
        );

        dto.setAttachmentType(
                complaint.getAttachmentType()
        );

        dto.setStatus(
                complaint.getStatus()
        );

        dto.setRemarks(
                complaint.getRemarks()
        );

        dto.setCreatedAt(
                complaint.getCreatedAt()
        );


        if (complaint.getUser() != null) {

            dto.setUserId(
                    complaint.getUser().getUserid()
            );
        }


        return dto;
    }
}