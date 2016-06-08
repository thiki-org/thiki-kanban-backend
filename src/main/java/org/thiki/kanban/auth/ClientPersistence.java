package org.thiki.kanban.auth;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientPersistence {
    Integer create(Client clients);

    Client findById(@Param("id") String id);
    Client findByClientId(@Param("client_id") String client_id);
    List<Client> loadAll();

    Integer update(Client clients);

    Integer deleteById(@Param("id") String id);
}
