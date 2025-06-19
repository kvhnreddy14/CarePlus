package Repository;

import EntityDTO.PatientDTO;
import Service.RepositoryService;

import java.sql.*;
import java.util.*;

public class PatientRepository {
    public void save(PatientDTO p) {
        String sql = """
            INSERT INTO patients (patient_id, patient_name, patient_age, patient_phone_num, problem_specialization)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getPatientId());
            ps.setString(2, p.getPatientName());
            ps.setInt(3, p.getPatientAge());
            ps.setString(4, p.getPatientPhoneNum());
            ps.setString(5, p.getProblemSpecialization());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving patient " + p.getPatientId(), e);
        }
    }

    public List<PatientDTO> findAll() {
        List<PatientDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new PatientDTO(
                        rs.getString("patient_id"),
                        rs.getString("patient_name"),
                        rs.getInt("patient_age"),
                        rs.getString("patient_phone_num"),
                        rs.getString("problem_specialization")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading patients", e);
        }
        return list;
    }

    public Optional<PatientDTO> findById(String id) {
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new PatientDTO(
                            rs.getString("patient_id"),
                            rs.getString("patient_name"),
                            rs.getInt("patient_age"),
                            rs.getString("patient_phone_num"),
                            rs.getString("problem_specialization")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding patient " + id, e);
        }
        return Optional.empty();
    }

    public void update(PatientDTO p) {
        String sql = """
            UPDATE patients
               SET patient_name = ?, patient_age = ?, patient_phone_num = ?, problem_specialization = ?
             WHERE patient_id = ?
        """;
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getPatientName());
            ps.setInt(2, p.getPatientAge());
            ps.setString(3, p.getPatientPhoneNum());
            ps.setString(4, p.getProblemSpecialization());
            ps.setString(5, p.getPatientId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating patient " + p.getPatientId(), e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting patient " + id, e);
        }
    }
}
