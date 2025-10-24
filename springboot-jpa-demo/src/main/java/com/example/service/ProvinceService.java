package com.example.service;

import org.springframework.stereotype.Service;

import com.example.repository.ProvinceRepository;

@Service
public class ProvinceService {
    private final ProvinceRepository provinceRepository;

    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public Boolean provinceExistsById(Integer provinceId) {
        return provinceRepository.existsById(provinceId);
    }
}
