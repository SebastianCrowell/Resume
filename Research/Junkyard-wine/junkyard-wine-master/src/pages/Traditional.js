import React, {Component} from 'react';
import '../App.css';
import '@fortawesome/fontawesome-free/js/all.js';
import NavBar from '../components/NavBar';
import Footer from '../components/Footer';
import Wine from '../components/Wine';

class Traditional extends Component {
    render(){
        return (
            <div>
                <NavBar/>
                <Content/>
                <Footer/>
            </div>
        )
    }
}

class Content extends Component {
    render(){
        return (
            <div class="row">
                <div class="column-traditional">
                    <div class="col-wines">
                        <ul>
                            <Wine/>
                        </ul>
                    </div>
                </div>
            </div>
        )
    }
}

export default Traditional;