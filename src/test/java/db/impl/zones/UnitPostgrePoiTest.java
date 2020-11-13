package db.impl.zones;

import db.impl.ConfigTestService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.runner.RunWith;
import uz.smartgeo.lifemap.api.db.interfaces.IUnit;

@RunWith(VertxUnitRunner.class)
public class UnitPostgrePoiTest {
    static JDBCClient client;
    static JsonObject config;
    static IUnit dbService;
    static Vertx vertx;
    static ConfigTestService service;
    static JsonObject poi;
    static JsonObject dbQueries;

//    @BeforeClass
//    public static void init() throws Exception {
//        service = new ConfigTestService();
//        vertx = Vertx.vertx();
//        config = service.getTestConfig("/conf/db-test-config.json");
//        dbQueries = service.getTestConfig("/conf/dbqueries.json");
//        client = JDBCClient.createShared(vertx, config.getJsonObject("db"), config.getString("datasource", "datasource"));
//        dbService = new UnitService(vertx, config, LIFEMAP_CONST.TABLE_NAME_POI, dbQueries);
//        poi = new JsonObject()
//                .put("name", "Ivan") // "name", text, -- Наименование
//                .put("polygon_type", 12) // "polygon_type", bigint,
//                .put("polygon_wkt", "dsad") // "polygon_wkt", text, -- Строка построение WKT геометрии
//                .put("polygon_radius", 12.0) // "polygon_radius", double precision, -- Радиус если круг или линия
//                .put("polygon_color", "dsadsa") // "polygon_color", text, -- Цвет геометрии
//                .put("text_color", "red") // "text_color", text, -- Цвет текста
//                .put("pin_id", 5) // "pin_id", bigint, -- номер пина POI
//                .put("zone_poi_group_id", 9) // "zone_poi_group_id", bigint, -- Группа POI
//                .put("description", "test description"); // "zone_poi_group_id", bigint, -- Группа POI
//    }
//
//    @Test
//    public void getList(TestContext context) {
//        service.getList(context, poi, dbService);
//    }
//
//    @Test
//    public void getListByPerPage(TestContext context) {
//        service.getListByPerPage(context, dbService);
//    }
//
//    @Test
//    public void getCount(TestContext context) {
//        service.getCount(context, poi, dbService);
//    }
//
//    @Test
//    public void getPageCountByPerPage(TestContext context) {
//        service.getPageCountByPerPage(context, dbService);
//    }
//
//    @Test
//    public void getById(TestContext context) {
//        service.getById(context, dbService, poi, "name", 0);
//    }
//
//    @Test
//    public void save(TestContext context) {
//        service.save(context, dbService, poi, "name", 0);
//    }
//
//    @Test
//    public void update(TestContext context) {
//        JsonObject params = new JsonObject()
//                .put("name", "John") // name text, -- Наименование
//                .put("polygon_type", 12) // polygon_type bigint,
//                .put("polygon_wkt", "dsad") // polygon_wkt text, -- Строка построение WKT геометрии
//                .put("polygon_radius", 12.0) // polygon_radius double precision, -- Радиус если круг или линия
//                .put("polygon_color", "dsadsa") // polygon_color text, -- Цвет геометрии
//                .put("text_color", "red") // text_color text, -- Цвет текста
//                .put("pin_id", 5) // pin_id bigint, -- номер пина POI
//                .put("zone_poi_group_id", 9) // zone_poi_group_id bigint, -- Группа POI
//                .put("description", "test description"); // "zone_poi_group_id", bigint, -- Группа POI
//
//        service.update(context, dbService, poi, params);
//    }
//
//    @Test
//    public void delete(TestContext context) {
//        service.delete(context, poi, dbService);
//    }
}

