package dev.local.Utils;

import java.util.HashMap;

public class Constants {

    public static final String ITEM_STR = "item";
    public static final String ITEM_ID_STR = "id";
    public static final String ITEM_NAME_STR = "name";
    public static final String ITEM_CATEGORY_STR = "category";
    public static final String ITEM_BRAND_STR = "brand";
    public static final String ITEM_PRICE_STR = "price";
    public static final String ITEM_URL_STR = "url";
    public static final String ITEM_POSITION_STR = "position";
    public static final String ITEM_SUBCATEGORY_STR = "subcategory";
    public static final String ITEM_ACTUAL_PRICE_STR = "actual_price";
    public static final String ITEM_DISCOUNTED_PRICE_STR = "discounted_price";


    public static final String LOG_MSG_NEW_GROUP_ST = "------------------------- START -------------------------";
    public static final String LOG_MSG_NEW_GROUP_END = "------------------------- END -------------------------";
    public static final String LOG_MSG_ITEM_SIZE = "Size of the items is : ";
    public static final String LOG_MSG_MORE_THAN_PAUSE_LIMIT = "Item size is more than expected. Need to wait a little to consume.";
    public static final String LOG_MSG_PARSER_START = "Parser Started";
    public static final String LOG_MSG_PARSER_FINISH = "Parser Finished";
    public static final String LOG_MSG_PARSER_IND = "Parser Index : ";
    public static final String LOG_MSG_DISTINCT_IND = "Distinct Index : ";
    public static final String LOG_MSG_NEW_ELEMENTS_SIZE = "New Elements Size : ";
    public static final String LOG_MSG_ITEM_SIZE_OK = "Item size is ok. Parser is starting again.";
    public static final String LOG_MSG_CONNECT_SUCCESS = "Connected successfully to server.";
    public static final String LOG_MSG_MONGO_EXCEPTION = "An error occurred while attempting to run a command: ";
    public static final String LOG_MSG_MONGO_ERROR = "Mongo Connection Error";
    public static final String LOG_MSG_INSERT_ID_LENGTH = "Inserted id length : ";
    public static final String LOG_MSG_GO_INSERT = "Going to insert ";
    public static final String LOG_MSG_ELEMENTS = " elements";
    public static final String LOG_MSG_GET_ALL_DOCS = "Getting all docs";
    public static final String LOG_MSG_TOTAL_EL_DB_COUNT = "total elements count in DB : ";
    public static final String LOG_MSG_FETCHED = "Fetched";
    public static final String LOG_MSG_SAME_SIZE = "Same item size: ";
    public static final String LOG_MSG_THREAD = "Thread : ";
    public static final String LOG_MSG_SYNC_MONGO_ST = " Sync to Mongo started";
    public static final String LOG_MSG_SYNC_MONGO_FN = " Sync to Mongo finished";
    public static final String LOG_MSG_CONC_THREAD_COUNT = "Concurrent Thread Count : ";


    public static final String ENV_MONGO_CONNECTION_URL = "mongodbUrl";
    public static final String ENV_MONGO_DATABASE="mongodbDatabase";
    public static final String ENV_MONGO_COLLECTION="mongodbCollection";
}
