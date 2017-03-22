package org.thiki.kanban.risk;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by wisdom on 3/19/17.
 */
@Service
public class RiskResources extends RestResource {

    @Resource
    private TLink tlink;
    @Resource
    private RiskResource riskResourceService;


    @Cacheable(value = "risk", key = "'risks'+#boardId+#stageId+#cardId+#userName")
    public Object toResource(List<Risk> risks, String boardId, String stageId, String cardId, String userName) throws Exception {
        RiskResources risksResources = new RiskResources();

        List<Object> riskResourceList = new ArrayList<>();

        for (Risk risk : risks) {
            Object riskResource = riskResourceService.toResource(risk, boardId, stageId, cardId, userName);
            riskResourceList.add(riskResource);
        }
        risksResources.buildDataObject("risks", riskResourceList);

        Link selfLink = linkTo(methodOn(RiskController.class).loadCardRisks(userName, boardId, stageId, cardId)).withSelfRel();
        risksResources.add(tlink.from(selfLink).build(userName));

        Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, stageId, cardId, userName)).withRel("card");
        risksResources.add(tlink.from(cardLink).build(userName));

        return risksResources.getResource();
    }

}
