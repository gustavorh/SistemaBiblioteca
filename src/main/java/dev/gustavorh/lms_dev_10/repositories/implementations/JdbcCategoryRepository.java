package dev.gustavorh.lms_dev_10.repositories.implementations;

import dev.gustavorh.lms_dev_10.entities.Category;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IRepository;
import dev.gustavorh.lms_dev_10.utils.CategoryMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcCategoryRepository implements IRepository<Category> {
    private final Connection connection;
    private final CategoryMapper categoryMapper;

    public JdbcCategoryRepository(Connection connection) {
        this.connection = connection;
        this.categoryMapper = new CategoryMapper();
    }

    private static final String FIND_ALL = "SELECT * FROM Categorias ORDER BY id_categoria ASC";
    private static final String FIND_BY_ID = "SELECT * FROM Categorias WHERE id_categoria = ?";
    private static final String UPDATE_BY_ID = "UPDATE Categorias SET nombre = ? WHERE id_categoria = ?";
    private static final String INSERT = "INSERT INTO Categorias (nombre) VALUES (?)";
    private static final String DELETE = "DELETE FROM Categorias WHERE id_categoria = ?";

    @Override
    public Category findById(Long id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return categoryMapper.mapRow(rs);
                }
                // TODO: Cast to optional in case ID is not found.
                return null;
            }
        }
    }

    @Override
    public List<Category> findAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(FIND_ALL)) {
            while (rs.next()) {
                categories.add(categoryMapper.mapRow(rs));
            }
        }
        return categories;
    }

    @Override
    public void save(Category entity) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, entity.getCategoryId());
            ps.setString(2, entity.getName());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating category failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setCategoryId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating category failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public void update(Category entity) {

    }

    @Override
    public void delete(Long id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setLong(1, id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting category failed, no rows affected.");
            }
        }
    }
}
