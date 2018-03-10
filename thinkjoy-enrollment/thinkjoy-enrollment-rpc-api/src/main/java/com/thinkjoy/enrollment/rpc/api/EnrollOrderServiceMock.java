package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.enrollment.dao.mapper.EnrollOrderMapper;
import com.thinkjoy.enrollment.dao.model.EnrollOrder;
import com.thinkjoy.enrollment.dao.model.EnrollOrderExample;

/**
* 降级实现EnrollOrderService接口
* Created by user on 2017/12/27.
*/
public class EnrollOrderServiceMock extends BaseServiceMock<EnrollOrderMapper, EnrollOrder, EnrollOrderExample> implements EnrollOrderService {

}
