package org.thiki.kanban.foundation.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xubt on 03/02/2017.
 */
@Component
@ConfigurationProperties(prefix = "security")
public class ApplicationProperties {
    private List<String> whiteList;

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
}
