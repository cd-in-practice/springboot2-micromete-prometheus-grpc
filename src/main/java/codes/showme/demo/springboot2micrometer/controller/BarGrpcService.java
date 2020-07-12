package codes.showme.demo.springboot2micrometer.controller;

import codes.showme.demo.springboot2micrometer.BarGrpc;
import codes.showme.demo.springboot2micrometer.BarReq;
import codes.showme.demo.springboot2micrometer.BarResp;
import io.grpc.stub.StreamObserver;
import io.micrometer.core.annotation.Timed;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class BarGrpcService extends BarGrpc.BarImplBase {

    @Override
    public void sayBye(BarReq request, StreamObserver<BarResp> responseObserver) {
        final BarResp.Builder replyBuilder = BarResp.newBuilder().setBlah(0);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
    }


}
