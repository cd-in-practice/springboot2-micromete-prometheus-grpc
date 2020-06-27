package codes.showme.demo.springboot2micrometer.controller;

import codes.showme.demo.springboot2micrometer.GreeterGrpc;
import codes.showme.demo.springboot2micrometer.HelloReply;
import codes.showme.demo.springboot2micrometer.HelloRequest;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class HelloGrpcService extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        final HelloReply.Builder replyBuilder = HelloReply.newBuilder().setBlah(2);
        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
    }
}
