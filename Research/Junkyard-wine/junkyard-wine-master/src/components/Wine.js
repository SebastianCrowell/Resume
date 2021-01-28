import React, {Component} from 'react';
import '../App.css';
import '@fortawesome/fontawesome-free/js/all.js';

class Wine extends Component {
    render(){
        return (
            <div>
                <li>
                    <h2 class="wine-h2">
                        <a href="/">
                            Some wine
                        </a>
                    </h2>
                </li>
            </div>
        )
    }
}

export default Wine;