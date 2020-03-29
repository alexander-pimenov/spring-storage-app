package ru.pimalex1978.springstorageapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.xml.sax.SAXException;
import ru.pimalex1978.springstorageapp.entity.Box;
import ru.pimalex1978.springstorageapp.entity.Item;
import ru.pimalex1978.springstorageapp.parser.XMLParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.List;

@SpringBootApplication
public class SpringStorageAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStorageAppApplication.class, args);
    }

    /**
     * Запрос на удаление двух таблиц Box и Item.
     */
    private static final String DROP_ITEM_QUERY;

    static {
        DROP_ITEM_QUERY = "DROP TABLE IF EXISTS ITEM";
    }

    private static final String DROP_BOX_QUERY;

    static {
        DROP_BOX_QUERY = "DROP TABLE IF EXISTS BOX";
    }

    /**
     * Запрос на создание двух таблиц Box и Item.
     */
    private static final String CREATE_BOX_QUERY;

    static {
        CREATE_BOX_QUERY = "CREATE TABLE IF NOT EXISTS BOX (id INTEGER PRIMARY KEY, contained_in INTEGER)";
    }

    private static final String CREATE_ITEM_QUERY;

    static {
        CREATE_ITEM_QUERY = "CREATE TABLE IF NOT EXISTS ITEM (id INTEGER PRIMARY KEY, contained_in INTEGER REFERENCES box(id), color VARCHAR(100))";
    }

    /**
     * Запуск метода для распарсивания Storage.xml сразу после старта программы.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void doParsingFileAfterStartup() {
        System.out.println("Hello, I'm parser. I have just started up");

        //##############################################################

        XMLParser xmlParser = new XMLParser();

        //Получим путь к нашему xml файлу Storage.xml
        ClassLoader classLoader = SpringStorageAppApplication.class.getClassLoader();
        String fileName = classLoader.getResource("Storage.xml").getPath();


        try {
            xmlParser.parseXML(fileName);
        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "root", "")) {

            try (Statement dataQuery = connection.createStatement()) {

                //Запросы для того, чтобы пересоздавать таблицы, если они остались в БД
                dataQuery.execute(DROP_BOX_QUERY);
                dataQuery.execute(DROP_ITEM_QUERY);
                dataQuery.execute(CREATE_BOX_QUERY);
                dataQuery.execute(CREATE_ITEM_QUERY);

                //Заполняем таблицу Box данными.
                List<Box> boxes = xmlParser.getBoxes();
                for (Box box : boxes) {

                    dataQuery.executeUpdate("INSERT INTO BOX (id, contained_in) VALUES (" + box.getId() + ", " + box.getParentId() + ");");
                }

                //Заполняем таблицу Item данными.
                List<Item> items = xmlParser.getItems();
                for (Item item : items) {
                    dataQuery.executeUpdate("INSERT INTO ITEM (id, contained_in, color) VALUES (" + item.getId() + ","
                            + item.getParentId() + "," + "'" + item.getColor() + "'" + ");");
                }
            }

            //Проверяем какие данные записались в таблицу Box, выводим эти данные в консоль
            try (PreparedStatement query =
                         connection.prepareStatement("SELECT * FROM BOX")) {
                ResultSet rs = query.executeQuery();
                System.out.println("=== Данные таблицы BOX ===");
                while (rs.next()) {
                    System.out.println(String.format("%d, %d",
                            rs.getInt(1),
                            rs.getInt(2))
                    );
                }
                rs.close();
            }

            //Проверяем какие данные записались в таблицу Item, выводим эти данные в консоль
            try (PreparedStatement query =
                         connection.prepareStatement("SELECT * FROM ITEM")) {
                ResultSet rs = query.executeQuery();
                System.out.println("=== Данные таблицы ITEM ===");
                while (rs.next()) {
                    System.out.println(String.format("%d, %d, %s",
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3))
                    );
                }

                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("Database connection failure: "
                    + ex.getMessage());
        }
    }
}
