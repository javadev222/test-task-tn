package com.test_task_tn.business.domain;

import lombok.Value;

@Value
public class StatusResponse {
    Long id;
    boolean oldStatus;
    boolean currentStatus;
}
