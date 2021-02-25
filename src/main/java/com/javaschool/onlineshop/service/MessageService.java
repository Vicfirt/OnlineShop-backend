package com.javaschool.onlineshop.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface MessageService {

    void sendMessage(String message) throws IOException, TimeoutException;
}
