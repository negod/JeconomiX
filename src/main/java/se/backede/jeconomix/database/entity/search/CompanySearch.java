/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.entity.search;

import com.negod.generics.persistence.search.GenericFilter;
import com.negod.generics.persistence.search.SearchMatch;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanySearch {
    
    public static GenericFilter createCompanyNameSearch(String name) {
        GenericFilter searchFilter = new GenericFilter();
        searchFilter.setGlobalSearchWord(name);
        Set<String> fieldNames = new HashSet<>();
        fieldNames.add("name");
        searchFilter.setSearchFields(fieldNames);
        searchFilter.setSearchMatch(SearchMatch.EXACT_MATCH);
        return searchFilter;
    }
    
}
