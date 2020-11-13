package uz.smartgeo.lifemap.api.routers.region;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import uz.smartgeo.lifemap.api.routers.UnitRouter;
import uz.smartgeo.lifemap.api.utils.LIFEMAP_CONST;
import uz.smartgeo.lifemap.api.utils.ResponseUtil;

public class RegionRouter extends UnitRouter {


    public RegionRouter(Vertx vertx, JsonObject dbConfig, JsonObject dbQueries) {
        super(vertx, dbConfig, LIFEMAP_CONST.TABLE_NAME_REGION, dbQueries);

        setRoutes();
    }

    protected void setRoutes() {
//        router.post("/getRolePermissionsByUserId").handler(this::getRolePermissionsByUserId);
        router.post("/getListByLang").handler(this::getListByLang);
    }

    public void getListByLang(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        String lang;

        if (formData != null) {
            if (formData.containsKey("lang")) {
                lang = formData.getString("lang", "en");

                unitService.getListByLang(lang).onComplete(unitHandlerList(context));
            } else {
                ResponseUtil.respondError(context, "Not found some post data");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }
}
