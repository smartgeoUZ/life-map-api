package db.impl.zones;

import db.impl.ConfigTestService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.runner.RunWith;
import uz.smartgeo.lifemap.api.db.interfaces.IUnit;

@RunWith(VertxUnitRunner.class)
public class UnitPostgreViloyatTest {
    static JDBCClient client;
    static JsonObject config;
    static IUnit dbService;
    static Vertx vertx;
    static ConfigTestService service;
    static JsonObject viloyat;
    static JsonObject dbQueries;

//    @BeforeClass
//    public static void init() throws Exception {
//        service = new ConfigTestService();
//        vertx = Vertx.vertx();
//        config = service.getTestConfig("/conf/db-test-config.json");
//        dbQueries = service.getTestConfig("/conf/dbqueries.json");
//        client = JDBCClient.createShared(vertx, config.getJsonObject("db"), config.getString("datasource", "datasource"));
//        dbService = new UnitService(vertx, config, LIFEMAP_CONST.TABLE_NAME_VILOYAT, dbQueries);
//        viloyat = new JsonObject()
//                .put("code", 1)         // "code", 						bigint, -- Код
//                .put("name_uz", "IIII")    // "name_uz", 				text, -- Наименование (узб)
//                .put("name_ru", "IIII")    // "name_ru", 				text, -- Наименование (рус)
//                .put("name_center_uz", "MR")    // "name_center_uz", 	text, -- Административный центр (узб)
//                .put("name_center_ru", "BR");   // "name_center_ru ",		text, -- Административный центр (рус)
//    }
//
//    @Test
//    public void getList(TestContext context) {
//        service.getList(context, viloyat, dbService);
//    }
//
//    @Test
//    public void getListByPerPage(TestContext context) {
//        service.getListByPerPage(context, dbService);
//    }
//
//    @Test
//    public void getCount(TestContext context) {
//        service.getCount(context, viloyat, dbService);
//    }
//
//    @Test
//    public void getPageCountByPerPage(TestContext context) {
//        service.getPageCountByPerPage(context, dbService);
//    }
//
//    @Test
//    public void getById(TestContext context) {
//        service.getById(context, dbService, viloyat, "name_uz", 1);
//    }
//
//    @Test
//    public void save(TestContext context) {
//        service.save(context, dbService, viloyat, "name_uz", 1);
//    }
//
//    @Test
//    public void update(TestContext context) {
//        JsonObject params = new JsonObject()
//                .put("code", 1)         // "code", 						bigint, -- Код
//                .put("name_uz", "IIII")    // "name_uz", 				text, -- Наименование (узб)
//                .put("name_ru", "IIII")    // "name_ru", 				text, -- Наименование (рус)
//                .put("name_center_uz", "MR")    // "name_center_uz", 	text, -- Административный центр (узб)
//                .put("name_center_ru", "BR");   // "name_center_ru ",		text, -- Административный центр (рус)
//
//        service.update(context, dbService, viloyat, params);
//    }
//
//    @Test
//    public void delete(TestContext context) {
//        service.delete(context, viloyat, dbService);
//    }
}

