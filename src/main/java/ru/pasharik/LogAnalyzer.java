package ru.pasharik;

import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pasharik.domain.LogEvent;
import ru.pasharik.service.LogEventService;
import ru.pasharik.service.ValidationService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class LogAnalyzer {
    final static Logger log = LoggerFactory.getLogger(LogAnalyzer.class);
    private Map<String, LogEvent> map = new HashMap<>();

    private SessionFactory sessionFactory;
    private LogEventService logEventService;
    private Gson gson;
    private ValidationService validationService;

    public LogAnalyzer(SessionFactory sessionFactory, LogEventService logEventService, Gson gson, ValidationService validationService) {
        this.sessionFactory = sessionFactory;
        this.logEventService = logEventService;
        this.gson = gson;
        this.validationService = validationService;
    }

    public void analyze(String[] args) {
        String fileName = validationService.getFileName(args);
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(this::processLine);
        } catch (IOException e) {
            log.error("Error while processing input file", e);
        }
        sessionFactory.close();
    }

    private void processLine(String line) {
        LogEvent event = gson.fromJson(line, LogEvent.class);
        String id = event.getId();
        if (map.containsKey(id)) {
            LogEvent eventFromMap = map.get(id);
            if (eventFromMap.isFinished()) {
                processEvent(event, eventFromMap);
            } else {
                processEvent(eventFromMap, event);
            }
            map.remove(id);
        } else {
            map.put(id, event);
        }
    }

    private void processEvent(LogEvent startEvent, LogEvent finishEvent) {
        Long time = finishEvent.getTimestamp() - startEvent.getTimestamp();
        finishEvent.setDuration(time);
        if (finishEvent.isAlert()) {
            log.info(String.format("Event took %s ms. ID: %s", time, finishEvent.getId()));
        }
        logEventService.save(finishEvent);
    }
}
