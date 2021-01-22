import React, { Component } from 'react';
import './App.css';
var serverURLBase = 'https://ediblecampusapi-dept-botanicalgarden.cloudapps.unc.edu/';

class App extends Component {
    constructor(props) {
      super(props);
      this.state = {
        error: null,
        isLoaded: false,
        items: []
      };
    }

    componentDidMount() {
      this.getItems();
    }

    getItems() {
      fetch(serverURLBase + "beds?", { 
        method: 'get', 
        headers: new Headers({
          'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS), 
        }), 
      })
      .then(results => results.json())
      .then(results => console.log(results))
    }

  
    render() {
      // return ({results});
    }
}

export default App;