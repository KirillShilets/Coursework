package org.coursework.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerUtil {

    private static final EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("org.coursework.persistance");;

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && !entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}