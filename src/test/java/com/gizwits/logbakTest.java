package com.gizwits;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by feel on 16/4/12.
 */
public class logbakTest {


    public static void main(String[] args) throws Exception {


        Logger logger = LoggerFactory.getLogger(logbakTest.class);


        while (true) {

            logger.info("info-----" + DateTime.now());
            Thread.sleep(500);

            logger.error("info-----" + DateTime.now());
            Thread.sleep(1000);

        }


        //end main
    }
}
