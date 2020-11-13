package db.impl.auto;

import db.impl.ConfigTestService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.runner.RunWith;
import uz.smartgeo.lifemap.api.db.interfaces.IUnit;

@RunWith(VertxUnitRunner.class)
public class UnitPostgreAutoGroupTest {

    static JDBCClient client;
    static JsonObject config;
    static IUnit dbService;
    static Vertx vertx;
    static ConfigTestService service;
    static JsonObject group;
    static JsonObject dbQueries;
/*
    @BeforeClass
    public static void init() throws Exception {
        service = new ConfigTestService();
        vertx = Vertx.vertx();
        config = service.getTestConfig("/conf/db-test-config.json");
        dbQueries = service.getTestConfig("/conf/dbqueries.json");
        client = JDBCClient.createShared(vertx, config.getJsonObject("db"), config.getString("datasource", "datasource"));
        dbService = new UnitService(vertx, config, LIFEMAP_CONST.TABLE_NAME_AUTOGROUP, dbQueries);
        group = new JsonObject().put("name", "group_test_00");
    }

    @Test
    public void getList(TestContext context) {
        service.getList(context, group, dbService);
    }

    @Test
    public void getListByPerPage(TestContext context) {
        service.getListByPerPage(context, dbService);
    }

    @Test
    public void getCount(TestContext context) {
        service.getCount(context, group, dbService);
    }

    @Test
    public void getPageCountByPerPage(TestContext context) {
        service.getPageCountByPerPage(context, dbService);
    }

    @Test
    public void getById(TestContext context) {
        service.getById(context, dbService, group, "name", 0);
    }

    @Test
    public void save(TestContext context) {
        service.save(context, dbService, group, "name", 0);
    }

    @Test
    public void update(TestContext context) {
        JsonObject params = new JsonObject()
                .put("name", "John Lennon");

        service.update(context, dbService, group, params);
    }

    @Test
    public void delete(TestContext context) {
        service.delete(context, group, dbService);
    }*/
}