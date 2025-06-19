package Repository;

import EntityDTO.ReceptionistDTO;
import Service.RepositoryService;

import java.sql.*;
import java.util.*;

public class ReceptionistRepository {
    public void save(ReceptionistDTO r) {
        String sql = "INSERT INTO receptionists (receptionist_id, receptionist_name) VALUES (?, ?)";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, r.getReceptionistId());
            ps.setString(2, r.getReceptionistName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving receptionist " + r.getReceptionistId(), e);
        }
    }

    public List<ReceptionistDTO> findAll() {
        List<ReceptionistDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM receptionists";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ReceptionistDTO(
                        rs.getString("receptionist_id"),
                        rs.getString("receptionist_name")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading receptionists", e);
        }
        return list;
    }

    public Optional<ReceptionistDTO> findById(String id) {
        String sql = "SELECT * FROM receptionists WHERE receptionist_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new ReceptionistDTO(
                            rs.getString("receptionist_id"),
                            rs.getString("receptionist_name")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding receptionist " + id, e);
        }
        return Optional.empty();
    }

    public void update(ReceptionistDTO r) {
        String sql = "UPDATE receptionists SET receptionist_name = ? WHERE receptionist_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, r.getReceptionistName());
            ps.setString(2, r.getReceptionistId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating receptionist " + r.getReceptionistId(), e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM receptionists WHERE receptionist_id = ?";
        try (Connection c = RepositoryService.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting receptionist " + id, e);
        }
    }
}
