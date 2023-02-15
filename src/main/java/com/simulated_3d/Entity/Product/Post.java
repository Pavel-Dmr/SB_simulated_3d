package com.simulated_3d.Entity.Product;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Post {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
}
