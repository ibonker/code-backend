/**
 * 
 */
package org.hotpotmaterial.code.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.entity.DatasourcePO;
import org.hotpotmaterial.code.entity.ProjectPO;
import org.hotpotmaterial.code.entity.TablePO;
import org.hotpotmaterial.code.repository.DatasourceRepository;
import org.hotpotmaterial.code.service.IDatasourceService;
import org.hotpotmaterial.code.service.IProjectService;
import org.hotpotmaterial.code.service.ITableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author wenxing
 *
 */
@Service
public class DatasourceServiceImpl implements IDatasourceService {

	// 注入datasource repository
	@Autowired
	private DatasourceRepository datasourceRepo;
	// 注入表service
	@Autowired
	private ITableService tableService;
	// 注入项目service
	@Autowired
	private IProjectService projectService;

	/**
	 * 检查数据源是否可用
	 * 
	 * @param datasource
	 * @return
	 */
	@Override
	public boolean checkDatasource(DatasourcePO datasource) {
		// 新建数据源
		DruidDataSource dataSource = new DruidDataSource();
		try {
			dataSource.setPassword(datasource.getDbtype());
			// 设置数据源驱动名称
			String Dbtype = datasource.getDbtype();
			if (Dbtype.equals(Constants.DATASOURCE_ORACLE))
				dataSource.setDriverClassName(Constants.DATASOURCE_ORACLE_DRIVER);
			else
				dataSource.setDriverClassName(Constants.DATASOURCE_MYSQL_DRIVER);

			// 设置数据源用户名
			dataSource.setUsername(datasource.getDbuser());
			// 设置数据源密码
			dataSource.setPassword(datasource.getDbpassword());
			// 设置数据库
			dataSource.setDbType(datasource.getDbtype());
			// 设置数据源连接字符串
			dataSource.setUrl(datasource.getDburl());
			// 数据源连接的测试的其它设置
			dataSource.setInitialSize(5);
			dataSource.setMinIdle(1);
			// 新建JDBC对象
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			// 执行SQL
			jdbcTemplate.execute("SELECT 1 FROM DUAL");
			// 关闭数据源
			dataSource.close();
			// 当数据源测试成功返回true
			return true;

		} catch (Exception e) {
			// 当数据源测试出现异常返回false
			dataSource.close();
			return false;
		}
	}

	@Override
	public List<DatasourcePO> findByProjectId(String projectId) {
		return datasourceRepo.findByProjectId(projectId);
	}

	@Override
	public void deleteDatasources(List<DatasourcePO> datasources) {
		datasourceRepo.delete(datasources);
	}

	@Override
	public void saveDatasources(List<DatasourcePO> datasources) {
		for (DatasourcePO datasource : datasources) {
			// 设置对应包名
			datasource.setPackageName(datasource.getName().toLowerCase().replace("_", "."));
			// 设置driver
			datasource.setDbdriver(
					Constants.DATASOURCE_MYSQL.equals(datasource.getDbtype()) ? Constants.DATASOURCE_MYSQL_DRIVER
							: Constants.DATASOURCE_ORACLE_DRIVER);
		}
		datasourceRepo.save(datasources);
	}

	/**
	 * 获取数据库连接信息
	 */
	@Override
	public DatasourcePO findById(String datasourceId) {
		return datasourceRepo.findOne(datasourceId);
	}

	/**
	 * 同步生成项目数据库字段
	 */
	@Override
	public void syncTableFromOriginalDatasource(String datasourceId, String usercode) {
		// 获取datasource
		DatasourcePO datasource = this.findById(datasourceId);
		// 获取项目
		ProjectPO project = projectService.getProjectById(datasource.getProjectId(), usercode);
		// 获取数据源的表
		List<TablePO> originTables = tableService.findTableListFromOriginalDatasource(datasource);
		// 获取保存的表
		List<TablePO> masterTables = tableService.findTableListFromMasterDatasource(datasourceId);
		// 同步数据库表
		tableService.saveAndDelMasterTables(originTables, masterTables, datasource, project);
	}

	/**
	 * 获取项目数据库个数
	 */
	@Override
	public Long countByProjectId(String projectId) {
		return datasourceRepo.countByProjectId(projectId);
	}

	@Override
	public Map<String, String> getTableNames(DatasourcePO datasource) {
		Map<String, String> map = new TreeMap<String, String>();
		// 不显示的表
		List<String> tableUnshown = Constants.getTableUnshown();
		// 获取数据源的表
		List<TablePO> originTables = tableService.findTableListFromOriginalDatasource(datasource);
		for (TablePO tablePO : originTables) {
			if (tableUnshown.contains(tablePO.getName())) {
				continue;
			}
			map.put(tablePO.getName(), tablePO.getComments());
		}
		Map<String, String> mapSort = new TreeMap<String, String>(new Comparator<String>() {
			public int compare(String obj1, String obj2) {
				// 降序排序
				return obj2.compareTo(obj1);
			}
		});
		mapSort = map;
		return mapSort;
	}

}
