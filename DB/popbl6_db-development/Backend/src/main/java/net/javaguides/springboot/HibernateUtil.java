package net.javaguides.springboot;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
 
public class HibernateUtil {
    
    /** 
     * Get the current sessions 
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
         
        SessionFactory factory = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
         
        return factory;
    }
}