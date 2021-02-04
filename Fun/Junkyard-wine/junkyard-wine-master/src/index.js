import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import reportWebVitals from './reportWebVitals';
import App from './App';
import Location from './pages/Location';
import Staff from './pages/Staff';
import Contact from './pages/Contact';
import Press from './pages/Press';
import Traditional from './pages/Traditional';
import Specialty from './pages/Specialty';
import {BrowserRouter, Route} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

ReactDOM.render(
  <BrowserRouter>
        <div>
            <Route path ={"/"} exact render={(props) => {
                return ( 
                <App/>
                )
            }} />
            <Route path={"/location"} component={Location} />
            <Route path={"/staff"} component={Staff} />
            <Route path={"/contact"} component={Contact} />
            <Route path={"/press"} component={Press} />
            <Route path={"/traditional"} component={Traditional} />
            <Route path={"/specialty"} component={Specialty} />
        </div>
  {/* <React.StrictMode>
    <App />
  </React.StrictMode>, */}
  </BrowserRouter>, document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
