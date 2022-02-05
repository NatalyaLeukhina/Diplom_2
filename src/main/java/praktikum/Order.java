package praktikum;

import com.fasterxml.jackson.annotation.JsonInclude;


public class Order {
    public String[] ingredients;
    public Order(String[] ingredients) {
        this.ingredients = ingredients;
    }

    }


