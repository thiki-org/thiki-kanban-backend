package org.thiki.kanban.worktile;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.board.BoardsService;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.foundation.exception.BusinessException;
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
    public static final String JSON_TYPE = "json";
    @Resource
    private BoardsService boardsService;
    @Resource
    private ProceduresService proceduresService;
    @Resource
    private CardsService cardsService;

    public Board importWorktileTasks(String userName, MultipartFile worktileTasksFile) throws IOException {
        String tasksJSONString;
        if (null == worktileTasksFile) {
            throw new BusinessException(WorktileCodes.FILE_IS_UN_UPLOAD);
        }
        String[] fileNameInformation = worktileTasksFile.getOriginalFilename().split("\\.");
        if (fileNameInformation.length < 2) {
            throw new BusinessException(WorktileCodes.FILE_NAME_INVALID);
        }
        String fileExtensionName = fileNameInformation[1];
        if (!JSON_TYPE.equals(fileExtensionName)) {
            throw new BusinessException(WorktileCodes.FILE_TYPE_INVALID);
        }
        try {
            worktileTasksFile.getOriginalFilename();
            ByteArrayInputStream stream = new ByteArrayInputStream(worktileTasksFile.getBytes());
            tasksJSONString = IOUtils.toString(stream, "UTF-8");
        } catch (Exception e) {
            throw new BusinessException(WorktileCodes.FILE_CONTENT_READ_ERROR);
        }
        if (tasksJSONString == null || "".equals(tasksJSONString)) {
            throw new BusinessException(WorktileCodes.FILE_IS_EMPTY);
        }
        try {
            JSON.parseArray(tasksJSONString);
        } catch (Exception e) {
            throw new BusinessException(WorktileCodes.FILE_CONTENT_FORMAT_INVALID);
        }
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
