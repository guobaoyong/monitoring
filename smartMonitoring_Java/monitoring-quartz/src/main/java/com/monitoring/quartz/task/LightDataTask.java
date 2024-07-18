package com.monitoring.quartz.task;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.monitoring.system.domain.SysCollectData;
import com.monitoring.system.service.ISysCollectDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Component("lightDataTask")
@Service
public class LightDataTask {

    @Autowired
    private ISysCollectDataService sysCollectDataService;

    public void data() {
        String dateStr = "2022-01-01 00:00:00";
        Date date = DateUtil.parse(dateStr);
        for (int i = 0; i < 144 * 365; i++) {
            Random random = new Random();
            int nextInt = random.nextInt(200) + 350;
            SysCollectData sysCollectData = new SysCollectData();
            sysCollectData.setCollectTime(DateUtil.offset(date, DateField.MINUTE, i * 10));
            sysCollectData.setSensorId(9L);
            sysCollectData.setLight(String.valueOf(Double.valueOf(nextInt)));
            sysCollectDataService.insertSysCollectData(sysCollectData);
        }
    }
}
