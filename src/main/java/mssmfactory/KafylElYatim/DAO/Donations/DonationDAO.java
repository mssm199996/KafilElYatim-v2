package mssmfactory.KafylElYatim.DAO.Donations;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import msjfxuicomponents.others.IDescriptorUpdator;
import mssmfactory.KafylElYatim.DAO.Others.ApplicationDAO;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donateur;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donation;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donation.DonationType;

public class DonationDAO extends ApplicationDAO<Donation> implements IDescriptorUpdator<Donation> {

	@SuppressWarnings("unchecked")
	public List<Donation> getSpecifiedDonations(Integer donationId, DonationType donationType, LocalDate startDate,
			LocalDate endDate, Donateur donateur) {
		if (donationId == null && donationType == null && startDate == null && endDate == null && donateur == null)
			return this.getAll();

		if (donationId != null)
			return (List<Donation>) (Object) this.getSessionFactoryHandler().select(
					"FROM Donation donation LEFT OUTER JOIN FETCH donation.donator WHERE donation.id = ?", donationId);

		String request = "FROM Donation donation LEFT OUTER JOIN FETCH donation.donator WHERE (";
		LinkedList<Object> params = new LinkedList<Object>();

		if (donationType != null) {
			request += "(donation.type = ?) and ";
			params.add(donationType);
		}

		if (donateur != null) {
			request += "(donation.donator = ?) and ";

			params.add(donateur);
		}

		if (startDate != null && endDate != null) {
			request += " (donation.date BETWEEN ? and ?) and ";

			params.add(startDate);
			params.add(endDate);
		}

		else if (startDate != null) {
			request += "(donation.date >= ?) and ";

			params.add(startDate);
		}

		else if (endDate != null) {
			request += "(donation.date <= ?) and ";

			params.add(endDate);
		}

		request = request.substring(0, request.length() - 4);
		request += ")";
		
		return (List<Donation>) (Object) this.getSessionFactoryHandler().select(request, params);
	}

	@Override
	public String getGlobalSelectionQuery() {
		return "FROM Donation donation INNER JOIN FETCH donation.donator";
	}

	@Override
	public String onInsert(Donation entity) {
		return "INSERTION D'UNE DONATION DE " + entity.getValeur() + " FAITE PAR " + entity.getDonator();
	}

	@Override
	public String onDelete(Donation entity) {
		return "SUPPRESSION D'UNE DONATION DE " + entity.getValeur() + " FAITE PAR " + entity.getDonator();
	}

	@Override
	public String onUpdate(Donation entity) {
		return "MISE A JOUR D'UNE DONATION DE " + entity.getValeur() + " FAITE PAR " + entity.getDonator();
	}

	@Override
	public String getNameOfEntity() {
		return "Donation";
	}

	@Override
	public void update(Donation descriptor) {
		this.updateEntity(descriptor);
	}
}
