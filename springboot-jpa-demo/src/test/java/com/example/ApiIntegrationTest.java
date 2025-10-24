package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(post("/api/login")
                .param("username", "alice")
                .param("password", "alice123"))
                // .content("{\"username\":\"alice\", \"password\":\"alice123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void testGetStoreByProvinceWithoutToken() throws Exception {
        mockMvc.perform(get("/api/get-store-by-province?province=jakarta"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetStoreByProvinceWithToken() throws Exception {
        String loginResponse = mockMvc.perform(post("/api/login")
                .param("username", "alice")
                .param("password", "alice123"))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String token = JsonPath.read(loginResponse, "$.data");

        mockMvc.perform(get("/api/get-store-by-province?province=jakarta")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateWhitelistStoresWithToken() throws Exception {
        String loginResponse = mockMvc.perform(post("/api/login")
                .param("username", "alice")
                .param("password", "alice123"))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String token = JsonPath.read(loginResponse, "$.data");

        mockMvc.perform(post("/api/update-whitelist")
                .param("is_whitelist", "true")
                .param("store_ids", "1,2,3")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateWhitelistStoresWithoutToken() throws Exception {
        mockMvc.perform(post("/api/update-whitelist")
                .param("is_whitelist", "true")
                .param("store_ids", "1,2,3"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testUpdateBranchWithToken() throws Exception {
        String loginResponse = mockMvc.perform(post("/api/login")
                .param("username", "alice")
                .param("password", "alice123"))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String token = JsonPath.read(loginResponse, "$.data");

        mockMvc.perform(put("/api/update-branch/2")
                .param("province_id", "1")
                .param("new_name", "New Branch Name")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

    }

    @Test
    void testUpdateBranchWithoutToken() throws Exception {
        mockMvc.perform(put("/api/update-branch/2")
                .param("province_id", "1")
                .param("new_name", "New Branch Name"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testDeleteBranchWithToken() throws Exception {
        String loginResponse = mockMvc.perform(post("/api/login")
                .param("username", "alice")
                .param("password", "alice123"))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String token = JsonPath.read(loginResponse, "$.data");

        mockMvc.perform(delete("/api/delete-branch/2")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

    }

    @Test
    void testDeleteBranchWithoutToken() throws Exception {
        mockMvc.perform(delete("/api/delete-branch/2"))
                .andExpect(status().isUnauthorized());
    }

}
