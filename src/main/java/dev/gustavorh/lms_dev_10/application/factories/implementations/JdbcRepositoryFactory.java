package dev.gustavorh.lms_dev_10.application.factories.implementations;

import dev.gustavorh.lms_dev_10.domain.entities.Author;
import dev.gustavorh.lms_dev_10.domain.entities.Book;
import dev.gustavorh.lms_dev_10.domain.entities.Category;
import dev.gustavorh.lms_dev_10.domain.entities.Loan;
import dev.gustavorh.lms_dev_10.domain.entities.Member;
import dev.gustavorh.lms_dev_10.domain.entities.Permission;
import dev.gustavorh.lms_dev_10.domain.entities.Role;
import dev.gustavorh.lms_dev_10.domain.entities.RolePermissions;
import dev.gustavorh.lms_dev_10.domain.entities.RoleUsers;
import dev.gustavorh.lms_dev_10.domain.entities.Status;
import dev.gustavorh.lms_dev_10.application.factories.interfaces.IRepositoryFactory;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.implementations.JdbcAuthorRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.implementations.JdbcBookRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.implementations.JdbcCategoryRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.implementations.JdbcLoanRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.implementations.JdbcMemberRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.implementations.JdbcPermissionRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.implementations.JdbcRolePermissionsRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.implementations.JdbcRoleRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.implementations.JdbcRoleUsersRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.implementations.JdbcStatusRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.implementations.JdbcUserRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.implementations.MemberActivityReportRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.interfaces.IRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.interfaces.IUserRepository;

import java.sql.Connection;

public class JdbcRepositoryFactory implements IRepositoryFactory {
    private final Connection connection;

    public JdbcRepositoryFactory(Connection connection) {
        this.connection = connection;
    }


    @Override
    public IUserRepository createUserRepository() {
        return new JdbcUserRepository(connection);
    }

    @Override
    public IRepository<Book> createBookRepository() {
        return new JdbcBookRepository(connection);
    }

    @Override
    public IRepository<Author> createAuthorRepository() {
        return new JdbcAuthorRepository(connection);
    }

    @Override
    public IRepository<Category> createCategoryRepository() {
        return new JdbcCategoryRepository(connection);
    }

    @Override
    public IRepository<Member> createMemberRepository() { return new JdbcMemberRepository(connection); }

    @Override
    public IRepository<Status> createStatusRepository() { return new JdbcStatusRepository(connection); }

    @Override
    public IRepository<Role> createRoleRepository() { return new JdbcRoleRepository(connection); }

    @Override
    public IRepository<Permission> createPermissionRepository() { return new JdbcPermissionRepository(connection); }

    @Override
    public IRepository<Loan> createLoanRepository() { return new JdbcLoanRepository(connection); }

    @Override
    public IRepository<RolePermissions> createRolePermissionsRepository() { return new JdbcRolePermissionsRepository(connection); }

    @Override
    public IRepository<RoleUsers> createRoleUsersRepository() { return new JdbcRoleUsersRepository(connection); }

    @Override
    public MemberActivityReportRepository createMemberActivityReportRepository() { return new MemberActivityReportRepository(connection); }
}
