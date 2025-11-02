package ar.edu.unju.escmi.tp8.main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import ar.edu.unju.escmi.config.EmfSingleton;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = EmfSingleton.getInstance().getEmf();
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("Intentando crear EntityManagerFactory...");
            // Usa el mismo nombre que está en persistence.xml: <persistence-unit name="TP8-TestPersistence">
            System.out.println("EntityManagerFactory creado correctamente");
            
            System.out.println("EntityManager creado correctamente");
            System.out.println("¿EntityManager está abierto? " + em.isOpen());
            
            // Como tienes hibernate.hbm2ddl.auto=create-drop, aquí se crearán las tablas
            // Verás el SQL en la consola porque tienes hibernate.show_sql=true
            
        } catch (Exception e) {
            System.err.println("Error al crear EntityManager:");
            e.printStackTrace();
        } finally {
            System.out.println("Cerrando recursos...");
            if (em != null && em.isOpen()) {
                em.close();
                System.out.println("EntityManager cerrado");
            }
            if (emf != null) {
                emf.close();
                System.out.println("EntityManagerFactory cerrado");
            }
        }
    }
}