package dev.local;

import dev.local.Entities.Items;
import dev.local.Services.Parser;
import dev.local.Services.Persist;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static dev.local.Utils.Utils.log;

public class Application {

    public static final int BATCH_QUEUE_CHECK_COUNT = 50000;
    public static final int WAIT_MS_BATCH_QUEUE = 5000;
    public static final int PAUSE_ITEM_LIMIT = 200000;
    public static String FILE_PATH;
    public static Persist PERSIST;
    public static final Parser PARSER = new Parser();
    public static Properties prop = new Properties();
    

    public static void main(String[] args) throws InterruptedException, XMLStreamException, IOException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("application.properties");
        prop.load(stream);

        FILE_PATH = (String) prop.get("xml-file-path");
        PERSIST = new Persist(true);

        long start = System.nanoTime();

        PERSIST.getAll();
        PARSER.start();

        long end = System.nanoTime();
        long ms = (end - start) / 1000000;
        long s = ms / 60;

        waitAllThreadsEnd();
        log("Total inserted document size : " + (Items.insertedIdSet.size() - Items.loadedItemSize), Application.class.toString());
        log("Updated elements size : " + Items.updatedItemSize, Application.class.toString());
        log("Elapsed time : " + ms + " ms and " + s + " secs", Application.class.toString());
    }

    private static boolean waitAllThreadsEnd() throws InterruptedException {
        if (Items.concurrentThreadCount > 0) {
            Thread.sleep(1000);
            return waitAllThreadsEnd();
        }
        return true;
    }

}