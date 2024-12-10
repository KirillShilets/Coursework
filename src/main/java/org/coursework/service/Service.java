package org.coursework.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.coursework.entity.Material;
import org.coursework.entity.Product;
import org.coursework.exception.ProductNotFoundException;

import java.util.List;

import static org.coursework.utils.EntityManagerUtil.getEntityManager;

public class Service {

    @PersistenceContext
    private EntityManager entityManager = getEntityManager();

    public void saveProduct(Product product, List<Material> materials) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(product);

            for (Material material : materials) {
                material.setProduct(product);
                entityManager.persist(material);
            }
            entityManager.getTransaction().commit();
        } catch(Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    public double calculationPriceOfMaterials(Product product) {
        try {
            entityManager.getTransaction().begin();
            List<Material> materials = product.getMaterials();
            double sum = 0;
            for (Material material : materials) {
                sum += material.getPrice() * material.getQuantity();
            }
            entityManager.getTransaction().commit();
            return sum;
        } catch(Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    public List<Product> getAllProducts() {
        try {
            entityManager.getTransaction().begin();
            List<Product> products = entityManager.createQuery("from Product").getResultList();
            entityManager.getTransaction().commit();
            return products;
        } catch(Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    public List<Material> getAllMaterials() {
        try {
            entityManager.getTransaction().begin();
            List<Material> materials = entityManager.createQuery("from Material").getResultList();
            entityManager.getTransaction().commit();
            return materials;
        } catch(Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    public Product getProductById(Long id) {
        try {
            return entityManager.find(Product.class, id);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new ProductNotFoundException("Продукт с таким id не найден");
        }
    }


}
