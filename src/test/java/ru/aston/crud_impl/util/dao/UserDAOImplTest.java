package ru.aston.crud_impl.util.dao;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.crud_impl.model.UserAccount;


@Testcontainers
class UserDAOImplTest {

    private static final int containerPort = 5432;
    private static final int localPort = 5432;
    private final UserDAOImpl userDAO = new UserDAOImpl();
    private UserAccount user;

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("crud_example")
            .withUsername("postgres")
            .withPassword("admin")
            .withExposedPorts(containerPort)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))
            ));

    @BeforeEach
    public void setUpUserData() {
        user = new UserAccount();
        user.setId(1);
        user.setUsername("Username");
        user.setAge(20);
        user.setEmail("111@mail.test");
    }

    @Test
    void create() {
        Assertions.assertTrue(userDAO.create(user));
    }

    @Test
    void get() {
        UserAccount userAccount = userDAO.get(user.getUsername());
        Assertions.assertNotNull(userAccount);
    }

    @Test
    void update() {
        UserAccount updateUser = new UserAccount();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setAge(22);
        userDAO.update(updateUser);
        Assertions.assertEquals(22, user.getAge());
    }

    @Test
    void delete() {
        userDAO.delete(user.getUsername());
        Assertions.assertNull(userDAO.get(user.getUsername()));

    }
}