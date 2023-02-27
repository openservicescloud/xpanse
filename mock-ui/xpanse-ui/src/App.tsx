import logo from './logo.svg';
import './App.css';
import React, {useState} from 'react';
import {Route, Routes} from 'react-router-dom';
import './styles/app.css';
import Home from './components/content/Home';
import LoginScreen from './components/content/LoginScreen';
import Protected from './components/content/ProtectedRoute';
import {billingPageRoute, homePageRoute, servicesPageRoute} from './components/utils/constants';
import Services from "./components/content/services/Services";
import Billing from "./components/content/billing/Billing";
import IntegrationEndpoints from "./components/content/integration/IntegrationEndpoints";
function App() {
  return (
      // <div className="App">
      //   <header className="App-header">
      //     <img src={logo} className="App-logo" alt="logo" />
      //     <p>
      //       Edit <code>src/App.tsx</code> and save to reload.
      //     </p>
      //     <a
      //       className="App-link"
      //       href="https://reactjs.org"
      //       target="_blank"
      //       rel="noopener noreferrer"
      //     >
      //       Learn React
      //     </a>
      //   </header>
      // </div>
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
        <Route path="*" element={<LoginScreen/>}/>
      </Routes>
  );
}

export default App;
