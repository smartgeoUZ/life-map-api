package db.impl.auto;

import db.impl.ConfigTestService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.runner.RunWith;
import uz.smartgeo.lifemap.api.db.interfaces.IUnit;

@RunWith(VertxUnitRunner.class)
public class UnitPostgreAutoTest {

    static JDBCClient client;
    static JsonObject config;
    static IUnit dbService;
    static Vertx vertx;
    static ConfigTestService service;
    static JsonObject auto;
    static JsonObject dbQueries;
/*
    @BeforeClass
    public static void init() throws Exception {
        service = new ConfigTestService();
        vertx = Vertx.vertx();
        config = service.getTestConfig("/conf/db-test-config.json");
        dbQueries = service.getTestConfig("/conf/dbqueries.json");
        client = JDBCClient.createShared(vertx, config.getJsonObject("db"), config.getString("datasource", "datasource"));
        dbService = new UnitService(vertx, config, LIFEMAP_CONST.TABLE_NAME_AUTO, dbQueries);
        auto = new JsonObject()
                .put("mobject_id", 13)                 // "mobject_id",
                .put("mobject_plate_number", "A142ZZ")      // "mobject_plate_number",
                .put("mobject_tank_capacity", 2.0)                // "mobject_tank_capacity",
                .put("auto_name", "Malibu")         // "auto_name",
                .put("auto_group_id", 777)               // "auto_group_id",
                .put("auto_type_id", 1)                   // "auto_type_id",
                .put("auto_trailer_id", 1)                   // "auto_trailer_id",
                .put("trailer_width", 7.5)                // "trailer_width",
                .put("auto_marka", "Sedan")         // "auto_marka",
                .put("engine_capacity", 2.0)                // "engine_capacity",
                .put("owner_id", 1)                   // "owner_id",
                .put("fuel_lp100km", 10.0)              // "fuel_lp100km",
                .put("fuel_lp100hour", 10.0)              // "fuel_lp100hour",
                .put("lifting_capacity", 5.0)                // "lifting_capacity",
                .put("comment", "Cool car")      // "comment",
                .put("set_time_min_movement", 10)                 // "set_time_min_movement",
                .put("set_time_min_distance", 10.0)              // "set_time_min_distance",
                .put("mo_photo_filename", 100)               // "mo_photo_filename",
                .put("mo_pin_id", 101);             // "mo_pin_id",
    }

    @Test
    public void getList(TestContext context) {
        service.getList(context, auto, dbService);
    }

    @Test
    public void getListByPerPage(TestContext context) {
        service.getListByPerPage(context, dbService);
    }

    @Test
    public void getCount(TestContext context) {
        service.getCount(context, auto, dbService);
    }

    @Test
    public void getPageCountByPerPage(TestContext context) {
        service.getPageCountByPerPage(context, dbService);
    }

    @Test
    public void getById(TestContext context) {
        service.getById(context, dbService, auto, "mobject_plate_number", 1);
    }

    @Test
    public void save(TestContext context) {
        service.save(context, dbService, auto, "mobject_plate_number", 1);
    }

    @Test
    public void update(TestContext context) {
        JsonObject params = new JsonObject()
                .put("mobject_id", 13)                 // "mobject_id",
                .put("mobject_plate_number", "A142ZZ")      // "mobject_plate_number",
                .put("mobject_tank_capacity", 2.0)                // "mobject_tank_capacity",
                .put("auto_name", "Malibu")         // "auto_name",
                .put("auto_group_id", 777)               // "auto_group_id",
                .put("auto_type_id", 1)                   // "auto_type_id",
                .put("auto_trailer_id", 1)                   // "auto_trailer_id",
                .put("trailer_width", 7.5)                // "trailer_width",
                .put("auto_marka", "Sedan")         // "auto_marka",
                .put("engine_capacity", 2.0)                // "engine_capacity",
                .put("owner_id", 1)                   // "owner_id",
                .put("fuel_lp100km", 10.0)              // "fuel_lp100km",
                .put("fuel_lp100hour", 10.0)              // "fuel_lp100hour",
                .put("lifting_capacity", 5.0)                // "lifting_capacity",
                .put("comment", "Cool car")      // "comment",
                .put("set_time_min_movement", 10)                 // "set_time_min_movement",
                .put("set_time_min_distance", 10.0)              // "set_time_min_distance",
                .put("mo_photo_filename", 100)               // "mo_photo_filename",
                .put("mo_pin_id", 101);             // "mo_pin_id",
        service.update(context, dbService, auto, params);
    }

    @Test
    public void delete(TestContext context) {
        service.delete(context, auto, dbService);
    }*/
}