package com.nmefc.forcasthci.service.impl;

import com.nmefc.forcasthci.dao.BaseMapper;
import com.nmefc.forcasthci.exception.ServiceException;
import com.nmefc.forcasthci.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: QuYuan
 * @Description: 公共Service实现方法抽取
 * @Date: Created in 9:57 2019/2/22
 * @Modified By:
 */

public abstract class BaseServiceImp<T,E,PK extends Serializable> implements BaseService<T,E,PK> {
    @Autowired
    private BaseMapper<T,E,PK> baseMapper;
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    @Override
    public int deleteByPrimaryKey(PK pk) {
        return baseMapper.deleteByPrimaryKey(pk);
    }
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    @Override
    public int insert(T record) {
        return baseMapper.insert(record);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    @Override
    public int insertSelective(T record) throws ServiceException {
        int row = 0;
        try{
            row = baseMapper.insertSelective(record);
        }catch (Exception e){
            throw  new ServiceException("Insert Exception in Service :" + e.getMessage());
        }

        return row;
    }

    @Override
    public T selectByPrimaryKey(PK pk) throws ServiceException {
            return baseMapper.selectByPrimaryKey(pk);
    }
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKeySelective(T record) throws ServiceException{
        int row = 0;
        try{
            row  = baseMapper.updateByPrimaryKeySelective(record);
        }catch (Exception e){
            throw new ServiceException("update exception in Service ：" + e.getMessage());
        }
        return row;
    }
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKey(T record) {
        return baseMapper.updateByPrimaryKey(record);
    }

    @Override
    public long countByExample(E example) {
        return baseMapper.deleteByExample(example);
    }
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    @Override
    public int deleteByExample(E example) {
        return baseMapper.deleteByExample(example);
    }

    @Override
    public List<T> selectByExample(E example) {
        return baseMapper.selectByExample(example);
    }
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    @Override
    public int updateByExampleSelective(T record, E example) {
        return baseMapper.updateByExampleSelective(record,example);
    }
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    @Override
    public int updateByExample(T record, E example) {
        return baseMapper.updateByExample(record,example);
    }

    public abstract String check(T record,String response) throws ServiceException;
}
