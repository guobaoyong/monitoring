package com.monitoring.quartz.task;

import cn.hutool.core.date.DateUtil;
import com.monitoring.system.domain.SysCollectData;
import com.monitoring.system.domain.SysSensors;
import com.monitoring.system.service.ISysCollectDataService;
import com.monitoring.system.service.ISysSensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Component("simulation")
@Service
public class SimulationData {

    @Autowired
    private ISysSensorsService sysSensorsService;

    @Autowired
    private ISysCollectDataService sysCollectDataService;

    public void data() {
        List<SysSensors> sysSensors = sysSensorsService.selectSysSensorsList(null);
        sysSensors.forEach(sensors -> {
            SysCollectData sysCollectData = new SysCollectData();
            sysCollectData.setCollectTime(DateUtil.date());
            sysCollectData.setSensorId(sensors.getSensorsId());
            Random random = new Random();
            if (sensors.getType().equals("temperature")) {
                sysCollectData.setTemperature(String.valueOf(random.nextDouble() * 50));
            } else if (sensors.getType().equals("humidity")) {
                sysCollectData.setHumidity(String.valueOf(random.nextDouble() * 10 + 90));
            }
            sysCollectDataService.insertSysCollectData(sysCollectData);
        });
    }

}
