package Utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReaders {
    private FileInputStream fis;
    public Logger logger = LogManager.getLogger(this.getClass());
    public Properties callzenProp(String str) {
        Properties prop = new Properties();
        try {
            fis = new FileInputStream(System.getProperty("user.dir") + "/resource/ConfigDirectory/" + str + ".properties");
            prop.load(fis);
        } catch (FileNotFoundException e) {
            logger.error("File " + str + ".properties" + " does not exist", e);
        } catch (IOException e) {
            logger.error("Failed to read file " + str + ".properties", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("Failed to close FileInputStream", e);
                }
            }
        }
        return prop;
    }

}

