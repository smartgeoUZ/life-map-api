package uz.smartgeo.lifemap.api.routers.user;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import uz.smartgeo.lifemap.api.routers.UnitRouter;
import uz.smartgeo.lifemap.api.utils.LIFEMAP_CONST;
import uz.smartgeo.lifemap.api.utils.ResponseUtil;

public class UserRouter extends UnitRouter {

    public UserRouter(Vertx vertx, JsonObject dbConfig, JsonObject dbQueries) {
        super(vertx, dbConfig, LIFEMAP_CONST.TABLE_NAME_USER, dbQueries);
        setRoutes();
    }

    protected void setRoutes() {
        router.post("/protected/getUserByExtIdAndUpdate").handler(this::getUserByExtIdAndUpdate);
        router.post("/updateMobileNumber").handler(this::updateMobileNumber);
        router.post("/getUsersByRoleId").handler(this::getUsersByRoleId);
        router.post("/protected/blockUser").handler(this::blockUser);
        router.post("/protected/updateProfile").handler(this::updateProfile);
        router.post("/protected/updateUserData").handler(this::updateUserData);

        router.post("/protected/updateUserRegion").handler(this::updateUserRegion);
//        router.post("/updateUserRegion").handler(this::updateUserRegion);
    }

    public void getUserByExtIdAndUpdate(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        long extId;
        int authType;

        if (formData != null) {
            if (formData.containsKey("extId") && formData.getLong("extId", null) != null &&
                    formData.containsKey("authType") && formData.getLong("authType", null) != null) {
                extId = formData.getLong("extId");
                authType = formData.getInteger("authType");

                unitService.getUserByExtIdAndUpdate(extId, authType).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

    public void getUsersByRoleId(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        long roleId;

        if (formData != null) {
            if (formData.containsKey("roleId")) {
                roleId = formData.getLong("roleId", null);

                unitService.getUsersByRoleId(roleId).onComplete(unitHandlerList(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

    public void updateMobileNumber(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        long extId;
        int authType;
        String mobileNumber;

        if (formData != null) {
            if (formData.containsKey("extId") && formData.getLong("extId", null) != null &&
                    formData.containsKey("authType") && formData.getLong("authType", null) != null &&
                    formData.containsKey("mobileNumber") && formData.getString("mobileNumber", null) != null) {

                extId = formData.getLong("extId");
                authType = formData.getInteger("authType");
                mobileNumber = formData.getString("mobileNumber");

                unitService.updateMobileNumber(extId, authType, mobileNumber).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

    public void blockUser(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        long userId;
        long performBlockingUserId;

        if (formData != null) {
            if (formData.containsKey("userId") && formData.getLong("userId", null) != null &&
                    formData.containsKey("performBlockingUserId") && formData.getLong("performBlockingUserId", null) != null) {

                userId = formData.getLong("userId");
                performBlockingUserId = formData.getLong("performBlockingUserId");

                unitService.blockUser(userId, performBlockingUserId).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

    public void updateProfile(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();

        long userId;
        String firstName;
        String lastName;
        String email;

        if (formData != null) {
            if (formData.containsKey("id") && formData.getLong("id", null) != null &&
                    formData.containsKey("firstName") && formData.getString("firstName", "") != null &&
                    formData.containsKey("lastName") && formData.getString("lastName", "") != null &&
                    formData.containsKey("email") && formData.getString("email", "") != null) {

                userId = formData.getLong("id");
                firstName = formData.getString("firstName");
                lastName = formData.getString("lastName");
                email = formData.getString("email");

                unitService.updateProfile(userId, firstName, lastName, email).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

    public void updateUserData(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();

        long authTypeId;
        long extUserId;
        String login;
        String photoUrl;

        if (formData != null) {
            if (formData.containsKey("auth_type_id") && formData.getLong("auth_type_id", null) != null &&
                    formData.containsKey("ext_user_id") && formData.getLong("ext_user_id", null) != null &&
                    formData.containsKey("login") && formData.getString("login", "") != null &&
                    formData.containsKey("photo_url") && formData.getString("photo_url", "") != null) {

                authTypeId = formData.getLong("auth_type_id");
                extUserId = formData.getLong("ext_user_id");
                login = formData.getString("login");
                photoUrl = formData.getString("photo_url");

                unitService.updateUserData(extUserId, login, photoUrl, authTypeId).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

    public void updateUserRegion(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();

        long authTypeId;
        long userId;
        long regionId;

        if (formData != null) {
            if (formData.containsKey("auth_type_id") && formData.getLong("auth_type_id", null) != null &&
                    formData.containsKey("region_id") && formData.getLong("region_id", null) != null &&
                    formData.containsKey("user_id") && formData.getLong("user_id", null) != null) {

                authTypeId = formData.getLong("auth_type_id");
                userId = formData.getLong("user_id");
                regionId = formData.getLong("region_id");

                unitService.updateUserRegion(userId, regionId, authTypeId).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }
}
