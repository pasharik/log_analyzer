package ru.pasharik;

import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.pasharik.service.LogEventService;
import ru.pasharik.service.ValidationService;

public class IoCFactory {
    private volatile SessionFactory sessionFactory;
    private volatile LogEventService logEventService;
    private volatile ValidationService validationService;

    public LogAnalyzer createApp() {
        return new LogAnalyzer(
                getSessionFactory(),
                getLogEventService(),
                getGson(),
                getValidationService());
    }

    private SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (IoCFactory.this) {
                Configuration cfg = new Configuration().configure(LogAnalyzer.class.getResource("/hibernate.cfg.xml"));
                sessionFactory = cfg.buildSessionFactory();
            }
        }
        return sessionFactory;
    }

    private LogEventService getLogEventService() {
        if (logEventService == null) {
            synchronized (IoCFactory.this) {
                logEventService = new LogEventService(getSessionFactory());
            }
        }
        return logEventService;
    }

    // Not sure if Gson is thread safe, so just return new instance every time
    private Gson getGson() {
        return new Gson();
    }

    private ValidationService getValidationService() {
        if (validationService == null) {
            synchronized (IoCFactory.this) {
                validationService = new ValidationService();
            }
        }
        return validationService;
    }
}
