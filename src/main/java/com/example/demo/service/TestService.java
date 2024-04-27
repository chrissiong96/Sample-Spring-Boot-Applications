package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.enums.Status;
import com.example.demo.model.TestRequestDTO;
import com.example.demo.model.TestResponseDTO;
import com.example.demo.model.TestResponsePaginationDTO;
import com.example.demo.model.UserDetailsDTO;
import com.example.demo.model.thridPartyAPI.ThirdPartyResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private final UserRepository userRepository;

    public TestResponseDTO getUser(int id) throws JsonProcessingException {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Record Not Found"));
        TestResponseDTO responseDTO = new TestResponseDTO();
        responseDTO.setDetails(UserDetailsDTO.convertEntityToDTO(userEntity));
        return responseDTO;
    }

    public TestResponsePaginationDTO getUserPagination(Pageable pageable) throws IOException {
        Page<UserEntity> userEntityPage = userRepository.findAll(pageable);
        List<UserDetailsDTO> userDetailsDTOS = userEntityPage.getContent().stream()
                .map(item -> {
                    try {
                        return UserDetailsDTO.convertEntityToDTO(item);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        TestResponsePaginationDTO responseDTO = new TestResponsePaginationDTO();
        responseDTO.setDetailsList(userDetailsDTOS);
        responseDTO.setCurrentPage(userEntityPage.getNumber() + 1);
        responseDTO.setTotalItems(userEntityPage.getTotalElements());
        responseDTO.setTotalPages(userEntityPage.getTotalPages());

        return responseDTO;
    }

    @Transactional
    public TestResponseDTO processUser(TestRequestDTO testRequestDTO) throws IOException {
        UserDetailsDTO userDetailsDTO = UserDetailsDTO.builder()
                .email(testRequestDTO.getEmail())
                .name(testRequestDTO.getName())
                .designation(testRequestDTO.getDesignation())
                .dateCreated(LocalDateTime.now())
                .gender(testRequestDTO.getGender())
                .birthDate(testRequestDTO.getBirthDate())
                .build();

        UserEntity userEntity = saveUser(userDetailsDTO);
        userDetailsDTO.setId(userEntity.getId());

        TestResponseDTO responseDTO = new TestResponseDTO();
        responseDTO.setDetails(userDetailsDTO);

        return responseDTO;
    }

    private UserEntity saveUser(UserDetailsDTO userDetailsDTO) throws JsonProcessingException {
        UserEntity userEntity = new UserEntity();
        userEntity.setDetails(CommonUtils.getObjectMapper().writeValueAsString(userDetailsDTO));
        userEntity.setCreatedDate(LocalDateTime.now());
        userEntity.setStatus(Status.Active.getCode());
        return userRepository.save(userEntity);
    }

    @Transactional
    public TestResponseDTO updateUser(TestRequestDTO testRequestDTO) throws IOException {
        UserEntity userEntity = userRepository.findById(testRequestDTO.getId()).orElseThrow(() -> new RuntimeException("User Record Not Found"));
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setId(userEntity.getId());
        BeanUtils.copyProperties(testRequestDTO, userDetailsDTO);
        userEntity.setDetails(CommonUtils.getObjectMapper().writeValueAsString(userDetailsDTO));
        userEntity.setUpdatedDate(LocalDateTime.now());
        userEntity.setVersion(userEntity.getVersion() + 1);
        userRepository.save(userEntity);

        TestResponseDTO testResponseDTO = new TestResponseDTO();
        testResponseDTO.setDetails(userDetailsDTO);

        return testResponseDTO;
    }

    @Transactional
    public TestResponseDTO deleteUser(TestRequestDTO testRequestDTO) {
        UserEntity userEntity = userRepository.findById(testRequestDTO.getId()).orElseThrow(() -> new RuntimeException("User Record Not Found"));
        userEntity.setStatus(Status.Deleted.getCode());
        userRepository.save(userEntity);
        return new TestResponseDTO();
    }

    public ThirdPartyResponse callEndpoint(int page) {
        String api = "https://reqres.in/api/users?page={page}";
        Map<String, Serializable> uriVariables = new HashMap<>();
        uriVariables.put("page", page);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(api, ThirdPartyResponse.class, uriVariables);
    }
}
