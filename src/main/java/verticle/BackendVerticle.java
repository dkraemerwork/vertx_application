package verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class BackendVerticle extends AbstractVerticle {

    Router backendRouter = Router.router(vertx);

    @Override
    public void start(Future<Void> future) throws Exception {
        vertx.createHttpServer()
                .requestHandler(backendRouter)
                .listen(8080);
        vertx.deployVerticle(new DatabaseVerticle(), res -> {
            if (res.succeeded()) {
                System.out.println("Succeeded databaseVerticle deployment");
            } else System.out.println("Failed databaseVerticle deployment");

        });
        backendRouter.route("/").handler(routingContext -> {
            routingContext.response().putHeader("content-type", "text/html").end("Hello World!");
        });
        backendRouter.route("/handlerTest").handler(this::getUsers);
    }

    private void getUsers(RoutingContext routingContext) {
        vertx.eventBus().<String>request("verticle.DatabaseVerticle", "", result -> {
            if (result.succeeded()) {
                String jsonString = result.result().body();
                routingContext.response().putHeader("content-type", "Application/JSON").end(jsonString);
            }
        });
//        routingContext.response().putHeader("content-type", "text/html").end();
    }
}
