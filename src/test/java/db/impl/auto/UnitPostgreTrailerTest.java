package db.impl.auto;

import db.impl.ConfigTestService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.runner.RunWith;
import uz.smartgeo.lifemap.api.db.interfaces.IUnit;

@RunWith(VertxUnitRunner.class)
public class UnitPostgreTrailerTest {
    static JDBCClient client;
    static JsonObject config;
    static IUnit dbService;
    static Vertx vertx;
    static ConfigTestService service;
    static JsonObject trailer;
    static JsonObject dbQueries;

  /*  @BeforeClass
    public static void init() throws Exception {
        service = new ConfigTestService();
        vertx = Vertx.vertx();
        config = service.getTestConfig("/conf/db-test-config.json");
        dbQueries = service.getTestConfig("/conf/dbqueries.json");
        client = JDBCClient.createShared(vertx, config.getJsonObject("db"), config.getString("datasource", "datasource"));
        dbService = new UnitService(vertx, config, LIFEMAP_CONST.TABLE_NAME_TRAILER, dbQueries);
        trailer = new JsonObject()
                .put("name", "Trailer_00") // "name",	 text, -- Наименование
                .put("trailer_group_id", 12) // "trailer_group_id",	 bigint, -- Ширина прицепа
                .put("width", 12.0) // "width",	 double precision, -- Ширина прицепа
                .put("inventory_num", "123") // "inventory_num",	 text, -- Инвентарный №
                .put("rfid", "jkdfhsjkdf") // "rfid",	 	text, -- RFID
                .put("comment", "dsjdhsjdh"); // "comment",	 	text, -- Примечание
    }

    @Test
    public void getList(TestContext context) {
        service.getList(context, trailer, dbService);
    }

    @Test
    public void getListByPerPage(TestContext context) {
        service.getListByPerPage(context, dbService);
    }

    @Test
    public void getCount(TestContext context) {
        service.getCount(context, trailer, dbService);
    }

    @Test
    public void getPageCountByPerPage(TestContext context) {
        service.getPageCountByPerPage(context, dbService);
    }

    @Test
    public void getById(TestContext context) {
        service.getById(context, dbService, trailer, "name", 0);
    }

    @Test
    public void save(TestContext context) {
        service.save(context, dbService, trailer, "name", 0);
    }

    @Test
    public void update(TestContext context) {
        JsonObject params = new JsonObject()
                .put("name", "Trailer_00") // "name",	 text, -- Наименование
                .put("trailer_group_id", 12) // "trailer_group_id",	 bigint, -- Ширина прицепа
                .put("width", 12.0) // "width",	 double precision, -- Ширина прицепа
                .put("inventory_num", "123") // "inventory_num",	 text, -- Инвентарный №
                .put("rfid", "jkdfhsjkdf") // "rfid",	 	text, -- RFID
                .put("comment", "dsjdhsjdh"); // "comment",	 	text, -- Примечание

        service.update(context, dbService, trailer, params);
    }

    @Test
    public void delete(TestContext context) {
        service.delete(context, trailer, dbService);
    }*/
}
