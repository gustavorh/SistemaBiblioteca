package dev.gustavorh.lms_dev_10.repositories.implementations;

import dev.gustavorh.lms_dev_10.entities.Author;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IRepository;
import dev.gustavorh.lms_dev_10.utils.AuthorMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcAuthorRepository implements IRepository<Author> {
    private final Connection connection;
    private final AuthorMapper authorMapper;

    public JdbcAuthorRepository(Connection connection) {
        this.connection = connection;
        this.authorMapper = new AuthorMapper();
    }

    private static final String FIND_ALL = "SELECT * FROM Autores ORDER BY id_autor ASC";
    private static final String FIND_BY_ID = "SELECT * FROM Autores WHERE id_autor = ?";
    private static final String UPDATE_BY_ID = "UPDATE Autores SET nombre = ?, apellido = ?, nombre_completo = ? WHERE id_autor = ?";
    private static final String INSERT = "INSERT INTO Autores (nombre, apellido, nombre_completo) VALUES (?, ?, ?)";
    private static final String DELETE = "DELETE FROM Autores WHERE id_autor = ?";

    @Override
    public Author findById(Long id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return authorMapper.mapRow(rs);
                }
                // TODO: Cast to optional in case ID is not found.
                return null;
            }
        }
    }

    @Override
    public List<Author> findAll() throws SQLException {
        List<Author> authors = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(FIND_ALL)) {
            while (rs.next()) {
                authors.add(authorMapper.mapRow(rs));
            }
        }
        return authors;
    }

    @Override
    public void save(Author entity) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, entity.getAuthorId());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getSurname());
            ps.setString(4, entity.getFullName());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating author failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setAuthorId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating author failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public void update(Author entity) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setLong(1, id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting author failed, no rows affected.");
            }
        }
    }
}
