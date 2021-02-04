import React, {Component} from 'react';
import '../App.css';
import '@fortawesome/fontawesome-free/js/all.js';

class Footer extends Component {
    render() {
        return (
            <div class="row" style={{margin: 'auto'}}>
                <div class="col-lg-2">
                    <div class="est-image"></div>
                </div>
                <div class="col-lg-6"></div>
                <div class="col-lg-1 text-center">
                    <a class="nav-link" href="https://twitter.com/?lang=en">
                        <i class="fab fa-twitter-square"></i>
                    </a>
                </div>
                <div class="col-lg-1 text-center">
                    <a class="nav-link" href="https://www.facebook.com/">
                        <i class="fab fa-facebook-square"></i>
                    </a>
                </div>
                <div class="col-lg-1 text-center">
                    <a class="nav-link" href="https://www.instagram.com/?hl=en">
                        <i class="fab fa-instagram-square"></i>
                    </a>
                </div>
                <div class="col-lg-1 text-center">
                    <a class="nav-link" href="https://www.youtube.com/">
                        <i class="fab fa-youtube-square"></i>
                    </a>
                </div>
            </div>
        )
    }
  }  

export default Footer;