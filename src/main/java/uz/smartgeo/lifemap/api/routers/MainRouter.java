package uz.smartgeo.lifemap.api.routers;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import uz.smartgeo.lifemap.api.routers.event.EventRouter;
import uz.smartgeo.lifemap.api.routers.eventCategory.EventCategoryRouter;
import uz.smartgeo.lifemap.api.routers.eventResponse.EventResponseRouter;
import uz.smartgeo.lifemap.api.routers.exportedEvent.ExportedEventRouter;
import uz.smartgeo.lifemap.api.routers.permission.PermissionRouter;
import uz.smartgeo.lifemap.api.routers.region.RegionRouter;
import uz.smartgeo.lifemap.api.routers.rolePermission.RolePermissionRouter;
import uz.smartgeo.lifemap.api.routers.user.UserRouter;
import uz.smartgeo.lifemap.api.routers.userRole.UserRoleRouter;

public class MainRouter {
//    private static JsonObject MainRouterConfig;

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(MainRouter.class.getName());

    public static Router createRouting(Vertx vertx, JsonObject config, JsonObject dbQueries) {
        final Router router = Router.router(vertx);
//        MainRouterConfig = config;
        router.route().handler(BodyHandler.create());

        LocalSessionStore localSessionStore = LocalSessionStore.create(vertx);
        router.route().handler(SessionHandler.create(localSessionStore));

        router.route().handler(io.vertx.ext.web.handler.CorsHandler.create("*")
                .allowedHeader("Authorization"));

//        LOGGER.info(config);
//        LOGGER.info(dbQueries);

        router.mountSubRouter("/api/user", new UserRouter(vertx, config, dbQueries).getRouter());
        router.mountSubRouter("/api/userRole", new UserRoleRouter(vertx, config, dbQueries).getRouter());

        router.mountSubRouter("/api/permission", new PermissionRouter(vertx, config, dbQueries).getRouter());
        router.mountSubRouter("/api/rp", new RolePermissionRouter(vertx, config, dbQueries).getRouter());

        router.mountSubRouter("/api/eventCategory", new EventCategoryRouter(vertx, config, dbQueries).getRouter());
        router.mountSubRouter("/api/event", new EventRouter(vertx, config, dbQueries).getRouter());
        router.mountSubRouter("/api/eventResponse", new EventResponseRouter(vertx, config, dbQueries).getRouter());

        router.mountSubRouter("/api/eventResponse", new EventResponseRouter(vertx, config, dbQueries).getRouter());

        router.mountSubRouter("/api/exported-event", new ExportedEventRouter(vertx, config, dbQueries).getRouter());

        router.mountSubRouter("/api/region", new RegionRouter(vertx, config, dbQueries).getRouter());

        // disable cache for static asset reload
        router.route("/static/*").handler(StaticHandler.create().setCachingEnabled(false).setWebRoot("static"));

        return router;
    }

//    private static void demo(RoutingContext context) {
//        JsonObject formData = context.getBodyAsJson();
//        boolean bodyChanged = false;
//        Long contractId = MainRouterConfig.getLong("default.contract_id", 0L);
//        if (contractId > 0) {
//            formData.put("contract_id", contractId);
//            bodyChanged = true;
//        }
//        Long userId = MainRouterConfig.getLong("default.user_id", 0L);
//        if (userId > 0) {
//            formData.put("user_id", userId);
//            bodyChanged = true;
//        }
//        if (bodyChanged)
//            context.setBody(formData.toBuffer());
//        context.next();
//    }
}
