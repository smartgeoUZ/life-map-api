package uz.smartgeo.lifemap.api.routers;

import io.vertx.ext.web.RoutingContext;

public interface IUnitRouter {

    void getList(RoutingContext context);

    void getListPerPage(RoutingContext context);

    void getCount(RoutingContext context);

    void getPageCount(RoutingContext context);

    void getById(RoutingContext context);

    void save(RoutingContext context);

    void delete(RoutingContext context);
}
