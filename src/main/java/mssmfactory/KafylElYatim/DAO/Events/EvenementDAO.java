package mssmfactory.KafylElYatim.DAO.Events;

import msjfxuicomponents.others.ICategorizerDeleter;
import mssmfactory.KafylElYatim.DAO.Others.ApplicationDAO;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.Evenement;

public abstract class EvenementDAO<T extends Evenement> extends ApplicationDAO<T> implements ICategorizerDeleter<T> {
    @Override
    public String onInsert(T entity) {
        return "INSERTION DE L'EVENEMENT " + entity;
    }

    @Override
    public String onDelete(T entity) {
        return "SUPPRESSION DE L'EVENEMENT " + entity;
    }

    @Override
    public String onUpdate(T entity) {
        return "MISE A JOUR DE L'EVENEMENT " + entity;
    }

    @Override
    public void deleteEntity(T entity){
        this.deleteArray(entity);
    }
}
