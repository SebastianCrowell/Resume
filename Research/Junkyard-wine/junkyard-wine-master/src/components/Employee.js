import React, {Component} from 'react';

class Employee extends Component {
    render(){
        return (
            <div class="card" style={{width: 18+'rem'}}>
                <div class="employee-profile card-body" style={{ height: 18+'rem'}}/>
                <div class="card-body">
                    <h5 class="card-title">Last Name, First Name</h5>
                    <p class="card-text">Something nice about emplyee.</p>
                </div>
                <div class="card-body">
                    <a href="https://www.linkedin.com/" class="card-link">Portfolio</a>
                    <a href="mailto:name@domain" class="card-link">Contact</a>
                    <a href="https://www.glassdoor.com/" class="card-link">Position</a>
                </div>
            </div>
        )
    }
}

export default Employee;