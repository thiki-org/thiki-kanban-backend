package org.thiki.kanban.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.board.snapshot.BoardsSnapshotController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.page.PagesController;
import org.thiki.kanban.projects.project.ProjectsController;
import org.thiki.kanban.projects.members.MembersController;
import org.thiki.kanban.sprint.SprintController;
import org.thiki.kanban.stage.StageCodes;
import org.thiki.kanban.stage.StagesController;
import org.thiki.kanban.tag.TagsController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 05/26/16.
 */
@Service
public class BoardResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(BoardResource.class);
    @Resource
    private TLink tlink;

    @Cacheable(value = "board", key = "#userName+'boards-all'")
    public Object toResource(String userName) throws Exception {
        logger.info("build board resource.userName:{}", userName);
        BoardResource boardResource = new BoardResource();
        Link allLink = linkTo(methodOn(BoardsController.class).loadByUserName(userName)).withRel("all");
        boardResource.add(tlink.from(allLink));
        logger.info("board resource building complete.userName:{}", userName);
        return boardResource.getResource();
    }

    @Cacheable(value = "board", key = "#userName+'boards'+#board.id")
    public Object toResource(Board board, String userName) throws Exception {
        logger.info("build board resource.board:{},userName:{}", board, userName);
        BoardResource boardResource = new BoardResource();
        boardResource.domainObject = board;
        this.domainObject = board;
        if (board != null) {
            Link selfLink = linkTo(methodOn(BoardsController.class).findById(board.getId(), userName)).withSelfRel();
            boardResource.add(tlink.from(selfLink).build());

            Link stagesLink = linkTo(methodOn(StagesController.class).loadAll(board.getId(), null, userName)).withRel("stages");
            boardResource.add(tlink.from(stagesLink).build());

            Link sprintViewLink = linkTo(methodOn(StagesController.class).loadAll(board.getId(), StageCodes.VIEW_TYPE_SPRINT, userName)).withRel(StageCodes.VIEW_TYPE_SPRINT);
            boardResource.add(tlink.from(sprintViewLink).build());

            Link fullViewLink = linkTo(methodOn(StagesController.class).loadAll(board.getId(), StageCodes.VIEW_TYPE_FULL_VIEW, userName)).withRel(StageCodes.VIEW_TYPE_FULL_VIEW);
            boardResource.add(tlink.from(fullViewLink).build());

            Link roadMapViewLink = linkTo(methodOn(StagesController.class).loadAll(board.getId(), StageCodes.VIEW_TYPE_ROAD_MAP, userName)).withRel(StageCodes.VIEW_TYPE_ROAD_MAP);
            boardResource.add(tlink.from(roadMapViewLink).build());

            Link archiveViewLink = linkTo(methodOn(StagesController.class).loadAll(board.getId(), StageCodes.VIEW_TYPE_ARCHIVE, userName)).withRel(StageCodes.VIEW_TYPE_ARCHIVE);
            boardResource.add(tlink.from(archiveViewLink).build());

            if (board.getProjectId() != null) {
                Link projectLink = linkTo(methodOn(ProjectsController.class).findById(board.getProjectId(), userName)).withRel("project");
                boardResource.add(tlink.from(projectLink).build());
            }

            Link tagsLink = linkTo(methodOn(TagsController.class).loadTagsByBoard(board.getId(), userName)).withRel("tags");
            boardResource.add(tlink.from(tagsLink).build());

            Link snapshotLink = linkTo(methodOn(BoardsSnapshotController.class).load(board.getId(), null, userName)).withRel("snapshot");
            boardResource.add(tlink.from(snapshotLink).build(userName));

            Link sprintViewSnapShotLink = linkTo(methodOn(BoardsSnapshotController.class).load(board.getId(), StageCodes.VIEW_TYPE_SPRINT, userName)).withRel(StageCodes.VIEW_TYPE_SPRINT + "Snapshot");
            boardResource.add(tlink.from(sprintViewSnapShotLink).build(userName));

            Link fullViewSnapShotLink = linkTo(methodOn(BoardsSnapshotController.class).load(board.getId(), StageCodes.VIEW_TYPE_FULL_VIEW, userName)).withRel(StageCodes.VIEW_TYPE_FULL_VIEW + "Snapshot");
            boardResource.add(tlink.from(fullViewSnapShotLink).build(userName));

            Link roadMapViewSnapShotLink = linkTo(methodOn(BoardsSnapshotController.class).load(board.getId(), StageCodes.VIEW_TYPE_ROAD_MAP, userName)).withRel(StageCodes.VIEW_TYPE_ROAD_MAP + "Snapshot");
            boardResource.add(tlink.from(roadMapViewSnapShotLink).build(userName));

            Link archiveViewSnapShotLink = linkTo(methodOn(BoardsSnapshotController.class).load(board.getId(), StageCodes.VIEW_TYPE_ARCHIVE, userName)).withRel(StageCodes.VIEW_TYPE_ARCHIVE + "Snapshot");
            boardResource.add(tlink.from(archiveViewSnapShotLink).build(userName));

            Link sprintsLink = linkTo(methodOn(SprintController.class).createSprint(null, board.getId(), userName)).withRel("sprints");
            boardResource.add(tlink.from(sprintsLink).build(userName));

            Link activeSprintLink = linkTo(methodOn(SprintController.class).loadActiveSprint(board.getId(), userName)).withRel("activeSprint");
            boardResource.add(tlink.from(activeSprintLink).build(userName));

            Link pagesLink = linkTo(methodOn(PagesController.class).findByBoard(board.getId(), userName)).withRel("pages");
            boardResource.add(tlink.from(pagesLink).build(userName));

            if (board.getProjectId() != null) {
                Link membersLink = linkTo(methodOn(MembersController.class).loadMembersByProjectId(board.getProjectId(), userName)).withRel("members");
                boardResource.add(tlink.from(membersLink).build(userName));
            }
        }
        Link allLink = linkTo(methodOn(BoardsController.class).loadByUserName(userName)).withRel("all");
        boardResource.add(tlink.from(allLink).build());
        logger.info("board resource building complete.board:{},userName:{}", board, userName);
        return boardResource.getResource();
    }
}
