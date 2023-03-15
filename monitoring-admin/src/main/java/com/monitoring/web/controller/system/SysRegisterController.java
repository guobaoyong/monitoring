package com.monitoring.web.controller.system;

import com.monitoring.common.core.domain.entity.SysDept;
import com.monitoring.system.service.ISysDeptService;
import com.monitoring.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.monitoring.common.core.controller.BaseController;
import com.monitoring.common.core.domain.AjaxResult;
import com.monitoring.common.core.domain.entity.SysUser;
import com.monitoring.common.utils.StringUtils;
import com.monitoring.framework.shiro.service.SysRegisterService;
import com.monitoring.system.service.ISysConfigService;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 注册验证
 *
 * @author Guo Baoyong NWNU
 */
@Controller
public class SysRegisterController extends BaseController {
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysPostService postService;

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("register");
        // 放入院系
        modelAndView.addObject("depts", depts());
        // 放入岗位
        modelAndView.addObject("posts", postService.selectPostAll());
        return modelAndView;
    }

    @PostMapping("/register")
    @ResponseBody
    public AjaxResult ajaxRegister(SysUser user) {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }

    /**
     * 获取顶层所有院系
     */
    public List<SysDept> depts() {
        SysDept dept = new SysDept();
        // 顶层高校
        dept.setParentId(100L);
        List<SysDept> depts = deptService.selectDeptList(dept);
        return depts;
    }
}
