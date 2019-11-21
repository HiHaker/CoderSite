package com.ynu.codersite.controller;

import com.ynu.codersite.entity.AdminUser;
import com.ynu.codersite.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created on 2019/11/21 0021
 * BY Jianlong
 */
@RestController
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    AdminUserRepository adminUserRepository;

    @GetMapping("/findByName")
    public AdminUser findByName(@RequestParam String name){
        AdminUser au = adminUserRepository.findByName(name);
        return au;
    }

    @PostMapping("/addAdminUser")
    public AdminUser addAdminUser(AdminUser au){
        adminUserRepository.save(au);
        return au;
    }
}
