package org.thiki.kanban.worktile;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.board.BoardsService;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.procedure.Procedure;
import org.thiki.kanban.procedure.ProceduresService;
import org.thiki.kanban.worktile.domains.Entry;
import org.thiki.kanban.worktile.domains.Task;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubt on 11/1/16.
 */
@Service
public class WorktileService {
    @Resource
    private BoardsService boardsService;
    @Resource
    private ProceduresService proceduresService;
    @Resource
    private CardsService cardsService;

    public Board importWorktileTasks(String userName, MultipartFile worktileTasksFile) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(worktileTasksFile.getBytes());
        String tasksJSONString = IOUtils.toString(stream, "UTF-8");
        List<Task> tasks = JSON.parseArray(tasksJSONString, Task.class);

        Board savedBoard = buildNewBoard(userName);
        List<Procedure> savedProcedures = importEntries(userName, tasks, savedBoard);
        importTasks(userName, tasks, savedProcedures);
        return savedBoard;
    }

    private void importTasks(String userName, List<Task> tasks, List<Procedure> savedProcedures) {
        for (Task task : tasks) {
            Card card = new Card();
            card.setSummary(task.getName());
            card.setAuthor(userName);
            savedProcedures.stream().filter(procedure -> procedure.getTitle().equals(task.getEntry().getName())).forEach(procedure -> {
                card.setProcedureId(procedure.getId());
                cardsService.create(userName, procedure.getId(), card);
            });
        }
    }

    private List<Procedure> importEntries(String userName, List<Task> tasks, Board savedBoard) {
        List<Procedure> savedProcedures = new ArrayList<>();
        for (Task task : tasks) {
            Entry entry = task.getEntry();
            Procedure procedure = new Procedure();
            procedure.setTitle(entry.getName());
            Procedure savedProcedure = proceduresService.create(userName, savedBoard.getId(), procedure);
            savedProcedures.add(savedProcedure);
        }
        return savedProcedures;
    }

    private Board buildNewBoard(String userName) {
        Board board = new Board();
        board.setAuthor(userName);
        board.setOwner(userName);
        board.setName("worktile");
        return boardsService.create(userName, board);
    }
}
