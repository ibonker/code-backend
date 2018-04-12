/**
 * 
 */
package org.hotpotmaterial.code.common.template;

/**
 * @author Administrator
 *
 */
public enum DictFile {
  
  dictcache("dict/dictcache.xml"),
  dictController("uiconfig/dictController.xml"),
  dictDto("uiconfig/dictDto.xml"),
  dictService("uiconfig/dictService.xml"),
  dictServiceImpl("uiconfig/dictServiceImpl.xml"),
  dictTypeDao("uiconfig/dictTypeDao.xml"),
  dictValueExample("uiconfig/dictValueExample.xml"),
  dictTypeMapper("uiconfig/dictTypeMapper.xml"),
  dictValueDao("uiconfig/dictValueDao.xml"),
  dictTypeExample("uiconfig/dictTypeExample.xml"),
  dictValueMapper("uiconfig/dictValueMapper.xml");
  
  private String path;
  
  private DictFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }

}
