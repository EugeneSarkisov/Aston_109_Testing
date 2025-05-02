package ru.aston.crud_impl.service;

import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.aston.crud_impl.model.UserAccount;
import ru.aston.crud_impl.util.HibernateUtil;
import ru.aston.crud_impl.util.dao.UserDAOImpl;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    private UserAccount user;
    private final UserDAOImpl userDAO = Mockito.mock(UserDAOImpl.class);
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        this.transaction = HibernateUtil.getTransaction();
    }

    @BeforeEach
    public void setUpUserData() {
        user = new UserAccount();
        user.setId(1);
        user.setUsername("Username");
        user.setAge(20);
        user.setEmail("111@mail.test");
    }

    @Test
    public void createUser_success() {
        Mockito.when(userDAO.create(user)).thenReturn(true);
        userDAO.create(user);
        Mockito.when(userDAO.get(user.getUsername())).thenReturn(user);
        UserAccount userAccount = userDAO.get(user.getUsername());
        transaction.commit();
        Assertions.assertTrue(userAccount.getId() > 0);
    }

    @Test
    void updateUserInfo_success() {
        Mockito.when(userDAO.get(user.getUsername())).thenReturn(user);
        UserAccount account = userDAO.get("Username");
        UserAccount updateAccount = new UserAccount();
        updateAccount.setUsername(user.getUsername());
        updateAccount.setAge(user.getAge());
        updateAccount.setEmail("222@mail.test");
        updateAccount.setId(user.getId());
        Mockito.when(userDAO.update(updateAccount)).thenReturn(updateAccount);
        Assertions.assertEquals("222@mail.test", updateAccount.getEmail());
    }

    @Test
    void deleteUser_success() {
        Mockito.when(userDAO.get(user.getUsername())).thenReturn(user);
        UserAccount account = userDAO.get(user.getUsername());
        Mockito.when(userDAO.delete(user.getUsername())).thenReturn(true);
        account = null;
        transaction.commit();
        assertTrue(account == null);
    }

    @Test
    void getUserInfo_success() {
        Mockito.when(userDAO.get(user.getUsername())).thenReturn(user);
        UserAccount userAccount = userDAO.get(user.getUsername());
        Assertions.assertTrue(userAccount.getId() > 0);
    }
}