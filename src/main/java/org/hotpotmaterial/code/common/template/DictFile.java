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
  dictController("dict/dictController.xml"),
  dictDto("dict/dictDto.xml"),
  dictService("dict/dictService.xml"),
  dictServiceImpl("dict/dictServiceImpl.xml"),
  dictTypeDao("dict/dictTypeDao.xml"),
  dictValueExample("dict/dictValueExample.xml"),
  dictTypeMapper("dict/dictTypeMapper.xml"),
  dictValueDao("dict/dictValueDao.xml"),
  dictTypeExample("dict/dictTypeExample.xml"),
  dictValueMapper("dict/dictValueMapper.xml");
  
  private String path;
  
  private DictFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }

}
