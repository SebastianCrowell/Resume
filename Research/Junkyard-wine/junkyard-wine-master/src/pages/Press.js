import React, {Component} from 'react';
import '../App.css';
import '@fortawesome/fontawesome-free/js/all.js';
import NavBar from '../components/NavBar';
import Footer from '../components/Footer';

class Press extends Component {
    render () {
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
    render() {
        return ( 
            <div>
                
            </div>
        )
    }
}

export default Press;