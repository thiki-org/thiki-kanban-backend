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
import org.thiki.kanban.card.CardsResource;
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.cardTags.CardTag;
import org.thiki.kanban.cardTags.CardTagsResource;
import org.thiki.kanban.cardTags.CardTagsService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubt on 26/01/2017.
 */
@Service
public class SnapshotExecutor {
    public static Logger logger = LoggerFactory.getLogger(SnapshotService.class);
    @Resource
    private CardsService cardsService;
    @Resource
    private CardsResource cardsResource;
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

    public synchronized void loadCardTags(String boardId, String userName, String procedureId, JSONObject cardJSON, String cardId) throws Exception {
        logger.info("load card tags.");
        List<CardTag> stickCardTags = cardTagsService.loadTags(cardId, boardId, procedureId);
        JSONObject tagsJSON = (JSONObject) cardTagsResource.toResource(stickCardTags, boardId, procedureId, cardId, userName);
        cardJSON.put("tags", tagsJSON);
        logger.info("card tags loading completed.");
    }

    public synchronized void loadAcceptanceCriterias(String boardId, String userName, String procedureId, JSONObject cardJSON, String cardId) throws Exception {
        logger.info("load acceptanceCriterias.");
        List<AcceptanceCriteria> acceptanceCriteriaList = acceptanceCriteriaService.loadAcceptanceCriteriasByCardId(cardId);
        JSONObject acceptanceCriteriasJSON = (JSONObject) acceptanceCriteriasResource.toResource(acceptanceCriteriaList, boardId, procedureId, cardId, userName);
        cardJSON.put("acceptanceCriterias", acceptanceCriteriasJSON);
        logger.info("acceptanceCriterias loading completed.");
    }

    public synchronized void loadComments(String boardId, String userName, String procedureId, JSONObject cardJSON, String cardId) throws Exception {
        logger.info("load assignments.");
        List<Assignment> assignmentList = assignmentService.findByCardId(cardId);
        JSONObject assignmentsJSON = (JSONObject) assignmentsResource.toResource(assignmentList, boardId, procedureId, cardId, userName);
        cardJSON.put("assignments", assignmentsJSON);
        logger.info("assignments loading completed.");
    }
}
