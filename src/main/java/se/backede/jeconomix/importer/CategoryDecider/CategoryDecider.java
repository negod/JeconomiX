/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer.CategoryDecider;

import java.util.Map;
import java.util.Optional;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.importer.CategoryModelmporter;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CategoryDecider {
    
    private static CategoryDecider INSTANCE = new CategoryDecider();
    
    protected CategoryDecider() {
    }
    
    public static final CategoryDecider getInstance() {
        return INSTANCE;
    }
    
    public Optional<CategoryDto> decideCactegory(String transactionName) {
        Optional<Map<String, String>> categoryModel = CategoryModelmporter.getInstance().getCategoryModel("models//category_model.xml");
        if (categoryModel.isPresent()) {
            for (String string : categoryModel.get().keySet()) {
                String nameToLower = transactionName.toLowerCase();
                if (nameToLower.contains(string)) {
                    return CategoryHandler.getInstance().getById(categoryModel.get().get(string));
                }
            }
        }
        return Optional.empty();
    }
    
}
