package db.impl.personal;

import db.impl.ConfigTestService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.runner.RunWith;
import uz.smartgeo.lifemap.api.db.interfaces.IUnit;

@RunWith(VertxUnitRunner.class)
public class UnitPostgreFarmerTest {

    static JDBCClient client;
    static JsonObject config;
    static IUnit dbService;
    static Vertx vertx;
    static ConfigTestService service;
    static JsonObject farmer;
    static JsonObject dbQueries;
/*

    @BeforeClass
    public static void init() throws Exception {
        service = new ConfigTestService();
        vertx = Vertx.vertx();
        config = service.getTestConfig("/conf/db-test-config.json");
        dbQueries = service.getTestConfig("/conf/dbqueries.json");
        client = JDBCClient.createShared(vertx, config.getJsonObject("db"), config.getString("datasource", "datasource"));
        dbService = new UnitService(vertx, config, LIFEMAP_CONST.TABLE_NAME_FARMER, dbQueries);
        farmer = new JsonObject()
                .put("surname", "Ivanov") // "surname", text, -- Фамилия
                .put("name", "Ivan") // "name", text, -- Имя
                .put("midname", "Ivanovich") // "midname", text, -- Отчество
                .put("farming_place", "Fergana") // "farming_place", text, -- Фермерское хозяйство
                .put("address", "Fergana da") // "address", text, -- Адрес
                .put("phone_work", "123456") // "phone_work", text, -- Рабочий телефон
                .put("phone_home", "123456") // "phone_home", text, -- Домашний телефон
                .put("phone_cell", "123456") // "phone_cell", text, -- Сотовый телефон
                .put("email", "i@gmail.com") // "email", text, -- e-mail
                .put("commet", "dsda") // "commet", text, -- Примечание
                .put("photo", "dasdasd"); // "photo", text, -- ссылка на фото
    }

    @Test
    public void getList(TestContext context) {
        service.getList(context, farmer, dbService);
    }

    @Test
    public void getListByPerPage(TestContext context) {
        service.getListByPerPage(context, dbService);
    }

    @Test
    public void getCount(TestContext context) {
        service.getCount(context, farmer, dbService);
    }

    @Test
    public void getPageCountByPerPage(TestContext context) {
        service.getPageCountByPerPage(context, dbService);
    }

    @Test
    public void getById(TestContext context) {
        service.getById(context, dbService, farmer, "surname", 0);
    }

    @Test
    public void save(TestContext context) {
        service.save(context, dbService, farmer, "surname", 0);
    }

    @Test
    public void update(TestContext context) {
        JsonObject params = new JsonObject()
                .put("surname", "Petrov") // surname text, -- Фамилия
                .put("name", "Ivan") // name text, -- Имя
                .put("midname", "Ivanovich") // midname text, -- Отчество
                .put("farming_place", "Fergana") // farming_place text, -- Фермерское хозяйство
                .put("address", "Fergana da") // address text, -- Адрес
                .put("phone_work", "123456") // phone_work text, -- Рабочий телефон
                .put("phone_home", "123456") // phone_home text, -- Домашний телефон
                .put("phone_cell", "123456") // phone_cell text, -- Сотовый телефон
                .put("email", "i@gmail.com") // email text, -- e-mail
                .put("commet", "dsda") // commet text, -- Примечание
                .put("photo", "dasdasd"); // photo text, -- ссылка на фото

        service.update(context, dbService, farmer, params);
    }

    @Test
    public void delete(TestContext context) {
        service.delete(context, farmer, dbService);
    }
*/
}
