package ru.pasharik.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.pasharik.domain.LogEvent;

public class LogEventService {
    private SessionFactory sessionFactory;

    public LogEventService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(LogEvent event) {
        Session session = sessionFactory.openSession();
        //todo: check if can reuse the same session
        session.beginTransaction();


        session.save(event);
        session.getTransaction().commit();
    }
}
