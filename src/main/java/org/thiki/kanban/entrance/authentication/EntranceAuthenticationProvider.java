package org.thiki.kanban.entrance.authentication;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.hateoas.Action;
import org.thiki.kanban.foundation.security.authentication.AuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubt on 10/24/2016.
 */
@Service("entranceAuthProvider")
public class EntranceAuthenticationProvider extends AuthenticationProvider {
    @Override
    public String getPathTemplate() {
        return "/entrance";
    }

    @Override
    public boolean authGet() {
        return true;
    }

    @Override
    public List<Action> authenticate() {
        Action getAction = new Action();
        getAction.setMethod("read", authGet());
        List<Action> actions = new ArrayList<>();
        actions.add(getAction);
        return actions;
    }
}
