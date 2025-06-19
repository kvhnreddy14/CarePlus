package Repository;

import EntityDTO.AppointmentDTO;
import Service.RepositoryService;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class AppointmentRepository {
    public void save(AppointmentDTO a) {
        String sql = """
            INSERT INTO appointments (appointment_id, doc_id, patient_id, ts_id)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.getAppointmentId());
            ps.setString(2, a.getDocId());
            ps.setString(3, a.getPatientId());
            ps.setString(4, a.getTsId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving appointment " + a.getAppointmentId(), e);
        }
    }

    public Optional<AppointmentDTO> findById(String id) {
        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new AppointmentDTO(
                            rs.getString("appointment_id"),
                            rs.getString("doc_id"),
                            rs.getString("patient_id"),
                            rs.getString("ts_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding appointment " + id, e);
        }
        return Optional.empty();
    }

    public void update(AppointmentDTO a) {
        String sql = "UPDATE appointments SET doc_id = ?, patient_id = ?, ts_id = ? WHERE appointment_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.getDocId());
            ps.setString(2, a.getPatientId());
            ps.setString(3, a.getTsId());
            ps.setString(4, a.getAppointmentId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating appointment " + a.getAppointmentId(), e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting appointment " + id, e);
        }
    }

    public List<AppointmentDTO> findAllFromDate(LocalDate fromDate) {
        List<AppointmentDTO> list = new ArrayList<>();
        String sql = """
            SELECT a.appointment_id, a.doc_id, a.patient_id, a.ts_id
            FROM appointments a
            JOIN timeslots t ON a.ts_id = t.ts_id
            WHERE t.date >= ?       
        """;
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fromDate));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new AppointmentDTO(
                            rs.getString("appointment_id"),
                            rs.getString("doc_id"),
                            rs.getString("patient_id"),
                            rs.getString("ts_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving appointments from " + fromDate, e);
        }
        return list;
    }
}
