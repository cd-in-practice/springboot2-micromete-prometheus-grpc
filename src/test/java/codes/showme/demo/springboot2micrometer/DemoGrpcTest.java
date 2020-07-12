package codes.showme.demo.springboot2micrometer;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.Test;

public class DemoGrpcTest {

    private final ManagedChannel channel;

    public DemoGrpcTest() {
        this.channel = ManagedChannelBuilder.forAddress("127.0.0.1",6565)
                .usePlaintext()
                .build();
    }

    @Test
    void hello() {
        System.out.println(FooGrpc.newBlockingStub(channel)
                .sayHello(FooReq.newBuilder().setFoo("foo").build()));
    }
}
