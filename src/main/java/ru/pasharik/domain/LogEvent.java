package ru.pasharik.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
public class LogEvent implements Serializable {
    @Id
    private String id;
    @Transient
    private LogEventState state;
    @Transient
    private Long timestamp;
    private Long duration;
    private String type;
    private String host;
    private boolean alert;

    public boolean isFinished() {
        return LogEventState.FINISHED.equals(state);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LogEventState getState() {
        return state;
    }

    public void setState(LogEventState state) {
        this.state = state;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
        if (duration > 4) {
            setAlert(true);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }
}
