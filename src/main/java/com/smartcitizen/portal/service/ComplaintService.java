package com.smartcitizen.portal.service;

import java.util.List;

import com.smartcitizen.portal.dto.ComplaintDto;
import com.smartcitizen.portal.dto.ComplaintStatusDto;

public interface ComplaintService {

    ComplaintDto createComplaint(ComplaintDto complaintDto);

    List<ComplaintDto> getAllComplaints();

    ComplaintDto getComplaintById(Long complaintId);

    ComplaintDto updateComplaint(Long complaintId, ComplaintDto complaintDto);

    void deleteComplaint(Long complaintId);

    List<ComplaintDto> getComplaintsByUser(Long userId);

    void updateComplaintStatus(Long complaintId, ComplaintStatusDto statusDto);

}  