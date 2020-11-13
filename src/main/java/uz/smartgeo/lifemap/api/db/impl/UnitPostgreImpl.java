package uz.smartgeo.lifemap.api.db.impl;

import io.vertx.core.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.SQLOptions;
import org.apache.log4j.Logger;
import uz.smartgeo.lifemap.api.db.interfaces.IUnitPostgre;
import uz.smartgeo.lifemap.api.utils.ConfigService;
import uz.smartgeo.lifemap.api.utils.Converters;
import uz.smartgeo.lifemap.api.utils.LIFEMAP_CONST;

import java.util.ArrayList;
import java.util.List;

public class UnitPostgreImpl implements IUnitPostgre {

    private final Vertx vertx;
    private final JDBCClient postgreClient;

    private static final Logger LOGGER = Logger.getLogger(UnitPostgreImpl.class.getName());

    private String QUERY_GET_LIST;
    private String QUERY_GET_BY_ID;
    private String QUERY_GET_BY_EXT_ID;
    private String QUERY_GET_USERS_BY_ROLE_ID;
    private String QUERY_GET_USER_BY_EXT_ID_AND_UPDATE;
    private String QUERY_UPDATE_MOBILE_NUMBER;
    private String QUERY_UPDATE_USER_PROFILE;
    private String QUERY_UPDATE_USER_DATA;
    private String QUERY_UPDATE_USER_REGION;
    private String QUERY_UPDATE_USER_ROLE;

    private String QUERY_SAVE;

    private String QUERY_GET_ROLE_PERMISSION_BY_USER_ID;
    private String QUERY_UPDATE_ACCESS_VALUE;

    private String QUERY_GET_EVENTS_FOR_ANONYMOUS_RELEVANT;
    private String QUERY_GET_EVENTS_FOR_ANONYMOUS_ALL;

    private String QUERY_GET_RELEVANT_EVENTS;
    private String QUERY_GET_ALL_EVENTS;
    private String QUERY_GET_ALL_EVENTS_WITH_MODERATED;
    private String QUERY_GET_OWN_EVENTS;
    private String QUERY_GET_MODERATING_EVENTS;

    private String QUERY_GET_EVENTS_BY_TEXT_SEARCH;
    private String QUERY_GET_EVENTS_BY_TEXT_SEARCH_FOR_ANONYMOUS;

    private String QUERY_GET_ACTIVE_EVENT_CATEGORIES;
    private String QUERY_UPDATE_IS_ACTIVE;
    private String QUERY_UPDATE_DURATION_MIN;

//    private String QUERY_GET_RELEVANT_EVENTS_BY_FILTER;
//    private String QUERY_GET_RELEVANT_EVENTS_COUNT_BY_FILTER;

    private String QUERY_DELETE_EVENT;
    private String QUERY_UPDATE_EVENT_MODERATION_STATUS;
    private String QUERY_GET_REGION_BY_COORDINATES;

    private String QUERY_GET_DASHBOARD_DATA_EVENTS;
    private String QUERY_GET_DASHBOARD_DATA_USERS;

    private String QUERY_GET_DASHBOARD_DATA_EVENTS_BY_REGION_FILTER;
    private String QUERY_GET_DASHBOARD_DATA_EVENTS_BY_CATEGORY_REGION;
    private String QUERY_GET_DASHBOARD_DATA_EVENTS_BY_CATEGORY_FILTER;

    private String QUERY_SAVE_EVENT_ACTION;

    private String QUERY_BLOCK_USER;
    private String QUERY_SAVE_EVENT_RESPONSE;

    private String QUERY_UPDATE_EXPORTED_EVENT;

    private String QUERY_GET_LIST_BY_PER_PAGE;
    private String QUERY_GET_COUNT;

    private String QUERY_UPDATE;
    private String QUERY_DELETE;

    private String QUERY_GET_LIST_BY_FILTER;
    private String QUERY_GET_COUNT_BY_FILTER;

    private String QUERY_GET_LIST_BY_LANG;

    private String QUERY_SELECT_LIST_BY_GROUP_ID;
    private String QUERY_SELECT_LIST_BY_AUTO_ID;

    //region Dashboard func props

    //endregion
    private List<String> querySaveParams;
    private List<String> queryUpdateParams;
    private List<String> queryDeleteParams;

    private List<String> queryFilterParams;
    private List<String> queryCountFilterParams;

    public UnitPostgreImpl(Vertx vertx, JsonObject dbConfig, String tableName, JsonObject dbQueries) {
        this.vertx = vertx;
        this.postgreClient = JDBCClient.createShared(this.vertx, ConfigService.getPostgreConf(dbConfig.getJsonObject("db")), dbConfig.getString("datasource", "datasource"));

        if (dbQueries.getJsonObject(tableName) != null) {
            QUERY_GET_LIST = dbQueries.getJsonObject(tableName).getString("getList");
            QUERY_GET_BY_ID = dbQueries.getJsonObject(tableName).getString("getById");
            QUERY_SAVE = dbQueries.getJsonObject(tableName).getString("save");
            QUERY_UPDATE = dbQueries.getJsonObject(tableName).getString("update");
            QUERY_DELETE = dbQueries.getJsonObject(tableName).getString("delete");
            QUERY_GET_LIST_BY_FILTER = dbQueries.getJsonObject(tableName).getString("queryFilter");
            QUERY_GET_COUNT_BY_FILTER = dbQueries.getJsonObject(tableName).getString("queryCountFilter");
            QUERY_GET_BY_EXT_ID = dbQueries.getJsonObject(tableName).getString("getByExtId");
            QUERY_BLOCK_USER = dbQueries.getJsonObject(tableName).getString("blockUser");

            QUERY_SAVE_EVENT_RESPONSE = dbQueries.getJsonObject(tableName).getString("saveEventResponse");

            QUERY_UPDATE_EXPORTED_EVENT = dbQueries.getJsonObject(tableName).getString("update");

            QUERY_GET_USER_BY_EXT_ID_AND_UPDATE = dbQueries.getJsonObject(tableName).getString("getUserByExtIdAndUpdate");

            QUERY_GET_USERS_BY_ROLE_ID = dbQueries.getJsonObject(tableName).getString("getUsersByRoleId");

            QUERY_UPDATE_MOBILE_NUMBER = dbQueries.getJsonObject(tableName).getString("updateMobileNumber");
            QUERY_UPDATE_USER_PROFILE = dbQueries.getJsonObject(tableName).getString("updateProfile");
            QUERY_UPDATE_USER_DATA = dbQueries.getJsonObject(tableName).getString("updateUserData");

            QUERY_UPDATE_USER_REGION = dbQueries.getJsonObject(tableName).getString("updateUserRegion");

            QUERY_GET_ACTIVE_EVENT_CATEGORIES = dbQueries.getJsonObject(tableName).getString("getActiveEventCategories");

            QUERY_UPDATE_IS_ACTIVE = dbQueries.getJsonObject(tableName).getString("updateIsActive");
            QUERY_UPDATE_DURATION_MIN = dbQueries.getJsonObject(tableName).getString("updateDurationMin");

            QUERY_UPDATE_USER_ROLE = dbQueries.getJsonObject(tableName).getString("updateUserRole");

            QUERY_GET_ROLE_PERMISSION_BY_USER_ID = dbQueries.getJsonObject(tableName).getString("getRolePermissionsByUserId");
            QUERY_UPDATE_ACCESS_VALUE = dbQueries.getJsonObject(tableName).getString("updateAccessValue");

            QUERY_GET_EVENTS_FOR_ANONYMOUS_RELEVANT = dbQueries.getJsonObject(tableName).getString("getEventsForAnonymousRelevant");
            QUERY_GET_EVENTS_FOR_ANONYMOUS_ALL = dbQueries.getJsonObject(tableName).getString("getEventsForAnonymousAll");


            QUERY_GET_RELEVANT_EVENTS = dbQueries.getJsonObject(tableName).getString("getRelevantEvents");
            QUERY_GET_ALL_EVENTS = dbQueries.getJsonObject(tableName).getString("getAllEvents");
            QUERY_GET_ALL_EVENTS_WITH_MODERATED = dbQueries.getJsonObject(tableName).getString("getAllEventsWithModerated");
            QUERY_GET_OWN_EVENTS = dbQueries.getJsonObject(tableName).getString("getOwnEvents");
            QUERY_GET_MODERATING_EVENTS = dbQueries.getJsonObject(tableName).getString("getModeratingEvents");

            QUERY_GET_EVENTS_BY_TEXT_SEARCH = dbQueries.getJsonObject(tableName).getString("getEventsBySearchText");
            QUERY_GET_EVENTS_BY_TEXT_SEARCH_FOR_ANONYMOUS = dbQueries.getJsonObject(tableName).getString("getEventsBySearchTextForAnonymous");

//            QUERY_GET_RELEVANT_EVENTS_BY_FILTER = dbQueries.getJsonObject(tableName).getString("getRelevantEventsByFilter");
//            QUERY_GET_RELEVANT_EVENTS_COUNT_BY_FILTER = dbQueries.getJsonObject(tableName).getString("getRelevantEventsCountByFilter");

            QUERY_DELETE_EVENT = dbQueries.getJsonObject(tableName).getString("deleteEvent");
            QUERY_UPDATE_EVENT_MODERATION_STATUS = dbQueries.getJsonObject(tableName).getString("updateModerationStatus");

            QUERY_GET_REGION_BY_COORDINATES = dbQueries.getJsonObject(tableName).getString("getRegionByCoordinates");

            QUERY_GET_DASHBOARD_DATA_EVENTS = dbQueries.getJsonObject(tableName).getString("getDashboardDataEvents");

            QUERY_GET_DASHBOARD_DATA_USERS = dbQueries.getJsonObject(tableName).getString("getDashboardDataUsers");

            QUERY_GET_DASHBOARD_DATA_EVENTS_BY_REGION_FILTER = dbQueries.getJsonObject(tableName).getString("getDashboardDataEventsByRegionFilter");
            QUERY_GET_DASHBOARD_DATA_EVENTS_BY_CATEGORY_REGION = dbQueries.getJsonObject(tableName).getString("getDashboardDataEventsByCategoryRegion");
            QUERY_GET_DASHBOARD_DATA_EVENTS_BY_CATEGORY_FILTER = dbQueries.getJsonObject(tableName).getString("getDashboardDataEventsByCategoryFilter");

            QUERY_SAVE_EVENT_ACTION = dbQueries.getJsonObject(tableName).getString("saveEventAction");

            QUERY_GET_LIST_BY_LANG = dbQueries.getJsonObject(tableName).getString("getListByLang");

            QUERY_GET_LIST_BY_PER_PAGE = dbQueries.getJsonObject(tableName).getString("getListByPerPage");
            QUERY_GET_COUNT = dbQueries.getJsonObject(tableName).getString("getCount");

            QUERY_SELECT_LIST_BY_AUTO_ID = dbQueries.getJsonObject(tableName).getString("getListByAutoId");
            QUERY_SELECT_LIST_BY_GROUP_ID = dbQueries.getJsonObject(tableName).getString("getListByGroupId");

            if (QUERY_SAVE != null) {
                querySaveParams = new ArrayList<>();
                Converters.readQueryNamedParams(QUERY_SAVE, querySaveParams);
                QUERY_SAVE = Converters.replaceParams(QUERY_SAVE);
            }

            if (QUERY_UPDATE != null) {
                queryUpdateParams = new ArrayList<>();
                Converters.readQueryNamedParams(QUERY_UPDATE, queryUpdateParams);
                QUERY_UPDATE = Converters.replaceParams(QUERY_UPDATE);
            }

            if (QUERY_DELETE != null) {
                queryDeleteParams = new ArrayList<>();
                Converters.readQueryNamedParams(QUERY_DELETE, queryDeleteParams);
                QUERY_DELETE = Converters.replaceParams(QUERY_DELETE);
            }

            if (QUERY_GET_LIST_BY_FILTER != null) {
                queryFilterParams = new ArrayList<>();
                Converters.readQueryNamedParams(QUERY_GET_LIST_BY_FILTER, queryFilterParams);
                QUERY_GET_LIST_BY_FILTER = Converters.replaceParams(QUERY_GET_LIST_BY_FILTER);
            }

            if (QUERY_GET_COUNT_BY_FILTER != null) {
                queryCountFilterParams = new ArrayList<>();
                Converters.readQueryNamedParams(QUERY_GET_COUNT_BY_FILTER, queryCountFilterParams);
                QUERY_GET_COUNT_BY_FILTER = Converters.replaceParams(QUERY_GET_COUNT_BY_FILTER);
            }

//
//            if (QUERY_GET_RELEVANT_EVENTS_BY_FILTER != null) {
//                queryRelevantEventsFilterParams = new ArrayList<>();
//                Converters.readQueryNamedParams(QUERY_GET_RELEVANT_EVENTS_BY_FILTER, queryRelevantEventsFilterParams);
//                QUERY_GET_RELEVANT_EVENTS_BY_FILTER = Converters.replaceParams(QUERY_GET_RELEVANT_EVENTS_BY_FILTER);
//            }
//
//            if (QUERY_GET_RELEVANT_EVENTS_COUNT_BY_FILTER != null) {
//                queryRelevantEventsCountFilterParams = new ArrayList<>();
//                Converters.readQueryNamedParams(QUERY_GET_RELEVANT_EVENTS_COUNT_BY_FILTER, queryRelevantEventsCountFilterParams);
//                QUERY_GET_RELEVANT_EVENTS_COUNT_BY_FILTER = Converters.replaceParams(QUERY_GET_RELEVANT_EVENTS_COUNT_BY_FILTER);
//            }

        }
    }

    @Override
    public Future<List<JsonObject>> getList() {
        Promise<List<JsonObject>> promise = Promise.promise();

        postgreClient.getConnection(
                connHandler(promise, connection ->
                        connection.query(QUERY_GET_LIST, res -> {
                            if (res.succeeded()) {
                                promise.complete(res.result().getRows());
                            } else {
                                if (res.cause().getMessage() != null) {
                                    LOGGER.error("Request (getList) failed -> " + res.cause().getMessage());
                                    promise.fail(res.cause().getMessage());
                                } else {
                                    LOGGER.error("Request (getList) failed -> " + res.cause());
                                    promise.fail(res.cause());
                                }
                            }
                            connection.close();
                        })
                ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> getByExtId(long extId, int authType) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(extId)
                .add(authType);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.queryWithParams(QUERY_GET_BY_EXT_ID, params, res -> {
                    if (res.succeeded()) {
                        if (!res.result().getRows().isEmpty())
                            promise.complete(res.result().getRows().get(0));
                        else
                            promise.fail("No data");
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (getByExtId) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (getByExtId) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> getUserByExtIdAndUpdate(long extId, int authType) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(extId)
                .add(authType);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.queryWithParams(QUERY_GET_USER_BY_EXT_ID_AND_UPDATE, params, res -> {
                    if (res.succeeded())
                        if (!res.result().getRows().isEmpty())
                            promise.complete(res.result().getRows().get(0));
                        else
                            promise.fail("No data");
                    else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (getUserByExtIdAndUpdate) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (getUserByExtIdAndUpdate) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> updateMobileNumber(long extId, int authType, String mobileNumber) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(mobileNumber)
                .add(extId)
                .add(authType);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.updateWithParams(QUERY_UPDATE_MOBILE_NUMBER, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null
                                && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                && !res.result().getKeys().isEmpty()) {

                            promise.complete(new JsonObject().put("id", res.result().getKeys().getLong(0)));
                        } else
                            promise.complete(new JsonObject().putNull("id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (getUserByExtIdAndUpdate) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (getUserByExtIdAndUpdate) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> updateProfile(long userId, String firstName, String lastName, String email) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(firstName)
                .add(lastName)
                .add(email)
                .add(userId);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.updateWithParams(QUERY_UPDATE_USER_PROFILE, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null
                                && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                && !res.result().getKeys().isEmpty()) {

                            promise.complete(new JsonObject().put("id", res.result().getKeys().getLong(0)));
                        } else
                            promise.complete(new JsonObject().putNull("id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (updateProfile) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (updateProfile) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> updateUserData(long extUserId, String login, String photoUrl, long authTypeId) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(login)
                .add(photoUrl)
                .add(extUserId)
                .add(authTypeId);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.updateWithParams(QUERY_UPDATE_USER_DATA, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null
                                && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                && !res.result().getKeys().isEmpty()) {

                            promise.complete(new JsonObject().put("id", res.result().getKeys().getLong(0)));
                        } else
                            promise.complete(new JsonObject().putNull("id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (updateUserData) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (updateUserData) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> updateUserRegion(long userId, long regionId, long authTypeId) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(regionId)
                .add(userId)
                .add(authTypeId);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.updateWithParams(QUERY_UPDATE_USER_REGION, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null
                                && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                && !res.result().getKeys().isEmpty()) {
                            promise.complete(new JsonObject().put("id", res.result().getKeys().getLong(0)));
                        } else
                            promise.complete(new JsonObject().putNull("id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (updateUserRegion) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (updateUserRegion) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> updateUserRole(long userId, long roleId) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(roleId)
                .add(userId);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.updateWithParams(QUERY_UPDATE_USER_ROLE, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null
                                && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                && !res.result().getKeys().isEmpty()) {
                            promise.complete(new JsonObject().put("id", res.result().getKeys().getLong(0)));
                        } else
                            promise.complete(new JsonObject().putNull("id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (updateUserRole) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (updateUserRole) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> updateAccessValue(long id, int accessValue) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(accessValue)
                .add(id);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.updateWithParams(QUERY_UPDATE_ACCESS_VALUE, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null
                                && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                && !res.result().getKeys().isEmpty()) {

                            promise.complete(new JsonObject().put("id", res.result().getKeys().getLong(0)));
                        } else
                            promise.complete(new JsonObject().putNull("id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (updateAccessValue) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (updateAccessValue) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> updateIsActive(boolean isActive, long id) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(isActive)
                .add(id);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.updateWithParams(QUERY_UPDATE_IS_ACTIVE, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null
                                && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                && !res.result().getKeys().isEmpty()) {

                            promise.complete(new JsonObject().put("id", res.result().getKeys().getLong(0)));
                        } else
                            promise.complete(new JsonObject().putNull("id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (updateIsActive) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (updateIsActive) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> updateDurationMin(long durationMin, long id) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(durationMin)
                .add(id);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.updateWithParams(QUERY_UPDATE_DURATION_MIN, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null
                                && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                && !res.result().getKeys().isEmpty()) {

                            promise.complete(new JsonObject().put("id", res.result().getKeys().getLong(0)));
                        } else
                            promise.complete(new JsonObject().putNull("id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (updateDurationMin) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (updateDurationMin) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> save(JsonObject formData) {
        Promise<JsonObject> promise = Promise.promise();

        if (QUERY_SAVE.startsWith("SELECT")) {
            queryWithParams(promise, formData, QUERY_SAVE, querySaveParams);
        } else {
            crud(promise, formData, QUERY_SAVE, querySaveParams);
        }

        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getRolePermissionsByUserId(long userId) {
        Promise<List<JsonObject>> promise = Promise.promise();
        JsonArray params = new JsonArray().add(userId);
        queryWithParams(promise, QUERY_GET_ROLE_PERMISSION_BY_USER_ID, params);
        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getUsersByRoleId(long roleId) {
        Promise<List<JsonObject>> promise = Promise.promise();

        JsonArray params = new JsonArray()
                .add(roleId);

        queryWithParams(promise, QUERY_GET_USERS_BY_ROLE_ID, params);
        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getEventsForAnonymous(long categoryId, Boolean isActive, int filterStatusId, String startDate, String endDate) {

        Promise<List<JsonObject>> promise = Promise.promise();
        JsonArray params;

        switch (filterStatusId) {
            case LIFEMAP_CONST.FILTER_STATUS_ACTUAL:
                params = new JsonArray()
                        .add(categoryId)
                        .add(categoryId);
                queryWithParams(promise, QUERY_GET_EVENTS_FOR_ANONYMOUS_RELEVANT, params);
                break;
            case LIFEMAP_CONST.FILTER_STATUS_ALL:
                params = new JsonArray()
                        .add(categoryId)
                        .add(categoryId)
                        .add(startDate)
                        .add(endDate)
                        .add(startDate)
                        .add(endDate)
                        .add(startDate)
                        .add(endDate)
                        .add(startDate)
                        .add(endDate);

                queryWithParams(promise, QUERY_GET_EVENTS_FOR_ANONYMOUS_ALL, params);
                break;
            default:
                params = new JsonArray()
                        .add(categoryId)
                        .add(categoryId)
                        .add(startDate)
                        .add(endDate);

                queryWithParams(promise, QUERY_GET_EVENTS_FOR_ANONYMOUS_RELEVANT, params);
        }

        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getRelevantEvents(long user_id, long category_id, int roleId, String startDate, String endDate) {
        Promise<List<JsonObject>> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(user_id)
                .add(user_id)
                .add(category_id)
                .add(category_id)
//                .add(startDate)
//                .add(endDate)
                ;

        queryWithParams(promise, QUERY_GET_RELEVANT_EVENTS, params);
        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getAllEvents(long user_id, long category_id, int roleId, String startDate, String endDate) {
        Promise<List<JsonObject>> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(user_id)
                .add(user_id)
                .add(category_id)
                .add(category_id)
                .add(startDate)
                .add(endDate)
                .add(startDate)
                .add(endDate)


                .add(startDate)
                .add(endDate)
                .add(startDate)
                .add(endDate);

        switch (roleId) {
            case LIFEMAP_CONST.ROLE_USER:
                queryWithParams(promise, QUERY_GET_ALL_EVENTS_WITH_MODERATED, params);
                break;
            case LIFEMAP_CONST.ROLE_TRUSTED_USER:
                queryWithParams(promise, QUERY_GET_ALL_EVENTS_WITH_MODERATED, params);
                break;
            default:
                queryWithParams(promise, QUERY_GET_ALL_EVENTS, params);
        }

        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getModeratingEvents(long user_id, long category_id, int roleId, String startDate, String endDate, long userRegionId) {
        Promise<List<JsonObject>> promise = Promise.promise();
//        JsonArray params = new JsonArray()
//                .add(user_id)
//                .add(user_id)
//                .add(category_id)
//                .add(category_id)
//                .add(startDate)
//                .add(endDate);

        JsonArray params = new JsonArray()
                .add(user_id)
                .add(user_id)
                .add(category_id)
                .add(category_id)

                .add(startDate)
                .add(endDate)
                .add(startDate)
                .add(endDate)
                .add(startDate)
                .add(endDate)
                .add(startDate)
                .add(endDate)
                .add(userRegionId)
                .add(userRegionId);

        queryWithParams(promise, QUERY_GET_MODERATING_EVENTS, params);
        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getEventsBySearchText(long userId, String searchText, boolean isForAnonymous) {
        Promise<List<JsonObject>> promise = Promise.promise();

        JsonArray params = new JsonArray()
                .add(userId)
                .add(userId)
                .add(searchText)
                .add(searchText)
                .add(searchText);

        if (isForAnonymous){
            queryWithParams(promise, QUERY_GET_EVENTS_BY_TEXT_SEARCH_FOR_ANONYMOUS, params);
        } else {
            queryWithParams(promise, QUERY_GET_EVENTS_BY_TEXT_SEARCH, params);
        }

        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getOwnEvents(long user_id, long category_id, int roleId, String startDate, String endDate) {
        Promise<List<JsonObject>> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(user_id)
                .add(category_id)
                .add(category_id)
//                .add(startDate)
//                .add(endDate)
                ;

        queryWithParams(promise, QUERY_GET_OWN_EVENTS, params);
        return promise.future();
    }


    @Override
    public Future<JsonObject> deleteEvent(long eventId, long performDeletionUserId) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(performDeletionUserId)
                .add(eventId);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.updateWithParams(QUERY_DELETE_EVENT, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null
                                && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                && !res.result().getKeys().isEmpty()) {

                            promise.complete(new JsonObject().put("id", res.result().getKeys().getLong(0)));
                        } else
                            promise.complete(new JsonObject().putNull("id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (deleteEvent) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (deleteEvent) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> updateModerationStatus(long eventId, boolean moderationStatus) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(moderationStatus)
                .add(eventId);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.updateWithParams(QUERY_UPDATE_EVENT_MODERATION_STATUS, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null
                                && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                && !res.result().getKeys().isEmpty()) {

                            promise.complete(new JsonObject().put("id", res.result().getKeys().getLong(0)));
                        } else
                            promise.complete(new JsonObject().putNull("id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (deleteEvent) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (deleteEvent) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> getRegionByCoordinates(Double lon, Double lat) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(lon)
                .add(lat);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.queryWithParams(QUERY_GET_REGION_BY_COORDINATES, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null && res.result().getRows().get(0).getLong("region_id") != null) {
                            promise.complete(new JsonObject().put("region_id", res.result().getRows().get(0).getLong("region_id")));
                        } else
                            promise.complete(new JsonObject().putNull("region_id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (getRegionByCoordinates) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (getRegionByCoordinates) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getActiveEventCategories() {
        Promise<List<JsonObject>> promise = Promise.promise();

        postgreClient.getConnection(
                connHandler(promise, connection ->
                        connection.query(QUERY_GET_ACTIVE_EVENT_CATEGORIES, res -> {
                            if (res.succeeded()) {
                                promise.complete(res.result().getRows());
                            } else {
                                if (res.cause().getMessage() != null) {
                                    LOGGER.error("Request (getActiveEventCategories) failed -> " + res.cause().getMessage());
                                    promise.fail(res.cause().getMessage());
                                } else {
                                    LOGGER.error("Request (getActiveEventCategories) failed -> " + res.cause());
                                    promise.fail(res.cause());
                                }
                            }
                            connection.close();
                        })
                ));
        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getDashboardDataEvents(long categoryId, long regionId, long userId, String startDate, String endDate) {
        Promise<List<JsonObject>> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(categoryId)
                .add(regionId)
                .add(userId)
                .add(startDate)
                .add(endDate);

        queryWithParams(promise, QUERY_GET_DASHBOARD_DATA_EVENTS, params);
        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getDashboardDataUsers() {
        Promise<List<JsonObject>> promise = Promise.promise();

        postgreClient.getConnection(
                connHandler(promise, connection ->
                        connection.query(QUERY_GET_DASHBOARD_DATA_USERS, res -> {
                            if (res.succeeded()) {
                                promise.complete(res.result().getRows());
                            } else {
                                if (res.cause().getMessage() != null) {
                                    LOGGER.error("Request (getDashboardDataUsers) failed -> " + res.cause().getMessage());
                                    promise.fail(res.cause().getMessage());
                                } else {
                                    LOGGER.error("Request (getDashboardDataUsers) failed -> " + res.cause());
                                    promise.fail(res.cause());
                                }
                            }
                            connection.close();
                        })
                ));
        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getDashboardDataEventsByRegionFilter(String startDate, String endDate) {
        Promise<List<JsonObject>> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(startDate)
                .add(endDate);

        queryWithParams(promise, QUERY_GET_DASHBOARD_DATA_EVENTS_BY_REGION_FILTER, params);
        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getDashboardDataEventsByCategoryRegion(String startDate, String endDate) {
        Promise<List<JsonObject>> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(startDate)
                .add(endDate);

        queryWithParams(promise, QUERY_GET_DASHBOARD_DATA_EVENTS_BY_CATEGORY_REGION, params);
        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getDashboardDataEventsByCategoryFilter(String startDate, String endDate) {
        Promise<List<JsonObject>> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(startDate)
                .add(endDate);

        queryWithParams(promise, QUERY_GET_DASHBOARD_DATA_EVENTS_BY_CATEGORY_FILTER, params);
        return promise.future();
    }

    @Override
    public Future<JsonObject> blockUser(long userId, long performBlockingUserId) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(performBlockingUserId)
                .add(userId);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.updateWithParams(QUERY_BLOCK_USER, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null
                                && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                && !res.result().getKeys().isEmpty()) {

                            promise.complete(new JsonObject().put("id", res.result().getKeys().getLong(0)));
                        } else
                            promise.complete(new JsonObject().putNull("id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (blockUser) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (blockUser) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> saveEventResponse(long eventId, long responsedUserId, String name, String message, String startDate) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(eventId)
                .add(responsedUserId)
                .add(name)
                .add(message)
                .add(startDate);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.updateWithParams(QUERY_SAVE_EVENT_RESPONSE, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null
                                && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                && !res.result().getKeys().isEmpty()) {

                            promise.complete(new JsonObject().put("id", res.result().getKeys().getLong(0)));
                        } else
                            promise.complete(new JsonObject().putNull("id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (saveEventResponse) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (saveEventResponse) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> saveEventAction(long eventId, long responsedUserId, Long actionTypeId) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(eventId)
                .add(responsedUserId)
                .add(actionTypeId);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.updateWithParams(QUERY_SAVE_EVENT_ACTION, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null
                                && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                && !res.result().getKeys().isEmpty()) {

                            promise.complete(new JsonObject().put("id", res.result().getKeys().getLong(0)));
                        } else
                            promise.complete(new JsonObject().putNull("id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (saveEventResponse) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (saveEventResponse) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> updateExportedEvent(long externalId, long userId, String fullName, String phoneNumber) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()

                .add(userId)
                .add(fullName)
                .add(phoneNumber)
                .add(externalId);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.updateWithParams(QUERY_UPDATE_EXPORTED_EVENT, params, res -> {
                    if (res.succeeded()) {
                        if (res.result() != null
                                && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                && !res.result().getKeys().isEmpty()) {

                            promise.complete(new JsonObject().put("id", res.result().getKeys().getLong(0)));
                        } else
                            promise.complete(new JsonObject().putNull("id"));
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (updateExportedEvent) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (updateExportedEvent) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getListByAutoId(long auto_id) {
        Promise<List<JsonObject>> promise = Promise.promise();
        JsonArray params = new JsonArray().add(auto_id);
        queryWithParams(promise, QUERY_SELECT_LIST_BY_AUTO_ID, params);
        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getListByGroupId(long group_id) {
        Promise<List<JsonObject>> promise = Promise.promise();
        JsonArray params = new JsonArray().add(group_id);

        queryWithParams(promise, QUERY_SELECT_LIST_BY_GROUP_ID, params);

        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getListByPerPage(long page, long perPage) {
        Promise<List<JsonObject>> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(perPage)
                .add(page * perPage);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.queryWithParams(QUERY_GET_LIST_BY_PER_PAGE, params, res -> {
                    if (res.succeeded()) {
                        promise.complete(res.result().getRows());
                    } else {
                        LOGGER.error("Request(getListByPerPage) failed -> " + res.cause().getMessage());
                        promise.fail(res.cause().getMessage());
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> getCount() {
        Promise<JsonObject> promise = Promise.promise();

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.query(QUERY_GET_COUNT, res -> {
                    if (res.succeeded()) {
                        if (!res.result().getRows().isEmpty())
                            promise.complete(res.result().getRows().get(0));
                        else
                            promise.fail("No data");
                    } else {
                        if (res.cause().getMessage() != null) {
                            LOGGER.error("Request (getCount) failed -> " + res.cause().getMessage());
                            promise.fail(res.cause().getMessage());
                        } else {
                            LOGGER.error("Request (getCount) failed -> " + res.cause());
                            promise.fail(res.cause());
                        }
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> getPageCount(long perPage) {
        Promise<JsonObject> promise = Promise.promise();

        this.getCount().setHandler(res -> {
            if (res.succeeded()) {
                double count = res.result().getInteger("count", 0);
                long pageCount = (long) Math.ceil(count / perPage);
                promise.complete(new JsonObject().put("pageCount", pageCount));
            } else {
                LOGGER.error("Request(getPageCountByPerPage) failed -> " + res.cause().getMessage());
                promise.fail(res.cause().getMessage());
            }
        });

        return promise.future();
    }

    @Override
    public Future<JsonObject> getById(long id) {
        Promise<JsonObject> promise = Promise.promise();
        JsonArray params = new JsonArray()
                .add(id);

        postgreClient.getConnection(connHandler(promise, connection ->
                connection.queryWithParams(QUERY_GET_BY_ID, params, res -> {
                    if (res.succeeded())
                        if (!res.result().getRows().isEmpty())
                            promise.complete(res.result().getRows().get(0));
                        else
                            promise.fail("No data");
                    else {
                        LOGGER.error("Request(getById) failed -> " + res.cause().getMessage());
                        promise.fail(res.cause().getMessage());
                    }
                    connection.close();
                })
        ));
        return promise.future();
    }

    @Override
    public Future<JsonObject> update(JsonObject formData) {
        Promise<JsonObject> promise = Promise.promise();
        crud(promise, formData, QUERY_UPDATE, queryUpdateParams);
        return promise.future();
    }

    @Override
    public Future<JsonObject> delete(JsonObject formData) {
        Promise<JsonObject> promise = Promise.promise();
        crud(promise, formData, QUERY_DELETE, queryDeleteParams);
        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getListBySearchField(JsonObject formData) {
        try {
            Promise<List<JsonObject>> promise = Promise.promise();
            int page = 0;
            int perpage = 20;

            if (formData.containsKey("text_search")) {
                formData.put("text_search", "%" + formData.getString("text_search", "") + "%");
            }

            if (formData.containsKey("page")) {
                page = formData.getInteger("page");
            }

            if (formData.containsKey("perpage")) {
                perpage = formData.getInteger("perpage");
            }

            if (perpage < 0 || perpage > LIFEMAP_CONST.PER_PAGE_500) {
                perpage = LIFEMAP_CONST.PER_PAGE_500;
            }

            formData.put("perpage", perpage);
            formData.put("page", page * perpage);

            String order = "";

            if (!formData.getString("sort_field").isEmpty()) {
                order = " order by " + formData.getString("sort_field");

                if (formData.getString("order").equals("desc")) {
                    order = order + " desc ";
                } else {
                    order = order + " asc ";
                }
            }
            String sql = QUERY_GET_LIST_BY_FILTER.replace("$order by$", order);

            postgreClient.getConnection(connHandler(promise, connection ->
                    connection.queryWithParams(sql, formDataToParams(formData, queryFilterParams),
                            res -> {
                                if (res.succeeded()) {
                                    promise.complete(res.result().getRows());
                                } else {
                                    if (res.cause().getMessage() != null) {
                                        LOGGER.error("Request (getListBySearchField) failed -> " + res.cause().getMessage());
                                        promise.fail(res.cause().getMessage());
                                    } else {
                                        LOGGER.error("Request (getListBySearchField) failed -> " + res.cause());
                                        promise.fail(res.cause());
                                    }
                                }
                                connection.close();
                            })
            ));
            return promise.future();
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public Future<JsonObject> getCountBySearchField(JsonObject formData) {
        Promise<JsonObject> promise = Promise.promise();
        if (formData.containsKey("text_search") && !formData.getString("text_search").isEmpty()) {
            formData.put("text_search", "%" + formData.getString("text_search", "") + "%");
        } else {
            formData.put("text_search", "%%");
        }

        postgreClient.getConnection(connHandler(promise, connection ->
                connection
                        .queryWithParams(QUERY_GET_COUNT_BY_FILTER,
                                formDataToParams(formData, queryCountFilterParams),
                                res -> {
                                    if (res.succeeded()) {
                                        if (!res.result().getRows().isEmpty())
                                            promise.complete(res.result().getRows().get(0));
                                        else
                                            promise.fail("No data");
                                    } else {
                                        if (res.cause().getMessage() != null) {
                                            LOGGER.error("Request (getCountBySearchField) failed -> " + res.cause().getMessage());
                                            promise.fail(res.cause().getMessage());
                                        } else {
                                            LOGGER.error("Request (getCountBySearchField) failed -> " + res.cause());
                                            promise.fail(res.cause());
                                        }
                                    }
                                    connection.close();
                                })
        ));

        return promise.future();
    }

    @Override
    public Future<List<JsonObject>> getListByLang(String lang) {
        Promise<List<JsonObject>> promise = Promise.promise();

        JsonArray params = new JsonArray()
                .add(lang)
                .add(lang)
                .add(lang);

        queryWithParams(promise, QUERY_GET_LIST_BY_LANG, params);
        return promise.future();
    }

    //endregion

    private void queryWithParams(Promise<List<JsonObject>> promise, String sql, JsonArray params) {
        postgreClient.getConnection(
                connHandler(promise, connection ->
                        connection.queryWithParams(sql, params, res -> {
                            connection.close();
                            if (res.succeeded()) {
                                promise.complete(res.result().getRows());
                            } else {
                                if (res.cause().getMessage() != null) {
                                    LOGGER.error("Request (queryWithParams) failed -> " + res.cause().getMessage());
                                    promise.fail(res.cause().getMessage());
                                } else {
                                    LOGGER.error("Request (queryWithParams) failed -> " + res.cause());
                                    promise.fail(res.cause());
                                }
                            }
                        })
                ));
    }

    private void queryWithParams(Promise<JsonObject> promise, JsonObject formData, String sqlQuery, List<String> queryParams) {

        postgreClient.getConnection(connHandler(promise, connection -> {
                    JsonArray arr = formDataToParams(formData, queryParams);
                    connection
                            .queryWithParams(sqlQuery, arr,
                                    res -> {
                                        connection.close();
                                        if (res.succeeded()) {
                                            promise.complete(res.result().getRows().get(0));
                                        } else {
                                            if (res.cause().getMessage() != null) {
                                                LOGGER.error("Request (queryWithParams) failed -> " + res.cause().getMessage());
                                                promise.fail(res.cause().getMessage());
                                            } else {
                                                LOGGER.error("Request (queryWithParams) failed -> " + res.cause());
                                                promise.fail(res.cause());
                                            }
                                        }
                                    });
                }
        ));
    }

    private void crud(Promise<JsonObject> promise, JsonObject formData, String sqlQuery, List<String> queryParams) {

        postgreClient.getConnection(connHandler(promise, connection -> {
                    JsonArray arr = formDataToParams(formData, queryParams);

                    connection
                            .setOptions(new SQLOptions().setAutoGeneratedKeys(true))
                            .updateWithParams(sqlQuery,
                                    arr,
                                    res -> {
                                        if (res.succeeded()) {
                                            if (res.result() != null
                                                    && res.result().getUpdated() == LIFEMAP_CONST.SQL_UPDATED_SUCCESS
                                                    && !res.result().getKeys().isEmpty()) {
                                                promise.complete(new JsonObject().put("id", res.result().getKeys().getInteger(0)));
                                            } else
                                                promise.complete(new JsonObject().putNull("id"));
                                        } else {
                                            LOGGER.error("Request failed -> " + res.cause().getMessage());
                                            promise.fail(res.cause().getMessage());
                                        }
                                        connection.close();
                                    });
                }
        ));
    }

    private Handler<AsyncResult<SQLConnection>> connHandler(Promise promise, Handler<SQLConnection> handler) {
        return conn -> {
            if (conn.succeeded()) {
                final SQLConnection connection = conn.result();
                handler.handle(connection);
            } else {
                LOGGER.error("Database connection failed -> " + conn.cause().getMessage());
                promise.fail(conn.cause().getMessage());
            }
        };
    }

    public JsonArray formDataToParams(JsonObject formData, List<String> paramsNeeded) {
        final boolean[] canProceed = {false};
        JsonArray params = new JsonArray();
        if (formData != null && paramsNeeded != null) {
            canProceed[0] = true;
            paramsNeeded.stream()
                    .forEach(key ->
                    {
                        if (!formData.containsKey(key))
                            canProceed[0] = false;
                        else if (formData.containsKey(key) && formData.getValue(key) == null)
                            params.addNull();
                        else {
                            switch (key) {
                                case "user_id":
                                case "addr_rayon_id":
                                case "addr_viloyat_id":
                                case "fields_group_id":
                                case "farmer_id":
                                case "agro_culture_id":
                                case "agro_status_id":
                                case "field_status_id":
                                case "user_created_id":
                                case "user_measurer_id":
                                case "pin_id":
                                case "zone_poi_group_id":
                                case "ptype_id":
                                    params.add(formData.getLong(key, 0L));
                                    break;
                                case "area":
                                case "prediction_harvest_cpg":
                                case "prediction_harvest_c":
                                    Double valueDouble;
                                    try {
                                        valueDouble = formData.getDouble(key, 0.);
                                    } catch (Exception e) {
                                        valueDouble = 0.;
                                    }
                                    params.add(valueDouble);
                                    break;
                                default:
                                    params.add(formData.getValue(key));
                            }
                        }
                    });
        }
        return (canProceed[0]) ? params : null;
    }

}
