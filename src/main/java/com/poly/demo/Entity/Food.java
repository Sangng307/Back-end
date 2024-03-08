package com.poly.demo.Entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "food")
@NoArgsConstructor
@AllArgsConstructor
public class Food {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String food_name;
    private int quantity;
    private String description;
    private double price;
    private int star;
    private String image;
    private double discount;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
