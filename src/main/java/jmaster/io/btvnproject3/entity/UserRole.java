//package jmaster.io.btvnproject3.entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import lombok.Data;
//
//import javax.persistence.*;
//
//@Entity
//@Data
//public class UserRole {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @ManyToOne
//    @JsonBackReference
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    private String role; //admin, member
//}
