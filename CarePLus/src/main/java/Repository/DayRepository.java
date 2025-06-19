package Repository;

import EntityDTO.DayDTO;
import Service.RepositoryService;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DayRepository {
    public void save(DayDTO d) {
        String sql = """
            INSERT INTO days (day_id, doc_id, date, start_time, end_time)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, d.getDayId());
            ps.setString(2, d.getDocId());
            ps.setDate(3, Date.valueOf(d.getDate()));
            ps.setTime(4, Time.valueOf(d.getStartTime()));
            ps.setTime(5, Time.valueOf(d.getEndTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving day " + d.getDayId(), e);
        }
    }

    public List<DayDTO> findAllByDoctorId(String docId) {
        List<DayDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM days WHERE doc_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, docId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new DayDTO(
                            rs.getString("day_id"),
                            rs.getString("doc_id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getTime("start_time").toLocalTime(),
                            rs.getTime("end_time").toLocalTime()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading days for doctor " + docId, e);
        }
        return list;
    }

    public void update(DayDTO d) {
        String sql = """
            UPDATE days
            SET date = ?, start_time = ?, end_time = ?
             WHERE day_id = ?
        """;
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(d.getDate()));
            ps.setTime(2, Time.valueOf(d.getStartTime()));
            ps.setTime(3, Time.valueOf(d.getEndTime()));
            ps.setString(4, d.getDayId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating day " + d.getDayId(), e);
        }
    }

    public void deleteById(String dayId) {
        String sql = "DELETE FROM days WHERE day_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, dayId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting day " + dayId, e);
        }
    }
}
