package org.thiki.kanban.login;

import freemarker.template.TemplateException;
import org.springframework.hateoas.Link;
import org.thiki.kanban.board.BoardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.teams.team.TeamsController;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 7/8/16.
 */
public class IdentificationResource extends RestResource {
    public IdentificationResource(Identification identification) throws TemplateException, IOException, MessagingException {
        super.domainObject = identification;
        Link boardsLink = linkTo(methodOn(BoardsController.class).loadByUserName(identification.getUserName())).withRel("boards");
        this.add(boardsLink);

        Link teamsLink = linkTo(methodOn(TeamsController.class).findByUserName(identification.getUserName())).withRel("teams");
        this.add(teamsLink);
    }
}
