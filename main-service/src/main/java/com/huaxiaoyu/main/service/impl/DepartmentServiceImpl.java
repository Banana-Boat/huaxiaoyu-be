package com.huaxiaoyu.main.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huaxiaoyu.main.domain.Department;
import com.huaxiaoyu.main.mapper.DepartmentMapper;
import com.huaxiaoyu.main.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
}
