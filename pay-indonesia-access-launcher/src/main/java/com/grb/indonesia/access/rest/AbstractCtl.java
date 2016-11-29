package com.grb.indonesia.access.rest;

        import org.apache.logging.log4j.LogManager;
        import org.apache.logging.log4j.Logger;

public abstract class AbstractCtl{

    private Logger logger;

    public Logger getLogger() {
        this.logger = LogManager.getLogger(this);
        return logger;
    }
}