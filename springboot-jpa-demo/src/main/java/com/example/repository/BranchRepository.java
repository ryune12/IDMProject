package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

}
