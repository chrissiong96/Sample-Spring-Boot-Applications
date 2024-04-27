package com.example.demo.model;

import com.example.demo.entity.UserEntity;
import com.example.demo.utils.CommonUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsDTO implements Serializable {

    private Integer id;

    private String name;

    private String email;

    private String gender;

    private String designation;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateCreated;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdated;

    public static UserDetailsDTO convertEntityToDTO(UserEntity userEntity) throws JsonProcessingException {
        UserDetailsDTO userDetailsDTO = CommonUtils.getObjectMapper().readValue(userEntity.getDetails(), UserDetailsDTO.class);
        userDetailsDTO.setId(userEntity.getId());
        userDetailsDTO.setDateCreated(userEntity.getCreatedDate());
        userDetailsDTO.setLastUpdated(userEntity.getUpdatedDate());
        return userDetailsDTO;
    }
}
