package db.impl.zones;

import db.impl.ConfigTestService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.runner.RunWith;
import uz.smartgeo.lifemap.api.db.interfaces.IUnit;

@RunWith(VertxUnitRunner.class)
public class UnitPostgreAgroCultureTest {
    static JDBCClient client;
    static JsonObject config;
    static IUnit dbService;
    static Vertx vertx;
    static ConfigTestService service;
    static JsonObject agroCulture;
    static JsonObject dbQueries;

//    @BeforeClass
//    public static void init() throws Exception {
//        service = new ConfigTestService();
//        vertx = Vertx.vertx();
//        config = service.getTestConfig("/conf/db-test-config.json");
//        dbQueries = service.getTestConfig("/conf/dbqueries.json");
//        client = JDBCClient.createShared(vertx, config.getJsonObject("db"), config.getString("datasource", "datasource"));
//        dbService = new UnitService(vertx, config, LIFEMAP_CONST.TABLE_NAME_AGRO_CULTURE, dbQueries);
//        agroCulture = new JsonObject()
//                .put("name", "Ris")         // name text, -- Наименование
//                .put("photo", "jkdsahjd")    // photo text, -- Изображение
//                .put("comment", "dsadas");     // comment text, -- Примечание
//    }
//
//    @Test
//    public void getList(TestContext context) {
//        service.getList(context, agroCulture, dbService);
//    }
//
//    @Test
//    public void getListByPerPage(TestContext context) {
//        service.getListByPerPage(context, dbService);
//    }
//
//    @Test
//    public void getCount(TestContext context) {
//        service.getCount(context, agroCulture, dbService);
//    }
//
//    @Test
//    public void getPageCountByPerPage(TestContext context) {
//        service.getPageCountByPerPage(context, dbService);
//    }
//
//    @Test
//    public void getById(TestContext context) {
//        service.getById(context, dbService, agroCulture, "name", 0);
//    }
//
//    @Test
//    public void save(TestContext context) {
//        service.save(context, dbService, agroCulture, "name", 0);
//    }
//
//    @Test
//    public void update(TestContext context) {
//        JsonObject params = new JsonObject()
//                .put("name", "Ris")         // name text, -- Наименование
//                .put("photo", "jkdsahjd")    // photo text, -- Изображение
//                .put("comment", "dsadas");     // comment text, -- Примечание
//
//        service.update(context, dbService, agroCulture, params);
//    }
//
//    @Test
//    public void delete(TestContext context) {
//        service.delete(context, agroCulture, dbService);
//    }
}

