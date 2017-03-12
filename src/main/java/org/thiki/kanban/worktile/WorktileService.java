package org.thiki.kanban.worktile;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteria;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.board.BoardsService;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.foundation.common.date.DateService;
import org.thiki.kanban.foundation.common.date.DateStyle;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.stage.Stage;
import org.thiki.kanban.stage.StagesService;
import org.thiki.kanban.worktile.domains.Entry;
import org.thiki.kanban.worktile.domains.Task;
import org.thiki.kanban.worktile.domains.Todo;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xubt on 11/1/16.
 */
@Service
public class WorktileService {
    private static final String JSON_TYPE = "json";
    @Resource
    private BoardsService boardsService;
    @Resource
    private StagesService stagesService;
    @Resource
    private CardsService cardsService;
    @Resource
    private DateService dateService;
    @Resource
    private AcceptanceCriteriaService acceptanceCriteriaService;

    public Board importWorktileTasks(String projectId, String userName, MultipartFile worktileTasksFile) throws IOException {
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

        Board savedBoard = buildNewBoard(projectId, userName);
        List<Stage> savedStages = importEntries(userName, tasks, savedBoard);
        List<Card> savedCards = importTasks(userName, tasks, savedStages, savedBoard.getId());
        importTodos(userName, tasks, savedCards);
        return savedBoard;
    }

    private void importTodos(String userName, List<Task> tasks, List<Card> savedCards) {
        for (Task task : tasks) {
            List<Todo> todos = task.getTodos();
            for (Todo todo : todos) {
                AcceptanceCriteria acceptanceCriteria = new AcceptanceCriteria();
                acceptanceCriteria.setAuthor(userName);
                acceptanceCriteria.setSummary(todo.getName());
                acceptanceCriteria.setFinished(todo.getChecked() != 0);
                savedCards.stream()
                        .filter(card -> card.getSummary().equals(task.getName()))
                        .forEach(card -> acceptanceCriteriaService.addAcceptCriteria(userName, card.getId(), acceptanceCriteria));
            }
        }
    }

    private List<Card> importTasks(String userName, List<Task> tasks, List<Stage> savedStages, String boardId) {
        List<Card> savedCards = new ArrayList<>();
        for (Task task : tasks) {
            Card card = new Card();
            card.setSummary(task.getName());
            card.setAuthor(userName);
            savedStages.stream()
                    .filter(stage -> stage.getTitle().equals(task.getEntry().getName()))
                    .forEach(stage -> {
                        card.setStageId(stage.getId());
                        Card savedCard = cardsService.saveCard(userName, boardId, card);
                        savedCards.add(savedCard);
                    });
        }
        return savedCards;
    }

    private List<Stage> importEntries(String userName, List<Task> tasks, Board savedBoard) {
        List<Stage> savedStages = new ArrayList<>();
        for (Task task : tasks) {
            Entry entry = task.getEntry();
            if (isEntryAlreadySaved(savedStages, entry)) {
                continue;
            }
            Stage stage = new Stage();
            stage.setTitle(entry.getName());
            stage.setSortNumber(savedStages.size());
            Stage savedStage = stagesService.create(userName, savedBoard.getId(), stage);
            savedStages.add(savedStage);
        }
        return savedStages;
    }

    private boolean isEntryAlreadySaved(List<Stage> savedStages, Entry entry) {
        for (Stage stage : savedStages) {
            if (entry.getName().equals(stage.getTitle())) {
                return true;
            }
        }
        return false;
    }

    private Board buildNewBoard(String projectId, String userName) {
        Board board = new Board();
        board.setAuthor(userName);
        board.setProjectId(projectId);
        board.setOwner(userName);
        board.setName("worktile" + dateService.DateToString(new Date(), DateStyle.YYYY_MM_DD));
        return boardsService.create(userName, board, projectId);
    }
}
