import React, { Component } from 'react';
import { Container, Nav, NavItem, NavLink, Row } from 'reactstrap';
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faLeaf, faMapPin, faNewspaper, faInfoCircle, faScroll, faArrowLeft, faUserCircle } from '@fortawesome/free-solid-svg-icons'

library.add(faLeaf, faMapPin, faNewspaper, faInfoCircle, faScroll, faArrowLeft, faUserCircle)

class Recipe extends Component {
    render() {

        return (
            <Container>
                <br />
                <BackButton />
                <Header />
                <NavButtonRow navButtons={this.props.navButtons}/>
            </Container>
        );
    }
}

class Header extends Component {
    render() {
        return (
            // TODO: Figure out how to href the router link to home
            <div id="rh1"> 
                Recipes
            </div>
        )
    }
}

class NavButton extends Component {
    render() {
        const navButton = this.props.navButton;

    return (
        <div class="recipe-link-icon square" >
        <NavItem >
            <NavLink href={navButton.link} class="align-center">
                    <div class="align-center" >
                        <br />
                        <FontAwesomeIcon icon={navButton.icon} className="align-middle" size="2x" />
                    </div>
                    <div class="recipe-link-caption">
                        <br />
                        {navButton.caption}
                    </div>
                    <div class="recipe-link-undercaption">
                        {navButton.undercaption}
                    </div>
            </NavLink>
        </NavItem>
        </div>
    );}
}

class BackButton extends Component {
    render() {
        return (
            <FontAwesomeIcon icon="arrow-left" color="#0d2ec2" size="lg"/>
    );}
}


class NavButtonRow extends Component {
    render() {
        const columns = [];
        this.props.navButtons.forEach((navButton) => {
            console.log(navButton);
            columns.push(
                <NavButton
                    navButton = {navButton}
                    link={navButton.link}
                    icon={navButton.icon}
                    caption={navButton.caption} 
                    undercaption={navButton.undercaption}
                    key={navButton.caption}/>
            );
        })
        return (
            //flex wrap?
                <Nav className="d-flex justify-content-center" >
                            {columns}
                </Nav>
        )
    }
}


export default Recipe;    