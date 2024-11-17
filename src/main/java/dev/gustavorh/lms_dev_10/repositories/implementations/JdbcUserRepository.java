package dev.gustavorh.lms_dev_10.repositories.implementations;

import dev.gustavorh.lms_dev_10.entities.User;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IUserRepository;
import dev.gustavorh.lms_dev_10.utils.UserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcUserRepository implements IUserRepository {
    private Connection connection;
    private final UserMapper userMapper;

    public JdbcUserRepository(Connection connection) {
        this.connection = connection;
        this.userMapper = new UserMapper();
    }

    private static final String FIND_ALL = "SELECT * FROM Usuarios ORDER BY id_usuario ASC";
    private static final String FIND_BY_ID = "SELECT * FROM Usuarios WHERE id_usuario = ?";
    private static final String FIND_BY_USER_NAME = "SELECT * FROM Usuarios WHERE usuario = ?";
    private static final String UPDATE_BY_ID = "UPDATE Usuarios SET usuario = ?, clave = ? WHERE id_usuario = ?";
    private static final String INSERT = "INSERT INTO Usuarios (usuario, clave) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM Usuarios WHERE id_usuario = ?";

    @Override
    public User findByUserName(String userName) throws SQLException {
        User user = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_USER_NAME)) {
            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = userMapper.mapRow(rs);
                }
            }
        }
        return user;
    }

    @Override
    public User findById(Long id) throws SQLException {
        return null;
    }

    @Override
    public List<User> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public void save(User entity) throws SQLException {

    }

    @Override
    public void update(User entity) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }
}
