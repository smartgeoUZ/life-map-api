package db.impl.personal;

import db.impl.ConfigTestService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.runner.RunWith;
import uz.smartgeo.lifemap.api.db.interfaces.IUnit;

@RunWith(VertxUnitRunner.class)
public class UnitPostgreDriverTest {

    static JDBCClient client;
    static JsonObject config;
    static IUnit dbService;
    static Vertx vertx;
    static ConfigTestService service;
    static JsonObject driver;
    static JsonObject dbQueries;

   /* @BeforeClass
    public static void init() throws Exception {
        service = new ConfigTestService();
        vertx = Vertx.vertx();
        config = service.getTestConfig("/conf/db-test-config.json");
        dbQueries = service.getTestConfig("/conf/dbqueries.json");
        client = JDBCClient.createShared(vertx, config.getJsonObject("db"), config.getString("datasource", "datasource"));
        dbService = new UnitService(vertx, config, LIFEMAP_CONST.TABLE_NAME_DRIVER, dbQueries);
        driver = new JsonObject()
                .put("surname", "Ivanov")          // surname text, -- Фамилия
                .put("name", "Ivan")            // name text, -- Имя
                .put("midname", "Ivanovich")       // midname text, -- Отчество
                .put("working_place", "BePro")           // working_place text, -- Место работы
                .put("address", "Tashkent")        // address text, -- Адрес
                .put("phone_work", "2353535")         // phone_work text, -- Рабочий телефон
                .put("phone_home", "2313131")         // phone_home text, -- Домашний телефон
                .put("phone_cell", "2303030")         // phone_cell text, -- Сотовый телефон
                .put("email", "ivan@google.com") // email text, -- e-mail
                .put("rfid", "AX124A")          // rfid text, -- RFID
                .put("driver_licence", "Driver license")  // driver_licence text, -- Водительское удостоверение
                .put("commet", "Cool car")        // commet text, -- Примечание
                .put("photo", "It's me");        // photo text, -- ссылка на фото
    }

    @Test
    public void getList(TestContext context) {
        service.getList(context, driver, dbService);
    }

    @Test
    public void getListByPerPage(TestContext context) {
        service.getListByPerPage(context, dbService);
    }

    @Test
    public void getCount(TestContext context) {
        service.getCount(context, driver, dbService);
    }

    @Test
    public void getPageCountByPerPage(TestContext context) {
        service.getPageCountByPerPage(context, dbService);
    }

    @Test
    public void getById(TestContext context) {
        service.getById(context, dbService, driver, "surname", 0);
    }

    @Test
    public void save(TestContext context) {
        service.save(context, dbService, driver, "surname", 0);
    }

    @Test
    public void update(TestContext context) {
        JsonObject params = new JsonObject()
                .put("surname", "Kosimov")         // surname text, -- Фамилия
                .put("name", "Ivan")            // name text, -- Имя
                .put("midname", "Ivanovich")       // midname text, -- Отчество
                .put("working_place", "BePro")           // working_place text, -- Место работы
                .put("address", "Tashkent")        // address text, -- Адрес
                .put("phone_work", "2353535")         // phone_work text, -- Рабочий телефон
                .put("phone_home", "2313131")         // phone_home text, -- Домашний телефон
                .put("phone_cell", "2303030")         // phone_cell text, -- Сотовый телефон
                .put("email", "ivan@google.com") // email text, -- e-mail
                .put("rfid", "AX124A")          // rfid text, -- RFID
                .put("driver_licence", "Driver license")  // driver_licence text, -- Водительское удостоверение
                .put("commet", "Cool car")        // commet text, -- Примечание
                .put("photo", "It's me");        // photo text, -- ссылка на фото

        service.update(context, dbService, driver, params);
    }

    @Test
    public void delete(TestContext context) {
        service.delete(context, driver, dbService);
    }

*/
}