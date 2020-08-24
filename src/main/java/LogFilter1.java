import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Future;


public class LogFilter1 extends SimpleFilter<Request, Response> {

    @Override
    public Future apply(Request request, Service<Request, Response> service) {

        System.out.println("Request from port: 8081"  );
        System.out.println("Request params:");
        request.getParams().forEach(entry -> System.out.println("\t" + entry.getKey() + " : " + entry.getValue() ));
        return service.apply(request);
    }
}
