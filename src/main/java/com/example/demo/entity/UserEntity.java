package com.example.demo.entity;

import com.example.demo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="details", columnDefinition = "varchar")
    private String details;

    @Column(name="created_date")
    private LocalDateTime createdDate;

    @Column(name="updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "status", columnDefinition = "varchar")
    private String status;

    @Column(name="version")
    private int version;
}
