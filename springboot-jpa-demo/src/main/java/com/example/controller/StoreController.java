package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import com.example.entity.User;
import org.springframework.web.bind.annotation.*;

import com.example.config.Helper;
import com.example.service.StoreService;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StoreController {

    private final StoreService storeService;
    private final Helper helper;

    public StoreController(StoreService storeService, Helper helper) {
        this.storeService = storeService;
        this.helper = helper;
    }

    @GetMapping("/get-store-by-province")
    public ResponseEntity<Map<String, Object>> getStoreByProvince(@RequestParam String province) {

        Object data = new Object();

        // Example: using the query param
        if (province != null) {
            data = Collections.emptyList();
        } else {
            data = Collections.emptyList();
        }
        data = this.storeService.getStoresByProvince(province);

        return new ResponseEntity<>(helper.buildResponse(HttpStatus.OK, "Success", data), HttpStatus.OK);

    }

    @PostMapping("/update-whitelist")
    public ResponseEntity<Map<String, Object>> updateWhitelistStores(
            @RequestParam String is_whitelist,
            @RequestParam String store_ids) {

        this.storeService.updateWhitelistStores(store_ids, Boolean.parseBoolean(is_whitelist));

        return new ResponseEntity<>(helper.buildResponse(HttpStatus.OK, "Success", null),
                HttpStatus.OK);
    }
}
