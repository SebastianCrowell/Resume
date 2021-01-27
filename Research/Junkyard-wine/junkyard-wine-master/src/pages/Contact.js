import React, {Component} from 'react';
import '../App.css';
import '@fortawesome/fontawesome-free/js/all.js';
import NavBar from '../components/NavBar';
import Footer from '../components/Footer';

class Contact extends Component {
    render() {
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
            <div>
                <h1 class="contact-h1">Contact Us</h1>
                <section class="form-section">
                    <div class="container">
                    <h2 class="contact-h2">Please fill out with your information in order to contact us.</h2>
                        <div class="row">
                            <div class="form-contact col-lg-6">
                                <form class="form-horizontal">
                                    <fieldset>
                                        <input id="first_name" name="first_name" type="text" placeholder="First Name" class="form-control input-md" required=""/>
                                        <input id="last_name" name="last_name" type="text" placeholder="Last Name" class="form-control input-md" required=""/>
                                        <input id="email" name="email" type="email" placeholder="Email" class="form-control input-md" required=""/>
                                        <input id="telephone" name="telephone" type="number" placeholder="Phone" class="form-control input-md" required=""/>
                                        <input id="country" name="country" type="text" placeholder="Country" class="form-control input-md" required=""/>
                                        <textarea class="form-control" id="comment" name="comment" required="" placeholder="Comment" rows="4"/>
                                        <input type="submit" value="SEND" class="form-control first" id="send"/>
                                    </fieldset>
                                </form>
                            </div>

                            <div class="col-lg-6 google-maps">
                                <div class="google-maps last">
                                    <iframe title="GoogleMap" src="https://maps.google.com/maps?q=Cherokee%20NC&t=&z=13&ie=UTF8&iwloc=&output=embed" style={{border:0}} allowfullscreen="" width="100%" height="435"></iframe>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        )
    }
}

export default Contact;