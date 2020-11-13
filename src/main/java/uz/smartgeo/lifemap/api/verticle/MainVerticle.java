package uz.smartgeo.lifemap.api.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.apache.log4j.Logger;
import uz.smartgeo.lifemap.api.routers.MainRouter;
import uz.smartgeo.lifemap.api.utils.ConfigService;

public class MainVerticle extends AbstractVerticle {

    Router router;
    HttpServer server;
    JsonObject config = new JsonObject();
    JsonObject dbQueries = new JsonObject();
    private static final Logger LOGGER = Logger.getLogger(MainVerticle.class);

    @Override
    public void start() throws Exception {
        LOGGER.info("Starting MainVerticle ...");
        Future<JsonObject> configFromFile = Future.future();

        ConfigService.getConfig(vertx, "config", configFromFile);

        configFromFile
                .compose(v -> {
                    config = v;

                    Future<JsonObject> futureDbQueries = Future.future();
                    ConfigService.getConfig(vertx, "dbqueries", futureDbQueries);

                    return futureDbQueries;
                })
                .compose(resDbQueries -> {
                    dbQueries = resDbQueries;
                    Future<JsonObject> futureStartServer = Future.future();
                    router = MainRouter.createRouting(vertx, config, dbQueries);

                    server = vertx.createHttpServer();

                    server
                            .requestHandler(router::accept)
                            .listen(config.getInteger("server.port"),
                                    config.getString("server.host"), r -> {
                                        if (r.succeeded()) {
                                            LOGGER.info("Http server started on port :" + server.actualPort());
                                        } else {
                                            LOGGER.error("Http server start failed -> " + r.cause().getMessage());
                                        }
                                    });
                    return futureStartServer;
                });
    }

    @Override
    public void stop() {
        LOGGER.info("Shutting down MainVerticle...");
        server.close();
    }
}
