package com.mlacker.micros.data.domain.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-10)
@Component
public class MultiDataSourceAspect {

    @Before("@annotation(dataSource)")
    public void changeDataSource(JoinPoint point, DataSource dataSource) {
        MultiDataSourceContextHolder.setDataSource(dataSource.value());
    }

    @After("@annotation(dataSource)")
    public void restoreDataSource(JoinPoint point, DataSource dataSource) {
        MultiDataSourceContextHolder.reset();
    }
}
