package org.lobobrowser.html.jsimpl;

import java.util.Date;
import java.util.logging.Logger;

import org.lobobrowser.html.js.Console;

public class ConsoleImpl implements Console {
    
    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(ConsoleImpl.class.getName());
    
    private Date time;
    private String strTime;

    @Override
    public void log(Object obj) {
        logger.severe(obj.toString());
    }

    @Override
    public void debug(Object obj) {
        logger.severe(obj.toString());

    }

    @Override
    public void info(Object obj) {
        logger.info(obj.toString());

    }

    @Override
    public void warn(Object obj) {
        logger.warning(obj.toString());

    }

    @Override
    public void error(Object obj) {
        logger.severe(obj.toString());

    }

    @Override
    public void time(String name) {
        time = new Date();
        logger.info(name + ": timer started");
        strTime = name;

    }

    @Override
    public void timeEnd(String name) {
        if (name.equals(strTime)) {
            Date date = new Date();
            Date result = new Date(time.getTime() - date.getTime());
            logger.info(strTime + ": " + result);
        }

    }
}
