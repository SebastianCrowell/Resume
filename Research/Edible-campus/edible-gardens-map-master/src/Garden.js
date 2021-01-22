import React, { Component } from 'react';
import { Container, Col, Row,Button, Card, CardImg, CardImgOverlay} from 'reactstrap';
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUtensils, faArrowLeft } from '@fortawesome/free-solid-svg-icons'
import { Link } from 'react-router-dom';

// import { BrowserRouter, Switch, Link, Route } from "react-router-dom";
var serverURLBase = 'https://ediblecampusapi-dept-botanicalgarden.cloudapps.unc.edu/';

library.add(faUtensils, faArrowLeft)

class Garden extends Component {
    render() {
        const bed_id = this.props.bed_id;
        return (
            <Container id="mapp">
                <TitleBar id={bed_id} />
                <Picture id={bed_id} />
                <GardenStats id={bed_id} />
                <Description id={bed_id} />
                <MorePlants id={bed_id} />
            </Container>
        );
    }
}
class GardenStats extends Component {
    render() {
        const id = this.props.id
        return (
            <Row className="garden-stats">
                <PlantsCount id={id} />
                <RecipesCount id={id} />
                <Distance id={id} />
            </Row>
        )
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

class TitleBar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            bed_name: ""
        };
    }

    componentDidMount() {
        fetch(serverURLBase + "beds?bed_id=" + this.props.id, {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                this.setState({
                    isLoaded: true,
                    bed_name: json.BedName
                })
            }).catch((error) => {
                console.log("Can't pull from API :(")
                console.error(error);
            });
    }
    render() {
        return (
            <Row className="mt-3">
                <Col xs="2">
                    <BackButton />
                </Col>
                <Col xs="8" className="text-center">
                    <h4>
                        {/* TODO: Ajax call */}
                        {this.state.bed_name}
                    </h4>
                </Col>
            </Row>
        )
    }
}

class Picture extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            thumbnail: ""
        };
    }

    componentDidMount() {
        fetch(serverURLBase + "pictures?bed_id=" + this.props.id, {
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
            }).catch((error) => {
                console.log("Can't pull from API :(")
                console.error(error);
            });
    }
    render() {
        return (
            <div>
                <Card className="garden-single-card">
                    <CardImg height="50%" width="50%" src={this.state.thumbnail} alt="Card image cap"  />
                    <CardImgOverlay >
                        <FontAwesomeIcon icon="utensils" size="2x" className="garden-link-icon" />
                    </CardImgOverlay>
                </Card>
            </div>
        )
    }
}

class PlantsCount extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            plant_count: 0
        };
    }

    componentDidMount() {
        fetch(serverURLBase + "plants?bed_id=" + this.props.id, {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                this.setState({
                    isLoaded: true,
                    plant_count: json.length
                })
            }).catch((error) => {
                console.log("Can't pull from API :(")
                console.error(error);
            });
    }
    render() {
        return (
            <Col xs="4" >
                <Button className="garden-stats-btn">

                    <Row>
                        <Col xs="12">
                            <h4>{this.state.plant_count}</h4>
                        </Col>
                    </Row>
                    <Row>
                        <Col xs="12">
                            Plants
                    </Col>
                    </Row>
                </Button>
            </Col>
        )
    }
}

class RecipesCount extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            recipes_count: 0,
        };
    }
    componentDidMount() {
        fetch(serverURLBase + "plants?bed_id=" + this.props.id, {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                let plants = json.map((plant) => {
                    return fetch(serverURLBase + "recipes?plant_id=" + plant.PlantID, {
                        method: 'get',
                        headers: new Headers({
                            'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
                        }),
                    })
                        .then(results => results.json())
                        .then(json => {
                            this.setState({
                                isLoaded: true,
                                recipes_count: this.state.recipes_count + json.length
                            })
                        })
                })
                this.setState({
                    isLoaded: true,
                })
                this.setState({ plants: plants });
            }).catch((error) => {
                console.log("Can't pull from API :(")
                console.error(error);
            });
    }
    render() {
        return (
            <Col xs="4" >
                <Button className="garden-stats-btn">

                    <Row>
                        <Col xs="12">
                            <h4>{this.state.recipes_count}</h4>
                        </Col>
                    </Row>
                    <Row>
                        <Col xs="12">
                            Recipes
                    </Col>
                    </Row>
                </Button>
            </Col>
        )
    }
}

class Distance extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            bed_location: {
                lat: 0,
                long: 0
            },
            user_location: {
                lat: 0,
                long: 0
            },
            distance: 0
        };

    }
    distance(lat1, lon1, lat2, lon2) {
        var p = 0.017453292519943295;    // Math.PI / 180
        var c = Math.cos;
        var a = 0.5 - c((lat2 - lat1) * p) / 2 +
            c(lat1 * p) * c(lat2 * p) *
            (1 - c((lon2 - lon1) * p)) / 2;

        return (12742 * Math.asin(Math.sqrt(a))) / 1.609; // 2 * R; R = 6371 km
    }

    componentDidMount() {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                let lat = position.coords.latitude
                let long = position.coords.longitude
                console.log("getCurrentPosition Success " + lat + long) // logs position correctly
                this.setState({
                    user_location: {
                        lat: lat,
                        long: long
                    }
                })
            },
            (error) => {
                alert("Could not find location");
                console.error(JSON.stringify(error))
            },
            { enableHighAccuracy: true, timeout: 20000, maximumAge: 1000 }
        )

        fetch(serverURLBase + "beds?bed_id=" + this.props.id, {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                this.setState({
                    isLoaded: true,
                    bed_location: {
                        lat: json.BedLatitude,
                        long: json.BedLongitude
                    }
                })
            }).catch((error) => {
                console.log("Can't pull from API :(")
                console.error(error);
            });
    }

    render() {
        return (
            <Col xs="4" >
                <Button className="garden-stats-btn">
                    <a href={"https://www.google.com/maps/dir//" + this.state.bed_location.lat + ","+ this.state.bed_location.long + "/"}>
                        <Row>
                            <Col xs="12">
                                <h4>{Math.round(this.distance(this.state.bed_location.lat, this.state.bed_location.long, this.state.user_location.lat, this.state.user_location.long) * 100) / 100}</h4>
                            </Col>
                        </Row>
                        <Row>
                            <Col xs="12">
                                mi. Away
                    </Col>
                        </Row>
                    </a>
                </Button>
            </Col>
        )
    }
}

class Description extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            description: ""
        };
    }

    componentDidMount() {
        fetch(serverURLBase + "beds?bed_id=" + this.props.id, {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                this.setState({
                    isLoaded: true,
                    description: json.BedDescription
                })
            }).catch((error) => {
                console.log("Can't pull from API :(")
                console.error(error);
            });
    }
    render() {
        return (
            <div className="garden-description">
                <p className="d-none d-md-block">
                    {this.state.description}
                </p>
            </div>
        )
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
            }).catch((error) => {
                console.log("Can't pull from API :(")
                console.error(error);
            });
    }
    render() {
        return (
            <Col xs="4" md="3">
                <Link to={`/plants/${this.props.plant_id}`} >
                    <figure className="figure circle">
                        <img className="figure-img " src={this.state.photo} alt={this.props}></img>
                        <figcaption className="figure-caption">{this.props.name}</figcaption>
                    </figure>
                </Link>
            </Col>
        )
    }
}

class MorePlants extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            plants: [],
            plants_mobile: [],
        };
    }
    componentDidMount() {
        fetch(serverURLBase + "plants?bed_id=" + this.props.id, {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                let plants = json.map((plant) => {
                    return (
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
            }).catch((error) => {
                console.log("Can't pull from API :(")
                console.error(error);
            });
    }
    render() {
        for (let i = 0; i < 4; i++) {
            this.state.plants_mobile.push(this.state.plants[i]);
        }
        return (
            <div className="more-plants">
                <h4>
                    Plants
                </h4>
                {/* <div className="d-sm-none ">
                    <Row className="more-plants">
                        {this.state.plants_mobile}
                    </Row>

                            <Button>
                                More
                            </Button>
   
                </div> */}
                {/* <div className="d-none d-sm-block"> */}
                <div>
                    <Row >
                        {this.state.plants}
                    </Row>
                </div>
            </div>
        )
    }
}

export default Garden;  