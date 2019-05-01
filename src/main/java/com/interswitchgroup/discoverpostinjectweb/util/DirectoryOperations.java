package com.interswitchgroup.discoverpostinjectweb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryOperations {

    private static final Logger logger = LoggerFactory.getLogger(DirectoryOperations.class);
    private Path directoryName;

    public DirectoryOperations(Path fileDirectory) {
        this.directoryName = fileDirectory;
    }

    public Path RenameFile(Path oldFileName, Path newFileName) {

        try {
            if(oldFileName.toFile().renameTo(newFileName.toAbsolutePath().toFile())) {
                logger.info("Moved successful to :" + newFileName);
            }else {
                logger.error("Moved failed " + oldFileName);
            }
        } catch(Exception ex) {
            logger.error("[RenameFile()] " + ex.getMessage());
        }

        return newFileName;
    }

    public String[] getFilesInDirectory() {
        File startingDirectory = new File(directoryName.toFile().getAbsolutePath());

        int numberOfFiles = CountFilesInDirectory();

        String[] fileData = null;
        /*
         * CHECK IF DIRECTORY IF VALID
         */
        validateDirectory(startingDirectory);

        Path dir = Paths.get(directoryName.toFile().getAbsolutePath());

        //  try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{txt}")) {//PICK ONLY TXT FILES
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) { //PICK ANY FILES

            fileData = new String[numberOfFiles];

            int i = 0;
            for (Path entry : stream) {
                fileData[i] = entry.getFileName().toString();
                i++;
            }
        } catch (IOException x) {
            logger.error("getFilesInDirectory Exception: " + x.getMessage());
        }
        return fileData;
    }

    public void validateDirectory(File aDirectory)  {
        try {
            if (aDirectory == null) {
                throw new IllegalArgumentException("Directory should not be null.");
            }
            if (!aDirectory.exists()) {
                throw new FileNotFoundException("Directory does not exist: "
                        + aDirectory);
            }
            if (!aDirectory.isDirectory()) {
                throw new IllegalArgumentException("Is not a directory: "
                        + aDirectory);
            }
            if (!aDirectory.canRead()) {
                throw new IllegalArgumentException("Directory cannot be read: "
                        + aDirectory);
            }
        }
        catch( FileNotFoundException e)
        {
               logger.error("[validateDirectory ()] " + e.getMessage());
        }
    }

    public int CountFilesInDirectory()  {
        int fileLength =0;
        try
        {
            fileLength = new File(directoryName.toFile().getAbsolutePath()).listFiles().length;
        }
        catch (Exception e)
        {
             logger.error("[CountFilesInDirectory ()]" + e.getMessage());
        }
        return fileLength;

    }

}
