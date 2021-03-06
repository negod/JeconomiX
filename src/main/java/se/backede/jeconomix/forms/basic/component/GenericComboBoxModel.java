/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.basic.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private List<T> items = new ArrayList<>();
    private T selection = null;

    public GenericComboBoxModel() {
        refresh();
    }

    public GenericComboBoxModel(E... filter) {
        refresh(filter);
    }

    public void refresh() {
        getAllData().ifPresent(list -> {
            this.items = list;
            fireContentsChanged(this, items.size(), items.size());
        });
    }

    public void refresh(E... filter) {
        getAllData(filter).ifPresent(list -> {
            this.items = list;
            fireContentsChanged(this, items.size(), items.size());
        });
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

    public void addElement(T item) {
        items.add(item);
        fireContentsChanged(this, items.size(), items.size());
    }

    public void addElements(List<T> listData) {
        this.items.addAll(listData);
        fireContentsChanged(this, items.size(), items.size());
    }

    public abstract Optional<List<T>> getAllData();

    public abstract Optional<List<T>> getAllData(E... filter);

}
