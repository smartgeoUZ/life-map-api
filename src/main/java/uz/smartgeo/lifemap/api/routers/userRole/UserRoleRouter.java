package uz.smartgeo.lifemap.api.routers.userRole;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import uz.smartgeo.lifemap.api.routers.UnitRouter;
import uz.smartgeo.lifemap.api.utils.LIFEMAP_CONST;
import uz.smartgeo.lifemap.api.utils.ResponseUtil;

public class UserRoleRouter extends UnitRouter {

    public UserRoleRouter(Vertx vertx, JsonObject dbConfig, JsonObject dbQueries) {
        super(vertx, dbConfig, LIFEMAP_CONST.TABLE_NAME_USER_ROLE, dbQueries);
        setRoutes();
    }

    protected void setRoutes() {
        router.post("/protected/updateUserRole").handler(this::updateUserRole);
//        router.post("/updateUserRole").handler(this::updateUserRole);
    }

    public void updateUserRole(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();

        long roleId;
        long userId;

        if (formData != null) {
            if (formData.containsKey("role_id") && formData.getLong("role_id", null) != null &&
                    formData.containsKey("user_id") && formData.getLong("user_id", null) != null) {

                roleId = formData.getLong("role_id");
                userId = formData.getLong("user_id");

                unitService.updateUserRole(userId, roleId).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }
}
