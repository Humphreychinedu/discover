package com.interswitchgroup.discoverpostinjectweb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public class FileReader {
    private final static Logger logger = LoggerFactory.getLogger(FileReader.class);
    private Path path;

    java.io.FileReader fr  = null ;
    BufferedReader buffReader = null;

    public FileReader(Path file_path)
    {

        path = file_path;
    }

    /*
     * READ FILE CONTENTS
     *
     */
    public void openFile ()
    {
        logger.info("Discover FileConverter Service: openFile");

        String newPath = path.toAbsolutePath().toString();
        try {

            this.fr = new java.io.FileReader(newPath);
            this.buffReader = new BufferedReader(this.fr);
        }
        catch (FileNotFoundException fx)
        {
              logger.error("FileNotFoundException Discover FileConverter Error Read Discover File: " + fx.getMessage());
        }
    }

    public String fetchNextLine(){
        try {
            return this.buffReader.readLine();
        }
        catch (IOException ex){
               logger.error("IOException : " + ex.getMessage());
        }

        return null;
    }

    public void safeClose() {
        if (this.fr != null) {
            try {
                this.fr.close();
            } catch (IOException e) {
                logger.error("[safeClose()]" + e.getMessage());
            }
        }
    }
}
