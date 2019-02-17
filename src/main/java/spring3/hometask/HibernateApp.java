package spring3.hometask;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import spring3.hometask.entities.Ad;
import spring3.hometask.entities.Category;
import spring3.hometask.entities.Company;

import java.util.List;

public class HibernateApp {
    private static void fillDB(Session session) {

        //companies

        // GeekBrains
        Company company1 = new Company();
        company1.setName("GeekBrains");
        company1.setDescription("java_lessons");
        company1.setAddress("Moscow, Leningrad prospect");

        //Glowbyte Consulting
        Company company2 = new Company();
        company2.setName("Glowbyte_Consulting");
        company2.setDescription("DateWareHouses");
        company2.setAddress("Moscow, Kurskay underground");

        session.save(company1);
        session.save(company2);

        // Categories

        // Courses
        Category category1 = new Category();
        category1.setName("Courses");

        //Job offers
        Category category2 = new Category();
        category2.setName("Jobs");

        session.save(category1);
        session.save(category2);

        //ADS

        Ad ad1 = new Ad();
        ad1.setCompany(company1);
        ad1.setCategory(category1);
        ad1.setName("ad1");

        Ad ad2 = new Ad();
        ad2.setCompany(company1);
        ad2.setCategory(category2);
        ad2.setName("ad2");

        Ad ad3 = new Ad();
        ad3.setCompany(company2);
        ad3.setCategory(category1);
        ad3.setName("ad3");

        Ad ad4 = new Ad();
        ad4.setCompany(company2);
        ad4.setCategory(category2);
        ad4.setName("ad4");

        session.save(ad1);
        session.save(ad2);
        session.save(ad3);
        session.save(ad4);

        // session.getTransaction().commit();
    }

    // read all ads by geekbrains
    private static void task1(Session session) {
        List<Company> companyList = session.createQuery("from Company where name = 'GeekBrains'").getResultList();
        Company geekBrains = companyList.get(0);

        // Company geekBrains = session.get(Company.class, 1L);

        List<Ad> geekAds = geekBrains.getAds();
        for(Ad ad : geekAds) {
            System.out.println("GeekBrains ad: " + ad.getName() + " has category " + ad.getCategory().getName());
        }
    }

    // get company and category of ad4
    private static void task2(Session session) {
        List<Ad> adsList = session.createQuery("from Ad where name = 'ad4'").getResultList();
        Ad ad4 = adsList.get(0);


        System.out.println("ad4 has company " + ad4.getCompany().getName() +
                " and category " + ad4.getCategory().getName());
    }

    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hometask.cfg.xml")
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(Ad.class)
                .buildSessionFactory();

        Session session = null;

        try {
            session = factory.getCurrentSession();
            session.beginTransaction();

            // USE ONCE: filling the database
           // fillDB(session);

            task1(session);
            task2(session);

            session.getTransaction().commit();
        }
        finally {
            session.close();
            factory.close();
        }
    }
}
