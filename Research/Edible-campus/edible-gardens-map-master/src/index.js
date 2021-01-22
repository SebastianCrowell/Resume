import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import './css/scss.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import {BrowserRouter, Route} from 'react-router-dom';
import Gardens from './Gardens';
import Garden from './Garden';
import Plant from './Plant' 
import Plants from './Plants'
import Home from './Home';
import Info from './Info';

const BUTTONS = [
    { link: 'plants', icon: 'leaf', caption: 'Plants' },
    { link: 'gardens', icon: 'map-signs', caption: 'Gardens' },
    { link: 'info', icon: 'info-circle', caption: 'Info' },
    // { link: 'search', icon: 'search', caption: 'Search' },
];

ReactDOM.render(
    <BrowserRouter>
        <div>
            <Route path ={"/"} exact render={(props) => {
                return ( 
                <Home navButtons={BUTTONS} />
                )
            }} />
            <Route path={"/info"} component={Info} />
            <Route path={"/gardens/"} exact component={Gardens} />
            <Route path={"/gardens/:garden"} render={(props) => {
                const garden_id = props.match.params.garden;
                return (
                    garden_id
                    ? <Garden bed_id={garden_id}/>
                    : <h1>Garden not found <span role="img" aria-label="Sad Face">😔</span></h1>
                )
            }} />
            <Route path={"/plants"} exact component={Plants} />
            <Route path={"/plants/:plant"} render={(props) => {
                const plant_id = props.match.params.plant;
                return (
                    plant_id
                    ? <Plant plant_id={plant_id}/>
                    : <h1>Plant not found <span role="img" aria-label="Sad Face">😔</span></h1>
                )
            }} />
        </div>
    </BrowserRouter>, document.getElementById('root'));