package dev.gustavorh.lms_dev_10.application.factories.interfaces;

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
import dev.gustavorh.lms_dev_10.application.services.interfaces.IAuthService;
import dev.gustavorh.lms_dev_10.application.services.interfaces.IService;
import dev.gustavorh.lms_dev_10.application.services.interfaces.IUserService;

public interface IServiceFactory {
    IAuthService createAuthService();
    IUserService createUserService();
    IService<Book> createBookService();
    IService<Author> createAuthorService();
    IService<Category> createCategoryService();
    IService<Member> createMemberService();
    IService<Status> createStatusService();
    IService<Role> createRoleService();
    IService<Permission> createPermissionService();
    IService<Loan> createLoanService();
    IService<RolePermissions> createRolePermissionsService();
    IService<RoleUsers> createRoleUsersService();
}
