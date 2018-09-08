/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import com.negod.generics.persistence.mapper.DtoEntityBaseMapper;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.dao.BudgetDao;
import se.backede.jeconomix.database.dao.BudgetExpenseDao;
import se.backede.jeconomix.database.dao.CategoryDao;
import se.backede.jeconomix.database.entity.Category;
import se.backede.jeconomix.database.entity.budget.BudgetExpense;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class BudgetExpenseHandler {

    private final BudgetExpenseDao dao = new BudgetExpenseDao();
    private final BudgetDao budgetDao = new BudgetDao();
    private final CategoryDao categoryDao = new CategoryDao();

    private final DtoEntityBaseMapper<BudgetExpenseDto, BudgetExpense> mapper = new DtoEntityBaseMapper(BudgetExpenseDto.class, BudgetExpense.class);

    private static final BudgetExpenseHandler INSTANCE = new BudgetExpenseHandler();

    protected BudgetExpenseHandler() {
    }

    public static final BudgetExpenseHandler getInstance() {
        return INSTANCE;
    }

    public Optional<BudgetExpenseDto> createBudgetExpense(BudgetExpenseDto dto) {

        return mapper.mapFromDtoToEntity(dto).map(entity -> {

            budgetDao.getById(entity.getId()).ifPresent(budget -> {
                entity.setBudget(budget);
            });

            categoryDao.getById(entity.getId()).ifPresent(category -> {
                entity.setCategory(category);
            });

            return dao.executeTransaction(() -> dao.persist(entity)).map(persisted -> {
                return mapper.mapFromEntityToDto(persisted).get();
            });

        }).orElse(Optional.empty());

    }

    public Optional<BudgetExpenseDto> updateBudgetExpense(BudgetExpenseDto dto) {
        Optional<BudgetExpense> mapFromDtoToEntity = mapper.mapFromDtoToEntity(dto);
        if (mapFromDtoToEntity.isPresent()) {
            dao.startTransaction();

            Optional<BudgetExpense> budgetExpenseEntity = dao.getById(dto.getId());
            if (budgetExpenseEntity.isPresent()) {
                if (!budgetExpenseEntity.get().getCategory().getId().equals(dto.getCategory().getId())) {
                    Optional<Category> categoryEntity = CategoryHandler.getInstance().getById(dto.getCategory().getId());
                    budgetExpenseEntity.get().setCategory(categoryEntity.get());
                }
            }

            budgetExpenseEntity.get().setEstimatedsum(dto.getEstimatedsum());

            BudgetExpense merge = dao.getEntityManager().merge(budgetExpenseEntity.get());
            Optional<BudgetExpenseDto> mapFromEntityToDto = mapper.mapFromEntityToDto(merge);
            dao.commitTransaction();

            return mapFromEntityToDto;

        }
        return Optional.empty();
    }

    public Boolean deleteBudgetExpense(BudgetExpenseDto dto) {
        dao.startTransaction();
        Optional<Boolean> delete = dao.delete(dto.getId());
        dao.commitTransaction();
        return delete.get();
    }

    public Optional<BudgetExpenseDto> upsertBudgetExpense(BudgetExpenseDto dto) {
        if (dto.getId() == null) {
            return createBudgetExpense(dto);
        } else {
            return updateBudgetExpense(dto);
        }
    }

}
