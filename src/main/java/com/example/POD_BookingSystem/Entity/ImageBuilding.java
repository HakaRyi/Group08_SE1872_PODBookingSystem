//package com.example.POD_BookingSystem.Entity;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@Entity
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@Table(name = "Image")
//public class ImageBuilding {
//    @Id
//    String image_building_id;
//
//    @ManyToOne
//    @JoinColumn(name = "building",nullable = false, referencedColumnName = "building")
//    Building building;
//
//    String image;
//    String alt_text;// Quan hệ với ImageProduct
//
////    @OneToMany(mappedBy = "image_id", cascade = CascadeType.ALL, orphanRemoval = true)
////    List<ImageProduct> imageProducts;
//
//}
