package org.loboevolution.html.node.js.console;

/**
 * Provides access to the browser's debugging console (e.g. the Web Console in Firefox). The specifics of how it works varies from browser to browser, but there is a de facto set of features that are typically provided.
 */
public interface Console {
   
    void clear();

    void count(String label);

    void count();

    void debug(Object message);

    void debug(int message);

    void debug(double message);

    void debug(boolean message);

    void debug();

    void dir();

    void error(Object message);

    void error(int message);

    void error(double message);

    void error(boolean message);

    void error();
    void exception(Object message);

    void exception();

    void group(String groupTitle);

    void group();

    void groupCollapsed(String groupTitle);

    void groupCollapsed();

    void groupEnd();

    void info(Object message);

    void info(int message);

    void info(double message);

    void info(boolean message);

    void info();

    void log(Object message);

    void log(int message);

    void log(double message);

    void log(boolean message);

    void log();

    void markTimeline(String label);

    void markTimeline();

    void profile(String reportName);

    void profile();

    void profileEnd(String reportName);

    void profileEnd();

    void time(String label);

    void time();

    void timeEnd(String label);

    void timeEnd();

    void timeStamp(String label);

    void timeStamp();

    void timeline(String label);

    void timeline();

    void timelineEnd(String label);

    void timelineEnd();

    void trace(Object message);

    void trace(int message);

    void trace(double message);

    void trace(boolean message);

    void trace();

    void warn(Object message);

    void warn(int message);

    void warn(double message);

    void warn(boolean message);

    void warn();

}
