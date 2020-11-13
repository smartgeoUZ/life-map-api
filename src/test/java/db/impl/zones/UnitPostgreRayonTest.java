package db.impl.zones;

import db.impl.ConfigTestService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.runner.RunWith;
import uz.smartgeo.lifemap.api.db.interfaces.IUnit;

@RunWith(VertxUnitRunner.class)
public class UnitPostgreRayonTest {
    static JDBCClient client;
    static JsonObject config;
    static IUnit dbService;
    static Vertx vertx;
    static ConfigTestService service;
    static JsonObject rayon;
    static JsonObject dbQueries;

//    @BeforeClass
//    public static void init() throws Exception {
//        service = new ConfigTestService();
//        vertx = Vertx.vertx();
//        config = service.getTestConfig("/conf/db-test-config.json");
//        dbQueries = service.getTestConfig("/conf/dbqueries.json");
//        client = JDBCClient.createShared(vertx, config.getJsonObject("db"), config.getString("datasource", "datasource"));
//        dbService = new UnitService(vertx, config, LIFEMAP_CONST.TABLE_NAME_RAYON, dbQueries);
//        rayon = new JsonObject()
//                .put("code", 1)           // "code", 					bigint, -- Код
//                .put("name_uz", "IIII")      // "name_uz", 			text, -- Наименование (узб)
//                .put("name_ru", "IIII")      // "name_ru", 			text, -- Наименование (рус)
//                .put("name_center_uz", "MR")      // "name_center_uz", text, -- Административный центр (узб)
//                .put("name_center_ru", "BR");     // "name_center_ru", text, -- Административный центр (рус)
//
//    }
//
//    @Test
//    public void getList(TestContext context) {
//        service.getList(context, rayon, dbService);
//    }
//
//    @Test
//    public void getListByPerPage(TestContext context) {
//        service.getListByPerPage(context, dbService);
//    }
//
//    @Test
//    public void getCount(TestContext context) {
//        service.getCount(context, rayon, dbService);
//    }
//
//    @Test
//    public void getPageCountByPerPage(TestContext context) {
//        service.getPageCountByPerPage(context, dbService);
//    }
//
//    @Test
//    public void getById(TestContext context) {
//        service.getById(context, dbService, rayon, "name_uz", 1);
//    }
//
//    @Test
//    public void save(TestContext context) {
//        service.save(context, dbService, rayon, "name_uz", 1);
//    }
//
//    @Test
//    public void update(TestContext context) {
//        JsonObject params = new JsonObject()
//                .put("code", 1)           // "code", 					bigint, -- Код
//                .put("name_uz", "IIII")      // "name_uz", 			text, -- Наименование (узб)
//                .put("name_ru", "IIII")      // "name_ru", 			text, -- Наименование (рус)
//                .put("name_center_uz", "MR")      // "name_center_uz", text, -- Административный центр (узб)
//                .put("name_center_ru", "BR");     // "name_center_ru", text, -- Административный центр (рус)
//
//        service.update(context, dbService, rayon, params);
//    }
//
//    @Test
//    public void delete(TestContext context) {
//        service.delete(context, rayon, dbService);
//    }
}
