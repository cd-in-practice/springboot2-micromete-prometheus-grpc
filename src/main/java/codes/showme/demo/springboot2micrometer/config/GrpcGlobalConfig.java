package codes.showme.demo.springboot2micrometer.config;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcGlobalConfig {

    @Bean
    @GRpcGlobalInterceptor
    public ServerInterceptor globalInterceptor(){
        return new ServerInterceptor(){
            @Override
            public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {

                ServerCall.Listener<ReqT> reqTListener = next.startCall(call, headers);

                return reqTListener;
            }
        };
    }

}
