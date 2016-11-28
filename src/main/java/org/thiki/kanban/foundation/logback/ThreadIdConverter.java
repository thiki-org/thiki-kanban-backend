package org.thiki.kanban.foundation.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.thiki.kanban.foundation.common.SequenceNumber;

/**
 * Created by xubt on 28/11/2016.
 */
public class ThreadIdConverter extends ClassicConverter {
    private static final ThreadLocal<String> threadId = ThreadLocal.withInitial(() -> {
        SequenceNumber sequenceNumber = new SequenceNumber();
        return sequenceNumber.generate();
    });

    @Override
    public String convert(ILoggingEvent event) {
        return threadId.get();
    }
}
