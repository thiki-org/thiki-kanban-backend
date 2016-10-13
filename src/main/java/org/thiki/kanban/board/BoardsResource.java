package org.thiki.kanban.board;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.List;

/**
 * Created by xubitao on 05/26/16.
 */
public class BoardsResource extends RestResource {
    public BoardsResource(List<Board> boards) throws Exception {
        this.domainObject = boards;
        JSONArray boardsJSONArray = new JSONArray();
        for (Board board : boards) {
            BoardResource boardResource = new BoardResource(board, board.getAuthor());
            JSONObject boardJSON = boardResource.getResource();
            boardsJSONArray.add(boardJSON);
        }
        this.resourcesJSON = boardsJSONArray;
    }
}
