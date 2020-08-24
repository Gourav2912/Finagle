import com.twitter.finagle.Http;
import com.twitter.finagle.ListeningServer;
import com.twitter.finagle.Service;
import com.twitter.util.Await;
import com.twitter.util.TimeoutException;

public class MainClass {

    public static void main(String[] args) throws TimeoutException, InterruptedException {


    Service serverService = new LogFilter().andThen(new GreetingService());
    Service serverService1 = new LogFilter1().andThen(new GreetingService());
        final ListeningServer server = Http.server().serve("localhost:8080",serverService);
        final ListeningServer server1 = Http.server().serve("localhost:8081",serverService1);
        Await.ready(server);
        Await.ready(server1);

        //Http.serve(":8081", serverService);


    }
}
