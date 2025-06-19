package Repository;

import EntityDTO.TimeSlotDTO;
import Service.RepositoryService;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class TimeSlotRepository {
    public void save(TimeSlotDTO t) {
        String sql = """
            INSERT INTO timeslots (ts_id, date,  day_id, start_time, end_time)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, t.getTsId());
            ps.setDate(2, Date.valueOf(t.getDate()));
            ps.setString(3, t.getDayId());
            ps.setTime(4, Time.valueOf(t.getStartTime()));
            ps.setTime(5, Time.valueOf(t.getEndTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving timeslot " + t.getTsId(), e);
        }
    }

    public List<TimeSlotDTO> findByDayId(String dayId) {
        List<TimeSlotDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM timeslots WHERE day_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, dayId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new TimeSlotDTO(
                            rs.getString("ts_id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getString("day_id"),
                            rs.getTime("start_time").toLocalTime(),
                            rs.getTime("end_time").toLocalTime()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading timeslots for day " + dayId, e);
        }
        return list;
    }

    public void update(TimeSlotDTO t) {
        String sql = "UPDATE timeslots SET day_id = ? WHERE ts_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, t.getDayId());
            ps.setString(2, t.getTsId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating timeslot " + t.getTsId(), e);
        }
    }

    public void deleteByDayId(String dayId) {
        String sql = "DELETE FROM timeslots WHERE day_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, dayId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting timeslots for day " + dayId, e);
        }
    }
}
