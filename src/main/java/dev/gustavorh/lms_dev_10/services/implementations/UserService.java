package dev.gustavorh.lms_dev_10.services.implementations;

import dev.gustavorh.lms_dev_10.entities.User;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IUserRepository;
import dev.gustavorh.lms_dev_10.services.interfaces.IUserService;
import dev.gustavorh.lms_dev_10.utils.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        try {
            return userRepository.findById(id);
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving book with id: " + id, e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return userRepository.findAll();
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving all books", e);
        }
    }

    @Override
    public void save(User entity) {
        try {
            userRepository.save(entity);
        } catch (SQLException e) {
            throw new ServiceException("Error saving book", e);
        }
    }

    @Override
    public void update(User entity) {
        try {
            userRepository.update(entity);
        } catch (SQLException e) {
            throw new ServiceException("Error updating book", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            userRepository.delete(id);
        } catch (SQLException e) {
            throw new ServiceException("Error deleting book with id: " + id, e);
        }
    }

    @Override
    public User findByUserName(String userName) {
        try {
            return userRepository.findByUserName(userName);
        } catch (SQLException e) {
            throw new ServiceException("Error deleting user with username: " + userName, e);
        }
    }
}
