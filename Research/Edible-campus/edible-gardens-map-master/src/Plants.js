import React, { Component } from 'react';
import { Container, Col, Row, FormGroup, Input } from 'reactstrap';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
// import { BrowserRouter as Router, Route, Link, Switch} from "react-router-dom";    
var serverURLBase = 'https://ediblecampusapi-dept-botanicalgarden.cloudapps.unc.edu/';

library.add(faArrowLeft)
class Plants extends Component {
    render() {
        const bed_id = this.props.bed_id;
        return (
            <Container id="mapp">
                <TitleBar></TitleBar>
                <NavPlantRow id={bed_id} />
            </Container>);
    }
}

class TitleBar extends Component {
    render() {
        return (
            <Row className="mt-3 pb-2">
                <Col xs="2">
                    <BackButton />
                </Col>
                <Col xs="8" className="text-center">
                    <h4>
                        Plants
                    </h4>
                </Col>
            </Row>
        )
    }
}

class BackButton extends Component {
    render() {
        return (
            <Link to="/" >
                <FontAwesomeIcon icon="arrow-left" size="lg" className="back-button" />
            </Link>
        );
    }
}

class PlantCircle extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            photo: ""
        };
    }

    componentDidMount() {
        fetch(serverURLBase + "pictures?plant_id=" + this.props.plant_id, {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                this.setState({
                    photo: json.PicURL,
                })
            })
    }
    render() {
        return (
            <Col xs="3">
                <Link to={`/plants/${this.props.plant_id}`} >
                    <figure className="figure circle">
                        <img className="figure-img " alt={this.props.name} src={this.state.photo}></img>
                        <figcaption className="figure-caption">{this.props.name}</figcaption>
                    </figure>
                </Link>
            </Col>
        )
    }
}

class NavPlantRow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            plants: [],
            search: '',
        };
    }
    updateSearch(event){
        this.setState({search: event.target.value});
    }
    componentDidMount() {
        // fetch(serverURLBase + "plants?bed_id=" + this.props.id, {
        fetch(serverURLBase + "plants?", {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                let plants = json.map((plant) => {

                    return (
                        // if comma or paren, split after
                        <PlantCircle
                            plant_id={plant.PlantID}
                            name={plant.PlantName}
                            key={plant.PlantName} />
                    )
                })
                this.setState({
                    isLoaded: true,
                })
                this.setState({ plants: plants });
            })
    }
    render() {
        const filterSearch = this.state.search;

        let filterPlants = this.state.plants.filter(
            (plant) => {
                return plant.props.name.includes(filterSearch) || plant.props.name.toLowerCase().includes(filterSearch);
            }
        )
        return (
            <div>
                <FormGroup>
                    <Input
                    type = "text"
                    placeholder = "Plant Search"
                    value = {this.state.search}
                    onChange = {this.updateSearch.bind(this)}
                    />
                </FormGroup>
                <Row className="all-plants pt-3">
                    {filterPlants}
                </Row>
            </div>
        )
    }
}
export default Plants;    