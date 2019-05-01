package com.interswitchgroup.discoverpostinjectweb.util;

import com.interswitchgroup.discoverpostinjectweb.exception.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

public class WriteFile {

    private static final Logger logger = LoggerFactory.getLogger(WriteFile.class);

    private final Path path;
    private boolean append_to_file = false;

    public WriteFile(Path file_path) {

        path = file_path;

    }

    public WriteFile(Path file_path, boolean append_value) {

        path = file_path;
        append_to_file = append_value;

    }

    public void writeToFile(String textLine) {

        logger.info("Discover FileConverter Service: Start writing to PostInject file");
        FileWriter write = null;
        try {

            write = new FileWriter(path.toFile().getAbsolutePath(), append_to_file);
            PrintWriter print_line = new PrintWriter(write);

            print_line.write(textLine);

            print_line.close();

            logger.info("Discover FileConverter Service: End writing file to PostInject file");

        } catch (IOException e) {
            logger.error("Discover FileConveter Error Write to File: " + e.fillInStackTrace());
        } finally {
            try {
                if (write != null) {
                    write.close();
                }
            } catch (Exception ex) {
                throw new FileUploadException("there file could not be found " + ex);
//                logger.error(ex);
            }
        }

    }
}
