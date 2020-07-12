package codes.showme.demo.springboot2micrometer.controller;

import codes.showme.demo.springboot2micrometer.FooGrpc;
import codes.showme.demo.springboot2micrometer.FooReq;
import codes.showme.demo.springboot2micrometer.FooResp;
import io.grpc.stub.StreamObserver;
import io.micrometer.core.instrument.MeterRegistry;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class FooGrpcService extends FooGrpc.FooImplBase {

    private final MeterRegistry registry;

    public FooGrpcService(MeterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void sayHello(FooReq request, StreamObserver<FooResp> responseObserver) {
        final FooResp.Builder replyBuilder = FooResp.newBuilder().setBlah(0);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
    }


}
