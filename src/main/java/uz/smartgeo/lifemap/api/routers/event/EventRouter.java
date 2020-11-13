package uz.smartgeo.lifemap.api.routers.event;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;
import uz.smartgeo.lifemap.api.routers.UnitRouter;
import uz.smartgeo.lifemap.api.utils.LIFEMAP_CONST;
import uz.smartgeo.lifemap.api.utils.ResponseUtil;

public class EventRouter extends UnitRouter {
    private static final Logger LOGGER = Logger.getLogger(EventRouter.class.getName());

    public EventRouter(Vertx vertx, JsonObject dbConfig, JsonObject dbQueries) {
        super(vertx, dbConfig, LIFEMAP_CONST.TABLE_NAME_EVENT, dbQueries);

        setRoutes();
    }

    protected void setRoutes() {


//        router.post("/protected/getEventsByFilter").handler(this::getEventsByFilter);
//        router.post("/getEventsByFilter").handler(this::getEventsByFilter);
//
//        router.post("/protected/getEventsCountByFilter").handler(this::getEventsCountByFilter);
//        router.post("/getEventsCountByFilter").handler(this::getEventsCountByFilter);

        router.post("/protected/getEvents").handler(this::getEvents);
        router.post("/getEvents").handler(this::getEventsForAnonymous);

        router.post("/protected/deleteEvent").handler(this::deleteEvent);
        router.post("/updateModerationStatus").handler(this::updateModerationStatus);

        router.post("/getRegionByCoordinates").handler(this::getRegionByCoordinates);
        router.post("/protected/getRegionByCoordinates").handler(this::getRegionByCoordinates);

        router.post("/protected/getDashboardDataEvents").handler(this::getDashboardDataEvents);
        router.post("/getDashboardDataEvents").handler(this::getDashboardDataEvents);

        router.post("/protected/getDashboardDataUsers").handler(this::getDashboardDataUsers);
        router.post("/getDashboardDataUsers").handler(this::getDashboardDataUsers);

        router.post("/protected/getDashboardDataEventsByRegionFilter").handler(this::getDashboardDataEventsByRegionFilter);
        router.post("/getDashboardDataEventsByRegionFilter").handler(this::getDashboardDataEventsByRegionFilter);

        router.post("/protected/getDashboardDataEventsByCategoryRegion").handler(this::getDashboardDataEventsByCategoryRegion);
        router.post("/getDashboardDataEventsByCategoryRegion").handler(this::getDashboardDataEventsByCategoryRegion);

        router.post("/protected/getDashboardDataEventsByCategoryFilter").handler(this::getDashboardDataEventsByCategoryFilter);
        router.post("/getDashboardDataEventsByCategoryFilter").handler(this::getDashboardDataEventsByCategoryFilter);

        router.post("/protected/saveEventAction").handler(this::saveEventAction);

    }

    public void getEvents(RoutingContext context) {
        try {
            JsonObject formData = context.getBodyAsJson();
            long userId;
            long categoryId;
            int roleId;
            int filterStatusId;

            String startDate;
            String endDate;

            Long userRegionId;

            String searchText;

            if (formData != null) {
                if (formData.containsKey("user_id") && formData.getLong("user_id", 0L) != null &&
                        formData.containsKey("role_id") && formData.getInteger("role_id") != null &&
                        formData.containsKey("category_id") && formData.getLong("category_id", 0L) != null &&
                        formData.containsKey("filter_status_id") && formData.getInteger("filter_status_id", 1) != null &&
                        formData.containsKey("start_date") &&
                        formData.containsKey("end_date") &&
                        formData.containsKey("user_region_id")
                ) {
                    userId = formData.getLong("user_id");
                    categoryId = formData.getLong("category_id");
                    roleId = formData.getInteger("role_id");

                    filterStatusId = formData.getInteger("filter_status_id", 1);
                    startDate = formData.getString("start_date", null);
                    endDate = formData.getString("end_date", null);

                    userRegionId = formData.getLong("user_region_id", 1L);

                    switch (filterStatusId) {
                        case LIFEMAP_CONST.FILTER_STATUS_ACTUAL:
                            unitService.getRelevantEvents(userId, categoryId, roleId, startDate, endDate).onComplete(unitHandlerList(context));
                            break;
                        case LIFEMAP_CONST.FILTER_STATUS_ALL:
                            unitService.getAllEvents(userId, categoryId, roleId, startDate, endDate).onComplete(unitHandlerList(context));
                            break;
                        case LIFEMAP_CONST.FILTER_STATUS_OWN:
                            unitService.getOwnEvents(userId, categoryId, roleId, startDate, endDate).onComplete(unitHandlerList(context));
                            break;
                        case LIFEMAP_CONST.FILTER_STATUS_MODERATED:
                            unitService.getModeratingEvents(userId, categoryId, roleId, startDate, endDate, userRegionId).onComplete(unitHandlerList(context));
                            break;
                    }

                } else if (formData.containsKey("user_id") && formData.getLong("user_id", 0L) != null &&
                        formData.containsKey("search_text") && formData.getString("search_text") != null) {

                    userId = formData.getLong("user_id");
                    searchText = formData.getString("search_text");
                    searchText = '%' + searchText + '%';
                    unitService.getEventsBySearchText(userId, searchText, false).onComplete(unitHandlerList(context));
                } else {
                    ResponseUtil.respondError(context, "Not found some post data");
                }
            } else {
                ResponseUtil.respondError(context, "No data for fields");
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public void getEventsForAnonymous(RoutingContext context) {
        try {
            JsonObject formData = context.getBodyAsJson();
            long categoryId;
            Boolean isActive;
            int filterStatusId;
            String startDate;
            String endDate;

            String searchText;

            if (formData != null) {
                if (formData.containsKey("category_id") && formData.getLong("category_id", 0L) != null &&
                        formData.containsKey("filter_status_id") && formData.getInteger("filter_status_id", 0) != null &&
                        formData.containsKey("start_date") &&
                        formData.containsKey("end_date")) {

                    categoryId = formData.getLong("category_id");
                    filterStatusId = formData.getInteger("filter_status_id");

                    isActive = formData.getBoolean("is_active");
                    if (isActive == null) {
                        isActive = false;
                    }

                    startDate = formData.getString("start_date", null);
                    endDate = formData.getString("end_date", null);

                    unitService.getEventsForAnonymous(categoryId, isActive, filterStatusId, startDate, endDate).onComplete(unitHandlerList(context));
                } else if (formData.containsKey("user_id") && formData.getLong("user_id", 0L) != null &&
                        formData.containsKey("search_text") && formData.getString("search_text") != null) {

                    searchText = formData.getString("search_text");
                    searchText = '%' + searchText + '%';

                    unitService.getEventsBySearchText(0L, searchText, true).onComplete(unitHandlerList(context));
                } else {
                    ResponseUtil.respondError(context, "Not found some post data");
                }

            } else {
                ResponseUtil.respondError(context, "No data for fields");
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public void deleteEvent(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        long eventId;
        long performDeletionUserId;

        if (formData != null) {
            if (formData.containsKey("eventId") && formData.getLong("eventId", null) != null &&
                    formData.containsKey("performDeletionUserId") && formData.getLong("performDeletionUserId", null) != null) {

                eventId = formData.getLong("eventId");
                performDeletionUserId = formData.getLong("performDeletionUserId");

                unitService.deleteEvent(eventId, performDeletionUserId).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

    /**
     * Return region id by coordinates
     *
     * @param context Routeing Context
     */
    public void getRegionByCoordinates(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();

        Double lon;
        Double lat;

        if (formData != null) {
            if (formData.containsKey("lon") && formData.getDouble("lon", null) != null &&
                    formData.containsKey("lat") && formData.getDouble("lat", null) != null) {

                lon = formData.getDouble("lon");
                lat = formData.getDouble("lat");

                unitService.getRegionByCoordinates(lon, lat).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

    public void getDashboardDataEvents(RoutingContext context) {
        try {

            JsonObject formData = context.getBodyAsJson();

            long categoryId;
            long regionId;
            long userId;

            String startDate;
            String endDate;

            if (formData != null) {
                if (formData.containsKey("category_id") && formData.getLong("category_id", 0L) != null &&
                        formData.containsKey("region_id") && formData.getLong("region_id", 1L) != null &&
                        formData.containsKey("user_id") && formData.getLong("user_id", 0L) != null &&
                        formData.containsKey("start_date") &&
                        formData.containsKey("end_date")) {

                    categoryId = formData.getLong("category_id");
                    regionId = formData.getLong("region_id");
                    userId = formData.getLong("user_id");

                    startDate = formData.getString("start_date", null);
                    endDate = formData.getString("end_date", null);

                    unitService.getDashboardDataEvents(categoryId, regionId, userId, startDate, endDate).onComplete(unitHandlerList(context));
                } else {
                    ResponseUtil.respondError(context, "Not found some post data");
                }

            } else {
                ResponseUtil.respondError(context, "No data for fields");
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public void getDashboardDataUsers(RoutingContext context) {
        try {
//            JsonObject formData = context.getBodyAsJson();
//            if (formData != null) {
            unitService.getDashboardDataUsers().onComplete(unitHandlerList(context));
//            } else {
//                ResponseUtil.respondError(context, "No data for fields");
//            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public void getDashboardDataEventsByRegionFilter(RoutingContext context) {
        try {
            JsonObject formData = context.getBodyAsJson();

            String startDate;
            String endDate;

            if (formData != null) {
                if (formData.containsKey("start_date") && formData.getString("start_date", "1970-01-01") != null &&
                        formData.containsKey("end_date") && formData.getString("end_date", "1970-01-01") != null) {

                    startDate = formData.getString("start_date", null);
                    endDate = formData.getString("end_date", null);

                    unitService.getDashboardDataEventsByRegionFilter(startDate, endDate).onComplete(unitHandlerList(context));
                } else {
                    ResponseUtil.respondError(context, "Not found some post data");
                }

            } else {
                ResponseUtil.respondError(context, "No data for fields");
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public void getDashboardDataEventsByCategoryRegion(RoutingContext context) {
        try {
            JsonObject formData = context.getBodyAsJson();

            String startDate;
            String endDate;

            if (formData != null) {
                if (formData.containsKey("start_date") && formData.getString("start_date", "1970-01-01") != null &&
                        formData.containsKey("end_date") && formData.getString("end_date", "1970-01-01") != null) {

                    startDate = formData.getString("start_date", null);
                    endDate = formData.getString("end_date", null);

                    unitService.getDashboardDataEventsByCategoryRegion(startDate, endDate).onComplete(unitHandlerList(context));
                } else {
                    ResponseUtil.respondError(context, "Not found some post data");
                }

            } else {
                ResponseUtil.respondError(context, "No data for fields");
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public void getDashboardDataEventsByCategoryFilter(RoutingContext context) {
        try {
            JsonObject formData = context.getBodyAsJson();

            String startDate;
            String endDate;

            if (formData != null) {
                if (formData.containsKey("start_date") && formData.getString("start_date", "1970-01-01") != null &&
                        formData.containsKey("end_date") && formData.getString("end_date", "1970-01-01") != null) {

                    startDate = formData.getString("start_date", null);
                    endDate = formData.getString("end_date", null);

                    unitService.getDashboardDataEventsByCategoryFilter(startDate, endDate).onComplete(unitHandlerList(context));
                } else {
                    ResponseUtil.respondError(context, "Not found some post data");
                }

            } else {
                ResponseUtil.respondError(context, "No data for fields");
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }


    public void saveEventAction(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        long eventId;
        long responsedUserId;
        long actionTypeId;

        if (formData != null) {
            if (formData.containsKey("eventId") && formData.getLong("eventId") != null &&
                    formData.containsKey("responsedUserId") && formData.getLong("responsedUserId") != null &&
                    formData.containsKey("actionTypeId") && formData.getLong("actionTypeId") != null) {

                eventId = formData.getLong("eventId");
                responsedUserId = formData.getLong("responsedUserId");
                actionTypeId = formData.getLong("actionTypeId");

                unitService.saveEventAction(eventId, responsedUserId, actionTypeId).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

    public void updateModerationStatus(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        long eventId;
        boolean isModerated;

        if (formData != null) {
            if (formData.containsKey("eventId") && formData.getLong("eventId", null) != null &&
                    formData.containsKey("isModerated") && formData.getBoolean("isModerated", null) != null) {

                eventId = formData.getLong("eventId");
                isModerated = formData.getBoolean("isModerated");

                unitService.updateModerationStatus(eventId, isModerated).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

}
