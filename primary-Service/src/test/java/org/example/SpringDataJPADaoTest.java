package org.example;

import org.example.config.DatabaseConfig;
import org.example.dao.SpringDataJPADao;
import org.example.dao.UserDao;
import org.example.entity.Users;
import org.example.view.UserView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.List;

import static org.example.dao.specification.CustomSpecification.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {DatabaseConfig.class, TestContainersConfig.class},
        loader = AnnotationConfigContextLoader.class
)
@SpringBootTest
@Testcontainers
public class SpringDataJPADaoTest {

    @Autowired
    private SpringDataJPADao springDataJPADao;

    @Autowired
    private UserDao userDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

    @Transactional
    @Test
    public void findUsersByIdTest() throws SQLException, InterruptedException {

        Users user = Users.builder()
                .firstName("firstname")
                .lastName("lastname")
                .mail("31323@gmail.com")
                .phone("32234553")
                .password("24342523")
                .build();

        Users saveDishes = userDao.save(user);


        Users usersById = springDataJPADao.findUsersById(saveDishes.getId());

        Assert.assertEquals(user, usersById);
    }

    @Transactional
    @Test
    public void findUsersByIdWithJPQLQueryTest() throws SQLException, InterruptedException {

        Users user = Users.builder()
                .firstName("firstname")
                .lastName("lastname")
                .mail("31323@gmail.com")
                .phone("32234553")
                .password("24342523")
                .build();

        Users saveDishes = userDao.save(user);


        Users usersById = springDataJPADao.findUsersByIdWithJPQLQuery(saveDishes.getId());

        Assert.assertEquals(user, usersById);
    }

    @Transactional
    @Test
    public void findUsersByIdWithNativeQueryTest() throws SQLException, InterruptedException {

        Users user = Users.builder()
                .firstName("firstname")
                .lastName("lastname")
                .mail("31323@gmail.com")
                .phone("32234553")
                .password("24342523")
                .build();

        Users saveDishes = userDao.save(user);


        Users usersById = springDataJPADao.findUsersByIdWithNativeQuery(saveDishes.getId());

        Assert.assertEquals(user, usersById);
    }

    @Transactional
    @Test
    public void findByMailIdWithProjectionsTest() throws SQLException, InterruptedException {

        Users user = Users.builder()
                .firstName("firstname")
                .lastName("lastname")
                .mail("31323@gmail.com")
                .phone("32234553")
                .password("24342523")
                .build();

        Users saveDishes = userDao.save(user);

        UserView byMail = springDataJPADao.findByMail(saveDishes.getMail());

        String mail = byMail.getMail();
        String phone = byMail.getPhone();

        Assert.assertEquals(user.getMail(), mail);
        Assert.assertEquals(user.getPhone(), phone);
    }

    @Test
    public void findUsersByMailWithRolesTest() {

        Users usersByMail = springDataJPADao.findUsersByMail("admin@gmail.com");

        Assert.assertFalse(usersByMail.getRoles().isEmpty());
    }

    @Test
    public void findUsersByFirstNameWithOrdersTest() {

        Users vlad = springDataJPADao.findUsersByFirstName("Vlad");

        Assert.assertFalse(vlad.getOrders().isEmpty());
    }

    @Test
    public void findAllWithPageableTest() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("lastName").descending());

        Page<Users> all = springDataJPADao.findAll(pageable);

        Assert.assertEquals(2, all.getSize());
    }

    @Transactional
    @Test
    public void findAllByFirstNameWithPageableTest() throws SQLException, InterruptedException {
        Pageable pageable = PageRequest.of(0, 2);

        Users user = Users.builder()
                .firstName("Vlad")
                .lastName("Aus")
                .mail("31323@gmail.com")
                .phone("32234553")
                .password("24342523")
                .build();

        userDao.save(user);

        List<Users> all = springDataJPADao.findAllByFirstName("Vlad", pageable);

        Assert.assertEquals(2, all.size());
    }

    @Transactional
    @Test
    public void findUserByFirstNameWithSpecificationTest() throws SQLException, InterruptedException {
        String firstName = "Vlad";
        String lastName = "Lisay";

        Users user = Users.builder()
                .firstName("Vlad")
                .lastName("Aus")
                .mail("31323@gmail.com")
                .phone("32234553")
                .password("24342523")
                .build();

        userDao.save(user);

        List<Users> output = springDataJPADao.findAll(nameLike(firstName).and(lastNameLike(lastName)));

        Assert.assertEquals(1, output.size());
    }
}
