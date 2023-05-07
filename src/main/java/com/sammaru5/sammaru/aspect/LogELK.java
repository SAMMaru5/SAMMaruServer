package com.sammaru5.sammaru.aspect;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LogELK {

    String timestamp;
    String clientIp;
    String clientUrl;
    String callFunction;
    String parameter;
    Double responseTime;

}