import io.vertx.core.VertxOptions;
import verticle.BackendVerticle;
import io.vertx.core.Vertx;

public class Application {

        public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
        vertx.deployVerticle(new BackendVerticle());
    }
}
