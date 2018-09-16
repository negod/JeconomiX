/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.basic.component;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import lombok.Getter;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 * @param <T>
 * @param <E>
 */
@Getter
public abstract class GenericComboBoxModel<T, E extends Enum> extends AbstractListModel implements ComboBoxModel {

    private static final long serialVersionUID = 1L;

    List<T> items = new ArrayList<>();
    T selection = null;

    public GenericComboBoxModel() {
        getAllData();
    }

    public GenericComboBoxModel(E filter) {
        getAllData(filter);
    }

    @Override
    public Object getElementAt(int index) {
        return items.get(index);
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selection = (T) anItem;
        fireContentsChanged(this, -1, -1);
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }

    public void reInitModelData(List<T> listData) {
        items = listData;
        fireContentsChanged(this, items.size(), items.size());
    }

    public void addElement(T item) {
        items.add(item);
        fireContentsChanged(this, items.size(), items.size());
    }

    public void addElements(List<T> listData) {
        this.items.addAll(listData);
        fireContentsChanged(this, items.size(), items.size());
    }

    public abstract void getAllData();

    public abstract void getAllData(E filter);

}
