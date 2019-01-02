package com.oulam.dingding.dao;

import com.oulam.dingding.bean.RoleGroup;

import java.util.List;

public interface RoleGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleGroup record);

    int insertSelective(RoleGroup record);

    RoleGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleGroup record);

    int updateByPrimaryKey(RoleGroup record);

    int insertBatch(List<RoleGroup> list);
}