package uz.smartgeo.lifemap.api.routers.exportedEvent;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;
import uz.smartgeo.lifemap.api.routers.UnitRouter;
import uz.smartgeo.lifemap.api.services.LifeMapBotApi;
import uz.smartgeo.lifemap.api.utils.LIFEMAP_CONST;
import uz.smartgeo.lifemap.api.utils.ResponseUtil;

public class ExportedEventRouter extends UnitRouter {

    private static final Logger LOGGER = Logger.getLogger(ExportedEventRouter.class.getName());

    private static final Long BIRDAMLIK_USER_ID = 2L;

    String botApiHost;
    Integer botApiPort;
    Vertx vertx;

    public ExportedEventRouter(Vertx vertx, JsonObject config, JsonObject dbQueries) {
        super(vertx, config, LIFEMAP_CONST.TABLE_NAME_EXPORTED_EVENT, dbQueries);

        this.vertx = vertx;
        setRoutes();


        botApiHost = config.getString("bot.api.host");
        botApiPort = config.getInteger("bot.api.port");

        lifeMapBotApi(vertx, botApiHost, botApiPort);

    }

    private LifeMapBotApi lifeMapBotApi(Vertx vertx, String host, Integer port) {
        return LifeMapBotApi.getInstance(vertx, host, port);
    }


    protected void setRoutes() {
        router.post("/update").handler(this::update);
    }

    public void update(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();

        long eventId = 0L;
        long birdamlikPostId = 0L;

        String volunteerFullName = "Birdamlik Volunteer";
        String volunteerPhoneNumber = "99890xxxxxxx";

        if (formData != null) {
            if ((formData.containsKey("post_id") && formData.getLong("post_id") != null) &&
                    formData.containsKey("full_name") && formData.getString("full_name") != null &&
                    formData.containsKey("phone_number") && formData.getString("phone_number") != null) {

                if (formData.containsKey("post_id") && formData.getLong("post_id") != null) {
                    birdamlikPostId = formData.getLong("post_id");
                }

                if (formData.containsKey("full_name") && formData.getString("full_name") != null) {
                    volunteerFullName = formData.getString("full_name");
                }

                if (formData.containsKey("phone_number") && formData.getString("phone_number") != null) {
                    volunteerPhoneNumber = formData.getString("phone_number");
                }

                unitService.updateExportedEvent(birdamlikPostId, BIRDAMLIK_USER_ID, volunteerFullName, volunteerPhoneNumber)
                        .onComplete(updateExportedEventHandler(context, birdamlikPostId));

            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }


    protected Handler<AsyncResult<JsonObject>> updateExportedEventHandler(RoutingContext context, Long birdamlikPostId) {
        return updateExportedEventRes -> {

            if (updateExportedEventRes.succeeded()) {
                ResponseUtil.respondWithCreated(context, updateExportedEventRes.result());

                long eventId = updateExportedEventRes.result().getLong("id", 0L);

                unitService.getById(eventId).onComplete(eventRes -> {

                    if (eventRes.succeeded()) {

                        Long eventUserId = eventRes.result().getLong("user_id");
                        Integer categoryId = eventRes.result().getInteger("category_id");
                        String eventName = eventRes.result().getString("name");
                        String eventDescription = eventRes.result().getString("description");
                        String eventStartDate = eventRes.result().getString("start_date");
                        String eventEndDate = eventRes.result().getString("end_date");
                        String eventRegDate = eventRes.result().getString("reg_date");
                        Long extUserId = eventRes.result().getLong("user_ext_user_id");

                        String responsedUserFirstName = eventRes.result().getString("responded_first_name");
                        String responsedUserPhoneMobile = eventRes.result().getString("responded_phone_number");
                        String respondedDate = eventRes.result().getString("responded_date");
                        String address = new JsonObject(eventRes.result().getString("address")).getString("display_name");

                        unitService.saveEventResponse(eventId, BIRDAMLIK_USER_ID, eventName, eventDescription, eventStartDate)
                                .onComplete(saveEventResponseRes -> {
                                    if (saveEventResponseRes.succeeded()) {

                                        JsonObject postParams = new JsonObject()
                                                .put("eventId", eventId)
                                                .put("birdamlikPostId", birdamlikPostId)
                                                .put("userId", eventUserId)
                                                .put("userExtId", extUserId)
                                                .put("categoryId", categoryId)
                                                .put("name", eventName)
                                                .put("message", eventDescription)
                                                .put("startDate", eventStartDate)
                                                .put("endDate", eventEndDate)
                                                .put("regDate", eventRegDate)
                                                .put("responsedUserFirstName", responsedUserFirstName)
                                                .put("responsedUserPhoneMobile", responsedUserPhoneMobile)
                                                .put("respondedDate", respondedDate)
                                                .put("address", address)
                                                .put("responsedUserId", BIRDAMLIK_USER_ID);

                                        lifeMapBotApi(vertx, botApiHost, botApiPort).notifyTelegramBotAboutUserResponse(postParams).onComplete(notifyUserRes -> {
                                            if (notifyUserRes.succeeded()) {
                                                LOGGER.info(notifyUserRes.result());
                                            } else {
                                                LOGGER.error("notifyTelegramBotAboutUserResponse failed -> " + notifyUserRes.cause().getMessage());
                                            }
                                        });

                                    } else {
                                        if (saveEventResponseRes.cause().getMessage() != null) {
                                            ResponseUtil.respondError(context, saveEventResponseRes.cause().getMessage());
                                            LOGGER.error("saveEventResponse failed -> " + saveEventResponseRes.cause().getMessage());
                                        } else {
                                            ResponseUtil.respondError(context, saveEventResponseRes.cause().toString());
                                            LOGGER.error("saveEventResponse failed -> " + saveEventResponseRes.cause());
                                        }
                                    }
                                });
                    } else {
                        if (eventRes.cause().getMessage() != null) {
                            ResponseUtil.respondError(context, eventRes.cause().getMessage());
                            LOGGER.error("getById failed -> " + eventRes.cause().getMessage());
                        } else {
                            ResponseUtil.respondError(context, eventRes.cause().toString());
                            LOGGER.error("getById failed -> " + eventRes.cause());
                        }
                    }
                });
            } else {
                if (updateExportedEventRes.cause().getMessage() != null) {
                    ResponseUtil.respondError(context, updateExportedEventRes.cause().getMessage());
                    LOGGER.error("updateExportedEvent failed -> " + updateExportedEventRes.cause().getMessage());
                } else {
                    ResponseUtil.respondError(context, updateExportedEventRes.cause().toString());
                    LOGGER.error("updateExportedEvent failed -> " + updateExportedEventRes.cause());
                }
            }
        };
    }

}
