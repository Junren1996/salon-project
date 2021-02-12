package edu.uh.tech.cis3368.semesterproject;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class Product {

    private int id;
    private String name;
    private String description;
    private List<ProductComponent> productComponents = new ArrayList<>();


    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Product(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description", nullable=false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 24)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public BigDecimal wholesaleCost() {
        return BigDecimal.ZERO;
    }

    private Collection<ProductComponent> productComponent;

    @OneToMany(mappedBy = "product")
    public Collection<ProductComponent> getProductComponent() {
        return productComponent;
    }

    public void setProductComponent(Collection<ProductComponent> productComponent) {
        this.productComponent = productComponent;
    }


    public void setProductComponents(List<ProductComponent> productComponents) {
        this.productComponents = productComponents;
    }

    public void addComponent(Component component, int quantity) {
        ProductComponent productComponent =
                new ProductComponent(quantity, component, this);
        productComponents.add(productComponent);
        component.getProductComponents().add(productComponent);

    }
}
