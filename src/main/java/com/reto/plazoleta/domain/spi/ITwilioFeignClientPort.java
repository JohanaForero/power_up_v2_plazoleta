package com.reto.plazoleta.domain.spi;

import com.reto.plazoleta.domain.model.SmsMessageModel;

public interface ITwilioFeignClientPort {

    void sendSmsMessage(SmsMessageModel smsMessageModel);
}
