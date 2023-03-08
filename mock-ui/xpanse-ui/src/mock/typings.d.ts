declare namespace ServiceVendor {
  interface ServiceData {
    data: Service[];
  }

  interface Service {
    id: string;
    name: string;
    versionList: Version[];
  }

  interface Version {
    version: string;
    cloudProviderList: CloudProvider[]
  }

  interface CloudProvider {
    region: string;
    name: string;
    details: CloudProviderDetails;
  }

  interface CloudProviderDetails {
    product?: string;
    billing?: string;
    time?: string;
    amount?: string;
    discount?: string;
    official?: string;
  }
}


