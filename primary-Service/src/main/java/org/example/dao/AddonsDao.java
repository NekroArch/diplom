package org.example.dao;

import org.example.entity.Addons;

import java.math.BigDecimal;
import java.util.List;

public interface AddonsDao extends AbstractDao<Addons> {
    Addons getAddonWithFetchById(Integer id);
    List<Addons> findAddonsByPrice(BigDecimal price);
}
