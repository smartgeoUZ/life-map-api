package uz.smartgeo.lifemap.api.routers.rolePermission;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import uz.smartgeo.lifemap.api.routers.UnitRouter;
import uz.smartgeo.lifemap.api.utils.LIFEMAP_CONST;
import uz.smartgeo.lifemap.api.utils.ResponseUtil;

public class RolePermissionRouter extends UnitRouter {


    public RolePermissionRouter(Vertx vertx, JsonObject dbConfig, JsonObject dbQueries) {
        super(vertx, dbConfig, LIFEMAP_CONST.TABLE_NAME_ROLE_PERMISSION, dbQueries);

        setRoutes();
    }

    protected void setRoutes() {
        router.post("/getRolePermissionsByUserId").handler(this::getRolePermissionsByUserId);
        router.post("/protected/getRolePermissionsByUserId").handler(this::getRolePermissionsByUserId);

        router.post("/updateAccessValue").handler(this::updateAccessValue);
        router.post("/protected/updateAccessValue").handler(this::updateAccessValue);
    }

    public void getRolePermissionsByUserId(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        long userId;

        if (formData != null) {
            if (formData.containsKey("userId")) {
                userId = formData.getLong("userId", null);

                unitService.getRolePermissionsByUserId(userId).onComplete(unitHandlerList(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

    public void updateAccessValue(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();

        Long id;
        Integer accessValue;


        if (formData != null) {
            if (formData.containsKey("id") && formData.getLong("id", null) != null &&
                    formData.containsKey("accessValue") && formData.getInteger("accessValue", 0) != null) {

                id = formData.getLong("id");
                accessValue = formData.getInteger("accessValue");

                unitService.updateAccessValue(id, accessValue).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

}
