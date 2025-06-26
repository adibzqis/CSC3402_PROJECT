package com.Parfetch.ParFetch.repository;

import com.Parfetch.ParFetch.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByUsername(String username); // Method to find staff by username
}
