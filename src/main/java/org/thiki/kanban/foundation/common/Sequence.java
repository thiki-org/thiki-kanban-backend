package org.thiki.kanban.foundation.common;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("sequence")
public class Sequence {
    public String generate() {
        TimeBasedGenerator gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
        UUID uuid = gen.generate();
        return uuid.toString();
    }
}
