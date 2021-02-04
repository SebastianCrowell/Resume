import React, {Component} from 'react';
import '../App.css';
import '@fortawesome/fontawesome-free/js/all.js';

class NewsCard extends Component {
    render(){
        return (
            <div class="card-deck">
                <div class="card">
                    <img class="card-img-top" src="..." alt="6"/>
                    <div class="card-body">
                    <h5 class="card-title">Pre-order next batch</h5>
                    <p class="card-text">Sold out!</p>
                    </div>
                    <div class="card-footer">
                    <small class="text-muted">posted 3 mins ago.</small>
                    </div>
                </div>
                <div class="card">
                    <img class="card-img-top" src="..." alt="5"/>
                    <div class="card-body">
                    <h5 class="card-title">New Machine</h5>
                    <p class="card-text">Machine fixed and running again.</p>
                    </div>
                    <div class="card-footer">
                    <small class="text-muted">Posted 1 day ago.</small>
                    </div>
                </div>
                <div class="card">
                    <img class="card-img-top" src="..." alt="4"/>
                    <div class="card-body">
                    <h5 class="card-title">Hault on Wine</h5>
                    <p class="card-text">Machine broken down.</p>
                    </div>
                    <div class="card-footer">
                    <small class="text-muted">Posted 3 days ago.</small>
                    </div>
                </div>
                <div class="card">
                    <img class="card-img-top" src="..." alt="3"/>
                    <div class="card-body">
                    <h5 class="card-title">New Bottles</h5>
                    <p class="card-text">First batch down.</p>
                    </div>
                    <div class="card-footer">
                    <small class="text-muted">Posted 5 days ago.</small>
                    </div>
                </div>
                <div class="card">
                    <img class="card-img-top" src="..." alt="2"/>
                    <div class="card-body">
                    <h5 class="card-title">Grapes</h5>
                    <p class="card-text">Harvesting season.</p>
                    </div>
                    <div class="card-footer">
                    <small class="text-muted">Posted 16 days ago</small>
                    </div>
                </div>
                <div class="card">
                    <img class="card-img-top" src="..." alt="1"/>
                    <div class="card-body">
                    <h5 class="card-title">Great day</h5>
                    <p class="card-text">Grand opening of the facilities and winery.</p>
                    </div>
                    <div class="card-footer">
                    <small class="text-muted">Posted 100 days ago</small>
                    </div>
                </div>
            </div>
        )
    }
}

export default NewsCard;