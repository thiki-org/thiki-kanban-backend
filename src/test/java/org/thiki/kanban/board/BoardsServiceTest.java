package org.thiki.kanban.board;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.thiki.kanban.teams.team.Team;
import org.thiki.kanban.teams.teamMembers.TeamMembersService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by xubt on 03/11/2016.
 */
public class BoardsServiceTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    private BoardsPersistence boardsPersistence;
    @Mock
    private TeamMembersService teamMembersService;

    @InjectMocks
    private BoardsService boardsService;

    private String userName = "someone";

    @Test
    public void loadAllBoards() {
        Board expectedPersonalBoard = new Board();
        List<Board> expectedPersonalBoards = new ArrayList<>();
        expectedPersonalBoards.add(expectedPersonalBoard);
        when(boardsPersistence.findPersonalBoards(userName)).thenReturn(new ArrayList<>(expectedPersonalBoards));

        Team expectedTeam = new Team();
        List<Team> expectedTeams = new ArrayList<>();
        expectedTeams.add(expectedTeam);
        when(teamMembersService.loadTeamsByUserName(userName)).thenReturn(expectedTeams);


        Board expectedTeamBoard = new Board();
        List<Board> expectedTeamsBoards = new ArrayList<>();
        expectedTeamsBoards.add(expectedTeamBoard);
        when(boardsPersistence.findTeamsBoards(any())).thenReturn(expectedTeamsBoards);

        List<Board> actualBoards = boardsService.loadBoards(userName);
        assertEquals(2, actualBoards.size());
    }
}
