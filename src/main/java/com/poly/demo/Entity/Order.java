package com.poly.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "[order]")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    String status;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    Users user;

    String address;
    
    Double price;
    
    private String payment;

	private String note;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "UTC")
    private Date date;

    private int quantity;


    
    
    
}