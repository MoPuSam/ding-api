package com.oulam.dingding.dao;

import com.oulam.dingding.bean.Y_Message;
import java.math.BigDecimal;
import java.util.List;

public interface Y_MessageMapper {
    int deleteByPrimaryKey(BigDecimal latoid);

    int insert(Y_Message record);

    int insertSelective(Y_Message record);

    Y_Message selectByPrimaryKey(BigDecimal latoid);

    int updateByPrimaryKeySelective(Y_Message record);

    int updateByPrimaryKey(Y_Message record);

    List<Y_Message> getMessageNotFinish();

    int updateBatchByPrimaryKey(List<Y_Message> list);
}