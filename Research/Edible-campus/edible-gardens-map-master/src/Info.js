import React, { Component } from 'react';
import { Container, Col, Row, Button } from 'reactstrap';
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { Link } from 'react-router-dom';
import { faLeaf, faUtensils, faArrowLeft, faEnvelope, faQuestionCircle } from '@fortawesome/free-solid-svg-icons'
var serverURLBase = 'https://ediblecampusapi-dept-botanicalgarden.cloudapps.unc.edu/';

library.add(faLeaf, faUtensils, faArrowLeft, faEnvelope, faQuestionCircle)

class Info extends Component {
    render() {

        return (
            <div className="green-background full-width full-height pb-5">
                <Container >
                    <Header />
                    <Body />
                    <Footer />
                </Container>

            </div>
        );
    }
}

class BackButton extends Component {
    render() {
        return (
            <Col xs="1" className="align-middle">
                <Link to="/">
                    <FontAwesomeIcon icon="arrow-left" size="lg" className="back-button" />
                </Link>
            </Col>
        );
    }
}

// class Logo extends Component {
//     render() {
//         return (
//                 <Col xs="4" >
//                     <img className="botanical-logo mx-auto d-block grayscale" src={require(this.props.url)}></img>
//                 </Col>
//         )
//     }
// }

class Header extends Component {
    render() {
        return (
            <Row className="pt-3">
                <BackButton />
                <Col xs={{ size: 'auto', offset: 1 }}></Col>
                {/* <Logo url="./images/ncbg_logo.png"/> */}
                <Col xs="4" >
                    <img className="botanical-logo mx-auto d-block grayscale" alt="botanical garden logo" src={require("./images/ncbg_logo.png")}></img>
                </Col>
                <Col xs="4" >
                    <img className="botanical-logo mx-auto d-block grayscale" alt="edible garden logo" src={require("./images/eg_logo.png")}></img>
                </Col>
            </Row>
        )
    }
}

class Body extends Component {
    render() {
        return (
            <div className="pt-3">
                <h1 className="pb-4">Edible Campus Garden Map</h1>
                {/* TODO: Make this an API call to an FAQ */}
                <p>
                    Edible Campus UNC cultivates edible landscapes across the UNC-Chapel Hill campus to facilitate community engagement with sustainable food systems. Our team has converted eleven "satellite" garden beds across campus to incorporate edible, medicinal and pollinator friendly plants--free for passersby to pick! We maintain these gardens with the help of students in the residence halls and allied organizations. We also manage a quarter-acre production garden in the heart the UNC-CH campus that serves as an educational and community resource. The food in this main garden is allocated to the Carolina Cupboard student-run food pantry and used for educational programming. We look forward to seeing you in the gardens. Until then, forage on!
                </p>
            </div>
        )
    }
}

class Footer extends Component {
    render() {
        return (
            <div className="edible-campus-links">

                <Row>
                    <Col>
                        <a href="http://ediblecampus.web.unc.edu/">
                            <Row>
                                <Col xs="1"><FontAwesomeIcon icon="utensils"></FontAwesomeIcon>  </Col>
                                <Col xs="10">Edible Campus UNC</Col>
                            </Row>
                        </a>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <a href="http://ncbg.unc.edu/">
                            <Row>
                                <Col xs="1">
                                    <FontAwesomeIcon icon="leaf"></FontAwesomeIcon>
                                </Col>
                                <Col xs="10">North Carolina Botanical Garden</Col>
                            </Row>
                        </a>
                    </Col>
                </Row>
                <FAQs />
                <a href="mailto:ediblecampus@gmai.com">
                    <Button className="mt-3 green">
                        <FontAwesomeIcon icon="envelope"></FontAwesomeIcon> Contact Us
                    </Button>
                </a>
            </div>

        )
    }
}

class FAQs extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            faqs: []
        };
    }

    componentDidMount() {
        fetch(serverURLBase + "faqs?", {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Basic ' + btoa(process.env.REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS),
            }),
        })
            .then(results => results.json())
            .then(json => {
                let faqs = json.map((faq) => {
                    if (faq.QID !== 14) {
                        return (
                            <FAQ
                                faq_id={faq.QID}
                                question={faq.Question}
                                answer={faq.Answer}
                                key={faq.QID} />
                        )
                    }
                    return null;
                })
                this.setState({
                    isLoaded: true,
                })
                this.setState({ faqs: faqs });
            })
    }
    render() {
        return (
            <div className="mt-1" id="faq">
                <a class="btn green" data-toggle="collapse" href="#accordion" role="button" aria-expanded="false" aria-controls="accordion">
                    <FontAwesomeIcon icon="question-circle"></FontAwesomeIcon> FAQs
                </a>
                <div id="accordion" className="mt-3 collapse">
                    {this.state.faqs}
                </div>

            </div>
        )
    }
}

class FAQ extends Component {
    render() {
        return (
            <div className="card">
                <div className="card-header" id={`heading` + this.props.faq_id}>
                    <h5 className="mb-0">
                        <button className="btn btn-link" data-toggle="collapse" data-target={`#collapse` + this.props.faq_id} aria-controls={`collapse` + this.props.faq_id}>
                            {this.props.question}
                        </button>
                    </h5>
                </div>

                <div id={`collapse` + this.props.faq_id} className="collapse" aria-labelledby={`heading` + this.props.faq_id} data-parent="#accordion">
                    <div className="card-body">
                        {this.props.answer}
                    </div>
                </div>
            </div>

        )
    }
}


export default Info;  