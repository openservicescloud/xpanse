/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.database.v2;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;
import org.eclipse.xpanse.modules.engine.XpanseDeployResponse;
import org.eclipse.xpanse.modules.engine.XpanseDeployTask;
import org.eclipse.xpanse.modules.ocl.v2.OclResource;
import org.hibernate.annotations.Type;

/**
 * Represents the SERVICE_DETAIL table in the database.
 */
@Table(name = "SERVICE_TASK")
@Entity
@Data
public class ServiceTaskEntity {

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "OCL_RESOURCE", columnDefinition = "json")
    @Type(value = JsonType.class)
    @Convert(converter = OclResourceConverter.class)
    private OclResource oclResource;

    @Column(name = "DEPLOY_TASKT", columnDefinition = "json")
    @Type(value = JsonType.class)
    @Convert(converter = DeployTaskConverter.class)
    private XpanseDeployTask deployTask;

    @Column(name = "DEPLOY_RESPONSE", columnDefinition = "json")
    @Type(value = JsonType.class)
    @Convert(converter = DeployResponseConverter.class)
    private XpanseDeployResponse xpanseDeployResponse;


    @Column(name = "IS_DELETED")
    private Boolean isDeleted;

}
