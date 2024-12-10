package org.coursework;

import org.coursework.entity.Material;
import org.coursework.entity.Product;
import org.coursework.service.Service;

import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        defaultSave();
        ApplicationMenu.showMenu();
    }

    private static void defaultSave() {
        Service service = new Service();

        Material material = new Material("Дерево", 3, 14.88);
        Material material2 = new Material("Камень", 1, 9.99);
        Material material3 = new Material("Железо", 2, 10.00);
        Material material4 = new Material("Ткань", 5, 7.49);
        Material material5 = new Material("Шерсть", 2, 9.99);
        List<Material> listOfMaterials = Arrays.asList(material, material2,
                material3, material4, material5);

        Product product = new Product("Крепость", listOfMaterials);
        service.saveProduct(product,listOfMaterials);
    }
}

//Разработка системы расчета стоимости производимой продукции
