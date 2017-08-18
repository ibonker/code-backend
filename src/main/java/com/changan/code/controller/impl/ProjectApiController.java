/**
 * 
 */
package com.changan.code.controller.impl;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.changan.code.controller.ProjectApi;
import com.changan.code.dto.ResultOfProjectDTO;
import com.changan.code.entity.ProjectPO;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.customProperties.ValidationSchemaFactoryWrapper;

/**
 * @author wenxing
 *
 */
@Controller
public class ProjectApiController implements ProjectApi {

  @Override
  public ResponseEntity<JsonSchema> jsonSchemaExampleGet() {
    ValidationSchemaFactoryWrapper personVisitor = new ValidationSchemaFactoryWrapper();
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.acceptJsonFormatVisitor(ResultOfProjectDTO.class, personVisitor);
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    JsonSchema personSchema = personVisitor.finalSchema();
    return new ResponseEntity<JsonSchema>(personSchema, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ResultOfProjectDTO> projectExampleGet() {
    ProjectPO project = new ProjectPO();
    project.setCreatedAt(LocalDateTime.now());
    project.setUpdatedAt(LocalDateTime.now());
    return new ResponseEntity<ResultOfProjectDTO>(new ResultOfProjectDTO().project(project),
        HttpStatus.OK);
  }

}
