package com.oulam.dingding.dao;

import com.oulam.dingding.bean.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int batchInsert(List<User> list);

    User selectById(String id);

    User selectByName(String name);

    List<User> getUserListByIds(List<String> uidlist);
}