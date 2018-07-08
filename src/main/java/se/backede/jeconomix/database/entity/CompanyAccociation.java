/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.entity;

import com.negod.generics.persistence.entity.GenericEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompanyAccociation.findAll", query = "SELECT c FROM CompanyAccociation c")
    , @NamedQuery(name = "CompanyAccociation.findById", query = "SELECT c FROM CompanyAccociation c WHERE c.id = :id")
    , @NamedQuery(name = "CompanyAccociation.findByUpdateddate", query = "SELECT c FROM CompanyAccociation c WHERE c.updateddate = :updateddate")
    , @NamedQuery(name = "CompanyAccociation.findByName", query = "SELECT c FROM CompanyAccociation c WHERE c.name = :name")})
@Getter
@Setter
public class CompanyAccociation extends GenericEntity {

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    Company company;

}
