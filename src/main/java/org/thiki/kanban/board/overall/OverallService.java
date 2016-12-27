package org.thiki.kanban.board.overall;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteria;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriasResource;
import org.thiki.kanban.assignment.Assignment;
import org.thiki.kanban.assignment.AssignmentService;
import org.thiki.kanban.assignment.AssignmentsResource;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.board.BoardResource;
import org.thiki.kanban.board.BoardsService;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.card.CardsResource;
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.cardTags.CardTag;
import org.thiki.kanban.cardTags.CardTagsResource;
import org.thiki.kanban.cardTags.CardTagsService;
import org.thiki.kanban.procedure.Procedure;
import org.thiki.kanban.procedure.ProceduresResource;
import org.thiki.kanban.procedure.ProceduresService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubitao on 05/26/16.
 */
@Service
public class OverallService {
    public static Logger logger = LoggerFactory.getLogger(OverallService.class);

    @Resource
    private BoardsService boardsService;
    @Resource
    private BoardResource boardResource;
    @Resource
    private ProceduresService proceduresService;
    @Resource
    private ProceduresResource proceduresResource;
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

    @Cacheable(value = "board", key = "'board-overall'+#boardId+#userName")
    public Object loadAllByBoard(String boardId, String userName) throws Exception {
        logger.info("load overall.");
        JSONObject boardJSON = loadBoard(boardId, userName);
        logger.info("overall loading completed.");
        return boardJSON;
    }

    private JSONObject loadBoard(String boardId, String userName) throws Exception {
        logger.info("load board tags.");
        Board board = boardsService.findById(boardId);
        JSONObject boardJSON = (JSONObject) boardResource.toResource(board, userName);

        List<Procedure> procedureList = proceduresService.loadByBoardId(boardId);
        JSONObject proceduresJSON = (JSONObject) proceduresResource.toResource(procedureList, boardId, userName);
        JSONArray proceduresArray = (JSONArray) proceduresJSON.get("procedures");
        JSONArray newProceduresArray = loadCards(boardId, userName, proceduresArray);
        proceduresJSON.put("procedures", newProceduresArray);
        boardJSON.put("procedures", proceduresJSON);
        logger.info("board loading completed.");
        return boardJSON;
    }

    private JSONArray loadCards(String boardId, String userName, JSONArray proceduresArray) throws Exception {
        logger.info("load cards tags.");
        JSONArray newProceduresArray = new JSONArray();
        for (int i = 0; i < proceduresArray.size(); i++) {
            JSONObject procedureJSON = proceduresArray.getJSONObject(i);
            String procedureId = procedureJSON.getString("id");
            List<Card> cardList = cardsService.findByProcedureId(procedureId);
            JSONObject cardsJSON = (JSONObject) cardsResource.toResource(cardList, boardId, procedureId, userName);
            JSONArray cardsArray = (JSONArray) cardsJSON.get("cards");

            JSONArray newCardsArray = loadOthersByCard(boardId, userName, procedureId, cardsArray);
            cardsJSON.put("cards", newCardsArray);
            procedureJSON.put("cards", cardsJSON);
            newProceduresArray.add(procedureJSON);
        }
        logger.info("card loading completed.");
        return newProceduresArray;
    }

    private JSONArray loadOthersByCard(String boardId, String userName, String procedureId, JSONArray cardsArray) throws Exception {
        JSONArray newCardsArray = new JSONArray();
        for (int j = 0; j < cardsArray.size(); j++) {
            JSONObject cardJSON = cardsArray.getJSONObject(j);
            String cardId = cardJSON.getString("id");
            loadComments(boardId, userName, procedureId, cardJSON, cardId);
            loadAcceptanceCriterias(boardId, userName, procedureId, cardJSON, cardId);
            loadCardTags(boardId, userName, procedureId, cardJSON, cardId);
            newCardsArray.add(cardJSON);
        }
        return newCardsArray;
    }

    private void loadCardTags(String boardId, String userName, String procedureId, JSONObject cardJSON, String cardId) throws Exception {
        logger.info("load card tags.");
        List<CardTag> stickCardTags = cardTagsService.loadTags(cardId);
        JSONObject tagsJSON = (JSONObject) cardTagsResource.toResource(stickCardTags, boardId, procedureId, cardId, userName);
        cardJSON.put("tags", tagsJSON);
        logger.info("card tags loading completed.");
    }

    private void loadAcceptanceCriterias(String boardId, String userName, String procedureId, JSONObject cardJSON, String cardId) throws Exception {
        logger.info("load acceptanceCriterias.");
        List<AcceptanceCriteria> acceptanceCriteriaList = acceptanceCriteriaService.loadAcceptanceCriteriasByCardId(cardId);
        JSONObject acceptanceCriteriasJSON = (JSONObject) acceptanceCriteriasResource.toResource(acceptanceCriteriaList, boardId, procedureId, cardId, userName);
        cardJSON.put("acceptanceCriterias", acceptanceCriteriasJSON);
        logger.info("acceptanceCriterias loading completed.");
    }

    private void loadComments(String boardId, String userName, String procedureId, JSONObject cardJSON, String cardId) throws Exception {
        logger.info("load assignments.");
        List<Assignment> assignmentList = assignmentService.findByCardId(cardId);
        JSONObject assignmentsJSON = (JSONObject) assignmentsResource.toResource(assignmentList, boardId, procedureId, cardId, userName);
        cardJSON.put("assignments", assignmentsJSON);
        logger.info("assignments loading completed.");
    }
}
