import com.twitter.finagle.Http;
import com.twitter.finagle.Service;
import com.twitter.finagle.http.Method;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Await;
import com.twitter.util.Future;
import org.junit.jupiter.api.Test;
import scala.runtime.BoxedUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FinagleIntegrationTest {
    @Test
    public void givenServerAndClient_whenRequestSent_thenClientShouldReceiveResponseFromServer() throws Exception {
        // given
        Service serverService = new LogFilter().andThen(new GreetingService());
        Http.serve(":8080", serverService);
        //Http.serve(":8081", serverService);

        Service<Request, Response> clientService = new LogFilter().andThen((Service<Request, Response>) Http.newService(":8081,:8080"));

        // when
        Request request = Request.apply(Method.Get(), "/?name=John");
        request.host("localhost");
        Future<Response> response = clientService.apply(request);

        // then
        Await.result(response
                .onSuccess(r -> {
                    assertEquals("Hello John", r.getContentString());
                    return BoxedUnit.UNIT;
                })
                .onFailure(r -> {
                    throw new RuntimeException(r);
                })
        );
    }
}
