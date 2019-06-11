package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long, TimeEntry> teMap = new HashMap<>();
    private long idCounter = 0;

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setTimeEntryId(++idCounter);
        teMap.put(idCounter,timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long id) {
        return (TimeEntry) teMap.get(id);
    }

    public List<TimeEntry> list() {

        return teMap.values().stream().collect(Collectors.toList());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {

        TimeEntry te = find(id);
        if (te == null) return null;
        te.setDate(timeEntry.getDate());
        te.setHours(timeEntry.getHours());
        te.setProjectId(timeEntry.getProjectId());
        te.setUserId(timeEntry.getUserId());
        return te;
    }

    public void delete(long id) {
        teMap.remove(id, find(id));
    }
}
