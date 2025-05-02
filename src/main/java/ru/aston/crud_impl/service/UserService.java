package ru.aston.crud_impl.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;
import ru.aston.crud_impl.util.dao.UserDAOImpl;
import ru.aston.crud_impl.model.UserAccount;
import ru.aston.crud_impl.util.user_exceptions.IncorrectInputException;
import ru.aston.crud_impl.util.user_exceptions.UsernameAlreadyExistException;
import ru.aston.crud_impl.util.HibernateUtil;
import ru.aston.crud_impl.util.validation.Validator;

public class UserService {
    public static UserDAOImpl userDAO = new UserDAOImpl();
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Logger LOGGER_DEBUG = LogManager.getLogger("org.example");

    public void createUser(String username, String email, int age) {
        LOGGER.info("starting creating new user process in table '{}'", "user");
        try {
            Transaction transaction = HibernateUtil.getTransaction();
            LOGGER.info("starting validation process");
            Validator.isUsernameValid(username);
            Validator.isAgeValid(age);
            LOGGER.info("checking username accessibility");
            if (userDAO.get(username) != null) {
                throw new UsernameAlreadyExistException("Username " + username + " is already exist");
            } else {
                UserAccount userAccount = new UserAccount();
                userAccount.setUsername(username);
                userAccount.setEmail(email);
                userAccount.setAge(age);
                userAccount.setCreatedAt();
                userDAO.create(userAccount);
                transaction.commit();
                LOGGER.info("successfully created new user");
            }
        } catch (UsernameAlreadyExistException | IncorrectInputException e) {
            LOGGER_DEBUG.error("failed to create new user. please, check logs");
            System.out.println("Please, check your inputs and try again.");
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        } catch (SQLGrammarException e) {
            LOGGER_DEBUG.error("failed to create new user. please, check logs");
            System.out.println("Caught an SQLGrammarException");
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        } catch (PersistenceException e) {
            LOGGER_DEBUG.error("failed to create new user. please, check logs");
            System.out.println("Caught an PersistentException");
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        } catch (Exception e) {
            LOGGER_DEBUG.error("failed to create new user. please, check logs");
            System.out.println("Something went wrong... Please, contact with the administrator");
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        }
    }

    public void deleteUser(String username) {
        LOGGER.info("starting deleting user process in table '{}'", "user");
        try {
            Transaction transaction = HibernateUtil.getTransaction();
            LOGGER.info("checking user existence");
            if (userDAO.get(username) != null) {
                userDAO.delete(username);
            } else {
                throw new EntityNotFoundException("User isn't exist! Please, try again.");
            }
            transaction.commit();
            LOGGER.info("successfully deleted user '{}'", username);
        } catch (EntityNotFoundException e) {
            LOGGER_DEBUG.error("failed to create new user. please, check logs");
            LOGGER_DEBUG.error(e.getMessage());
            e.printStackTrace();
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        } catch (SQLGrammarException e) {
            LOGGER_DEBUG.error("Caught an SQLGrammarException");
            e.printStackTrace();
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        } catch (PersistenceException e) {
            LOGGER_DEBUG.error("Caught an PersistentException");
            e.printStackTrace();
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        } catch (Exception e) {
            LOGGER_DEBUG.error("Something went wrong... Please, contact with the administrator");
            e.printStackTrace();
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        }
    }

    public UserAccount getUserInfo(String username) {
        LOGGER.info("starting getting user info process in table '{}'", "user");
        UserAccount userAccount = null;
        try {
            Transaction transaction = HibernateUtil.getTransaction();
            LOGGER.info("getting info about user with username '{}'", username);
            userAccount = userDAO.get(username);
            if(userAccount == null){
                throw new EntityNotFoundException("User isn't exist! Please, try again.");
            }
            transaction.commit();
            LOGGER.info("successfully got info about user '{}'", username);
        } catch (EntityNotFoundException e) {
            LOGGER_DEBUG.error("failed to create new user. please, check logs");
            LOGGER_DEBUG.error(e.getMessage());
            e.printStackTrace();
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        } catch (SQLGrammarException e) {
            LOGGER_DEBUG.error("failed to create new user. please, check logs");
            LOGGER_DEBUG.error("Caught an SQLGrammarException");
            e.printStackTrace();
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        } catch (PersistenceException e) {
            LOGGER_DEBUG.error("failed to create new user. please, check logs");
            LOGGER_DEBUG.error("Caught an PersistentException");
            e.printStackTrace();
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        } catch (Exception e) {
            LOGGER_DEBUG.error("failed to create new user. please, check logs");
            LOGGER_DEBUG.error("Something went wrong... Please, contact with the administrator");
            e.printStackTrace();
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        }
        return userAccount;
    }

    public void updateUserInfo(String username, String newUsername, String newEmail, int newAge) {
        LOGGER.info("starting updating user info process in table '{}'", "user");
        try {
            Transaction transaction = HibernateUtil.getTransaction();
            LOGGER.info("starting validation process");
            Validator.isUsernameValid(newUsername);
            Validator.isAgeValid(newAge);
            LOGGER.info("checking new username accessibility");
            if (userDAO.get(newUsername) != null) {
                throw new UsernameAlreadyExistException("Username " + username + " is already exist");
            } else {
                LOGGER.info("setting up new user info");
                UserAccount updateUserAccount = userDAO.get(username);
                if(updateUserAccount == null){
                    throw new EntityNotFoundException("User isn't exist! Please, try again.");
                }
                updateUserAccount.setUsername(newUsername);
                updateUserAccount.setEmail(newEmail);
                updateUserAccount.setAge(newAge);
                userDAO.update(updateUserAccount);
                transaction.commit();
                LOGGER.info("successfully updated info about user '{}'", username);
            }
        } catch (EntityNotFoundException | IncorrectInputException e) {
            LOGGER_DEBUG.error("failed to create new user. please, check logs");
            LOGGER_DEBUG.error(e.getMessage());
            e.printStackTrace();
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        } catch (SQLGrammarException e) {
            LOGGER_DEBUG.error("failed to create new user. please, check logs");
            LOGGER_DEBUG.error("Caught an SQLGrammarException");
            e.printStackTrace();
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        } catch (PersistenceException e) {
            LOGGER_DEBUG.error("failed to create new user. please, check logs");
            LOGGER_DEBUG.error("Caught an PersistentException");
            e.printStackTrace();
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        } catch (Exception e) {
            LOGGER_DEBUG.error("failed to create new user. please, check logs");
            LOGGER_DEBUG.error("Something went wrong... Please, contact with the administrator");
            e.printStackTrace();
            LOGGER_DEBUG.debug(e.getMessage());
            LOGGER_DEBUG.debug(e.getStackTrace());
            HibernateUtil.transactionRollback(HibernateUtil.getTransaction());
        }
    }
}
