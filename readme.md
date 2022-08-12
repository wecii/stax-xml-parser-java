## Stax-Google-Feed-Parser
It is an application that helps to read and extract google feed xml data very quickly and then quickly perform bulk upsert operations on mongodb.

## Prerequisites

- MongoDB
- XML file

## Sample XML

```

<rss xmlns:g="http://base.google.com/ns/1.0" version="2.0">
    <channel>
        <title>Teknosa - Online Store</title>
        <link>http://www.teknosa.com</link>
        <description>This is a sample feed containing the required and recommended attributes for a variety of different products</description>
            <item>
                <id>780290960</id>
                <name>Logitech H150 Mikrofonlu Kulak Üstü Kulaklık</name>
                <category>Kulak Üstü Kulaklık</category>
                <brand>Logitech</brand>
                <price>390.9</price>
                <url>/logitech-h150-mikrofonlu-kulak-ustu-kulaklik-p-780290960</url>
                <position>1</position>
                <subcategory>Kulak Üstü Kulaklık</subcategory>
                <actual_price>0.0</actual_price>
                <discounted_price>390.9</discounted_price>
            </item>
    </channel>
</rss>

```