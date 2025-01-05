package dev.mf1.tinyradar.gui;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("", e);
    }

}
