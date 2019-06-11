package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Objects;

public class TimeEntry {
    public TimeEntry() {

    }

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

    public long getTimeEntryId() {
        return timeEntryId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setTimeEntryId(long timeEntryId) {
        this.timeEntryId = timeEntryId;
    }

    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;
    private long timeEntryId;

    public TimeEntry(long projectId, long userId, LocalDate date, int hours) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    @Deprecated //Test purposes only
    public TimeEntry(long timeEntryId, long projectId, long userId, LocalDate date, int hours) {
        this(projectId, userId, date, hours);
        this.timeEntryId = timeEntryId;
    }

    public long getId() {
        return timeEntryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeEntry timeEntry = (TimeEntry) o;
        return projectId == timeEntry.projectId &&
                userId == timeEntry.userId &&
                hours == timeEntry.hours &&
                timeEntryId == timeEntry.timeEntryId &&
                date.equals(timeEntry.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, userId, date, hours, timeEntryId);
    }

    @Override
    public String toString() {
        return "TimeEntry{" +
                "projectId=" + projectId +
                ", userId=" + userId +
                ", date=" + date +
                ", hours=" + hours +
                ", timeEntryId=" + timeEntryId +
                '}';
    }
}
