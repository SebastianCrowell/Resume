import React, { Component } from 'react';
import { Container, Col, Row, FormGroup, Input } from 'reactstrap';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
var serverURLBase = 'https://ediblecampusapi-dept-botanicalgarden.cloudapps.unc.edu/';

library.add(faArrowLeft)

class Search extends Component {
    render() {
        return (
            <Container>
                <SearchBar className="mt-5"></SearchBar>
                <GardensRow></GardensRow>
                <FilterablePlantsRow></FilterablePlantsRow>
            </Container>
        )
    }
}
class SearchBar extends React.Component {
    constructor(props) {
        super(props);
        this.handleFilterTextChange = this.handleFilterTextChange.bind(this);
        this.handleInStockChange = this.handleInStockChange.bind(this);
    }

    handleFilterTextChange(e) {
        this.props.onFilterTextChange(e.target.value);
    }

    render() {
        return (
            <form>
                <Input
                    type="text"
                    placeholder="Search..."
                    value={this.props.filterText}
                    onChange={this.handleFilterTextChange}
                />
            </form>
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
                        <img className="figure-img " src={this.state.photo}></img>
                        <figcaption className="figure-caption">{this.props.name}</figcaption>
                    </figure>
                </Link>
            </Col>
        )
    }
}

class FilterablePlantsRow extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            plants: [],
            filterText: '',
        };

        this.handleFilterTextChange = this.handleFilterTextChange.bind(this);
    }

    handleFilterTextChange(filterText) {
        this.setState({
            filterText: filterText
        });
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
        return (
            <div>
                <SearchBar
                    filterText={this.state.filterText}
                    onFilterTextChange={this.handleFilterTextChange}
                />
                <PlantsRow
                    plants={this.state.plants}
                    filterText={this.state.filterText}
                />
            </div>
        );
    }
}

class GardensRow extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            filterText: '',
        };

        this.handleFilterTextChange = this.handleFilterTextChange.bind(this);
    }

    handleFilterTextChange(filterText) {
        this.setState({
            filterText: filterText
        });
    }

    render() {
        return (
            <div>
                <SearchBar
                    filterText={this.state.filterText}
                    onFilterTextChange={this.handleFilterTextChange}
                />
                <ProductTable
                    products={this.props.products}
                    filterText={this.state.filterText}
                />
            </div>
        );
    }
}


export default Search;