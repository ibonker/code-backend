/**
 * 
 */
package org.hotpotmaterial.code.common;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.Filter;

/**
 * @author wenxing
 *
 */
public class PredictUtils {

  private PredictUtils() {
    
  }
  
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static Predicate covertFilterToPredicate(Root<?> root, CriteriaBuilder cb, Filter filter) {
    Predicate predicate = null;
    switch(filter.getOperator()) {
      case LIKE:
        predicate = cb.like(root.get(filter.getField()), "%" + filter.getValue() + "%");
        break;
      case EQ:
        predicate = cb.equal(root.get(filter.getField()), filter.getValue());
        break;
      case GT:
        predicate = cb.gt(root.get(filter.getField()), (Number) filter.getValue());
        break;
      case LT:
        predicate = cb.lt(root.get(filter.getField()), (Number) filter.getValue());
        break;
      case GTE:
        predicate = cb.greaterThanOrEqualTo(root.get(filter.getField()), (Comparable) filter.getValue());
        break;
      case LTE:
        predicate = cb.lessThanOrEqualTo(root.get(filter.getField()), (Comparable) filter.getValue());
        break;
      case NOT:
        predicate = cb.notEqual(root.get(filter.getField()), filter.getValue());
        break;
      case IN:
        // List测试
        predicate = root.get(filter.getField()).in(filter.getValue());
        break;
      default:
        break;
    }
    
    return predicate;
  }
  
}
