package com.mark.java.DAO;

import com.mark.java.entity.Result;
import com.mark.java.entity.User;

import java.util.List;

/**
 * Created by vcc on 2016/10/22.
 */
public interface resultDao {
    public int save(Result u);
    public List<Result> findAll();
}
