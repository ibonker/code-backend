/**
 * 
 */
package org.hotpotmaterial.code.extend;

import java.util.List;

import org.hotpotmaterial.code.annotation.JsonSchemaLink;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.customProperties.ValidationSchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.LinkDescriptionObject;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.google.common.collect.Lists;

/**
 * @author wenxing
 *
 */
public class ValidationSchemaFactoryWrapperExtend extends ValidationSchemaFactoryWrapper {

  @Override
  protected JsonSchema addValidationConstraints(JsonSchema schema, BeanProperty prop) {
    schema = super.addValidationConstraints(schema, prop);
    JsonProperty jsonProperty = prop.getAnnotation(JsonProperty.class);
    if (null != jsonProperty && jsonProperty.access().equals(Access.READ_ONLY)) {
      schema.setReadonly(true);
    }
    // 获取link的list
    JsonSchemaLink link = prop.getAnnotation(JsonSchemaLink.class);
    List<LinkDescriptionObject> links = Lists.newArrayList();
    if (null != link) {
      LinkDescriptionObject linkobj = new LinkDescriptionObject();
      linkobj.setHref(link.href());
      links.add(linkobj);
    }
    // 转换为link的array
    LinkDescriptionObject[] linkarr = links.toArray(new LinkDescriptionObject[links.size()]);
    if (schema.isArraySchema()) {
      ArraySchema arraySchema = schema.asArraySchema();
      // TODO item的操作
      arraySchema.set$ref("urn:jsonschema:"
          .concat(prop.getType().getContentType().getRawClass().getName().replace(".", ":")));
      arraySchema.setItems(null);
      arraySchema.setLinks(linkarr);
    }
    // 引用的其他实体类
    if (schema.isSimpleTypeSchema()) {
      if (schema.isObjectSchema()) {
        ObjectSchema objSchema = schema.asObjectSchema();
        objSchema.set$ref("urn:jsonschema:"
            .concat(prop.getType().getRawClass().getName().replace(".", ":")));
        objSchema.setProperties(null);
      }
    }

    return schema;
  }

}
