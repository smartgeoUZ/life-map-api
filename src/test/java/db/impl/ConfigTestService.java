package db.impl;
//
//import io.vertx.core.CompositeFuture;
//import io.vertx.core.json.JsonObject;
//import io.vertx.ext.unit.Async;
//import io.vertx.ext.unit.TestContext;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.Arrays;
//import java.util.Set;

public class ConfigTestService {

   /* public JsonObject getTestConfig(String filename) throws IOException, ParseException {
        JsonObject config = new JsonObject();
        InputStream queriesInputStream = getClass().getResourceAsStream(filename);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser
                .parse(new InputStreamReader(queriesInputStream, "UTF-8"));

        Set<String> keySet = jsonObject.keySet();
        keySet.stream().forEach(key -> config.put(key, jsonObject.get(key)));

        return config;
    }

    public void getCount(TestContext context, JsonObject formData, IUnit dbService) {
        Async async = context.async();

        CompositeFuture.all(Arrays.asList(dbService.save(formData))).setHandler(r -> {
            if (r.succeeded()  && r.result().size() > 0) {

                dbService
                        .getCount()
                        .setHandler(res -> {
                            if (res.succeeded())
                                context.assertNotNull(res);
                            else
                                context.fail("Failed method - getCount() : " + res.cause().getMessage());
                            async.complete();
                        });
            } else
                context.fail("Failed method - save() : " + r.cause().getMessage());
        });
    }

    public void getList(TestContext context, JsonObject formData, IUnit dbService) {
        Async async = context.async();

        CompositeFuture.all(Arrays.asList(dbService.save(formData))).setHandler(ar -> {
            if (ar.succeeded()) {
                dbService
                        .getList()
                        .setHandler(res -> {
                            if (res.succeeded() && !res.result().isEmpty())
                                context.assertNotNull(res.result().get(0).toString());
                            else
                                context.fail("Failed method - getList() : " + res.cause().getMessage());
                            async.complete();
                        });
            } else
                context.fail("Failed method - save() : " + ar.cause().getMessage());
        });
    }

    public void getListByPerPage(TestContext context, IUnit dbService) {
        Async async = context.async();
        long page = 1;
        long per_page = 5;

        dbService
                .getListByPerPage(page, per_page)
                .setHandler(res -> {
                    if (res.succeeded())
                        context.assertNotNull(res);
                    else
                        context.fail("Failed method - getListByPerPage() : " + res.cause().getMessage());
                    async.complete();
                });
    }

    public void getPageCountByPerPage(TestContext context, IUnit dbService) {
        Async async = context.async();
        long per_page = 5;

        dbService
                .getPageCount(per_page)
                .setHandler(res -> {
                    if (res.succeeded())
                        context.assertNotNull(res.result());
                    else
                        context.fail("Failed method - getPageCountByPerPage() : " + res.cause().getMessage());
                    async.complete();
                });
    }

    public void delete(TestContext context, JsonObject formData, IUnit dbService) {
        Async async = context.async();

        CompositeFuture.all(Arrays.asList(dbService.save(formData))).setHandler(id -> {
            if (id.succeeded() && !id.result().resultAt(0).toString().contains("null")) {
                JsonObject groupId = id.result().resultAt(0);
                dbService
                        .delete(groupId)
                        .setHandler(res -> {
                            if (res.succeeded())
                                context.assertTrue(true);
                            else
                                context.fail(res.cause());
                            async.complete();
                        });
            } else
                context.fail(id.cause());
        });
    }

    public void update(TestContext context, IUnit dbService, JsonObject formData, JsonObject params) {
        Async async = context.async();

        CompositeFuture.all(Arrays.asList(dbService.save(formData))).setHandler(id -> {
            if (id.succeeded()) {
                params.put("id", ((JsonObject) id.result().resultAt(0)).getInteger("id"));

                dbService
                        .update(params)
                        .setHandler(res -> {
                            if (res.succeeded())
                                context.assertTrue(res.succeeded());
                            else
                                context.fail(res.cause());
                            async.complete();
                        });
            } else
                context.fail("Failed method - save() : " + id.cause().getMessage());
        });
    }

    public void getById(TestContext context, IUnit dbService, JsonObject formData, String searchField, int position) {
        Async async = context.async();

        CompositeFuture.all(Arrays.asList(dbService.save(formData))).setHandler(id -> {
            if (id.succeeded() && id.result().size() > 0) {
                JsonObject groupId = id.result().resultAt(0);
                dbService
                        .getById(groupId.getInteger("id"))
                        .setHandler(res -> {
                            if (res.succeeded())
                                context.assertEquals(formData.getString(searchField), res.result().getString(searchField));
                            else
                                context.fail("Failed method - getById() : " + res.cause().getMessage());
                            async.complete();
                        });
            } else
                context.fail("Failed method - save() : " + id.cause().getMessage());
        });
    }

    public void save(TestContext context, IUnit dbService, JsonObject formData, String searchField, int position) {
        Async async = context.async();

        dbService.save(formData).setHandler(res -> {
            if (res.succeeded() && res.result().size() > 0) {
                dbService
                        .getById(res.result().getInteger("id"))
                        .setHandler(r -> {
                            if (r.succeeded())
                                context.assertEquals(formData.getString(searchField), r.result().getString(searchField));
                            else
                                context.fail("Failed method - getById() : " + r.cause().getMessage());
                            async.complete();
                        });
            } else
                context.fail("Failed method - save() : " + res.cause().getMessage());
        });
    }*/
}
