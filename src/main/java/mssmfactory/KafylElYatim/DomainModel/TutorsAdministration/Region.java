package mssmfactory.KafylElYatim.DomainModel.TutorsAdministration;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import msdatabaseutils.ICategorizer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Regions")
public class Region implements ICategorizer {
    private int id;
    private SimpleStringProperty nom = new SimpleStringProperty();
    private SimpleIntegerProperty code = new SimpleIntegerProperty();

    @Id
    @GeneratedValue(generator = "regionIdGenerator", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "regionIdGenerator", strategy = "increment")
    @Column(name = "ID_REGION")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NOM_REGION")
    public String getNom() {
        return nom.getValue();
    }

    public void setNom(String nom) {
        this.nom.setValue(nom);
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    @Basic
    @Column(name = "CODE_REGION")
    public Integer getCode() {
        return code.getValue();
    }

    public void setCode(Integer code) {
        this.code.setValue(code);
    }

    public SimpleIntegerProperty codeProperty() {
        return code;
    }

    @Basic
    @Transient
    public String getPrintableName() {
        return this.toString();
    }

    @Override
    public String toString() {
        return this.getCode() + " - " + this.getNom();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Region)
            return this.getId() == ((Region) o).getId();
        return false;
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
