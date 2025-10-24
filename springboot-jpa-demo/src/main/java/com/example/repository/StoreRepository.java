package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Store;
import com.example.model.StoreView;

import jakarta.transaction.Transactional;

public interface StoreRepository extends JpaRepository<Store, Integer> {

    @Query(value = """
                SELECT s.id, s.name, s.address, s.branch_id, s.is_whitelist AS whitelist, s.address,
                       s.telp, s.post_code, s.created_at, s.created_by,
                       s.updated_at, s.updated_by, s.deleted_at, s.deleted_by,
                       p.province_name
                FROM m_stores s
                LEFT JOIN m_branches b ON s.branch_id = b.id
                LEFT JOIN m_province p ON p.id = b.province_id
                WHERE (s.is_whitelist = TRUE
                   OR LOWER(p.province_name) LIKE LOWER(CONCAT('%', :provinceName, '%'))) AND s.deleted_at IS NULL
            """, nativeQuery = true)
    List<StoreView> findStorebyProvince(String provinceName);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE m_stores
            SET is_whitelist = :isWhitelist,
                updated_at = CURRENT_TIMESTAMP
            WHERE id IN (:storeIds)
            """, nativeQuery = true)
    void updateWhitelistStatus(
            @Param("storeIds") List<Integer> storeIds,
            @Param("isWhitelist") Boolean isWhitelist);
}
