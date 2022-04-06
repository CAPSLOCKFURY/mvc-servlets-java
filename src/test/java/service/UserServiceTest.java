package service;

import db.ConnectionPool;
import forms.AddBalanceForm;
import forms.LoginForm;
import forms.RegisterForm;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    public final static UserService service = UserService.getInstance();

    @BeforeAll
    public static void setUp(){
        ConnectionPool.initPool();
    }

    @AfterEach
    public void tearDownAfterTest(){
        ConnectionPool.releaseAllConnections();
    }

    @Test
    void getUserByIdTest(){
        User user = service.getUserById(1L);
        assertEquals(1, user.getId());
        assertEquals("Vadim", user.getFirstName());
        assertEquals("Demb", user.getLastName());
    }

    @Test
    void loginUserTest(){
        LoginForm form = new LoginForm();
        form.setLocale(new Locale("en"));
        form.setLogin("vadim");
        form.setPassword("123");
        User user = service.loginUser(form);
        assertEquals(1, user.getId());
        assertEquals("vadim", user.getLogin());

        LoginForm wrongForm = new LoginForm();
        wrongForm.setLocale(new Locale("en"));
        wrongForm.setLogin("nonexistinglogin");
        wrongForm.setPassword("nonexistingpassword");
        User wrongUser = service.loginUser(wrongForm);
        assertNull(wrongUser.getId());
        assertEquals(1, wrongForm.getErrors().size());
    }

    @Test
    void createUser(){
        RegisterForm form = new RegisterForm();
        form.setLocale(new Locale("en"));
        form.setEmail("junit@gmail.com");
        form.setLogin("junit");
        form.setPassword("1");
        form.setLastName("Junit");
        form.setLastName("Junitov");
        long userId = service.createUser(form);
        assertTrue(userId > 0);
        long nullUserId = service.createUser(form);
        assertEquals(0, nullUserId);
        assertEquals(1, form.getErrors().size());
        form.setEmail("unique@gmail.com");
        form.getErrors().clear();
        long nullUserId2 = service.createUser(form);
        assertEquals(0, nullUserId2);
        assertEquals(1, form.getErrors().size());
    }

    @Test
    void addUserBalance(){
        AddBalanceForm form = new AddBalanceForm();
        form.setAmount("1000");
        boolean result = service.addUserBalance(form, 1L);
        assertTrue(result);
    }
}
