package org.example.dao;

import org.example.entity.AddonsCategory;

public interface AddonsCategoryDao extends AbstractDao<AddonsCategory> {
    AddonsCategory getAddonsInCategoryByCategoryName(String name);
}
