/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.entity;

import com.negod.generics.persistence.entity.GenericEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import se.backede.jeconomix.constants.EntityConstants;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Table(name = EntityConstants.COMPANY)
@Entity
@Getter
@Setter
@Indexed
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = EntityConstants.COMPANY)
@AnalyzerDef(name = "company_customanalyzer",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
            @TokenFilterDef(factory = LowerCaseFilterFactory.class)
        })
public class Company extends GenericEntity {

    @Analyzer(definition = "company_customanalyzer")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
    @Column(name = "name", insertable = true, unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Transaction> transactions = new HashSet<>();

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CompanyAccociation> accociations = new HashSet<>();

}
