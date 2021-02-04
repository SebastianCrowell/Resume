import React, {Component} from 'react';
import '../App.css';
import '@fortawesome/fontawesome-free/js/all.js';
import NavBar from '../components/NavBar';
import Footer from '../components/Footer';

class Specialty extends Component {
    render(){
        return (
            <div>
                <NavBar/>
                <Footer/>
            </div>
        )
    }
}

export default Specialty;