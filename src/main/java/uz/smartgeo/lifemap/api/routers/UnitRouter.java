package uz.smartgeo.lifemap.api.routers;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;
import org.apache.log4j.Logger;
import uz.smartgeo.lifemap.api.db.impl.UnitService;
import uz.smartgeo.lifemap.api.db.interfaces.IUnit;
import uz.smartgeo.lifemap.api.utils.AuthUtils;
import uz.smartgeo.lifemap.api.utils.Converters;
import uz.smartgeo.lifemap.api.utils.LIFEMAP_CONST;
import uz.smartgeo.lifemap.api.utils.ResponseUtil;

import java.util.List;

public class UnitRouter implements IUnitRouter {
    private static final Logger LOGGER = Logger.getLogger(UnitRouter.class);

    protected Router router;
    protected IUnit unitService;

    protected String tableName;
    protected WebClient client;

    protected JsonObject config;

    public UnitRouter(Vertx vertx, JsonObject config, String tableName, JsonObject dbQueries) {
        this.router = Router.router(vertx);
        this.router.route().handler(BodyHandler.create());
        this.tableName = tableName;
        this.unitService = new UnitService(vertx, config, this.tableName, dbQueries);

        this.config = config;

        this.client = WebClient.create(vertx);

        configureUrlMap();
    }

    public Router getRouter() {
        return router;
    }

    protected void configureUrlMap() {

        router.post("/protected/*").handler(this::authHandler);
        router.get("/protected/*").handler(this::authHandler);

        router.get("/protected/getList").handler(this::getList);
        router.get("/getList").handler(this::getList);

        router.get("/protected/getListPerPage/:page").handler(this::getListPerPage);
        router.get("/protected/getCount").handler(this::getCount);

        router.post("/protected/getListByFilter").handler(this::getListByFilter);
        router.post("/getListByFilter").handler(this::getListByFilter);

        router.post("/protected/getCountByFilter").handler(this::getCountByFilter);
        router.post("/getCountByFilter").handler(this::getCountByFilter);

        router.get("/protected/getById/:id").handler(this::getById);

        router.post("/protected/save").handler(this::save);

        router.post("/protected/getByExtId").handler(this::getByExtId);
        router.post("/getByExtId").handler(this::getByExtId);

//        router.get("/getListPerPage/:page").handler(this::getListPerPage);
//        router.get("/getCount").handler(this::getCount);
//        router.get("/getPageCount").handler(this::getPageCount);

//        router.post("/delete").handler(this::delete);

//        router.post("/getListByDate").handler(this::getListByDate);
//        router.post("/getCountByDate").handler(this::getCountByDate);
//
//        router.post("/getListFieldStatusByContract").handler(this::getListFieldStatusByContract);
//        router.post("/getListMObjectByContract").handler(this::getListMObjectByContract);

    }

    private void authHandler(RoutingContext context) {
        final String authorization = context.request().getHeader(HttpHeaders.AUTHORIZATION);

        if (AuthUtils.tokenIsValid(authorization)) {
            context.next();
        } else {
            ResponseUtil.respondError(context, "Not authorized request");
        }
    }

    public void getByExtId(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        long extId;
        int authType;

        if (formData != null) {
            if (formData.containsKey("extId") && formData.getLong("extId", null) != null &&
                    formData.containsKey("authType") && formData.getLong("authType", null) != null) {
                extId = formData.getLong("extId");
                authType = formData.getInteger("authType");

                unitService.getByExtId(extId, authType).onComplete(unitHandler(context));
            } else {
                ResponseUtil.respondError(context, "c");
            }
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

    private void getCountByFilter(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        unitService.getCountBySearchField(formData).setHandler(unitHandler(context));
    }

    private void getListByFilter(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        unitService.getListBySearchField(formData).setHandler(unitHandlerList(context));
    }

    @Override
    public void getList(RoutingContext context) {
        unitService.getList().setHandler(unitHandlerList(context));
    }

    @Override
    public void getListPerPage(RoutingContext context) {
        long page = Converters.strToLong(context.request().getParam("page"), 0L);
        long per_page = Converters.strToLong(context.request().getParam("per_page"), LIFEMAP_CONST.PER_PAGE);

        if (per_page < 0 || per_page > LIFEMAP_CONST.PER_PAGE_500)
            per_page = LIFEMAP_CONST.PER_PAGE_500;

        unitService.getListByPerPage(page, per_page).setHandler(unitHandlerList(context));
    }

    @Override
    public void getCount(RoutingContext context) {
        unitService.getCount().setHandler(unitHandler(context));
    }

    @Override
    public void getPageCount(RoutingContext context) {
        long per_page = Converters.strToLong(context.request().getParam("per_page"), LIFEMAP_CONST.PER_PAGE);
        unitService.getPageCount(per_page).setHandler(unitHandler(context));
    }

    @Override
    public void getById(RoutingContext context) {
        long id = Converters.strToLong(context.request().getParam("id"), 0L);
        unitService.getById(id).setHandler(unitHandler(context));
    }

    @Override
    public void save(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        if (formData != null) {
            if (formData.containsKey("id") && formData.getLong("id", null) != null)
                unitService.update(formData).setHandler(unitHandler(context));
            else
                unitService.save(formData).setHandler(unitHandler(context));
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

    @Override
    public void delete(RoutingContext context) {
        JsonObject formData = context.getBodyAsJson();
        if (formData != null
                && formData.containsKey("id")
                && formData.getLong("id", null) != null) {
            unitService.delete(formData).setHandler(unitHandler(context));
        } else {
            ResponseUtil.respondError(context, "No data for fields");
        }
    }

    /**
     * Responder to client. Here we form result structure
     *
     * @param context Routing context that received
     * @return Response with headers
     */
    protected Handler<AsyncResult<JsonObject>> unitHandler(RoutingContext context) {
        return res -> {
            if (res.succeeded())
                ResponseUtil.respondWithCreated(context, res.result());
            else {
                if (res.cause().getMessage() != null) {
                    ResponseUtil.respondError(context, res.cause().getMessage());
                    LOGGER.error("Request failed -> " + res.cause().getMessage());
                } else {
                    ResponseUtil.respondError(context, res.cause().toString());
                    LOGGER.error("Request failed -> " + res.cause());
                }
            }
        };
    }

    /**
     * Responder to client. Here we form result structure
     *
     * @param context Routing context that received
     * @return Response with headers
     */
    protected Handler<AsyncResult<List<JsonObject>>> unitHandlerList(RoutingContext context) {
        return res -> {
            if (res.succeeded())
                ResponseUtil.respondWithCreated(context, res.result());
            else {
                if (res.cause().getMessage() != null) {
                    ResponseUtil.respondError(context, res.cause().getMessage());
                    LOGGER.error("Request(getById) failed -> " + res.cause().getMessage());
                } else {
                    ResponseUtil.respondError(context, res.cause().toString());
                    LOGGER.error("Request(getById) failed -> " + res.cause().toString());
                }

            }
        };
    }

}
