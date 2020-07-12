package codes.showme.demo.springboot2micrometer.config;

import io.grpc.*;
import io.micrometer.core.instrument.*;
import io.prometheus.client.Gauge;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Configuration
public class GrpcGlobalConfig {

    private final MeterRegistry meterRegistry;

    @Value(value = "${spring.application.name}")
    private String applicationName;


    public GrpcGlobalConfig(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    private String getGaugeName() {
        return applicationName + "_gauge";
    }

    @Bean
    @GRpcGlobalInterceptor
    public ServerInterceptor globalInterceptor() {
        return new ServerInterceptor() {
            @Override
            public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
                MethodDescriptor<ReqT, RespT> methodDescriptor = call.getMethodDescriptor();
                Timer timer = Metrics.timer(applicationName + ".grpc.request", Arrays.asList(
                        Tag.of(methodDescriptor.getServiceName(), methodDescriptor.getFullMethodName())));
                long now = System.currentTimeMillis();
                try {
                    ServerCall.Listener<ReqT> reqTListener;
                    reqTListener = next.startCall(call, headers);
                    timer.record(System.currentTimeMillis() - now, MILLISECONDS);
                    Counter counter = Metrics.counter(applicationName + ".grpc.request",
                            Arrays.asList(
                                    Tag.of(methodDescriptor.getServiceName(), methodDescriptor.getFullMethodName()), Tag.of("state","SUCCESS")));
                    counter.increment();
                    return reqTListener;
                } catch (Exception e) {
                    Counter counter = Metrics.counter(applicationName + ".grpc.request",
                            Arrays.asList(
                                    Tag.of(methodDescriptor.getServiceName(), methodDescriptor.getFullMethodName()), Tag.of("state","ERROR")));
                    counter.increment();
                    timer.record(System.currentTimeMillis() - now, MILLISECONDS);
                    throw e;
                }


            }
        };
    }



}
