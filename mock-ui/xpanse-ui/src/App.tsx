import './App.css';
import React from 'react';
import {Route, Routes} from 'react-router-dom';
import './styles/app.css';
import Home from './components/content/Home';
import Protected from './components/content/ProtectedRoute';
import {homePageRoute} from './components/utils/constants';
import Services from "./components/content/services/Services";
import Billing from "./components/content/billing/Billing";
import IntegrationEndpoints from "./components/content/integration/IntegrationEndpoints";
import Catalog from "./components/content/catalog/Catalog";
import MiddleWare from "./components/content/catalog/children/MiddleWare";
import AI from "./components/content/catalog/children/AI";
import MediaService from "./components/content/catalog/MediaService/MediaService";
import LoginScreen from "./components/content/LoginScreen";

function App() {
  return (
      <Routes>
        <Route
            path={homePageRoute}
            element={
              <Protected>
                <Home/>
              </Protected>
            }
        />
        <Route path={'/services'}
               element={
                 <Protected>
                   <Services/>
                 </Protected>
               }
        />
        <Route path={'/billing'}
               element={
                 <Protected>
                   <Billing/>
                 </Protected>
               }
        />
        <Route path={'/integration-endpoints'}
               element={
                 <Protected>
                   <IntegrationEndpoints/>
                 </Protected>
               }
        />
        <Route path={'/catalog'}
               element={
                 <Protected>
                   <Catalog/>
                 </Protected>
               }
        />
        <Route path={'/middleware'}
               element={
                 <Protected>
                   <MiddleWare/>
                 </Protected>
               }
        />
        <Route path={'/ai'}
               element={
                 <Protected>
                   <AI/>
                 </Protected>
               }
        />
        <Route path={'/mediaservice'}
               element={
                 <Protected>
                   <MediaService/>
                 </Protected>
               }
        />
        <Route path="*" element={<LoginScreen/>}/>
      </Routes>
  );
}

export default App;
