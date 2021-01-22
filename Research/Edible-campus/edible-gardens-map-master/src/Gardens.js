import React, { Component } from 'react';
import { Container, Row, Col, Input, FormGroup, Card, CardImgOverlay, CardImg, CardText } from 'reactstrap';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUtensils } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
library.add(faUtensils);
var serverURLBase = 'https://ediblecampusapi-dept-botanicalgarden.cloudapps.unc.edu/';

class Gardens extends Component {
    render() {
        return (
            <Container>
                <Header />
                <NavGardenRow />
            </Container>
        );
    }
}

class BackButton extends Component {
    render() {
        return (
            <Link to="/">
                <FontAwesomeIcon icon="arrow-left" size="lg" className="back-button" />
            </Link>
        );
    }
}
class Header extends Component {
    render() {
        return (
            // TODO: Figure out how to href the router link to home
            <Row className="pt-3 pb-2">
                <Col xs="2">
                    <BackButton />
                </Col>
                <Col xs="8" className="text-center">
                    <h4>
                        Gardens
                    </h4>
                </Col>
            </Row>
        )
    }
}

class NavGarden extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            thumbnail: ""
        };
    }

    componentDidMount() {
        fetch(serverURLBase + "pictures?bed_id=" + this.props.bed.BedID, {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                this.setState({
                    isLoaded: true,
                    thumbnail: json[0].PicURL
                })
                console.log("state", this.state.thumbnail);
            })
    }
    render() {
        const bed = this.props.bed;
        return (
            <Col sm="4" xs="6" className="garden-col">
                <Link to={`/gardens/${bed.BedID}`} >
                    <Card inverse className="garden-card">
                        <CardImg top width="100%" src={this.state.thumbnail} alt="Card image cap" className="img-fluid" />
                        <CardImgOverlay className="p-3">
                            <div className="row garden-icon-row">
                                <div class="garden-link-icon">
                                    <Col md={{ size: 1, offset: 5 }} xs={{ size: 1, offset: 4 }} className=" align-middle text-center" >
                                        <FontAwesomeIcon icon="utensils" />
                                    </Col>
                                    <Col sm="12" className="text-center">
                                        <CardText >
                                            {bed.BedName}
                                        </CardText>
                                    </Col>
                                </div>
                            </div>
                        </CardImgOverlay>
                    </Card>
                </Link>

                {/* </NavLink> */}
            </Col>
        );
    }
}

class NavGardenRow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            beds: [],
            search: '',
        };
    }

    updateSearch(event){
        this.setState({search: event.target.value});
    }

    componentDidMount() {
        fetch(serverURLBase + "beds?", {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                let beds = json.map((bed) => {
                    return (
                        <NavGarden
                            bed={bed}
                            caption={bed.BedName}
                            key={bed.BedName}
                            bed_id={bed.BedID} />
                    )
                })
                this.setState({
                    isLoaded: true,
                })
                this.setState({ beds: beds });
            })
    }
    render() {
        //implementing of search
        const filterSearch = this.state.search;

        let filterGardens = this.state.beds.filter(
            (bed) => {
                return bed.props.caption.includes(filterSearch) || bed.props.caption.toLowerCase().includes(filterSearch);
            }
        )
        const { error, isLoaded } = this.state;
        if (error) {
            return <div>Error: {error.message}</div>;
        } else if (!isLoaded) {
            return <div>Loading...</div>;
        } else {
            return (
                <div>
                    {/* placeholding for garden */}
                <FormGroup>
                    <Input
                    type = "text"
                    placeholder = "Garden"
                    value = {this.state.search}
                    onChange = {this.updateSearch.bind(this)}
                    />
                </FormGroup>
                <Row className="garden-nav-button-row align-center">
                    {filterGardens}
                </Row>
                </div>
            )
        }
    }
}

export default Gardens;    