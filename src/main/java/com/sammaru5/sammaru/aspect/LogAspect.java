package com.sammaru5.sammaru.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static java.lang.System.currentTimeMillis;


@Aspect
@Component
public class LogAspect {

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final Logger log = LoggerFactory.getLogger("ELK_LOGGER");
    private ObjectMapper mapper = new ObjectMapper();
    private String clientIp = "";
    private String clientUrl = "";

    @Around("bean(*Controller)")
    public Object controllerAroundLogging(ProceedingJoinPoint pjp) throws Throwable {
        String timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(currentTimeMillis()));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        this.clientIp = request.getRemoteAddr();
        this.clientUrl = request.getRequestURL().toString();

        String callFunction = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = pjp.proceed();

        stopWatch.stop();

        LogELK logelk = LogELK.builder()
                        .timestamp(timeStamp)
                        .clientIp(clientIp)
                        .clientUrl(clientUrl)
                        .callFunction(callFunction)
                        .parameter(mapper.writeValueAsString(request.getParameterMap()))
                        .responseTime(stopWatch.getTotalTimeSeconds()).build();

        log.info("{}", mapper.writeValueAsString(logelk));
        return result;
    }
}