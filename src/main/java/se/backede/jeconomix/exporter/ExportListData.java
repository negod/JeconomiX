/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.exporter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 * @param <T>
 */
@XmlRootElement(name = "listData")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class ExportListData<T> {

    @XmlAttribute
    private Date exported;

    @XmlAnyElement(lax = true)
    private List<T> data = new ArrayList<T>();

    public ExportListData() {
    }

    public ExportListData(List<T> data) {
        this.data = data;
    }

}
