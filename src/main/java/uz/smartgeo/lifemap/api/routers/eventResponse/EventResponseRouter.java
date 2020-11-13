package uz.smartgeo.lifemap.api.routers.eventResponse;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import uz.smartgeo.lifemap.api.routers.UnitRouter;
import uz.smartgeo.lifemap.api.utils.LIFEMAP_CONST;
import uz.smartgeo.lifemap.api.utils.ResponseUtil;

public class EventResponseRouter extends UnitRouter {


    public EventResponseRouter(Vertx vertx, JsonObject dbConfig, JsonObject dbQueries) {
        super(vertx, dbConfig, LIFEMAP_CONST.TABLE_NAME_EVENT_RESPONSE, dbQueries);

        setRoutes();
    }

    protected void setRoutes() {
        router.post("/protected/saveEventResponse").handler(this::saveEventResponse);
    }

    public void saveEventResponse(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        long eventId;
        long responsedUserId;
        String name;
        String message;
        String startDate;

        if (formData != null) {
            if (formData.containsKey("eventId") && formData.getLong("eventId") != null &&
                    formData.containsKey("responsedUserId") && formData.getLong("responsedUserId") != null &&
                    formData.containsKey("name") && formData.getString("name") != null &&
                    formData.containsKey("message") && formData.getString("message") != null &&
                    formData.containsKey("startDate") && formData.getString("startDate") != null) {

                eventId = formData.getLong("eventId");
                responsedUserId = formData.getLong("responsedUserId");
                name = formData.getString("name");
                message = formData.getString("message");
                startDate = formData.getString("startDate");

                unitService.saveEventResponse(eventId, responsedUserId, name, message, startDate).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

}
