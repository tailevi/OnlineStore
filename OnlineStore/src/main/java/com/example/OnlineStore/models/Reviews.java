package com.example.OnlineStore.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name ="reviews")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reviews implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    Integer rating;
    String comment;
    String date;
    String reviewerName;
    String reviewerEmail;
}
