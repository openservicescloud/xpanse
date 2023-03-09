import axios from "axios";

export const getServiceVendorData = async () => {
  return axios.get<ServiceVendor.ServiceData>(`/xpanse/service`);
}
export const getCategoryList = async () => {
  return axios.get<ServiceVendor.CategoryList>(`/xpanse/register/categories`);
}


