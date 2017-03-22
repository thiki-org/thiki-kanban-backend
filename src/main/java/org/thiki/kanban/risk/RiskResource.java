package org.thiki.kanban.risk;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by cain on 2017/2/28.
 */
@Service
public class RiskResource extends RestResource {

    @Resource
    private TLink tlink;


    @Cacheable(value = "risk", key = "'risk'+risk.id+#boardId+#stageId+#cardId+#userName")
    public Object toResource(Risk risk, String boardId, String stageId, String cardId, String userName) throws Exception {
        RiskResource riskResource = new RiskResource();
        riskResource.domainObject = risk;
        if (risk != null) {
            Link selfLink = linkTo(methodOn(RiskController.class).findById(boardId, stageId, cardId, risk.getId(), userName)).withSelfRel();
            riskResource.add(tlink.from(selfLink).build(userName));

            Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, stageId, cardId, userName)).withRel("card");
            riskResource.add(tlink.from(cardLink).build(userName));
        }
        return riskResource.getResource();
    }

    @Cacheable(value = "risk", key = "'risk'+risk.id+#boardId+#stageId+#cardId+#userName")
    public Object toResource(String boardId, String stageId, String cardId, String userName) throws Exception {

        RiskResource riskResource = new RiskResource();

        Link risksLink = linkTo(methodOn(RiskController.class).loadCardRisks(userName, boardId, stageId, cardId)).withRel("risks");
        riskResource.add(tlink.from(risksLink).build(userName));

        return riskResource.getResource();

    }


}
