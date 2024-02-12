import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("Математика", 50));
        courseList.add(new Course("Физика", 35));
        courseList.add(new Course("Химия", 40));

        // Create
        create(courseList);

        // Read
        System.out.println(read(1));

        // Update
        update(2);

        // Delete
        delete(3);
    }

    /**
     * Создает записи в таблице БД
     * @param courseList список курсов
     */
    public static void create(List<Course> courseList) {
        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory()) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            for (Course course : courseList) {
                session.save(course);
            }
            session.getTransaction().commit();
        }
    }

    /**
     * Считывает из таблицы БД курс с указанным id
     * @param id id курса
     * @return курс
     */
    public static Course read(int id) {
        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory()) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Course currentCourse = session.get(Course.class, id);
            session.getTransaction().commit();
            return currentCourse;
        }
    }

    /**
     * Обновляет данные в таблице БД: увеличивает продолжительность курса на 10
     * @param id id курса
     */
    public static void update(int id) {
        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory()) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Course curentCourse = session.get(Course.class, id);
            curentCourse.setDuration(curentCourse.getDuration() + 10);
            session.update(curentCourse);
            session.getTransaction().commit();
        }
    }

    /**
     * Удаляет курс в таблице БД
     * @param id id курса
     */
    public static void delete(int id) {
        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory()) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Course curentCourse = session.get(Course.class, id);
            session.delete(curentCourse);
            session.getTransaction().commit();
        }
    }

}
