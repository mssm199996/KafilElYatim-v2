package mssmfactory.KafylElYatim.Utilities.Components;

import javafx.scene.control.TableRow;
import msjfxuicomponents.rows.ColoredRow;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin.Genre;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;

public class OrphelinTableRow extends TableRow<Orphelin> implements ColoredRow {

	@Override
	protected void updateItem(Orphelin orphelin, boolean empty) {
		super.updateItem(orphelin, empty);

		if (!empty) {
			Integer age = orphelin.getAge();
			Genre genre = orphelin.getGenre();

			Integer ageLimit = genre == Genre.Feminin ? ConstantsHolder.FEMALE_AGE_LIMIT
					: ConstantsHolder.MALE_AGE_LIMIT;

			int dAge = age - ageLimit;

			if (dAge >= 0) {

				if (orphelin.isAuthorized())
					this.addColorfullClass("blueTableRow", this);
				else
					this.addColorfullClass("redTableRow", this);
			}

			else
				this.addColorfullClass("greenTableRow", this);
		} else
			this.clearAll(this);
	}

	@Override
	public String[] getPossibleClasses() {
		return new String[] { "greenTableRow", "redTableRow", "blueTableRow" };
	}
}
