package com.park.controller;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.park.job.JobForDay;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Aaron
 * @date 2020/6/18 9:57
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    @Resource
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Resource
    private JobEventConfiguration jobEventConfiguration;

    @GetMapping("/addTask/{jobName}/{cron}")
    public void addTask(@PathVariable("jobName") String jobName, @PathVariable("cron") String cron) {
        new SpringJobScheduler(new JobForDay(), zookeeperRegistryCenter, getLiteJobConfiguration(jobName, cron), jobEventConfiguration).init();
    }

    private LiteJobConfiguration getLiteJobConfiguration(String jobName, String cron) {
        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder(jobName, "0/8 * * * * ?", 1).build();
        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, JobForDay.class.getCanonicalName());
        // 定义Lite作业根配置
        LiteJobConfiguration.Builder builder = LiteJobConfiguration.newBuilder(simpleJobConfig);
        builder.reconcileIntervalMinutes(1);
        LiteJobConfiguration build = builder.build();
        return build;
    }
}
