package com.example.demo.controller;

import com.example.demo.common.ResourcePath;
import com.example.demo.model.RestResponse;
import com.example.demo.model.TestRequestDTO;
import com.example.demo.model.TestResponseDTO;
import com.example.demo.model.TestResponsePaginationDTO;
import com.example.demo.model.thridPartyAPI.ThirdPartyResponse;
import com.example.demo.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestController {

    private final TestService testService;

    @PostMapping(value = ResourcePath.CREATE_USER, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<TestResponseDTO> createNewUser(
            @RequestBody TestRequestDTO testRequestDTO) throws IOException {
        try {
            TestResponseDTO testResponseDTO = testService.processUser(testRequestDTO);
            return RestResponse.success(testResponseDTO, "create-new-user", testRequestDTO);
        } catch (Exception e) {
            return RestResponse.fail(e.getMessage(), "create-new-user", testRequestDTO);
        }
    }

    @GetMapping(value = ResourcePath.GET_USER, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<TestResponseDTO> retrieveUser(
            @PathVariable("id") int id) throws IOException {
        try {
            TestResponseDTO testResponseDTO = testService.getUser(id);
            return RestResponse.success(testResponseDTO, "retrieve-user");
        } catch (Exception e) {
            return RestResponse.fail(e.getMessage(), "retrieve-user");
        }
    }

    @GetMapping(value = ResourcePath.GET_USER_PAGINATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<TestResponsePaginationDTO> retrieveUserPagination(
            @RequestParam int page,
            @RequestParam int size) throws IOException {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            TestResponsePaginationDTO testResponsePaginationDTO = testService.getUserPagination(pageable);
            return RestResponse.success(testResponsePaginationDTO, "retrieve-user-pagination");
        } catch (Exception e) {
            return RestResponse.fail(e.getMessage(), "retrieve-user-pagination");
        }
    }

    @PostMapping(value = ResourcePath.UPDATE_USER, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<TestResponseDTO> updateUser(
            @RequestBody TestRequestDTO testRequestDTO) throws IOException {
        try {
            TestResponseDTO testResponseDTO = testService.updateUser(testRequestDTO);
            return RestResponse.success(testResponseDTO, "update-user", testRequestDTO);
        } catch (Exception e) {
            return RestResponse.fail(e.getMessage(), "update-user", testRequestDTO);
        }
    }

    @PostMapping(value = ResourcePath.DELETE_USER, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<TestResponseDTO> deleteUser(
            @RequestBody TestRequestDTO testRequestDTO) throws IOException {
        try {
            TestResponseDTO testResponseDTO = testService.deleteUser(testRequestDTO);
            return RestResponse.success(testResponseDTO, "delete-user", testRequestDTO);
        } catch (Exception e) {
            return RestResponse.fail(e.getMessage(), "delete-user", testRequestDTO);
        }
    }

    @GetMapping(value = ResourcePath.THIRD_PARTY_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<ThirdPartyResponse> callThirdPartyEndpoint(
            @RequestParam int page) throws IOException {
        try {
            ThirdPartyResponse testResponseDTO = testService.callEndpoint(page);
            return RestResponse.success(testResponseDTO, "call-third-party-endpoint");
        } catch (Exception e) {
            return RestResponse.fail(e.getMessage(), "call-third-party-endpoint");
        }
    }
}
