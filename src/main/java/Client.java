import com.twitter.finagle.Http;
import com.twitter.finagle.HttpRichClient;
import com.twitter.finagle.Service;
import com.twitter.finagle.Status;
import com.twitter.finagle.http.Method;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Await;
import com.twitter.util.Future;
import io.netty.handler.codec.http.HttpRequest;
import scala.runtime.BoxedUnit;

import java.util.concurrent.CompletableFuture;

public class Client {

    public static void main(String[] args) throws Exception {


        Service<Request, Response> clientService =
                new LogFilter().andThen((Service<Request, Response>) Http.newService("localhost:8081,localhost:8080"));
        Request request = Request.apply(Method.Get(), "/?name=John");
        request.host("abc");
        Future<Response> response = clientService.apply(request);


        Await.result(response

                .onSuccess(r -> {
                    System.out.println(r.contentString());
                    System.out.println(r.status());


                    return BoxedUnit.UNIT;
                })
                .onFailure(r -> {
                    throw new RuntimeException(r);
                })
        );




    }


}
