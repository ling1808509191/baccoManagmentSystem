package com.mark.java.DAOImp;

import com.mark.java.DAO.resultDao;
import com.mark.java.entity.Result;

import java.util.List;
import com.mark.java.entity.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
/**
 * Created by vcc on 2016/10/22.
 */
@Component
public class ResultDaoImp implements resultDao {
    @Autowired
    private SessionFactory sessionFactory;
    public int save(Result u) {
        return (Integer) sessionFactory.getCurrentSession().save(u);
    }

    public List<Result> findAll() {
        return null;
    }
}
