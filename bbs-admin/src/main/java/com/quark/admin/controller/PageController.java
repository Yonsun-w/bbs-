package com.quark.admin.controller;

import com.quark.admin.utils.PasswordHelper;
import com.quark.common.entity.AdminUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 *  17-8-2.
 */
@Controller
public class PageController {

    /**
     * 登录界面
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }


    @RequestMapping(value = "/myadd", method = RequestMethod.GET)
    public void myadd() {
        PasswordHelper passwordHelper = new PasswordHelper();
        AdminUser adminUser =  new AdminUser();
        adminUser.setId(88);
        adminUser.setEnable(1);
        adminUser.setUsername("root");
        adminUser.setPassword("123456");
    }



    /**
     * 用户登录
     * @param request
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request, AdminUser user, Model model) {

        if (StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getPassword())){
            request.setAttribute("msg","用户名或者密码不能为空!");
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            subject.login(token);
            return "redirect:/initPage";
        }catch (LockedAccountException lae) {
            token.clear();
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "login";
        } catch (AuthenticationException e) {
            token.clear();
            request.setAttribute("msg", "用户或密码不正确！");
            return "login";
        }
    }

    @RequestMapping("/initPage")
    public String InitPage(){
        return "init";
    }

    @RequestMapping("/adminsPage")
    public String AdminsPage(){
        return "admin/admins";
    }

    @RequestMapping("/permissionsPage")
    public String PermissionPage(){
        return "permission/permissions";
    }

    @RequestMapping("/rolesPage")
    public String RolesPage(){
        return "role/roles";
    }

    @RequestMapping("/usersPage")
    public String UsersPage(){
        return "user/users";
    }

    @RequestMapping("/postsPage")
    public String PostsPage(){
        return "posts/posts";
    }

    @RequestMapping("/replysPage")
    public String ReplysPage(){
        return "reply/replys";
    }

    @RequestMapping("/labelsPage")
    public String LabelsPage(){
        return "label/labels";
    }
}
