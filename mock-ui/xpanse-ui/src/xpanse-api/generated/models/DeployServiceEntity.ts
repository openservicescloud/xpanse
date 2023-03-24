/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 */

/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * OpenAPI spec version: v0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { CreateRequest } from './CreateRequest';
import { DeployResourceEntity } from './DeployResourceEntity';

export class DeployServiceEntity {
    'createTime'?: Date;
    'lastModifiedTime'?: Date;
    'category'?: DeployServiceEntityCategoryEnum;
    'name'?: string;
    'version'?: string;
    'csp'?: DeployServiceEntityCspEnum;
    'flavor'?: string;
    'serviceState'?: DeployServiceEntityServiceStateEnum;
    'createRequest'?: CreateRequest;
    'deployResourceEntity'?: Array<DeployResourceEntity>;

    static readonly discriminator: string | undefined = undefined;

    static readonly attributeTypeMap: Array<{ name: string; baseName: string; type: string; format: string }> = [
        {
            name: 'createTime',
            baseName: 'createTime',
            type: 'Date',
            format: 'date-time',
        },
        {
            name: 'lastModifiedTime',
            baseName: 'lastModifiedTime',
            type: 'Date',
            format: 'date-time',
        },
        {
            name: 'category',
            baseName: 'category',
            type: 'DeployServiceEntityCategoryEnum',
            format: '',
        },
        {
            name: 'name',
            baseName: 'name',
            type: 'string',
            format: '',
        },
        {
            name: 'version',
            baseName: 'version',
            type: 'string',
            format: '',
        },
        {
            name: 'csp',
            baseName: 'csp',
            type: 'DeployServiceEntityCspEnum',
            format: '',
        },
        {
            name: 'flavor',
            baseName: 'flavor',
            type: 'string',
            format: '',
        },
        {
            name: 'serviceState',
            baseName: 'serviceState',
            type: 'DeployServiceEntityServiceStateEnum',
            format: '',
        },
        {
            name: 'createRequest',
            baseName: 'createRequest',
            type: 'CreateRequest',
            format: '',
        },
        {
            name: 'deployResourceEntity',
            baseName: 'deployResourceEntity',
            type: 'Array<DeployResourceEntity>',
            format: '',
        },
    ];

    static getAttributeTypeMap() {
        return DeployServiceEntity.attributeTypeMap;
    }

    public constructor() {}
}

export type DeployServiceEntityCategoryEnum =
    | 'ai'
    | 'compute'
    | 'container'
    | 'storage'
    | 'network'
    | 'database'
    | 'media_service'
    | 'security'
    | 'middleware'
    | 'others';
export type DeployServiceEntityCspEnum = 'aws' | 'azure' | 'alibaba' | 'huawei' | 'openstack';
export type DeployServiceEntityServiceStateEnum =
    | 'REGISTERED'
    | 'UPDATED'
    | 'DEPLOYING'
    | 'DEPLOY_SUCCESS'
    | 'DEPLOY_FAILED'
    | 'DESTROYING'
    | 'DESTROY_SUCCESS'
    | 'DESTROY_FAILED';
