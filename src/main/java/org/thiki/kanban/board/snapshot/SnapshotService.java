package org.thiki.kanban.board.snapshot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.board.BoardResource;
import org.thiki.kanban.board.BoardsService;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.card.CardsResource;
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.procedure.Procedure;
import org.thiki.kanban.procedure.ProceduresResource;
import org.thiki.kanban.procedure.ProceduresService;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by xubitao on 05/26/16.
 */
@Service
public class SnapshotService {
    public static Logger logger = LoggerFactory.getLogger(SnapshotService.class);

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
    private SnapshotExecutor snapshotExecutor;

    @Cacheable(value = "board", key = "'board-snapshot'+#boardId+#userName")
    public Object loadSnapshotByBoard(String boardId, String userName) throws Exception {
        logger.info("load snapshot.");
        JSONObject boardJSON = loadBoard(boardId, userName);
        logger.info("snapshot loading completed.");
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
        Integer nThreads = cardsArray == null ? 0 : cardsArray.size();
        if (nThreads == 0) {
            return new JSONArray();
        }
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(nThreads);
        RequestAttributes currentRequestAttributes = RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(currentRequestAttributes, Boolean.TRUE);
        CompletionService<JSONObject> completionService = new ExecutorCompletionService<>(fixedThreadPool);
        for (int j = 0; j < cardsArray.size(); j++) {
            int finalJ = j;
            Callable<JSONObject> callable = new Callable<JSONObject>() {
                @Override
                public JSONObject call() throws Exception {
                    JSONObject cardJSON = cardsArray.getJSONObject(finalJ);
                    String cardId = cardJSON.getString("id");
                    snapshotExecutor.loadAssignments(boardId, userName, procedureId, cardJSON, cardId);
                    snapshotExecutor.loadAcceptanceCriterias(boardId, userName, procedureId, cardJSON, cardId);
                    snapshotExecutor.loadCardTags(boardId, userName, procedureId, cardJSON, cardId);
                    snapshotExecutor.loadComments(boardId, userName, procedureId, cardJSON, cardId);
                    return cardJSON;
                }
            };
            completionService.submit(callable);
        }
        for (int j = 0; j < cardsArray.size(); j++) {
            JSONObject card = completionService.take().get();
            newCardsArray.add(card);
        }
        return newCardsArray;
    }
}
