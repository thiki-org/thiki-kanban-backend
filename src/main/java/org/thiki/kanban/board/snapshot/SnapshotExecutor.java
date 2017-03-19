package org.thiki.kanban.board.snapshot;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteria;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriasResource;
import org.thiki.kanban.assignment.Assignment;
import org.thiki.kanban.assignment.AssignmentService;
import org.thiki.kanban.assignment.AssignmentsResource;
import org.thiki.kanban.cardTags.CardTag;
import org.thiki.kanban.cardTags.CardTagsResource;
import org.thiki.kanban.cardTags.CardTagsService;
import org.thiki.kanban.comment.Comment;
import org.thiki.kanban.comment.CommentService;
import org.thiki.kanban.comment.CommentsResource;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubt on 26/01/2017.
 */
@Service
public class SnapshotExecutor {
    public static Logger logger = LoggerFactory.getLogger(SnapshotService.class);
    @Resource
    private AssignmentService assignmentService;
    @Resource
    private AssignmentsResource assignmentsResource;
    @Resource
    private AcceptanceCriteriaService acceptanceCriteriaService;
    @Resource
    private AcceptanceCriteriasResource acceptanceCriteriasResource;
    @Resource
    private CardTagsService cardTagsService;
    @Resource
    private CardTagsResource cardTagsResource;
    @Resource
    private CommentService commentService;
    @Resource
    private CommentsResource commentsResource;

    public synchronized void loadCardTags(String boardId, String userName, String stageId, JSONObject cardJSON, String cardId) throws Exception {
        logger.info("load card tags.");
        List<CardTag> stickCardTags = cardTagsService.loadTags(cardId, boardId);
        JSONObject tagsJSON = (JSONObject) cardTagsResource.toResource(stickCardTags, boardId, stageId, cardId, userName);
        cardJSON.put("tagsNode", tagsJSON);
        logger.info("card tags loading completed.");
    }

    public synchronized void loadAcceptanceCriterias(String boardId, String userName, String stageId, JSONObject cardJSON, String cardId) throws Exception {
        logger.info("load acceptanceCriterias.");
        List<AcceptanceCriteria> acceptanceCriteriaList = acceptanceCriteriaService.loadAcceptanceCriteriasByCardId(cardId);
        JSONObject acceptanceCriteriasJSON = (JSONObject) acceptanceCriteriasResource.toResource(acceptanceCriteriaList, boardId, stageId, cardId, userName);
        cardJSON.put("acceptanceCriteriasNode", acceptanceCriteriasJSON);
        logger.info("acceptanceCriterias loading completed.");
    }

    public synchronized void loadAssignments(String boardId, String userName, String stageId, JSONObject cardJSON, String cardId) throws Exception {
        logger.info("load assignments.");
        List<Assignment> assignmentList = assignmentService.findByCardId(cardId);
        JSONObject assignmentsJSON = (JSONObject) assignmentsResource.toResource(assignmentList, boardId, stageId, cardId, userName);
        cardJSON.put("assignmentsNode", assignmentsJSON);
        logger.info("assignments loading completed.");
    }

    public synchronized void loadComments(String boardId, String userName, String stageId, JSONObject cardJSON, String cardId) throws Exception {
        logger.info("load comments.");
        List<Comment> commentList = commentService.loadCommentsByCardId(cardId);
        JSONObject commentsJSON = (JSONObject) commentsResource.toResource(commentList, boardId, stageId, cardId, userName);
        cardJSON.put("commentsNode", commentsJSON);
        logger.info("comments loading completed.");
    }
}
