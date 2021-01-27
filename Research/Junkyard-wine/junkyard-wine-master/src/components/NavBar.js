import React, {Component} from 'react';
import {
    UncontrolledDropdown,
    DropdownToggle,
    DropdownMenu,
    DropdownItem
  } from 'reactstrap';
import '../App.css';
import '@fortawesome/fontawesome-free/js/all.js';

class NavBar extends Component{
    render(){
      return (
        <div>
          <nav class="navbar navbar-expand-lg navbar-light bg-light">
              <a class="navbar-brand" href="/">
                  {/* <div class="nav-brand"></div> */}
                  Junkyard Vineyard
              </a>
              <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                  <span class="navbar-toggler-icon"></span>
              </button>
  
              <div class="collapse navbar-collapse" id="navbarSupportedContent">
                  <ul class="navbar-nav mr-auto">
                      <li class="nav-item">
                          <a class="nav-link" href="/location">Location</a>
                      </li>
                      <li class="nav-item">
                          <a class="nav-link" href="/staff">Staff</a>
                      </li>
                      <li class="nav-item">
                          <a class="nav-link" href="/contact">Contact</a>
                      </li>
                      <li class="nav-item">
                          <a class="nav-link" href="/press">Press</a>
                      </li>
                      <UncontrolledDropdown nav inNavbar>
                      <DropdownToggle nav caret>
                          Wines
                      </DropdownToggle>
                      <DropdownMenu right>
                          <DropdownItem>
                          Traditional
                          </DropdownItem>
                          <DropdownItem>
                          Specialty
                          </DropdownItem>
                      </DropdownMenu>
                      </UncontrolledDropdown>
                  </ul>
                  <form class="form-inline my-2 my-lg-0">
                      <button style={{color:'black'}} class="btn btn-sm btn-outline-secondary" type="button">Checkout</button>
                      <text style={{paddingLeft:20 + 'px', color:'black'}}>$0.00</text>
                  </form>
              </div>
          </nav>
      </div>
      );
    }
  }

export default NavBar;