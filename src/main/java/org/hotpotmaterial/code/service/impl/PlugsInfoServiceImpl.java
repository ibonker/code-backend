package org.hotpotmaterial.code.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.Filter;
import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.common.PredictUtils;
import org.hotpotmaterial.code.entity.PlugsInfoPO;
import org.hotpotmaterial.code.repository.PlugsInfoRepository;
import org.hotpotmaterial.code.service.IPlugsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hotpotmaterial-Code2 业务接口声明实现类 - PlugsInfoServiceImpl
 */
@Service
@Slf4j
public class PlugsInfoServiceImpl implements IPlugsInfoService {

	@Autowired
	private PlugsInfoRepository plugsInfoRepository;

	// 新增
	@Override
	public int insertPlugsInfo(PlugsInfoPO plugsInfo) {
		// 设置值
		plugsInfo.preInsert();
		plugsInfoRepository.save(plugsInfo);
		return 1;
	}

	// 修改
	@Override
	public int updatePlugsInfo(String id, PlugsInfoPO plugsInfo) {
		plugsInfo.preUpdate();
		plugsInfo.setId(id);
		plugsInfoRepository.save(plugsInfo);
		return 1;
	}

	// 通过id查询
	@Override
	public PlugsInfoPO findById(String id) {
		return plugsInfoRepository.findOne(id);
	}

	// 通过id删除
	@Override
	public int deleteById(String id) {
		plugsInfoRepository.delete(id);
		return 1;
	}

	// 分页查询
	@Override
	public Page<PlugsInfoPO> getPlugsInfoList(PageDTO searchParams) {

		List<Order> orders = Lists.newArrayList();
		// 排序
		if (searchParams.getOrders() != null&&searchParams.getOrders().size()>0) {
			for (org.hotpotmaterial.anywhere.common.mvc.page.rest.request.Order order : searchParams.getOrders()) {
				orders.add(new Order(Direction.valueOf(order.getOrderType().toUpperCase()), order.getFieldName()));
			}
		}
		Sort sort = null;
		if(!orders.isEmpty()) {
			sort = new Sort(orders);
		}
		int pageIndex = searchParams.getPageParms().getPageIndex();
		int pageSize = searchParams.getPageParms().getPageSize();
		if(pageIndex==0&&pageSize==0) {
			pageSize = Integer.MAX_VALUE;
		}
		// 分页
		PageRequest pagereq = new PageRequest(pageIndex,
				pageSize,sort);
		// 使用specification查询
		Specification<PlugsInfoPO> spec = new Specification<PlugsInfoPO>() {
			@Override
			public Predicate toPredicate(Root<PlugsInfoPO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = Lists.newArrayList();
				// 查询参数
				if (null != searchParams.getCollection()) {
					for (Filter filter : searchParams.getCollection().getFilters()) {
						predicates.add(PredictUtils.covertFilterToPredicate(root, cb, filter));
					}
				}
				// 逻辑未删除查询
				predicates.add(cb.equal(root.get("delFlag"), Constants.DATA_IS_NORMAL));

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		// 获取数据
		return plugsInfoRepository.findAll(spec, pagereq);
	}

}