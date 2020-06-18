package com.park.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * @author Aaron
 * @date 2020/6/17 14:41
 */
public class JobForDay implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        switch (shardingContext.getShardingItem()) {
            case 0:
                System.out.println(" ---------------- run 0");
                break;
            case 1:
                System.out.println(" ---------------- run 1");
                break;
            case 2:
                System.out.println(" ---------------- run 2");
                break;
        }
    }
}
