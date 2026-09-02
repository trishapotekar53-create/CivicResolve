package com.smartcitizen.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartcitizen.portal.model.Complaint;

public interface ComplaintRepository
        extends JpaRepository<Complaint, Long> {

    List<Complaint> findByUserUserid(Long userid);

    void deleteByUserUserid(Long userid);
}