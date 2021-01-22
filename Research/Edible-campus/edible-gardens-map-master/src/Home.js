import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';
import { Container, Row, Col } from 'reactstrap';
import { Link } from 'react-router-dom';
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faLeaf, faMapSigns, faSearch, faInfoCircle, faUtensils, faSeedling } from '@fortawesome/free-solid-svg-icons'
library.add(faLeaf, faMapSigns, faSearch, faInfoCircle, faUtensils, faSeedling)
var serverURLBase = 'https://ediblecampusapi-dept-botanicalgarden.cloudapps.unc.edu/';



class Home extends Component {

    render() {
        return (
            <div>
                <GoogleMap />

                <Container>
                    <Header />
                    <NavButtonRow navButtons={this.props.navButtons} />
                </Container>
            </div>
        );
    }
}
class Header extends Component {
    render() {
        return (
            <img className="mapp-header mx-auto d-block mt-3 mb-3" alt="edible gardens logo" src={require('./images/eg_logo.png')} />
        )
    }
}
class NavButton extends Component {
    render() {
        const navButton = this.props.navButton;

        return (
            <Col xs="4" className="text-center">
                <Link to={navButton.link}>

                    <Row>

                        <Col xs="12">
                            <div className="home-link-icon circle align-middle text-center" >
                                <FontAwesomeIcon icon={navButton.icon} />
                            </div>
                        </Col>
                    </Row>
                    <Row>
                        <Col xs="12">
                            <div className="home-link-caption text-center">
                                {navButton.caption}
                            </div>
                        </Col>
                    </Row>
                </Link>
            </Col>
        );
    }
}
class NavButtonRow extends Component {
    render() {
        const columns = [];
        this.props.navButtons.forEach((navButton) => {
            console.log(navButton);
            columns.push(
                <NavButton
                    navButton={navButton}
                    link={navButton.link}
                    icon={navButton.icon}
                    caption={navButton.caption}
                    key={navButton.caption} />
            );
        })
        return (
            <Row className="mapp-nav-button-row align-center">
                {columns}
            </Row>
        )
    }
}
class GardenMarker extends Component {
    render() {
        if (this.props.id === 5) {
            return (
                <div>
                    <Link to={`/gardens/${this.props.id}/`}>
                        <img className="garden-marker garden-marker-main" alt="main-garden" src={require('./images/main_garden.png')} />
                        <div className="map-infobox" ><p>{this.props.name}</p></div>
                    </Link>
                </div>
            );
        }
        return (
            <div>
                <Link to={`/gardens/${this.props.id}/`}>
                    <FontAwesomeIcon icon="utensils" size="2x" className="garden-marker" />
                    <div className="map-infobox" ><p>{this.props.name}</p></div>
                </Link>
            </div>
        );
    }
}

class GoogleMap extends Component {

    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            beds: []
        };
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
                        <GardenMarker
                            id={bed.BedID}
                            name={bed.BedName}
                            lat={bed.BedLatitude}
                            lng={bed.BedLongitude}
                            key={bed.BedName} />
                    )
                })
                this.setState({
                    isLoaded: true,
                })
                this.setState({ beds: beds });
            }).catch((error) => {
                console.log("Can't pull from API :(")
                console.error(error);
            });
    }

    static defaultProps = {
        center: {
            lat: 35.912,
            lng: -79.047
        },
        zoom: 15
    };
    render() {
        const { error, isLoaded } = this.state;
        if (error) {
            return <div>Error: {error.message}</div>;
        } else if (!isLoaded) {
            return <div>Loading...</div>;
        } else {
            return (
                // Important! Always set the container height explicitly
                <div style={{ height: '100%', width: '100%', top: '0px', position: 'absolute', zIndex: '-1' }}>
                    <GoogleMapReact className="mapp-map"
                        bootstrapURLKeys={{ key: process.env.REACT_APP_GMAPS_API_KEY }}
                        defaultCenter={this.props.center}
                        defaultZoom={this.props.zoom}
                        onChildMouseDown={this.onChildMouseDown}>
                        {this.state.beds}
                    </GoogleMapReact>
                </div>
            );
        }
    };
}

export default Home;    