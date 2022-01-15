package mssmfactory.KafylElYatim.DAO.Events;

import mssmfactory.KafylElYatim.DAO.Others.ApplicationDAO;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.Action;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.Evenement;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionDAO extends ApplicationDAO<Action> {

    @SuppressWarnings("unchecked")
    public List<Action> findByTuteur(Tuteur tuteur) {
        String request = "FROM Action action " + "INNER JOIN FETCH action.evenement "
                + "INNER JOIN FETCH action.tuteur t WHERE t = :tuteur";

        Map<String, Object> params = new HashMap<>();
        params.put("tuteur", tuteur);

        return (List<Action>) (Object) this.getSessionFactoryHandler().select(request, params);
    }

    public List<Action> findByEvent(Evenement evenement) {
        String request = "FROM Action action " + "INNER JOIN FETCH action.evenement e "
                + "INNER JOIN FETCH action.tuteur t LEFT OUTER JOIN FETCH t.region WHERE e = :evenement";

        Map<String, Object> params = new HashMap<>();
        params.put("evenement", evenement);

        return (List<Action>) (Object) this.getSessionFactoryHandler().select(request, params);
    }

    @Override
    public String getGlobalSelectionQuery() {
        return "FROM Action action " + "INNER JOIN FETCH action.evenement " + "INNER JOIN FETCH action.tuteur";
    }

    @Override
    public String onInsert(Action entity) {
        return "INSERTION D'UNE NOUVELLE ACTION: " + entity;
    }

    @Override
    public String onDelete(Action entity) {
        return "INSERTION D'UNE ACTION: " + entity;
    }

    @Override
    public String onUpdate(Action entity) {
        return "MISE A JOUR D'UNE ACTION: " + entity;
    }

    @Override
    public String getNameOfEntity() {
        return "Action";
    }
}
