package org.hotpotmaterial.code.service.impl;

import java.sql.Types;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

public class JavaTypeResolver extends JavaTypeResolverDefaultImpl {
  public JavaTypeResolver() {  
    super();
    super.typeMap.put(Types.OTHER, new JdbcTypeInformation("NVARCHAR", new FullyQualifiedJavaType(String.class.getName())));
    super.typeMap.put(Types.LONGVARCHAR, new JdbcTypeInformation("VARCHAR", new FullyQualifiedJavaType(String.class.getName())));
    //super.typeMap.put(Types.CLOB, new JdbcTypeInformation("CLOB", new FullyQualifiedJavaType(String.class.getName())));
}  
}
