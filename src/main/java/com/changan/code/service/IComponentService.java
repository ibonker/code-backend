/**
 * 
 */
package com.changan.code.service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.changan.code.dto.Component;
import com.changan.code.dto.ComponentCategory;
import com.google.common.collect.Lists;

/**
 * @author wenxing
 *
 */
public interface IComponentService {

  default ComponentCategory getComponentCategory(Supplier<String> select, Supplier<String> type,
      Consumer<List<Component>> addcomponent) {
    ComponentCategory category = new ComponentCategory();
    category.setIsMultiSelect(select.get());
    category.setCategory(type.get());
    List<Component> components = Lists.newArrayList();
    addcomponent.accept(components);
    category.setComponents(components);
    return category;

  }

}
