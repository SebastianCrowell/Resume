import React, { Component } from 'react';
import { Container, Row, Col, ListGroup, Button, Card, CardImg, CardImgOverlay, ListGroupItem } from 'reactstrap';
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { Link } from 'react-router-dom';
import { faLeaf, faMapPin, faNewspaper, faInfoCircle, faScroll, faArrowLeft } from '@fortawesome/free-solid-svg-icons'
var serverURLBase = 'https://ediblecampusapi-dept-botanicalgarden.cloudapps.unc.edu/';

library.add(faLeaf, faMapPin, faNewspaper, faInfoCircle, faScroll, faArrowLeft)

class Plant extends Component {
    render() {
        const plant_id = this.props.plant_id;
        return (
            <Container id="mapp">
                <BackButton />
                <Picture id={plant_id} />
                <Description id={plant_id} />
                <Locations id={plant_id} />
                <Recipes id={plant_id} />
            </Container>
        );
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
        fetch(serverURLBase + "pictures?plant_id=" + this.props.id, {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                this.setState({
                    isLoaded: true,
                    thumbnail: json.PicURL
                })
            })
    }
    render() {
        return (
            <div>
                <Card className="plant-single-card">
                    <CardImg src={this.state.thumbnail} alt="Card image cap" />
                    <CardImgOverlay >
                        <FontAwesomeIcon icon="leaf" size="2x" className="garden-link-icon" />
                    </CardImgOverlay>
                </Card>
            </div>
        )
    }
}

class BackButton extends Component {
    render() {
        return (
            <Row className="mt-4 pb-2">
                <Col>
                    <Link to="/">
                        <FontAwesomeIcon icon="arrow-left" size="lg" className="back-button" />
                    </Link>
                </Col>
            </Row>
        );
    }
}

class Description extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            name: "",
            latin_name: "",
            descriptors: [],
            meta_data: []
        };
    }

    componentDidMount() {
        fetch(serverURLBase + "plants?plant_id=" + this.props.id, {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                this.setState({
                    isLoaded: true,
                    name: json.PlantName,
                    latin_name: json.LatinName,
                })
                let descriptors = [];
                let meta_data = []
                for (var i in json) {
                    if (json[i] !== "" && (i === "TimeOfBloom" || i === "Origin" || i === "EdibleParts" || i === "Season")) {
                        if (i === "TimeOfBloom") {meta_data.push(<MetaData title="Lifecycle" description={json[i]} />); }
                        else meta_data.push(<MetaData title={i.split(/(?=[A-Z])/).join(" ")} description={json[i]} />);
                    }
                    else if (json[i] !== "" && i !== "PlantID" & i !== "LatinName" && i !== "PlantName") {
                        if (i === "ReadyToPick") {
                            descriptors.push(<Descriptor title={"How Do I Know Plant is Ready to Pick?"} description={json[i]} />);
                        } else {
                            descriptors.push(<Descriptor title={i.split(/(?=[A-Z])/).join(" ")} description={json[i]} />);
                        }
                    }
                }
                this.setState({
                    descriptors: descriptors,
                    meta_data: meta_data
                });
            })
    }
    render() {
        return (
            <div className="garden-description">
                <h3>{this.state.name}</h3>
                <sup>{this.state.latin_name}</sup>
                <Row>
                    {this.state.meta_data}
                </Row>
                <div>
                    {this.state.descriptors}
                </div>
            </div>
        )
    }
}

class Descriptor extends Component {
    render() {
        return (
            <div>
                <dl className="row">
                    <dt className="col-sm-2">
                        {this.props.title}
                    </dt>
                    <dd className="col-sm-10">
                        {this.props.description}
                    </dd>
                </dl>
            </div>
        )
    }
}

class MetaData extends Component {
    render() {
        return (
            <Col  className="pt-2 mb-1 border-top border-bottom">
                <p className="font-weight-bold">
                    {this.props.title}
                </p>
                <p>
                    {this.props.description}
                </p>
            </Col>
        )
    }
}

class Locations extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            beds: []
        };
    }

    componentDidMount() {
        fetch(serverURLBase + "beds?plant_id=" + this.props.id, {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                if (json.length === 0) {
                    this.setState({
                        beds: [<p>No locations for this plant yet <span role="img" aria-label="Gardens">🏡🌳🌾</span></p>]
                    })
                }
                else {
                let beds = json.map((bed) => {
                    return (
                        <BedLink
                            bed_id={bed.BedID}
                            name={bed.BedName}
                            key={bed.BedName} />
                    )
                })
                this.setState({
                    isLoaded: true,
                })
                this.setState({ beds: beds });
            }})
            
    }
    render() {
        return (
            <div className="bed-locations">
                <h4>Locations</h4>
                {this.state.beds}
            </div>
        )
    }
}

class BedLink extends Component {
    render() {
        return (
            <Link to={`/gardens/${this.props.bed_id}/`}>
                <Button outline className="m-2">
                    {this.props.name}
                </Button>
            </Link>
        )
    }
}

class Recipes extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            recipes: []
        };
    }

    componentDidMount() {
        fetch(serverURLBase + "recipes?plant_id=" + this.props.id, {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                console.log(json);
                if (json.length === 0) {
                    this.setState({
                        recipes: [<p>No recipes for this plant yet <span role="img" aria-label="Vegetables">🍆🥕🥒</span></p>]
                    })
                }
                else {
                    let recipes = json.map((recipe) => {
                        return (
                            <RecipeLink
                                recipe_id={recipe.RecipeID}
                                name={recipe.RecipeName}
                                link={recipe.RecipeURL}
                                key={recipe.RecipeID} />
                        )
                    })
                    this.setState({
                        isLoaded: true,
                    })
                    this.setState({ recipes: recipes });
                }
            })
    }
    render() {
        // if (!Array.isArray(this.state.recipes) || !this.state.recipes.length) {
        // array does not exist, is not an array, or is empty
        return (
            <div>
                <h4>Recipes</h4>
                <ListGroup flush>
                    {this.state.recipes}
                </ListGroup>
            </div>
        )
    }
    // }
}

class RecipeLink extends Component {
    render() {
        return (
            <ListGroupItem tag="a" href={this.props.link} >{this.props.name}</ListGroupItem>
        )
    }
}

export default Plant;    