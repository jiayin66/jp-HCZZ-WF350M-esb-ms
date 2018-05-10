package com.jp.hczz.dsj350m.service.impl;

import com.jp.hczz.dsj350m.dao.Dsj350MDao;
import com.jp.hczz.dsj350m.entity.Dsj350M;
import com.jp.hczz.dsj350m.service.Dsj350MDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Dsj350MDaoServiceImpl implements Dsj350MDaoService {

    @Autowired
    private Dsj350MDao dsj350MDao;

    @Override
    public Dsj350M saveDsj350M(Dsj350M dsj350M) {
        return dsj350MDao.save(dsj350M);
    }

}