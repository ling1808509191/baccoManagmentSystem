package com.mark.java.serviceImp;

import com.mark.java.DAOImp.ResultDaoImp;
import com.mark.java.entity.Result;
import com.mark.java.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vcc on 2016/10/22.
 */
@Service
@Transactional
public class ResultServiceImp implements ResultService {
    @Autowired
    private ResultDaoImp resultdao;
    public void saveResults(List<Result> us) {
        for (Result u : us) {
            resultdao.save(u);
        }

    }

    public ResultDaoImp getResultdao() {
        return resultdao;
    }

    public void setResultdao(ResultDaoImp resultdao) {
        this.resultdao = resultdao;
    }
}
