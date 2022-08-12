package dev.local.Entities;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;

import static dev.local.Application.BATCH_QUEUE_CHECK_COUNT;
import static dev.local.Application.PERSIST;
import static dev.local.Utils.Constants.*;
import static dev.local.Utils.Utils.log;

@Data
public class Items {

    public static int updatedItemSize = 0;
    public static int concurrentThreadCount = 0;
    public static int loadedItemSize = 0;
    public static HashMap<String, Item> documentMap = new HashMap<>();
    public static HashSet<String> insertedIdSet = new HashSet<>();

    public static void add(Item item) {
        if (!insertedIdSet.contains(item.getId())) {
            insertedIdSet.add(item.getId());
        } else {
            updatedItemSize++;
            if (updatedItemSize % BATCH_QUEUE_CHECK_COUNT == 0)
                log(LOG_MSG_SAME_SIZE + updatedItemSize, Items.class.toString());
        }
        documentMap.put(item.getId(),item);
    }

    public static void syncToMongo(HashMap<String, Item> docCloned, String threadOrder) {
        concurrentThreadCount++;
        log(LOG_MSG_THREAD + threadOrder + LOG_MSG_SYNC_MONGO_ST, Items.class.toString());
        log(LOG_MSG_CONC_THREAD_COUNT + concurrentThreadCount, Items.class.toString());

        PERSIST.many(docCloned);
        docCloned.clear();

        log(LOG_MSG_THREAD + threadOrder + LOG_MSG_SYNC_MONGO_FN, Items.class.toString());
        concurrentThreadCount--;
    }

}
