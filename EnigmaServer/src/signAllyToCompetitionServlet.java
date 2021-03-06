import EnigmaCompetition.Ally;
import EnigmaCompetition.Competition;
import EnigmaCracking.DM;
import Machine.MachineProxy;
import Utils.CookieUtils;
import agentUtilities.EnigmaDictionary;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//TODO:: change servlet to start competition if everyone is ready - so for all allies start DM.

@WebServlet("/signAllyToCompetition")
public class signAllyToCompetitionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = CookieUtils.getUserCookie(req.getCookies()).getValue();
        Ally ally = CookieUtils.getAllyFromUserName(username, getServletContext());
        setCompAndAlly(ally, Integer.parseInt(req.getParameter("selectCompetition")));
        resp.sendRedirect("/EnigmaServer/readyToCompetition.html");
    }

    private void setCompAndAlly(Ally ally, int index) {
        Competition competition = Utils.generalUtils.getCompetitionFromIndex(index, getServletContext());
        ally.setCompetition(competition);
        ally.setCompetitionObserver(competition);
        competition.addAlly(ally);
        setDmFromCompetition(ally.getDm(), competition);
    }


    private DM setDmFromCompetition(DM dm,Competition competition) {
        try {
            MachineProxy machineProxy = competition.getIntegrator().getMachine().clone();
            String stringToDecrypt = competition.getEncryptedString().toUpperCase();
            EnigmaDictionary enigmaDictionary = competition.getDictionary();
            int taskLevel = competition.getTaskLevel();
            dm.setMachine(machineProxy);
            dm.setEnigmaDictionary(enigmaDictionary);
            dm.setTaskLevel(taskLevel);
            dm.setEncryptedString(stringToDecrypt);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return dm;

    }

}

