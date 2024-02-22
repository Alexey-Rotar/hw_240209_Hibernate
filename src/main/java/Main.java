import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

        // Чтение коллекции данных
        readAll();

        // Update
        update(2);

        // Delete
        //delete(4);
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

    public static void readAll() {
        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory()) {
            var entityManager = sessionFactory.createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
            Root<Course> notificationRoot = criteriaQuery.from(Course.class);
            TypedQuery<Course> query = entityManager.createQuery(criteriaQuery);
            List<Course> studentsList = query.getResultList();
            for (Course student : studentsList) {
                System.out.println(student);
            }
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

    // ---------------------- ПРИМЕР --------------------------

//    try (sessionFactory) {
//        Session session = sessionFactory.openSession();
//        // Начало транзакции
//        var transaction = session.beginTransaction();
//
//        // Создание объекта
//        Student student1 = Student.create();
//        Student student2 = Student.create();
//        Student student3 = Student.create();
//
//        // Сохранение объекта в базе данных
//        session.save(student1);
//        session.save(student2);
//        session.save(student3);
//        System.out.println("Object student save successfully");
//
//        // Чтение объекта из базы данных
//        Student retrievedStudent = session.get(Student.class, student1.getId());
//        System.out.println("Object student retrieved successfully");
//        System.out.println("Retrieved student object: " + retrievedStudent);
//
//        // Обновление объекта
//        retrievedStudent.updateName();
//        retrievedStudent.updateAge();
//        session.update(retrievedStudent);
//        System.out.println("Object student update successfully");
//
//        // Коммит (завершение/подтверждение изменений) транзакции
//        transaction.commit();
//
//        // Чтение коллекции данных
//        var entityManager = sessionFactory.createEntityManager();
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
//        Root<Student> notificationRoot = criteriaQuery.from(Student.class);
//        TypedQuery<Student> query = entityManager.createQuery(criteriaQuery);
//        List<Student> studentsList = query.getResultList();
//        for (Student student : studentsList) {
//            System.out.println(student);
//        }
//
//        // Начало транзакции
//        transaction = session.beginTransaction();
//
//        // Удаление объекта
//        session.delete(retrievedStudent);
//        System.out.println("Object student delete successfully");
//
//        // Коммит (завершение/подтверждение изменений) транзакции
//        transaction.commit();
//        System.out.println("Transaction commit successfully");
//
//    }



}
