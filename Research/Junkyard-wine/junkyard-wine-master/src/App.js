import React, {Component} from 'react';
import './App.css';
import '@fortawesome/fontawesome-free/js/all.js';
import NavBar from './components/NavBar';
import Footer from './components/Footer';

class App extends Component {
  render() {
    return(
    <div>
      <header>
        <NavBar/>
        <Content/>
        <Footer/>
      </header>
    </div>
    )
  }
}

class Content extends Component {
  render() {
      return (
          <div class="bg-image">
              <div class="container" style={{paddingTop:15+'px'}}>
                  <iframe title="Wine" width="420" height="315" src="https://www.youtube.com/embed/7gquYRxLMFI"/>
              </div>
          </div>
      )
  }
}

export default App;
