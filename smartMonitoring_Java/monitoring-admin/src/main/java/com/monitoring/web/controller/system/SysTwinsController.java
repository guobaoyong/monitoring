package com.monitoring.web.controller.system;

import cn.hutool.core.date.DateUtil;
import com.monitoring.common.annotation.Log;
import com.monitoring.common.core.controller.BaseController;
import com.monitoring.common.core.domain.AjaxResult;
import com.monitoring.common.core.domain.entity.SysDept;
import com.monitoring.common.core.domain.entity.SysUser;
import com.monitoring.common.enums.BusinessType;
import com.monitoring.system.domain.SysNotice;
import com.monitoring.system.domain.SysSensors;
import com.monitoring.system.service.ISysDeptService;
import com.monitoring.system.service.ISysNoticeService;
import com.monitoring.system.service.ISysSensorsService;
import com.monitoring.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 前端孪生页面供数据
 */
@Controller
@RequestMapping("/system/twins")
@CrossOrigin
public class SysTwinsController extends BaseController {

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysSensorsService sensorsService;

    @Autowired
    private ISysNoticeService noticeService;

    @Log(title = "前端数字孪生查询数据", businessType = BusinessType.OTHER)
    @GetMapping("/query")
    @ResponseBody
    public AjaxResult query(String ids) {
        Map<String,Object> data = new HashMap<>();
        SysDept dept = new SysDept();
        dept.setDeptName("ZKY0"+ids+"车间");
        deptService.selectDeptList(dept).forEach(sysDept -> {
            System.out.println(sysDept.toString());
            data.put("deptName",sysDept.getDeptName());
            // 车间工人数
            SysUser user = new SysUser();
            user.setDeptId(sysDept.getDeptId());
            data.put("workerNumber",userService.selectUserList(user).size());
            data.put("deptLeader",sysDept.getLeader());
            data.put("deptPhone",sysDept.getPhone());
            // 传感器数量
            SysSensors sensors = new SysSensors();
            sensors.setAddress(dept.getDeptName());
            List<SysSensors> sensorsList = sensorsService.selectSysSensorsList(sensors);
            data.put("sensorsNumber",sensorsList.size());
            AtomicInteger nowError = new AtomicInteger();
            AtomicInteger oldError = new AtomicInteger();
            sensorsList.forEach(sensor -> {
                SysNotice notice = new SysNotice();
                notice.setSensorsId(sensor.getSensorsId());
                oldError.addAndGet(noticeService.selectNoticeListCount(notice));
                // 今日异常
                notice.setWarningDate(DateUtil.today());
                nowError.addAndGet(noticeService.selectNoticeListCount(notice));
            });
            data.put("nowError",nowError.get());
            data.put("oldError",oldError.get());
        });
        return AjaxResult.success(data);
    }
}
