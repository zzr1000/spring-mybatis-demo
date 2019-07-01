package org.zzr1000.mybatisTest.test2.dao;

import org.zzr1000.mybatisTest.test2.model.User2;

import java.util.List;

public interface User2mapper {

    /**
     * 查询所有用户的信息
     *
     * @return
     */
    public List<User2> getObjects();


}