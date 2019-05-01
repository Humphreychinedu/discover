package com.interswitchgroup.discoverpostinjectweb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static String validateDirectory(File aDirectory) {

        if (aDirectory == null) {
            return "Directory  should not be empty.";
        }
        if (!aDirectory.exists()) {
            return "Directory " +aDirectory.getPath() +  " does not exist." ;
        }
        if (!aDirectory.isDirectory()) {
            return aDirectory.getPath() + " is not a Directory." ;

        }
        if (!aDirectory.canRead()) {
            return aDirectory.getPath() + " Directory cannot be read." ;
        }

        return null;
    }

    public static int createDirectory(String fileName) {
        int retVal;

        try {
            File file = new File(fileName);

            if(!file.exists())
            {
                if(file.mkdir())
                {
                    logger.info("Directory is created!");
                    retVal = 1;
                }
                else {
                    logger.info("Directory creation failed!");
                    retVal = 0;
                }
            }
            else {
                logger.info("Directory already exists.");
                retVal = 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info(ex.getMessage());
            retVal = 0;
        }

        return retVal;
    }
}
