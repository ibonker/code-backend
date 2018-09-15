package org.hotpotmaterial.code.common.template;

/**
 * excel模板文件
 * @author Administrator
 *
 */
public enum ExcelFile {

  excelDTO("excel/excelDTO.xml"),
  ExcelFailedDTO("excel/ExcelFailedDTO.xml"),
  excelImportDTO("excel/excelImportDTO.xml"),
  excelOperation("excel/excelOperation.xml"),
  excelOperationController("excel/excelOperationController.xml"),
  excelOperationServiceImpl("excel/excelOperationServiceImpl.xml"),
  ExcelReportDTO("excel/ExcelReportDTO.xml"),
  IExcelOperationService("excel/IExcelOperationService.xml"),
  ResultOfExcelReportDTO("excel/ResultOfExcelReportDTO.xml"),
  searchMapper("excel/searchMapper.xml"),
  excelExportTempDTO("excel/excelExportTempDTO.xml"),
  searchMapperXML("excel/searchMapperXML.xml"),
  titleDTO("excel/titleDTO.xml");
  
  private String path;
  
  private ExcelFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
