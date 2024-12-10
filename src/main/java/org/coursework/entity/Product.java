package org.coursework.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Material> materials = new ArrayList<>();

    public Product(String name, List<Material> materials) {
        this.name = name;
        this.materials = materials;
    }

    public Product() {

    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", materials=" + materials.stream()
                .map(material -> List.of(material.getName(), material.getPrice(), material.getQuantity()))
                .collect(Collectors.toList()) +
                '}';
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }
}
