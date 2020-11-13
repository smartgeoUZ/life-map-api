package db.impl.zones;

import db.impl.ConfigTestService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.runner.RunWith;
import uz.smartgeo.lifemap.api.db.interfaces.IUnit;

@RunWith(VertxUnitRunner.class)
public class UnitPostgreFieldGroupTest {
    static JDBCClient client;
    static JsonObject config;
    static IUnit dbService;
    static Vertx vertx;
    static ConfigTestService service;
    static JsonObject fieldGroup;
    static JsonObject dbQueries;
/*
    @BeforeClass
    public static void init() throws Exception {
        service = new ConfigTestService();
        vertx = Vertx.vertx();
        config = service.getTestConfig("/conf/db-test-config.json");
        dbQueries = service.getTestConfig("/conf/dbqueries.json");
        client = JDBCClient.createShared(vertx, config.getJsonObject("db"), config.getString("datasource", "datasource"));
        dbService = new UnitService(vertx, config, LIFEMAP_CONST.TABLE_NAME_FIELD_GROUP, dbQueries);
        fieldGroup = new JsonObject()
                .put("name", "FieldGroup_00");

    }

    @Test
    public void getList(TestContext context) {
        service.getList(context, fieldGroup, dbService);
    }

    @Test
    public void getListByPerPage(TestContext context) {
        service.getListByPerPage(context, dbService);
    }

    @Test
    public void getCount(TestContext context) {
        service.getCount(context, fieldGroup, dbService);
    }

    @Test
    public void getPageCountByPerPage(TestContext context) {
        service.getPageCountByPerPage(context, dbService);
    }

    @Test
    public void getById(TestContext context) {
        service.getById(context, dbService, fieldGroup, "name", 0);
    }

    @Test
    public void save(TestContext context) {
        service.save(context, dbService, fieldGroup, "name", 0);
    }

    @Test
    public void update(TestContext context) {
        JsonObject params = new JsonObject()
                .put("name", "FieldGroup_00");

        service.update(context, dbService, fieldGroup, params);
    }

    @Test
    public void delete(TestContext context) {
        service.delete(context, fieldGroup, dbService);
    }*/
}
