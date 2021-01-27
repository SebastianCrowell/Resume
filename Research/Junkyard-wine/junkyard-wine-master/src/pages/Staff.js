import React, {Component} from 'react';
import '../App.css';
import '@fortawesome/fontawesome-free/js/all.js';
import NavBar from '../components/NavBar';
import Footer from '../components/Footer';
import Employee from '../components/Employee';

class Staff extends Component {
    render(){
        return(
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
        return(
            <div class="row mx-auto" style={{justifyContent: 'center'}}>
                <Employee/>
                <Employee/>
                <Employee/>
                <Employee/>
                <Employee/>
                <Employee/>
                <Employee/>
                <Employee/>
            </div>
        )
    }
}

export default Staff;