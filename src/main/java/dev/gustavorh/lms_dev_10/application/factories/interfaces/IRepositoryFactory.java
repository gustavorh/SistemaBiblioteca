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
import dev.gustavorh.lms_dev_10.infrastructure.repositories.implementations.MemberActivityReportRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.interfaces.IRepository;
import dev.gustavorh.lms_dev_10.infrastructure.repositories.interfaces.IUserRepository;

public interface IRepositoryFactory {
    MemberActivityReportRepository createMemberActivityReportRepository();
    IUserRepository createUserRepository();
    IRepository<Book> createBookRepository();
    IRepository<Author> createAuthorRepository();
    IRepository<Category> createCategoryRepository();
    IRepository<Member> createMemberRepository();
    IRepository<Status> createStatusRepository();
    IRepository<Role> createRoleRepository();
    IRepository<Permission> createPermissionRepository();
    IRepository<Loan> createLoanRepository();
    IRepository<RolePermissions> createRolePermissionsRepository();
    IRepository<RoleUsers> createRoleUsersRepository();
}
