package com.mark.java.serviceImp;

import com.mark.java.DAO.UserDAO;
import com.mark.java.DAOImp.UserDAOImpl;
import com.mark.java.entity.User;
import com.mark.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by mark on 4/24/15.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAOImpl userDao;

    public void saveUsers(List<User> us) {
        for (User u : us) {
            userDao.save(u);
        }
    }

    public UserDAOImpl getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDAOImpl userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsernames() {
        return userDao.findAll();
    }
}
