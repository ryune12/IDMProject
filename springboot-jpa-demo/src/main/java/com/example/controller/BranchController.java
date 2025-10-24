package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import com.example.entity.User;
import org.springframework.web.bind.annotation.*;

import com.example.config.Helper;
import com.example.service.BranchService;
import com.example.service.ProvinceService;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BranchController {

    private final BranchService branchService;
    private final ProvinceService provinceService;
    private final Helper helper;

    public BranchController(BranchService branchService, Helper helper, ProvinceService provinceService) {
        this.branchService = branchService;
        this.helper = helper;
        this.provinceService = provinceService;
    }

    @PutMapping("/update-branch/{id}")
    public ResponseEntity<Map<String, Object>> updateBranch(@PathVariable String id,
            @RequestParam String new_name,
            @RequestParam String province_id) {

        if (!this.provinceService.provinceExistsById(Integer.parseInt(province_id))) {
            return new ResponseEntity<>(helper.buildResponse(HttpStatus.NOT_FOUND, "Province Not Found", id),
                    HttpStatus.NOT_FOUND);
        }

        this.branchService.updateBranchById(Integer.parseInt(id), new_name, Integer.parseInt(province_id));

        return new ResponseEntity<>(helper.buildResponse(HttpStatus.OK, "Success", id), HttpStatus.OK);
    }

    @DeleteMapping("/delete-branch/{id}")
    public ResponseEntity<Map<String, Object>> deleteBranch(@PathVariable String id) {

        this.branchService.deleteBranchById(Integer.parseInt(id));

        return new ResponseEntity<>(helper.buildResponse(HttpStatus.OK, "Success", id), HttpStatus.OK);
    }

    // @PostMapping("/update-whitelist")
    // public ResponseEntity<Map<String, Object>> updateWhitelistStores(
    // @RequestParam String is_whitelist,
    // @RequestParam String store_ids) {

    // this.branchService.updateWhitelistStores(store_ids,
    // Boolean.parseBoolean(is_whitelist));

    // return new ResponseEntity<>(helper.buildResponse(HttpStatus.OK, "Success",
    // null),
    // HttpStatus.OK);
    // }

}
