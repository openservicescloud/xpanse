// @ts-ignore
import Mock from 'mockjs';

Mock.mock(`/api/products`, 'get',
    {
        'id|+1': 1,
        'product': '@cname',
        'billing|18-60': 1,
        'time': '@email',
        'amount': /^1[34578]\d{9}$/,
        'discount': '@city()',
        'official': '@zip()'
    }
);

Mock.mock(`/xpanse/health`, 'get', {
    'data|10': [{
        'id|+1': 1,
        'name': '@cname',
        'age|18-60': 1,
        'email': '@email',
        'phone': /^1[34578]\d{9}$/,
        'address': '@city()',
        'zip': '@zip()'
    }]
});

Mock.mock(`/xpanse/service`, 'get', {
    'data': [{
        "id":"123",
        "name": "kafka",
        "versionList": [{
            "version": '1.2',
            "cloudProviderList": [{
                "region": ["ab", "bb"],
                "name": "huaweicloud",
                "details": {
                    'product': '@cname',
                    'billing|18-60': 1,
                    'time': '@email',
                    'amount': /^1[34578]\d{9}$/,
                    'discount': '@city()',
                    'official': '@zip()'
                },
            }, {
                "region": ["rg1", "rg2"],
                "name": "aws",
                "details": {
                    'product': '@cname',
                    'billing|18-60': 1,
                    'time': '@email',
                    'amount': /^1[34578]\d{9}$/,
                    'discount': '@city()',
                    'official': '@zip()'
                },
            }]
        }, {
            "version": '1.3',
            "cloudProviderList": [{
                "region": ["cn-north-1", "cn-north-2"],
                "name": "huaweicloud",
                "details": {
                    'product': '@cname',
                    'billing|18-60': 1,
                    'time': '@email',
                    'amount': /^1[34578]\d{9}$/,
                    'discount': '@city()',
                    'official': '@zip()'
                },
            }, {
                "region": ["aaa1", "aaa2"],
                "name": "azure",
                "details": {
                    'product': '@cname',
                    'billing|18-60': 1,
                    'time': '@email',
                    'amount': /^1[34578]\d{9}$/,
                    'discount': '@city()',
                    'official': '@zip()'
                },
            }]
        }],
    },{
        "name": "Rocket MQ",
        "versionList": [{
            "version": '3.4',
            "cloudProviderList": [{
                "region": ["ab", "bb"],
                "name": "huaweicloud",
                "details": {
                    'product': '@cname',
                    'billing|18-60': 1,
                    'time': '@email',
                    'amount': /^1[34578]\d{9}$/,
                    'discount': '@city()',
                    'official': '@zip()'
                },
            }, {
                "region": ["rg1", "rg2"],
                "name": "AZure",
                "details": {
                    'product': '@cname',
                    'billing|18-60': 1,
                    'time': '@email',
                    'amount': /^1[34578]\d{9}$/,
                    'discount': '@city()',
                    'official': '@zip()'
                },
            }]
        }, {
            "version": '3.5',
            "cloudProviderList": [{
                "region": ["cn-north-4", "cn-north-5"],
                "name": "huaweicloud",
                "details": {
                    'product': '@cname',
                    'billing|18-60': 1,
                    'time': '@email',
                    'amount': /^1[34578]\d{9}$/,
                    'discount': '@city()',
                    'official': '@zip()'
                },
            }, {
                "region": ["aws1", "aws2"],
                "name": "AWS",
                "details": {
                    'product': '@cname',
                    'billing|18-60': 1,
                    'time': '@email',
                    'amount': /^1[34578]\d{9}$/,
                    'discount': '@city()',
                    'official': '@zip()'
                },
            }, {
                "region": ["za1", "za2"],
                "name": "AZure",
                "details": {
                    'product': '@cname',
                    'billing|18-60': 1,
                    'time': '@email',
                    'amount': /^1[34578]\d{9}$/,
                    'discount': '@city()',
                    'official': '@zip()'
                },
            }]
        }],
    }]
});
