package com.order.Restarant.model.order;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name ="dish_type")
@Data
public class DishTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "dishType")
    private List<DishEntity> dishes;
    }
