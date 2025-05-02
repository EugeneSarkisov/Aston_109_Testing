package ru.aston.crud_impl.util.dao;

import ru.aston.crud_impl.model.UserAccount;

public interface UserDAO {
    boolean create(UserAccount userAccount);
    UserAccount get(String username);
    UserAccount update(UserAccount userAccount);
    boolean delete(String username);
}
