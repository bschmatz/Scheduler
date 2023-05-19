package com.example.javafxscheduler.util;

import com.example.javafxscheduler.entities.Event;
import com.example.javafxscheduler.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class UserUtil {

    public User getUserByName(String userName) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();

        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User u where name = '" + userName + "'", User.class);
        User user = (User) query.list().get(0);
        transaction.commit();
        session.close();

        return user;
    }

    public ArrayList<User> getAllUsers() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();

        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User", User.class);
        ArrayList<User> users = (ArrayList<User>) query.list();
        transaction.commit();
        session.close();

        return users;
    }

    public ArrayList<Event> getUserEvents(User user) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        ArrayList<Event> events;

        Transaction transaction = session.beginTransaction();
        String query = "SELECT e FROM Event e, User u, EventRegistration er WHERE u.userId = er.studentId AND er.eventId = e.eventId AND u.userId = " + user.getUserId();
        events = (ArrayList<Event>) session.createQuery(query).list();
        transaction.commit();
        session.close();

        System.out.println(events);

        return events;
    }
}
