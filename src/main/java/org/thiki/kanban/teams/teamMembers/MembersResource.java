package org.thiki.kanban.teams.teamMembers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import freemarker.template.TemplateException;
import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.teams.invitation.InvitationController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 9/10/16.
 */
public class MembersResource extends RestResource {
    public MembersResource(String teamId, String userName, List<Member> members) throws TemplateException, IOException, MessagingException {

        JSONArray membersJSONArray = new JSONArray();
        for (Member member : members) {
            MemberResource memberResource = new MemberResource(member);
            JSONObject memberJSON = memberResource.getResource();
            membersJSONArray.add(memberJSON);
        }

        this.buildDataObject("members", membersJSONArray);

        Link invitationLink = linkTo(methodOn(InvitationController.class).invite(null, teamId, userName)).withRel("invitation");
        this.add(invitationLink);
    }
}
