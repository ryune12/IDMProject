package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.StoreView;
import com.example.repository.StoreRepository;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<StoreView> getStoresByProvince(String provinceName) {
        return storeRepository.findStorebyProvince(provinceName);
    }

    public void updateWhitelistStores(String storeIds, boolean isWhitelist) {
        List<Integer> storeIdList = storeIds.isEmpty() ? List.of()
                : List.of(storeIds.split(",")).stream()
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .toList();

        storeRepository.updateWhitelistStatus(storeIdList, isWhitelist);
    }
}
