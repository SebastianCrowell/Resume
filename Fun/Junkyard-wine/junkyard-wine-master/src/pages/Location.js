import React, {Component} from 'react';
import '../App.css';
import '@fortawesome/fontawesome-free/js/all.js';
import NavBar from '../components/NavBar';
import Footer from '../components/Footer';

class Location extends Component {
    render(){
        return(
            <div>
                <NavBar/>
                <Content/>
                <Footer/>
            </div>
        )
    }
}

class Content extends Component {
    render(){
        return(
            <div>
            <div class="location-bg row" style={{margin: 'auto'}}>
                <p class="col-8">
                Maecenas ullamcorper vehicula sapien, eget luctus ligula gravida ut. Praesent vitae hendrerit orci. Etiam malesuada efficitur venenatis. Aliquam erat volutpat. Etiam posuere ipsum ut tempus cursus. Duis fermentum nisl nec orci interdum, vitae sagittis lectus malesuada. Donec id dui eget risus pretium feugiat. Proin risus ex, auctor viverra consequat ac, pulvinar id enim. Proin vel enim ac velit fermentum suscipit. Mauris facilisis tellus non hendrerit fermentum. Phasellus quis pharetra mauris. Maecenas ultricies quam euismod urna pellentesque facilisis.
                Nam semper turpis justo, mollis commodo tellus pretium non. Cras at viverra dolor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nullam pretium pharetra mauris, eget gravida lectus sollicitudin eget. Vivamus sed egestas eros, ut mattis nunc. Maecenas efficitur augue vitae elit venenatis, vitae porttitor urna varius. Interdum et malesuada fames ac ante ipsum primis in faucibus. Suspendisse tincidunt, dui eget laoreet consectetur, tellus tellus blandit ligula, id luctus neque augue sed neque. Aliquam odio nibh, vulputate sed mauris in, aliquam vulputate risus. Donec sagittis lorem eu laoreet porta.
                Aliquam aliquam nulla eget dictum tincidunt. Interdum et malesuada fames ac ante ipsum primis in faucibus. Cras iaculis porttitor ipsum, sit amet lobortis ipsum semper a. Sed vestibulum ultricies molestie. Aliquam orci dolor, elementum nec risus sit amet, ullamcorper rhoncus mauris. Pellentesque ornare velit vitae vestibulum tempus. Curabitur pulvinar ornare pulvinar. Aliquam facilisis sit amet leo gravida placerat. In consequat purus lorem, a efficitur massa ullamcorper id. Phasellus sollicitudin vestibulum mi in eleifend. Integer dictum ante eu nisi fringilla faucibus in quis velit. Vestibulum luctus justo dui, eu lacinia risus sodales vitae. Mauris volutpat accumsan efficitur. Nullam nec magna ac nibh fermentum ornare tincidunt vel tellus. Morbi sit amet libero nec quam eleifend facilisis.
                Nulla facilisi. Morbi non leo ac est laoreet gravida a vel nisi. 
                </p>
                <div class="location-image1 col-4"/>
            </div>
            <div class="location-bg row" style={{margin: 'auto'}}>
                <div class="location-image2 col-4"/>
                <p class="col-8">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam pulvinar ipsum et lorem volutpat, a maximus nibh molestie. Maecenas ullamcorper vehicula sapien, eget luctus ligula gravida ut. Praesent vitae hendrerit orci. Etiam malesuada efficitur venenatis. Aliquam erat volutpat. Etiam posuere ipsum ut tempus cursus. Duis fermentum nisl nec orci interdum, vitae sagittis lectus malesuada. Donec id dui eget risus pretium feugiat. Proin risus ex, auctor viverra consequat ac, pulvinar id enim. Proin vel enim ac velit fermentum suscipit. Mauris facilisis tellus non hendrerit fermentum. Phasellus quis pharetra mauris. Maecenas ultricies quam euismod urna pellentesque facilisis.
                Nam semper turpis justo, mollis commodo tellus pretium non. Cras at viverra dolor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nullam pretium pharetra mauris, eget gravida lectus sollicitudin eget. Vivamus sed egestas eros, ut mattis nunc. Maecenas efficitur augue vitae elit venenatis, vitae porttitor urna varius. Interdum et malesuada fames ac ante ipsum primis in faucibus. Suspendisse tincidunt, dui eget laoreet consectetur, tellus tellus blandit ligula, id luctus neque augue sed neque. Aliquam odio nibh, vulputate sed mauris in, aliquam vulputate risus. Donec sagittis lorem eu laoreet porta.
                Aliquam aliquam nulla eget dictum tincidunt. Interdum et malesuada fames ac ante ipsum primis in faucibus.
                </p>
            </div>
            </div>
        )
    }
}

export default Location;