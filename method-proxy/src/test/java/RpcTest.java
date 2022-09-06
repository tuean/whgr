import com.caucho.hessian.io.Hessian2Input;
import com.tuean.mp.netty.ServerConfig;
import com.tuean.mp.netty.serialize.impl.HessianSerializer;
import com.tuean.mp.netty.server.InnerServer;
import org.junit.Test;

public class RpcTest {

    public void test() {
        ServerConfig config = new ServerConfig(8888, 1, "boss", 1, "worker", HessianSerializer.class);
        InnerServer server = new InnerServer(config);
        new Thread(server).start();
    }


}
