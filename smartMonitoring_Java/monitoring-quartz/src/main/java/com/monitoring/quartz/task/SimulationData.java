package com.monitoring.quartz.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import com.monitoring.system.domain.SysCollectData;
import com.monitoring.system.domain.SysSensors;
import com.monitoring.system.service.ISysCollectDataService;
import com.monitoring.system.service.ISysSensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component("simulation")
@Service
public class SimulationData {

    @Autowired
    private ISysSensorsService sysSensorsService;

    @Autowired
    private ISysCollectDataService sysCollectDataService;

    // 超时时间
    private final int ERROR_TIME = 20000;

    public void data() {
        List<SysSensors> sysSensors = sysSensorsService.selectSysSensorsList(null);
        sysSensors.forEach(sensors -> {
            SysCollectData sysCollectData = new SysCollectData();
            // 获取采集时间
            sysCollectData.setCollectTime(DateUtil.date());
            sysCollectData.setSensorId(sensors.getSensorsId());
            if (sensors.getType().equals("temperature")) {
                // 获取采集地址
                try {
                    // 获取采集数据
                    String result = HttpRequest.get(sensors.getIp()).timeout(ERROR_TIME).execute().body();
                    result = result.replaceAll("Temperature: ", "");
                    result = result.replaceAll("C", "");
                    // 写入采集数据
                    sysCollectData.setTemperature(result);
                    if (sensors.getStatus().equals("1")) {
                        sensors.setStatus("0");
                        sysSensorsService.updateSysSensors(sensors);
                    }
                } catch (Exception e) {
                    sensors.setStatus("1");
                    sysSensorsService.updateSysSensors(sensors);
                    return;
                }
            } else if (sensors.getType().equals("humidity")) {
                // 获取采集地址
                try {
                    // 获取采集数据
                    String result = HttpRequest.get(sensors.getIp()).timeout(ERROR_TIME).execute().body();
                    result = result.replaceAll("Humidity: ", "");
                    result = result.replaceAll("%", "");
                    // 写入采集数据
                    sysCollectData.setHumidity(result);
                    if (sensors.getStatus().equals("1")) {
                        sensors.setStatus("0");
                        sysSensorsService.updateSysSensors(sensors);
                    }
                } catch (Exception e) {
                    sensors.setStatus("1");
                    sysSensorsService.updateSysSensors(sensors);
                    return;
                }
            } else if (sensors.getType().equals("light")) {
                // 获取采集地址
                try {
                    // 获取采集数据
                    String result = HttpRequest.get(sensors.getIp()).timeout(ERROR_TIME).execute().body();
                    result = result.replaceAll("Light: ", "");
                    result = result.replaceAll("lx", "");
                    // 写入采集数据
                    sysCollectData.setLight(result);
                    if (sensors.getStatus().equals("1")) {
                        sensors.setStatus("0");
                        sysSensorsService.updateSysSensors(sensors);
                    }
                } catch (Exception e) {
                    sensors.setStatus("1");
                    sysSensorsService.updateSysSensors(sensors);
                    return;
                }
            }
            sysCollectDataService.insertSysCollectData(sysCollectData);
        });
    }

}
