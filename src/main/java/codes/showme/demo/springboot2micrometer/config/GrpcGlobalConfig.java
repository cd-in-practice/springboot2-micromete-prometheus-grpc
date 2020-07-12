package codes.showme.demo.springboot2micrometer.config;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Configuration
public class GrpcGlobalConfig {

    @Bean
    @GRpcGlobalInterceptor
    public ServerInterceptor globalInterceptor() {
        return new ServerInterceptor() {
            @Override
            public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
                Timer timer = Metrics.timer(call.getMethodDescriptor().getFullMethodName(), Arrays.asList(Tag.of("env", "prod"), Tag.of("region", "us-east-1")));
                try {
                    ServerCall.Listener<ReqT> reqTListener;
                    long now = System.currentTimeMillis();
                    reqTListener = next.startCall(call, headers);
                    timer.record(System.currentTimeMillis() - now, MILLISECONDS);
                    return reqTListener;
                }catch (Exception e){

                    throw e;
                }

            }
        };
    }

}
