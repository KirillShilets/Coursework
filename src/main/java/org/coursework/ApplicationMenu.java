package org.coursework;

import org.coursework.entity.Material;
import org.coursework.entity.Product;
import org.coursework.exception.ProductNotFoundException;
import org.coursework.service.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ApplicationMenu {

    private final static Service service = new Service();
    private final static Scanner scanner = new Scanner(System.in);

    public static void showMenu() {
        int choice;
        do {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить свой продукт.");
            System.out.println("2. Вывести все продукты.");
            System.out.println("3. Вывести все материалы.");
            System.out.println("4. Рассчитать стоимость производимой продукции.");
            System.out.println("5. Выход.");
            System.out.print("Выберите пункт меню: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Некорректный ввод. Пожалуйста, введите число от 1 до 4.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createProduct();
                    break;
                case 2:
                    showAllProducts();
                    break;
                case 3:
                    showAllMaterials();
                    break;
                case 4:
                    calculateProductionCost();
                    break;
                case 5:
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Некорректный пункт меню.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void createProduct() {
        String productName = "";
        while (productName.isEmpty() || productName.matches(".*\\d+.*")) {
            System.out.println("Введите имя продукта: ");
            productName = scanner.nextLine().trim();
            if (productName.isEmpty()) {
                System.out.println("Имя продукта не может быть пустым!");
            } else if (productName.matches(".*\\d+.*")) {
                System.out.println("Имя продукта не может содержать цифры!");
            }
        }


        List<Material> materialList = new ArrayList<>();
        materialList.add(createMaterial());

        int choice;
        do {
            System.out.println("Есть ли еще материал для этого продукта?: ");
            System.out.println("1. Да");
            System.out.println("2. Нет");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        materialList.add(createMaterial());
                        break;
                    case 2:
                        System.out.println("Выход из ввода материалов.");
                        break;
                    default:
                        System.out.println("Некорректный пункт меню. Пожалуйста, введите 1 или 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод. Пожалуйста, введите число 1 или 2.");
                scanner.next();
                choice = 0;
            }
        } while (choice != 2);

        Product product = new Product(productName, materialList);
        service.saveProduct(product, materialList);
        System.out.println("Продукт успешно добавлен!");
        showMenu();
    }

    private static Material createMaterial() {
        String materialName;
        do {
            System.out.println("Введите имя материала: ");
            materialName = scanner.nextLine().trim();
            if (materialName.isEmpty()) {
                System.out.println("Имя материала не может быть пустым!");
            } else if (materialName.matches(".*\\d+.*")) {
                System.out.println("Имя материала не может содержать цифры!");
            }
        } while (materialName.isEmpty() || materialName.matches(".*\\d+.*"));

        int materialQuantity = 0;
        do {
            System.out.println("Введите его количество (целое число больше 0): ");
            try {
                materialQuantity = scanner.nextInt();
                if (materialQuantity <= 0) {
                    System.out.println("Количество материала должно быть больше 0!");
                }
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод. Пожалуйста, введите целое число.");
                scanner.next();
            }
        } while (materialQuantity <= 0);


        double materialPrice = 0;
        do {
            System.out.println("Введите цену за 1 ед. материала (число больше 0): ");
            try {
                materialPrice = scanner.nextDouble();
                if (materialPrice <= 0) {
                    System.out.println("Цена материала должна быть больше 0!");
                }
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод. Пожалуйста, введите число.");
                scanner.next();
            }
        } while (materialPrice <= 0);

        return new Material(materialName, materialQuantity, materialPrice);
    }

    private static void showAllProducts() {
        System.out.println("Список продуктов:");
        List<Product> productList = service.getAllProducts();
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    private static void showAllMaterials() {
        System.out.println("Список материалов:");
        List<Material> materialList = service.getAllMaterials();
        for (Material material : materialList) {
            System.out.println(material);
        }
    }

    private static void calculateProductionCost() {
        System.out.println("Введите id продукта, у которого хотите расчитать стоимость: ");

        while (!scanner.hasNextLong()) {
            System.out.println("Некорректный ввод. Пожалуйста, введите целое число.");
            scanner.next();
        }
        Long id = scanner.nextLong();

        try {
            Product product = service.getProductById(id);
            System.out.println(service.calculationPriceOfMaterials(product));
        } catch (Exception e) {
            throw new ProductNotFoundException("Продукт с таким id не найден.");
        }
    }
}
