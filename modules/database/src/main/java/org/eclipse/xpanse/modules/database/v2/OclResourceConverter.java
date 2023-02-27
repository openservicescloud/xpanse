/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.database.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.eclipse.xpanse.modules.ocl.v2.OclResource;

/**
 * Converter to handle OclResources data type and string automatic conversion between database and
 * the entity.
 */
@Converter
public class OclResourceConverter implements AttributeConverter<OclResource, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(OclResource oclResource) {
        try {
            return
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oclResource);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Serial OCL object to json failed.", ex);
        }
    }

    @Override
    public OclResource convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, OclResource.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
