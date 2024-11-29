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
import dev.gustavorh.lms_dev_10.application.factories.interfaces.IServiceFactory;
import dev.gustavorh.lms_dev_10.application.services.implementations.AuthService;
import dev.gustavorh.lms_dev_10.application.services.implementations.AuthorService;
import dev.gustavorh.lms_dev_10.application.services.implementations.BookService;
import dev.gustavorh.lms_dev_10.application.services.implementations.CategoryService;
import dev.gustavorh.lms_dev_10.application.services.implementations.LoanService;
import dev.gustavorh.lms_dev_10.application.services.implementations.MemberService;
import dev.gustavorh.lms_dev_10.application.services.implementations.PermissionService;
import dev.gustavorh.lms_dev_10.application.services.implementations.RolePermissionsService;
import dev.gustavorh.lms_dev_10.application.services.implementations.RoleService;
import dev.gustavorh.lms_dev_10.application.services.implementations.RoleUsersService;
import dev.gustavorh.lms_dev_10.application.services.implementations.StatusService;
import dev.gustavorh.lms_dev_10.application.services.implementations.UserService;
import dev.gustavorh.lms_dev_10.application.services.interfaces.IAuthService;
import dev.gustavorh.lms_dev_10.application.services.interfaces.IService;
import dev.gustavorh.lms_dev_10.application.services.interfaces.IUserService;

public class DefaultServiceFactory implements IServiceFactory {
    private final IRepositoryFactory repositoryFactory;

    public DefaultServiceFactory(IRepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }


    @Override
    public IAuthService createAuthService() { return new AuthService(createUserService()); }

    @Override
    public IUserService createUserService() { return new UserService(repositoryFactory.createUserRepository()); }

    @Override
    public IService<Book> createBookService() { return new BookService(repositoryFactory.createBookRepository()); }

    @Override
    public IService<Author> createAuthorService() { return new AuthorService(repositoryFactory.createAuthorRepository()); }

    @Override
    public IService<Category> createCategoryService() { return new CategoryService(repositoryFactory.createCategoryRepository()); }

    @Override
    public IService<Member> createMemberService() { return new MemberService(repositoryFactory.createMemberRepository()); }

    @Override
    public IService<Status> createStatusService() { return new StatusService(repositoryFactory.createStatusRepository()); }

    @Override
    public IService<Role> createRoleService() { return new RoleService(repositoryFactory.createRoleRepository()); }

    @Override
    public IService<Permission> createPermissionService() { return new PermissionService(repositoryFactory.createPermissionRepository()); }

    @Override
    public IService<Loan> createLoanService() { return new LoanService(repositoryFactory.createLoanRepository()); }

    @Override
    public IService<RolePermissions> createRolePermissionsService() { return new RolePermissionsService(repositoryFactory.createRolePermissionsRepository()); }

    @Override
    public IService<RoleUsers> createRoleUsersService() { return new RoleUsersService(repositoryFactory.createRoleUsersRepository()); }
}
