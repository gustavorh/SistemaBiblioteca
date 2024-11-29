package dev.gustavorh.lms_dev_10.infrastructure.repositories.implementations;

import dev.gustavorh.lms_dev_10.domain.entities.Status;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.interfaces.IRepository;
import dev.gustavorh.lms_dev_10.utils.mappers.IRowMapper;
import dev.gustavorh.lms_dev_10.utils.mappers.StatusMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcStatusRepository implements IRepository<Status> {
    private final Connection connection;
    private final IRowMapper<Status> statusMapper;

    public JdbcStatusRepository(Connection connection) {
        this.connection = connection;
        this.statusMapper = new StatusMapper();
    }

    private static final String FIND_ALL = "SELECT * FROM Estados";
    private static final String FIND_BY_ID = "SELECT * FROM Estados WHERE id_estado = ?";
    private static final String UPDATE_BY_ID = "UPDATE Estados SET nombre = ?, descripcion = ? WHERE id_estado = ?";
    private static final String INSERT = "INSERT INTO Estados (nombre, descripcion) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM Estados WHERE id_estado = ?";

    @Override
    public Optional<Status> findById(Long id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(statusMapper.mapRow(rs));
                }
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Status> findAll() throws SQLException {
        List<Status> statuses = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(FIND_ALL)) {
            while (rs.next()) {
                statuses.add(statusMapper.mapRow(rs));
            }
        }
        return statuses;
    }

    @Override
    public void save(Status entity) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating status failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setStatusId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating status failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public void update(Status entity) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_BY_ID)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
            ps.setLong(3, entity.getStatusId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating status failed, no rows affected.");
            }
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setLong(1, id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting status failed, no rows affected.");
            }
        }
    }
}