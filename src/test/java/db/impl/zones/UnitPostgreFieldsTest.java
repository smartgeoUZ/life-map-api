package db.impl.zones;

import db.impl.ConfigTestService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.runner.RunWith;
import uz.smartgeo.lifemap.api.db.interfaces.IUnit;

@RunWith(VertxUnitRunner.class)
public class UnitPostgreFieldsTest {
    static JDBCClient client;
    static JsonObject config;
    static IUnit dbService;
    static Vertx vertx;
    static ConfigTestService service;
    static JsonObject fields;
    static JsonObject dbQueries;

//    @BeforeClass
//    public static void init() throws Exception {
//        service = new ConfigTestService();
//        vertx = Vertx.vertx();
//        config = service.getTestConfig("/conf/db-test-config.json");
//        dbQueries = service.getTestConfig("/conf/dbqueries.json");
//        client = JDBCClient.createShared(vertx, config.getJsonObject("db"), config.getString("datasource", "datasource"));
//        dbService = new UnitService(vertx, config, LIFEMAP_CONST.TABLE_NAME_FIELDS, dbQueries);
//        fields = new JsonObject()
//                .put("name", "Ivan") // name text, -- Наименование
//                .put("polygon_wkt", "djaskhda") // polygon_wkt text, -- Строка построение WKT геометрии
//                .put("polygon_color", "dasda") // polygon_color text, -- Цвет геометрии
//                .put("text_color", "blue") // text_color text, -- Цвет текста
//                .put("area", 12.0) // area double precision, -- Площадь
//                .put("territory", "dsda") // territory text, -- Территория (ручной ввод)
//                .put("addr_rayon_id", 12) // addr_rayon_id bigint, -- Район
//                .put("addr_viloyat_id", 7) // addr_viloyat_id bigint, -- Область
//                .put("date_registered", 121213) // date_registered bigint, -- Дата регистрации
//                .put("ekip", "dsada") // ekip text, -- ЕКИП - Единый кадастровый идентификатор поля
//                .put("fields_group_id", 5) // fields_group_id bigint, -- Группа
//                .put("farmer_id", 1) // farmer_id bigint, -- Фермер
//                .put("farmer_company_id", 2) // farmer_company_id bigint, -- Фермерское хозяйство
//                .put("agro_culture_id", 3) // agro_culture_id bigint, -- Сельхоз культура
//                .put("agro_status_id", 6) // agro_status_id bigint, -- Состояние поля по Агро
//                .put("prediction_harvest_cpg", 5.0) // prediction_harvest_cpg double precision, -- Прогноз сбора (ц/га)
//                .put("prediction_harvest_c", 6.0) // prediction_harvest_c double precision, -- Прогноз сбора с поля (ц)
//                .put("field_status_id", 5) // field_status_id bigint, -- Статус поля (Активный,Блокированный)
//                .put("user_created_id", 7) // user_created_id bigint, -- Создан (Ф.И.О пользователя)
//                .put("user_measurer_id", 8); // user_measurer_id bigint, -- Замерщик (Ф.И.О Замерщика)
//    }
//
//    @Test
//    public void getList(TestContext context) {
//        service.getList(context, fields, dbService);
//    }
//
//    @Test
//    public void getListByPerPage(TestContext context) {
//        service.getListByPerPage(context, dbService);
//    }
//
//    @Test
//    public void getCount(TestContext context) {
//        service.getCount(context, fields, dbService);
//    }
//
//    @Test
//    public void getPageCountByPerPage(TestContext context) {
//        service.getPageCountByPerPage(context, dbService);
//    }
//
//    @Test
//    public void getById(TestContext context) {
//        service.getById(context, dbService, fields, "name", 0);
//    }
//
//    @Test
//    public void save(TestContext context) {
//        service.save(context, dbService, fields, "name", 0);
//    }
//
//    @Test
//    public void update(TestContext context) {
//        JsonObject params = new JsonObject()
//                .put("name", "Not Ivan")    // name                     text, -- Наименование
//                .put("polygon_wkt", "djaskhda")    // polygon_wkt              text, -- Строка построение WKT геометрии
//                .put("polygon_color", "dasda")       // polygon_color            text, -- Цвет геометрии
//                .put("text_color", "blue")        // text_color               text, -- Цвет текста
//                .put("area", 12.0)          // area                     double precision, -- Площадь
//                .put("territory", "dsda")        // territory                text, -- Территория (ручной ввод)
//                .put("addr_rayon_id", 12)            // addr_rayon_id            bigint, -- Район
//                .put("addr_viloyat_id", 7)             // addr_viloyat_id          bigint, -- Область
//                .put("date_registered", 121213)        // date_registered          bigint, -- Дата регистрации
//                .put("ekip", "dsada")       // ekip                     text, -- ЕКИП - Единый кадастровый идентификатор поля
//                .put("fields_group_id", 5)             // fields_group_id          bigint, -- Группа
//                .put("farmer_id", 1)             // farmer_id                bigint, -- Фермер
//                .put("farmer_company_id", 2)             // farmer_company_id        bigint, -- Фермерское хозяйство
//                .put("agro_culture_id", 3)             // agro_culture_id          bigint, -- Сельхоз культура
//                .put("agro_status_id", 6)             // agro_status_id           bigint, -- Состояние поля по Агро
//                .put("prediction_harvest_cpg", 5.0)           // prediction_harvest_cpg   double precision, -- Прогноз сбора (ц/га)
//                .put("prediction_harvest_c", 6.0)           // prediction_harvest_c     double precision, -- Прогноз сбора с поля (ц)
//                .put("field_status_id", 5)             // field_status_id          bigint, -- Статус поля (Активный,Блокированный)
//                .put("user_created_id", 7)             // user_created_id          bigint, -- Создан (Ф.И.О пользователя)
//                .put("user_measurer_id", 8);            // user_measurer_id         bigint, -- Замерщик (Ф.И.О Замерщика)
//
//        service.update(context, dbService, fields, params);
//    }
//
//    @Test
//    public void delete(TestContext context) {
//        service.delete(context, fields, dbService);
//    }
}
