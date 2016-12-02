package com.grb.indonesia;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Author:zhangjing
 * Date:2016-10-17
 * Description:
 *    初始化加载入口
 */
@Component
public class InitCommandLine implements CommandLineRunner {

    private Logger logger = LogManager.getLogger(InitCommandLine.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("CommandLine Run....");
    }
}
