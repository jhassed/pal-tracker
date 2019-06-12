package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry te) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
                    PreparedStatement statement = con.prepareStatement("INSERT INTO time_entries (project_id, user_id, date, hours)" +
                                    "VALUES (?, ?, ?, ?)",
                            RETURN_GENERATED_KEYS
                    );

                    statement.setLong(1, te.getProjectId());
                    statement.setLong(2, te.getUserId());
                    statement.setDate(3, Date.valueOf(te.getDate()));
                    statement.setInt(4, te.getHours());

                    return statement;
                }, generatedKeyHolder);

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return jdbcTemplate.query(
                "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?",
                new Object[]{timeEntryId},
                extractor);
    }

    @Override
    public List<TimeEntry> list() {
        return jdbcTemplate.query("SELECT id, project_id, user_id, date, hours FROM time_entries",
                    mapper);
    }

    @Override
    public TimeEntry update(long timeEntryId, TimeEntry te) {
        jdbcTemplate.update( con -> {
            PreparedStatement statement = con.prepareStatement("UPDATE time_entries SET project_id = ?, user_id = ?, date = ?, hours = ? WHERE id = ?"
            );

            statement.setLong(1, te.getProjectId());
            statement.setLong(2, te.getUserId());
            statement.setDate(3, Date.valueOf(te.getDate()));
            statement.setInt(4, te.getHours());
            statement.setLong(5, timeEntryId);

            return statement;
        });

        return find(timeEntryId);
    }

    @Override
    public void delete(long timeEntryId) {
        jdbcTemplate.update( con -> {
            PreparedStatement statement = con.prepareStatement("DELETE FROM time_entries WHERE id = ?");

            statement.setLong(1, timeEntryId);

            return statement;
        });
    }

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}
