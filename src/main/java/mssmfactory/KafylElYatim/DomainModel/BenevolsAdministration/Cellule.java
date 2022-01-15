package mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration;

import javafx.beans.property.SimpleStringProperty;
import msdatabaseutils.ICategorizer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Cellules")
public class Cellule implements ICategorizer {
    private int id;
    private SimpleStringProperty nom = new SimpleStringProperty();

    @Id
    @Column(name = "ID_CELLULE")
    @GeneratedValue(generator = "idCelluleGenerator", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "idCelluleGenerator", strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NOM_CELLULE")
    public String getNom() {
        return nom.getValue();
    }

    public void setNom(String nom) {
        this.nom.setValue(nom);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Cellule)
            return this.getId() == (((Cellule) o).getId());

        return false;
    }

    @Override
    public String toString() {
        return this.getNom();
    }

    @Override
    public String getDesignation() {
        return this.getNom();
    }

    @Override
    public void setDesignation(String designation) {
        this.setNom(designation);
    }

    @Override
    public SimpleStringProperty designationProperty() {
        return this.nom;
    }
}
