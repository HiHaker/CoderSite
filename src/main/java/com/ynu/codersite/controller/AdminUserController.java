package com.ynu.codersite.controller;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.AdminUser;
import com.ynu.codersite.service.AdminUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2019/11/22 0022
 * BY Jianlong
 */
@RestController
@RequestMapping("/admin")
public class AdminUserController {
    @Autowired
    AdminUserService adminUserService;

    /**
     * 删除管理员
     * @param adminUser
     * @return
     */
    @ApiOperation(value = "添加管理员", notes="添加管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "管理员id", required = true),
            @ApiImplicitParam(name = "nickname", value = "管理员昵称", required = true),
            @ApiImplicitParam(name = "password", value = "管理员密码", required = true),
            @ApiImplicitParam(name = "registerDate", value = "管理员注册日期", required = true)
    })
    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
    public JSONObject addAdmin(AdminUser adminUser){
        JSONObject msg = new JSONObject();
        AdminUser temp = adminUserService.getAdminUserById(adminUser.getAdminId());
        if (temp == null){
            adminUserService.addAdminUser(adminUser);
            msg.put("message","添加管理员成功");
        } else {
            msg.put("message","添加失败，已经存在管理员");
        }
        return msg;
    }

    /**
     * 管理员删除
     * @param adminId
     * @return
     */
    @ApiOperation(value = "管理员删除", notes = "管理员删除")
    @RequestMapping(value = "/deleteAdmin", method = RequestMethod.DELETE)
    public JSONObject deleteAdmin(@RequestParam String adminId){
        JSONObject msg = new JSONObject();
        AdminUser temp = adminUserService.getAdminUserById(adminId);
        if (temp == null){
            msg.put("message","管理员不存在");
        } else {
            adminUserService.deleteAdminUser(adminId);
            msg.put("message","删除成功");
        }
        return msg;
    }
}