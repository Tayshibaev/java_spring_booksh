package com.example.MyBookShopApp.DTO;

import com.example.MyBookShopApp.data.AbstractHibernateDao;
import com.example.MyBookShopApp.data.TestEntity;
import org.springframework.stereotype.Repository;

@Repository
public class TestEntityDao extends AbstractHibernateDao<TestEntity> {
    public TestEntityDao(){
        super();
        setClazz(TestEntity.class);
    }
}
