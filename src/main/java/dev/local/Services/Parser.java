package dev.local.Services;

import dev.local.Application;
import dev.local.Entities.Item;
import dev.local.Entities.Items;
import org.bson.Document;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;

import static dev.local.Application.*;
import static dev.local.Utils.Constants.*;
import static dev.local.Utils.Utils.log;

public class Parser {

    private static final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

    private static Item item = null;
    private static int indexCounter = 0;

    private boolean checkPause() throws InterruptedException {

        log(LOG_MSG_ITEM_SIZE + Items.documentMap.size(), Parser.class.toString());

        if (Items.documentMap.size() >= PAUSE_ITEM_LIMIT) {
            log(LOG_MSG_MORE_THAN_PAUSE_LIMIT, Parser.class.toString());
            Thread.sleep(WAIT_MS_BATCH_QUEUE);
            return checkPause();
        }

        return false;
    }

    private void triggerPersist(){
        if (Items.documentMap.size() > 0) {
            HashMap<String, Item> docCloned = (HashMap<String, Item>) Items.documentMap.clone();
            Items.documentMap.clear();
            new Thread(() -> {
                Items.syncToMongo(docCloned, String.valueOf(indexCounter / 50_000));
            }).start();
        }
    }

    private void parseItem(StartElement startElement, XMLEvent nextEvent, XMLEventReader reader) throws XMLStreamException {
        switch (startElement.getName().getLocalPart()) {
            case ITEM_STR -> {
                item = new Item();
                indexCounter++;
            }
            case ITEM_ID_STR -> {
                nextEvent = reader.nextEvent();
                item.setId(nextEvent.asCharacters().getData());
            }
            case ITEM_NAME_STR -> {
                nextEvent = reader.nextEvent();
                item.setName(nextEvent.asCharacters().getData());
            }
            case ITEM_CATEGORY_STR -> {
                nextEvent = reader.nextEvent();
                item.setCategory(nextEvent.asCharacters().getData());
            }
            case ITEM_BRAND_STR -> {
                nextEvent = reader.nextEvent();
                item.setBrand(nextEvent.asCharacters().getData());
            }
            case ITEM_PRICE_STR -> {
                nextEvent = reader.nextEvent();
                item.setPrice(Float.parseFloat(nextEvent.asCharacters().getData().replace("USD", "").trim()));
            }
            case ITEM_URL_STR -> {
                nextEvent = reader.nextEvent();
                item.setUrl(nextEvent.asCharacters().getData());
            }
            case ITEM_POSITION_STR -> {
                nextEvent = reader.nextEvent();
                item.setPosition(Integer.parseInt(nextEvent.asCharacters().getData()));
            }
            case ITEM_SUBCATEGORY_STR -> {
                nextEvent = reader.nextEvent();
                item.setSubcategory(nextEvent.asCharacters().getData());
            }
            case ITEM_ACTUAL_PRICE_STR -> {
                nextEvent = reader.nextEvent();
                item.setActualPrice(Float.parseFloat(nextEvent.asCharacters().getData().replace("USD", "").trim()));
            }
            case ITEM_DISCOUNTED_PRICE_STR -> {
                nextEvent = reader.nextEvent();
                item.setDiscountedPrice(Float.parseFloat(nextEvent.asCharacters().getData().replace("USD", "").trim()));
            }
        }
    }

        public void start () throws FileNotFoundException, XMLStreamException, InterruptedException {
            log(LOG_MSG_PARSER_START, Parser.class.toString());

            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(Application.FILE_PATH));

            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();

                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();

                    try {
                        parseItem(startElement, nextEvent, reader);
                    } catch (Exception ex) {
                        continue;
                    }
                }

                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();

                    if (endElement.getName().getLocalPart().equals(ITEM_STR)) {

                        Items.add(item);

                        int newElementsSize = Items.documentMap.size();
                        int distinctSize = Items.insertedIdSet.size();

                        if (indexCounter % BATCH_QUEUE_CHECK_COUNT == 0 || !reader.hasNext()) {

                            log(LOG_MSG_NEW_GROUP_ST,Parser.class.toString());
                            log(LOG_MSG_PARSER_IND+ indexCounter, Parser.class.toString());
                            log(LOG_MSG_DISTINCT_IND + distinctSize, Parser.class.toString());
                            log(LOG_MSG_NEW_ELEMENTS_SIZE + newElementsSize, Parser.class.toString());
                            log(LOG_MSG_NEW_GROUP_END,Parser.class.toString());

                            triggerPersist();

                            // Busy-Looping
                            Thread.sleep(1000);

                            if (!checkPause())
                                log(LOG_MSG_ITEM_SIZE_OK, Parser.class.toString());
                        }

                        item = null;
                    }
                }
            }

            triggerPersist();
            log(LOG_MSG_PARSER_FINISH, Parser.class.toString());
        }
    }
