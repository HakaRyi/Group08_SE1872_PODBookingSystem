package com.example.POD_BookingSystem.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Image")
public class Image {
    @Id
    String image_id;
   // String product_id;
    String image;
    String alt_text;// Quan hệ với ImageProduct

//    @OneToMany(mappedBy = "image_id", cascade = CascadeType.ALL, orphanRemoval = true)
//    List<ImageProduct> imageProducts;

}
