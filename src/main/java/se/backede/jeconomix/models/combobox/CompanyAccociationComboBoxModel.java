/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.combobox;

import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import se.backede.jeconomix.dto.CompanyAccociationDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanyAccociationComboBoxModel extends AbstractListModel implements ComboBoxModel {

    List<CompanyAccociationDto> accociatedCompanies;
    CompanyAccociationDto selection = null;

    public CompanyAccociationComboBoxModel(List<CompanyAccociationDto> accociatedCompanies) {
        this.accociatedCompanies = accociatedCompanies;
    }

    @Override
    public Object getElementAt(int index) {
        return accociatedCompanies.get(index);
    }

    @Override
    public int getSize() {
        return accociatedCompanies.size();
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selection = (CompanyAccociationDto) anItem;
        fireContentsChanged(this, -1, -1);
    } // item from the pull-down list

    // Methods implemented from the interface ComboBoxModel
    @Override
    public Object getSelectedItem() {
        return selection; // to add the selection to the combo box
    }

    public void addElement(CompanyAccociationDto accociatedCompany) {
        accociatedCompanies.add(accociatedCompany);
        fireContentsChanged(this, accociatedCompanies.size(), accociatedCompanies.size());
    }

}
