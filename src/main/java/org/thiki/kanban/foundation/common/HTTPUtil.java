package org.thiki.kanban.foundation.common;

import org.thiki.kanban.foundation.security.Constants;

/**
 * Created by xubt on 21/11/2016.
 */
public class HTTPUtil {
    public static boolean isLocalRequest(String remoteAddr) {
        return Constants.LOCAL_ADDRESS.equals(remoteAddr);
    }
}
