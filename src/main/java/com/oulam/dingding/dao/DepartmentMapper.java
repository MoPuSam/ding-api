package com.oulam.dingding.dao;


import com.oulam.dingding.bean.Department;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    int batchInsert(List<Department> list);

    List<Department> selectAll();

    Department selectById(long l);

    Department selectByName(String name);
}