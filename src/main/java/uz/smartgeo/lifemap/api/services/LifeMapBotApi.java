package uz.smartgeo.lifemap.api.services;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.codec.BodyCodec;
import org.apache.log4j.Logger;

public class LifeMapBotApi {
    protected WebClient webClient;
    private static Logger LOGGER = Logger.getLogger(LifeMapBotApi.class);

    private static volatile LifeMapBotApi instance;

    protected int httpPort;
    protected String httpHost;

    public LifeMapBotApi(Vertx vertx, String host, Integer port) {
        WebClientOptions options = new WebClientOptions()
                .setUserAgent("LM-api/1.0.1");

        options
                .setKeepAlive(true)
                .setDefaultHost(host)
                .setDefaultPort(port);

        httpPort = port;
        httpHost = host;
        webClient = WebClient.create(vertx, options);
    }

    /**
     * Get Singleton instance
     *
     * @return instance of the class
     */
    public static LifeMapBotApi getInstance(Vertx vertx, String host, Integer port) {
        final LifeMapBotApi currentInstance;
        if (instance == null) {
            synchronized (LifeMapBotApi.class) {
                if (instance == null) {
                    try {
                        instance = new LifeMapBotApi(vertx, host, port);
                    } catch (Exception e) {
                        LOGGER.error("LifeMapBotApi" + e.getMessage());
                    }
                }
                currentInstance = instance;
            }
        } else {
            currentInstance = instance;
        }
        return currentInstance;
    }

    public Future<JsonObject> notifyTelegramBotAboutUserResponse(JsonObject params) {
        Promise<JsonObject> promise = Promise.promise();

        LOGGER.info(params);

//        JsonObject postParams = new JsonObject()
//                .put("userId", params.getString("userId"))
//                .put("authType", 1)
//                .put("mobileNumber", mobileNumber);

        try {
            webClient
                    .post("/notify/eventResponsedFromBirdamlik/")
                    .as(BodyCodec.jsonObject())
                    .sendJsonObject(params, ar -> {
                        if (ar.succeeded()) {
                            LOGGER.info(ar.result());
                            LOGGER.info(ar.result().body());
                            LOGGER.info(ar.result().bodyAsJsonObject());

                            if (ar.result() != null ) {
                                promise.complete(ar.result().body());
                            } else {
                                promise.fail("updateUserMobileNumber FAILED");
                            }
                        } else {
                            LOGGER.info(ar);
                            promise.fail("DATA FAILED");
                            System.out.println("Something went wrong " + ar.cause().getMessage());
                            LOGGER.error("Error in notifyTelegramBotAboutUserResponse. " + ar.cause().toString());
                        }
                    });
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return promise.future();
    }

}
