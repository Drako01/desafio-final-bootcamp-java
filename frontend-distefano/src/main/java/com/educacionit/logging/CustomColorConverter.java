package com.educacionit.logging;



import ch.qos.logback.classic.Level;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;


@SuppressWarnings("rawtypes")
public class CustomColorConverter extends ForegroundCompositeConverterBase {
    
    @Override
    protected String getForegroundColorCode(Object event) {
        Level level = ((ch.qos.logback.classic.spi.ILoggingEvent) event).getLevel();
        switch (level.toInt()) {
            case Level.ERROR_INT:
                return "31"; // Red
            case Level.WARN_INT:
                return "33"; // Yellow
            case Level.INFO_INT:
                return "36"; // Cyan
            case Level.DEBUG_INT:
                return "32"; // Green
            default:
                return "0"; // Default (no color)
        }
    }
}