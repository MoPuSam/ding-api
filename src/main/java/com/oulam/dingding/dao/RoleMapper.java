package com.oulam.dingding.dao;

import com.oulam.dingding.bean.Role;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRoleListByGroupId(Long groupid);

    int insertBatch(List<Role> list);

    Role selectByRid(long roleId);

    void deleteByRid(long roleId);
}