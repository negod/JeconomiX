/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;
import se.backede.jeconomix.exporter.ExportListData;
import se.backede.jeconomix.exporter.Exporter;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 * @param <T>
 */
public class ImportExportUtils<T> {

    public Optional<Set<Class<?>>> extractClasses(ExportListData<T> data) {
        return Optional.ofNullable(extractClassesFromObject(data));        //});
    }

    public Set<Class<?>> extractClassesFromObject(Object object) {

        Set<Class<?>> clazzes = new HashSet<>();

        if (object == null) {
            return clazzes;
        }

        if (!object.getClass().isAnnotationPresent(XmlRootElement.class)) {
            return clazzes;
        } else {
            clazzes.add(object.getClass());
        }

        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (Collection.class.isAssignableFrom(field.getType())) {
                    Collection collection = (Collection) field.get(object);
                    getObjectFromList(collection).ifPresent(extractedObject -> {
                        clazzes.addAll(extractClassesFromObject(extractedObject));
                    });
                } else {
                    clazzes.addAll(extractClassesFromObject(field.get(object)));
                }
                field.setAccessible(false);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return clazzes;
    }

    public Optional<T> getObjectFromList(Collection<T> data) {
        return (data != null || !data.isEmpty()) ? Optional.ofNullable(data.iterator().next()) : Optional.empty();
    }

}
