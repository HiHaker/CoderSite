package com.ynu.codersite.repository;

import com.ynu.codersite.entity.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * Created on 2019/11/21 0021
 * BY Jianlong
 */
@Repository
public class AdminUserRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 增加对象
     * @param au
     */
    public void save(AdminUser au){
        mongoTemplate.save(au);
    }

    /**
     * 根据名称删除对象
     * @param name
     */
    public void deleteByName(String name){
        Query query=new Query(Criteria.where("name").is(name));
        mongoTemplate.remove(query,AdminUser.class);
    }

    /**
     * 修改对象
     * @param au
     */
    public void update(AdminUser au){
        Query query=new Query(Criteria.where("name").is(au.getName()));
        Update update= new Update().set("password", au.getPassword()).set("email", au.getEmail()).set("sex",au.getSex());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,AdminUser.class);
    }

    /**
     * 根据名字查询对象
     * @param name
     * @return
     */
    public AdminUser findByName(String name){
        Query query=new Query(Criteria.where("name").is(name));
        AdminUser au =  mongoTemplate.findOne(query , AdminUser.class);
        return au;
    }
}