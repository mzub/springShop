package ru.geekbrains.repr;

import ru.geekbrains.model.Category;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryRepr implements Serializable {

    private Long id;

    private String name;

    private List<ProductRepr> products;

    public CategoryRepr() {
    }

    public CategoryRepr(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.products = category.getProducts().stream().map(ProductRepr::new).collect(Collectors.toList());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductRepr> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRepr> products) {
        this.products = products;
    }
}
