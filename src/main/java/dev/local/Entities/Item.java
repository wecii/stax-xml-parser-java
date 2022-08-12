package dev.local.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Item implements Serializable {

    private String id;
    private String name;
    private String category;
    private String brand;
    private float price;
    private String url;
    private int position;
    private String subcategory;
    private float actualPrice;
    private float discountedPrice;

}