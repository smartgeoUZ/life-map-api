package uz.smartgeo.lifemap.api.routers.eventCategory;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import uz.smartgeo.lifemap.api.routers.UnitRouter;
import uz.smartgeo.lifemap.api.utils.LIFEMAP_CONST;
import uz.smartgeo.lifemap.api.utils.ResponseUtil;

public class EventCategoryRouter extends UnitRouter {

    public EventCategoryRouter(Vertx vertx, JsonObject dbConfig, JsonObject dbQueries) {
        super(vertx, dbConfig, LIFEMAP_CONST.TABLE_NAME_EVENT_CATEGORY, dbQueries);

        setRoutes();
    }

    protected void setRoutes() {
        router.post("/getEventCategories").handler(this::getEventCategories);
        router.post("/protected/getEventCategories").handler(this::getEventCategories);

        router.post("/updateIsActive").handler(this::updateIsActive);
        router.post("/protected/updateIsActive").handler(this::updateIsActive);

        router.post("/updateDurationMin").handler(this::updateDurationMin);
        router.post("/protected/updateDurationMin").handler(this::updateDurationMin);
    }

    public void getEventCategories(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();

        int roleId;

        if (formData != null) {
            if (formData.containsKey("roleId") && formData.getInteger("roleId", null) != null) {

                roleId = formData.getInteger("roleId");

                switch (roleId) {
                    case LIFEMAP_CONST.ROLE_USER:
                        unitService.getActiveEventCategories().onComplete(unitHandlerList(context));
                        break;
                    case LIFEMAP_CONST.ROLE_TRUSTED_USER:
                        unitService.getActiveEventCategories().onComplete(unitHandlerList(context));
                        break;
                    case LIFEMAP_CONST.ROLE_MODERATOR:
                        unitService.getList().onComplete(unitHandlerList(context));
                        break;
                    case LIFEMAP_CONST.ROLE_ADMINISTRATOR:
                        unitService.getList().onComplete(unitHandlerList(context));
                        break;
                    case LIFEMAP_CONST.ROLE_SYSTEM_ADMINISTRATOR:
                        unitService.getList().onComplete(unitHandlerList(context));
                        break;
                    default:
                        unitService.getActiveEventCategories().onComplete(unitHandlerList(context));
                }

            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }

    }

    public void updateIsActive(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();

        Long id;
        Boolean isActive;

        if (formData != null) {
            if (formData.containsKey("id") && formData.getLong("id", null) != null &&
                    formData.containsKey("isActive") && formData.getBoolean("isActive", false) != null) {

                id = formData.getLong("id");
                isActive = formData.getBoolean("isActive");

                unitService.updateIsActive(isActive, id).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

    public void updateDurationMin(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();

        Long id;
        Long durationMin;

        if (formData != null) {
            if (formData.containsKey("id") && formData.getLong("id", null) != null &&
                    formData.containsKey("durationMin") && formData.getLong("durationMin", 1440L) != null) {

                id = formData.getLong("id");
                durationMin = formData.getLong("durationMin");

                unitService.updateDurationMin(durationMin, id).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

}
