package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Province;

public interface ProvinceRepository extends JpaRepository<Province, Integer> {

}
