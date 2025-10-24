package com.example.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Branch;
import com.example.entity.Province;
import com.example.repository.BranchRepository;
import com.example.repository.ProvinceRepository;

@Service
public class BranchService {
    private final BranchRepository branchRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Branch updateBranchById(int branchId, String newName, Integer provinceId) {
        Province province = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new RuntimeException("Province not found"));

        // Implementation for updating branch by ID
        return branchRepository.findById(branchId)
                .map(branch -> {
                    branch.setName(newName);
                    branch.setProvince(province);
                    branch.setUpdatedAt(LocalDateTime.now());
                    return branchRepository.save(branch);
                })
                .orElseThrow(() -> new RuntimeException("Branch not found"));
    }

    public Branch deleteBranchById(int branchId) {
        // Implementation for deleting branch by ID
        return branchRepository.findById(branchId)
                .map(branch -> {
                    branch.setDeletedAt(LocalDateTime.now());
                    return branchRepository.save(branch);
                })
                .orElseThrow(() -> new RuntimeException("Branch not found"));
    }
}
