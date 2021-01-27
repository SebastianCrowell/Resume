import React, {Component} from 'react';
import '../App.css';
import '@fortawesome/fontawesome-free/js/all.js';
import NavBar from '../components/NavBar';
import Footer from '../components/Footer';

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
            <div class="row">
                <div class="col-sm-6">
                    <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Special title treatment</h5>
                        <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                        <a href="/" class="btn btn-primary">Go somewhere</a>
                    </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Special title treatment</h5>
                        <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                        <a href="/" class="btn btn-primary">Go somewhere</a>
                    </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Staff;