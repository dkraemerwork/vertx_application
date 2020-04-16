package verticle;

import com.alibaba.fastjson.JSON;
import dto.PersonDto;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;

import java.util.Arrays;

public class DatabaseVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.eventBus().localConsumer("verticle.DatabaseVerticle", this::getAllUsers);
    }

    private <T> void getAllUsers(Message<T> tMessage) {
        System.out.println("message received");
        tMessage.reply(JSON.toJSONString(Arrays.asList(new PersonDto(5, "Herbert"), new PersonDto(25, "Diana"))));
    }
}
