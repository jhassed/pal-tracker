package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {


    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry te = timeEntryRepository.create(timeEntryToCreate);
        if( te != null ) {
            ResponseEntity<TimeEntry> response = new ResponseEntity(te, HttpStatus.CREATED);
            return response;
        }
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity read(@PathVariable("id") long timeEntryId) {
        ResponseEntity<TimeEntry> response;
        TimeEntry te = timeEntryRepository.find(timeEntryId);
        if( te != null ) {
            response = new ResponseEntity(te, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping
    public ResponseEntity list() {
        ResponseEntity<List<TimeEntry>> response;
        List<TimeEntry> entries = timeEntryRepository.list();
        if (entries != null) {
            response = new ResponseEntity(entries, HttpStatus.OK);
        } else {
            response = new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") long timeEntryId, @RequestBody TimeEntry expected) {
        ResponseEntity<TimeEntry> response;
        TimeEntry te = timeEntryRepository.update(timeEntryId, expected);
        if (te != null) {
            response = new ResponseEntity(te, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") long timeEntryId) {
        ResponseEntity<TimeEntry> response;
        timeEntryRepository.delete(timeEntryId);
        response = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return response;
    }
}
