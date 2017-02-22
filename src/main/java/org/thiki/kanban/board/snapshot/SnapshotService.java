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
import org.thiki.kanban.stage.Stage;
import org.thiki.kanban.stage.StagesResource;
import org.thiki.kanban.stage.StagesService;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by xubitao on 05/26/16.
 */
@Service
public class SnapshotService {
    public static final int MAX_THREAD = 8;
    public static Logger logger = LoggerFactory.getLogger(SnapshotService.class);

    @Resource
    private BoardsService boardsService;
    @Resource
    private BoardResource boardResource;
    @Resource
    private StagesService stagesService;
    @Resource
    private StagesResource stagesResource;
    @Resource
    private CardsService cardsService;
    @Resource
    private CardsResource cardsResource;
    @Resource
    private SnapshotExecutor snapshotExecutor;

    @Cacheable(value = "board", key = "'board-snapshot'+#boardId+#userName+#viewType")
    public Object loadSnapshotByBoard(String boardId, String viewType, String userName) throws Exception {
        logger.info("load snapshot.");
        JSONObject boardJSON = loadBoard(boardId, viewType, userName);
        logger.info("snapshot loading completed.");
        return boardJSON;
    }

    private JSONObject loadBoard(String boardId, String viewType, String userName) throws Exception {
        logger.info("load board tags.");
        Board board = boardsService.findById(boardId);
        JSONObject boardJSON = (JSONObject) boardResource.toResource(board, userName);

        List<Stage> stageList = stagesService.loadByBoardId(boardId, viewType);
        JSONObject stagesJSON = (JSONObject) stagesResource.toResource(stageList, boardId, viewType, userName);
        JSONArray stagesArray = (JSONArray) stagesJSON.get("stages");
        JSONArray newStagesArray = loadStageCards(boardId, userName, stagesArray);
        stagesJSON.put("stages", newStagesArray);
        boardJSON.put("stages", stagesJSON);
        logger.info("board loading completed.");
        return boardJSON;
    }

    private JSONArray loadStageCards(String boardId, String userName, JSONArray stagesArray) throws Exception {
        logger.info("load cards tags.");
        JSONArray newStagesArray = new JSONArray();
        for (int i = 0; i < stagesArray.size(); i++) {
            JSONObject stageJSON = stagesArray.getJSONObject(i);
            String stageId = stageJSON.getString("id");
            List<Card> cardList = cardsService.findByStageId(stageId);


            JSONObject cardsJSON = buildCards(cardList, boardId, stageId, userName);
            stageJSON.put("cardsNode", cardsJSON);
            newStagesArray.add(stageJSON);
        }
        logger.info("card loading completed.");
        return newStagesArray;
    }

    private JSONObject buildCards(List<Card> cardList, String boardId, String stageId, String userName) throws Exception {
        JSONObject cardsJSON = (JSONObject) cardsResource.toResource(cardList, boardId, stageId, userName);
        JSONArray cardsArray = (JSONArray) cardsJSON.get("cards");
        JSONArray newCardsArray = loadOthersByCard(boardId, userName, stageId, cardsArray);
        cardsJSON.put("cards", newCardsArray);
        return cardsJSON;
    }

    private JSONArray loadOthersByCard(String boardId, String userName, String stageId, JSONArray cardsArray) throws Exception {
        JSONArray newCardsArray = new JSONArray();
        if (cardsArray == null || cardsArray.size() == 0) {
            return new JSONArray();
        }
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(MAX_THREAD);
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
                    snapshotExecutor.loadAssignments(boardId, userName, stageId, cardJSON, cardId);
                    snapshotExecutor.loadAcceptanceCriterias(boardId, userName, stageId, cardJSON, cardId);
                    snapshotExecutor.loadCardTags(boardId, userName, stageId, cardJSON, cardId);
                    snapshotExecutor.loadComments(boardId, userName, stageId, cardJSON, cardId);
                    List<Card> childCards = cardsService.findByParentId(cardId);
                    if (childCards.size() > 0) {
                        JSONObject childCardsJSON = buildCards(childCards, boardId, stageId, userName);
                        cardJSON.put("child", childCardsJSON);
                    }
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
