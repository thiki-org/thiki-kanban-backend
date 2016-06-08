package org.thiki.kanban.auth;


import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by winie on 6/7/2016.
 */
@Service
public class ClientService {
    @Resource
    private ClientPersistence clientPersistence;

    private List<Client> clients;

    public List<Client> getClients() {
        return clients;
    }

    public Client findById(String id) {
        return clientPersistence.findById(id);
    }

    public Client findByClientId(String client_id) {
        return clientPersistence.findByClientId(client_id);
    }
}
