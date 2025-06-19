package Repository;

import EntityDTO.DoctorDTO;
import Service.RepositoryService;

import java.sql.*;
import java.util.*;

public class DoctorRepository {
    public void save(DoctorDTO d) {
        String sql = "INSERT INTO doctors (doc_id, doc_name, specialization, available_start_time, available_end_time) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, d.getDocId());
            ps.setString(2, d.getDocName());
            ps.setString(3, d.getSpecialization());
            ps.setTime(4, Time.valueOf(d.getAvailableStartTime()));
            ps.setTime(5, Time.valueOf(d.getAvailableEndTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving doctor " + d.getDocId(), e);
        }
    }

    public List<DoctorDTO> findAll() {
        List<DoctorDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new DoctorDTO(
                        rs.getString("doc_id"),
                        rs.getString("doc_name"),
                        rs.getString("specialization"),
                        rs.getTime("available_start_time").toLocalTime(),
                        rs.getTime("available_end_time").toLocalTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading doctors", e);
        }
        return list;
    }

    public Optional<DoctorDTO> findById(String id) {
        String sql = "SELECT * FROM doctors WHERE doc_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new DoctorDTO(
                            rs.getString("doc_id"),
                            rs.getString("doc_name"),
                            rs.getString("specialization"),
                            rs.getTime("available_start_time").toLocalTime(),
                            rs.getTime("available_end_time").toLocalTime()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding doctor " + id, e);
        }
        return Optional.empty();
    }

    public void update(DoctorDTO d) {
        String sql = """
            UPDATE doctors
               SET doc_name = ?, specialization = ?, available_start_time = ?, available_end_time = ?
             WHERE doc_id = ?
        """;
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, d.getDocName());
            ps.setString(2, d.getSpecialization());
            ps.setTime(3, Time.valueOf(d.getAvailableStartTime()));
            ps.setTime(4, Time.valueOf(d.getAvailableEndTime()));
            ps.setString(5, d.getDocId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating doctor " + d.getDocId(), e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM doctors WHERE doc_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting doctor " + id, e);
        }
    }
}
