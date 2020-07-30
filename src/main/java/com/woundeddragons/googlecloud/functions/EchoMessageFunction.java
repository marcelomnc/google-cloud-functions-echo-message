package com.woundeddragons.googlecloud.functions;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoMessageFunction implements HttpFunction {
    private static final Logger logger = Logger.getLogger(EchoMessageFunction.class.getName());

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        BufferedWriter writer = httpResponse.getWriter();
        httpRequest.getFirstQueryParameter("message")
                .ifPresentOrElse(message -> {
                    try {
                        writer.write("Echo: '" + message + "'");
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, e.getMessage(), e);
                    }
                }, () -> {
                    try {
                        writer.write("Parameter 'message' not sent !");
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, e.getMessage(), e);
                    }
                });
        writer.close();
    }
}