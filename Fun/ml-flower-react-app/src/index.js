import React from 'react'
import ReactDOM from 'react-dom'

import useModel from './useModel'
import ObjectDetectionVideo from './object-detection-video/cam-obj-detection'
import { predictionText } from './object-detection-video/cam-obj-detection'

import './index.css'

const App = () => {
  const model = useModel(process.env.PUBLIC_URL + '/model_web')
  let firstFlower = "";
  let secondFlower = "";
  let thirdLine = "";
  let fourthLine = "";
  var color;
  var colorTwo;

  // let cheerio = require('cheerio')
  // let $;
  // var wordsThatRhyme = [];

  const createPoem = () => {
    var newContentFirst = document.getElementById("first");
    var newContentSecond = document.getElementById("second");
    var random = 0;
    updatePoem();
    try {
      firstFlower = predictionText[0].class;
      //console.log(predictionText[0].class)
      random = 0;
      switch(predictionText[0].class){
        case "Aster":
          firstFlower = firstFlower + "s";
          random = Math.floor((Math.random() * 5) + 1);
          switch(random){
            case 1:
              color = "purple";
              break;
            case 2:
              color = "pink";
              break;
            case 3:
              color = "blue";
              break;
            case 4:
              color = "orange";
              break;
            case 5:
              color = "white";
              break;
            default:
          }
          //purple, pink, blue, orange, white
          break;
        case "Azalea":
          firstFlower = firstFlower + "s";
          random = Math.floor((Math.random() * 5) + 1);
          switch(random){
            case 1:
              color = "red";
              break;
            case 2:
              color = "pink";
              break;
            case 3:
              color = "yellow";
              break;
            case 4:
              color = "white";
              break;
            case 5:
              color = "purple";
              break;
            default:
          }
          //red, pink, yellow, white, purple
          break;
        case "Bluestar":
          firstFlower = firstFlower + "s";
          color = "blue";
          //blue
          break;
        case "Coneflower":
          firstFlower = firstFlower + "s";
          random = Math.floor((Math.random() * 5) + 1);
          switch(random){
            case 1:
              color = "pink";
              break;
            case 2:
              color = "yellow";
              break;
            case 3:
              color = "orange";
              break;
            case 4:
              color = "red";
              break;
            case 5:
              color = "white";
              break;
            default:
          }
          //pink, yellow, orange, red, white
          break;
        case "Daisy":
          firstFlower = firstFlower + "s";
          random = Math.floor((Math.random() * 5) + 1);
          switch(random){
            case 1:
              color = "white";
              break;
            case 2:
              color = "orange";
              break;
            case 3:
              color = "pink";
              break;
            case 4:
              color = "purple";
              break;
            case 5:
              color = "blue";
              break;
            default:
          }
          //white, orange, pink, purple, blue
          break;
        case "Dandelion":
          firstFlower = firstFlower + "s";
          random = Math.floor((Math.random() * 2) + 1);
          switch(random){
            case 1:
              color = "white";
              break;
            case 2:
              color = "yellow";
              break;
            default:
          }
          //white, yellow
          break;
        case "Iris":
          firstFlower = firstFlower + "'s";
          random = Math.floor((Math.random() * 7) + 1);
          switch(random){
            case 1:
              color = "blue";
              break;
            case 2:
              color = "yellow";
              break;
            case 3:
              color = "white";
              break;
            case 4:
              color = "red";
              break;
            case 5:
              color = "black";
              break;
            case 6:
              color = "pink";
              break;
            case 7:
              color = "purple";
              break;
            default:
          }
          //blue, yellow, white, red, black, pink, purple
          break;
        case "Lily":
          firstFlower = firstFlower + "'s";
          random = Math.floor((Math.random() * 7) + 1);
          switch(random){
            case 1:
              color = "white";
              break;
            case 2:
              color = "pink";
              break;
            case 3:
              color = "orange";
              break;
            case 4:
              color = "green";
              break;
            case 5:
              color = "black";
              break;
            case 6:
              color = "red";
              break;
            case 7:
              color = "yellow";
              break;
            default:
          }
          //white, pink, orange, green, black, red, yellow
          break;
        case "Lupine":
          firstFlower = firstFlower + "s";
          color = "yellow";
          //yellow
          break;
        case "Magnolia":
          firstFlower = firstFlower + "s";
          random = Math.floor((Math.random() * 2) + 1);
          switch(random){
            case 1:
              color = "white";
              break;
            case 2:
              color = "pink";
              break;
            default:
          }
          //white, pink
          break;
        case "Orchid":
          firstFlower = firstFlower + "s";
          random = Math.floor((Math.random() * 5) + 1);
          switch(random){
            case 1:
              color = "white";
              break;
            case 2:
              color = "pink";
              break;
            case 3:
              color = "purple";
              break;
            case 4:
              color = "yellow";
              break;
            case 5:
              color = "red";
              break;
            default:
          }
          //white, pink, purple, yellow, red
          break;
        case "Pansies":
          random = Math.floor((Math.random() * 6) + 1);
          switch(random){
            case 1:
              color = "purple";
              break;
            case 2:
              color = "blue";
              break;
            case 3:
              color = "yellow";
              break;
            case 4:
              color = "white";
              break;
            case 5:
              color = "red";
              break;
            case 6:
              color = "black";
              break;
            default:
          }
          //purple, blue, yellow, white, red, black
          break;
        case "Roses":
          random = Math.floor((Math.random() * 8) + 1);
          switch(random){
            case 1:
              color = "pink";
              break;
            case 2:
              color = "red";
              break;
            case 3:
              color = "white";
              break;
            case 4:
              color = "orange";
              break;
            case 5:
              color = "yellow";
              break;
            case 6:
              color = "black";
              break;
            case 7:
              color = "purple";
              break;
            case 8:
              color = "blue";
              break;
            default:
          }
          //pink, red, white, orange, yellow, black, purple, blue
          break;
        case "Sunflower":
          firstFlower = firstFlower + "s";
          random = Math.floor((Math.random() * 2) + 1);
          switch(random){
            case 1:
              color = "yellow";
              break;
            case 2:
              color = "white";
              break;
            default:
          }
          //yellow, white
          break;
        case "Thimbleweed":
          firstFlower = firstFlower + "s";
          random = Math.floor((Math.random() * 2) + 1);
          switch(random){
            case 1:
              color = "white";
              break;
            case 2:
              color = "pink";
              break;
            default:
          }
          //white, pink
          break;
        case "Tulip":
          firstFlower = firstFlower + "s";
          random = Math.floor((Math.random() * 6) + 1);
          switch(random){
            case 1:
              color = "orange";
              break;
            case 2:
              color = "white";
              break;
            case 3:
              color = "yellow";
              break;
            case 4:
              color = "red";
              break;
            case 5:
              color = "purple";
              break;
            case 6:
              color = "pink";
              break;
            default:
          }
          //orange, white, yellow, red, purple, pink
          break;
        case "Verbena":
          firstFlower = firstFlower + "s";
          random = Math.floor((Math.random() * 4) + 1);
          switch(random){
            case 1:
              color = "white";
              break;
            case 2:
              color = "pink";
              break;
            case 3:
              color = "purple";
              break;
            case 4:
              color = "red";
              break;
            default:
          }
          //red, white, pink, purple
          break;
        default:
      }
      ReactDOM.render(firstFlower + " are " + color + ",", newContentFirst);
    } catch (error) {}
    try {
      secondFlower = predictionText[1].class;
      random = 0;
      switch(predictionText[1].class){
        case "Aster":
          secondFlower = secondFlower + "s";
          random = Math.floor((Math.random() * 5) + 1);
          switch(random){
            case 1:
              colorTwo = "purple";
              break;
            case 2:
              colorTwo = "pink";
              break;
            case 3:
              colorTwo = "blue";
              break;
            case 4:
              colorTwo = "orange";
              break;
            case 5:
              colorTwo = "white";
              break;
            default:
          }
          //purple, pink, blue, orange, white
          break;
        case "Azalea":
          secondFlower = secondFlower + "s";
          random = Math.floor((Math.random() * 5) + 1);
          switch(random){
            case 1:
              colorTwo = "red";
              break;
            case 2:
              colorTwo = "pink";
              break;
            case 3:
              colorTwo = "yellow";
              break;
            case 4:
              colorTwo = "white";
              break;
            case 5:
              colorTwo = "purple";
              break;
            default:
          }
          //red, pink, yellow, white, purple
          break;
        case "Bluestar":
          secondFlower = secondFlower + "s";
          colorTwo = "blue";
          //blue
          break;
        case "Coneflower":
          secondFlower = secondFlower + "s";
          random = Math.floor((Math.random() * 5) + 1);
          switch(random){
            case 1:
              colorTwo = "pink";
              break;
            case 2:
              colorTwo = "yellow";
              break;
            case 3:
              colorTwo = "orange";
              break;
            case 4:
              colorTwo = "red";
              break;
            case 5:
              colorTwo = "white";
              break;
            default:
          }
          //pink, yellow, orange, red, white
          break;
        case "Daisy":
          secondFlower = secondFlower + "s";
          random = Math.floor((Math.random() * 5) + 1);
          switch(random){
            case 1:
              colorTwo = "white";
              break;
            case 2:
              colorTwo = "orange";
              break;
            case 3:
              colorTwo = "pink";
              break;
            case 4:
              colorTwo = "purple";
              break;
            case 5:
              colorTwo = "blue";
              break;
            default:
          }
          //white, orange, pink, purple, blue
          break;
        case "Dandelion":
          secondFlower = secondFlower + "s";
          random = Math.floor((Math.random() * 2) + 1);
          switch(random){
            case 1:
              colorTwo = "white";
              break;
            case 2:
              colorTwo = "yellow";
              break;
            default:
          }
          //white, yellow
          break;
        case "Iris":
          secondFlower = secondFlower + "'s";
          random = Math.floor((Math.random() * 7) + 1);
          switch(random){
            case 1:
              colorTwo = "blue";
              break;
            case 2:
              colorTwo = "yellow";
              break;
            case 3:
              colorTwo = "white";
              break;
            case 4:
              colorTwo = "red";
              break;
            case 5:
              colorTwo = "black";
              break;
            case 6:
              colorTwo = "pink";
              break;
            case 7:
              colorTwo = "purple";
              break;
            default:
          }
          //blue, yellow, white, red, black, pink, purple
          break;
        case "Lily":
          secondFlower = secondFlower + "'s";
          random = Math.floor((Math.random() * 7) + 1);
          switch(random){
            case 1:
              colorTwo = "white";
              break;
            case 2:
              colorTwo = "pink";
              break;
            case 3:
              colorTwo = "orange";
              break;
            case 4:
              colorTwo = "green";
              break;
            case 5:
              colorTwo = "black";
              break;
            case 6:
              colorTwo = "red";
              break;
            case 7:
              colorTwo = "yellow";
              break;
            default:
          }
          //white, pink, orange, green, black, red, yellow
          break;
        case "Lupine":
          secondFlower = secondFlower + "s";
          colorTwo = "yellow";
          //yellow
          break;
        case "Magnolia":
          secondFlower = secondFlower + "s";
          random = Math.floor((Math.random() * 2) + 1);
          switch(random){
            case 1:
              colorTwo = "white";
              break;
            case 2:
              colorTwo = "pink";
              break;
            default:
          }
          //white, pink
          break;
        case "Orchid":
          secondFlower = secondFlower + "s";
          random = Math.floor((Math.random() * 5) + 1);
          switch(random){
            case 1:
              colorTwo = "white";
              break;
            case 2:
              colorTwo = "pink";
              break;
            case 3:
              colorTwo = "purple";
              break;
            case 4:
              colorTwo = "yellow";
              break;
            case 5:
              colorTwo = "red";
              break;
            default:
          }
          //white, pink, purple, yellow, red
          break;
        case "Pansies":
          random = Math.floor((Math.random() * 6) + 1);
          switch(random){
            case 1:
              colorTwo = "purple";
              break;
            case 2:
              colorTwo = "blue";
              break;
            case 3:
              colorTwo = "yellow";
              break;
            case 4:
              colorTwo = "white";
              break;
            case 5:
              colorTwo = "red";
              break;
            case 6:
              colorTwo = "black";
              break;
            default:
          }
          //purple, blue, yellow, white, red, black
          break;
        case "Roses":
          random = Math.floor((Math.random() * 8) + 1);
          switch(random){
            case 1:
              colorTwo = "pink";
              break;
            case 2:
              colorTwo = "red";
              break;
            case 3:
              colorTwo = "white";
              break;
            case 4:
              colorTwo = "orange";
              break;
            case 5:
              colorTwo = "yellow";
              break;
            case 6:
              colorTwo = "black";
              break;
            case 7:
              colorTwo = "purple";
              break;
            case 8:
              colorTwo = "blue";
              break;
            default:
          }
          //pink, red, white, orange, yellow, black, purple, blue
          break;
        case "Sunflower":
          secondFlower = secondFlower + "s";
          random = Math.floor((Math.random() * 2) + 1);
          switch(random){
            case 1:
              colorTwo = "yellow";
              break;
            case 2:
              colorTwo = "white";
              break;
            default:
          }
          //yellow, white
          break;
        case "Thimbleweed":
          secondFlower = secondFlower + "s";
          random = Math.floor((Math.random() * 2) + 1);
          switch(random){
            case 1:
              colorTwo = "white";
              break;
            case 2:
              colorTwo = "pink";
              break;
            default:
          }
          //white, pink
          break;
        case "Tulip":
          secondFlower = secondFlower + "s";
          random = Math.floor((Math.random() * 6) + 1);
          switch(random){
            case 1:
              colorTwo = "orange";
              break;
            case 2:
              colorTwo = "white";
              break;
            case 3:
              colorTwo = "yellow";
              break;
            case 4:
              colorTwo = "red";
              break;
            case 5:
              colorTwo = "purple";
              break;
            case 6:
              colorTwo = "pink";
              break;
            default:
          }
          //orange, white, yellow, red, purple, pink
          break;
        case "Verbena":
          secondFlower = secondFlower + "s";
          random = Math.floor((Math.random() * 4) + 1);
          switch(random){
            case 1:
              colorTwo = "white";
              break;
            case 2:
              colorTwo = "pink";
              break;
            case 3:
              colorTwo = "purple";
              break;
            case 4:
              colorTwo = "red";
              break;
            default:
          }
          //red, white, pink, purple
          break;
        default:
      }
      //console.log(predictionText[1].class)
      // $ = cheerio.load('https://www.rhymezone.com/r/rhyme.cgi?Word=' + colorTwo + '&typeofrhyme=perfect&org1=syl&org2=l&org3=y');
      ReactDOM.render(secondFlower + " are " + colorTwo + ",", newContentSecond);
    } catch (error) {
      colorTwo = "pink";
      // $ = cheerio.load('https://www.rhymezone.com/r/rhyme.cgi?Word=' + colorTwo + '&typeofrhyme=perfect&org1=syl&org2=l&org3=y');
      ReactDOM.render("Chrysanthemum are " + colorTwo + ",", newContentSecond);
    }
  }

  const updatePoem = () => {
    var newContentThrid = document.getElementById("third");
    var newContentFourth = document.getElementById("fourth");

    var random = 0;

    switch(colorTwo){
      case "white":
        random = Math.floor((Math.random() * 4) + 1);
        switch(random){
          case 1:
            fourthLine = "None as fair to hear my plight."
            break;
          case 2:
            fourthLine = "Your smile cracks the night."
            break;
          case 3:
            fourthLine = "For sore eyes, you are a sight."
            break;
          case 4:
            fourthLine = "Will you join me in the moonlight?"
            break;
          default:
        }
        break;
      case "black":
        random = Math.floor((Math.random() * 4) + 1);
        switch(random){
          case 1:
            fourthLine = "I'll always have your back."
            break;
          case 2:
            fourthLine = "With you its hard to keep track."
            break;
          case 3:
            fourthLine = "You've got me babbling like a maniac."
            break;
          case 4:
            fourthLine = "I forget everything before you like an amnesiac."
            break;
          default:
          }
        break;
      case "blue":
      random = Math.floor((Math.random() * 4) + 1);
      switch(random){
        case 1:
          fourthLine = "We stick together like glue."
          break;
        case 2:
          fourthLine = "I like someone for valentine, ill give you a clue."
          break;
        case 3:
          fourthLine = "Someones my cruch, guess who."
          break;
        case 4:
          fourthLine = "I think i've fallen for you."
          break;
        default:
        }
        break;
      case "red":
        random = Math.floor((Math.random() * 4) + 1);
        switch(random){
          case 1:
            fourthLine = "You make thoughts swim in my head."
            break;
          case 2:
            fourthLine = "I was going to ask Chris Pratt, but will you be my valentine instead."
            break;
          case 3:
            fourthLine = "With me you'll never be misled."
            break;
          case 4:
            fourthLine = "Seeing you made me stop dead."
            break;
          default:
        }
        break;
      case "blue":
        random = Math.floor((Math.random() * 4) + 1);
        switch(random){
          case 1:
            fourthLine = "We stick together like glue."
            break;
          case 2:
            fourthLine = "I like someone for valentine, ill give you a clue."
            break;
          case 3:
            fourthLine = "Someones my cruch, guess who."
            break;
          case 4:
            fourthLine = "I think i've fallen for you."
            break;
          default:
          }
        break;
      case "yellow":
        random = Math.floor((Math.random() * 4) + 1);
        switch(random){
          case 1:
            fourthLine = "You're sweeter than a marshmellow."
            break;
          case 2:
            fourthLine = "You make me squishy like jell-o."
            break;
          case 3:
            fourthLine = "You get my heart going like an espresso."
            break;
          case 4:
            fourthLine = "I love how you keep it mellow."
            break;
          default:
          }
        break;
      case "orange":
      random = Math.floor((Math.random() * 4) + 1);
      switch(random){
        case 1:
          fourthLine = "The way you are is adoring."
          break;
        case 2:
          fourthLine = "Moments with you are never boring."
          break;
        case 3:
          fourthLine = "Around you my hearts a pouring."
          break;
        case 4:
          fourthLine = "Your love is like a torrent."
          break;
        default:
        }
      break;
      case "purple":
      random = Math.floor((Math.random() * 4) + 1);
      switch(random){
        case 1:
          fourthLine = "Like achilles tendon you make me mortal."
          break;
        case 2:
          fourthLine = "Your eyes like twin stars twinkle and sparkle."
          break;
        case 3:
          fourthLine = "You've got me running in circles."
          break;
        case 4:
          fourthLine = "Being with you today is one thing for which I am certain."
          break;
        default:
        }
      break;
      case "pink":
      random = Math.floor((Math.random() * 4) + 1);
      switch(random){
        case 1:
          fourthLine = "You make it hard to think."
          break;
        case 2:
          fourthLine = "You are my missing link."
          break;
        case 3:
          fourthLine = "We are always so in sync."
          break;
        case 4:
          fourthLine = "You've got me on the brink."
          break;
        default:
        }
      break;
      default:
    }

    ReactDOM.render("Wishing you a happy valentine,", newContentThrid);
    ReactDOM.render(fourthLine, newContentFourth);
  }

  return (
    <div className="card container bg-dark">
      <div className="row">
        <div className="fillPage col-8">
          <ObjectDetectionVideo
            model={model}
            // aspectFill: The option to scale the video to fill the size of the view.
            //             Some portion of the video may be clipped to fill the view's
            //             bounds.
            // aspectFit:  The option to scale the video to fit the size of the view
            //             by maintaining the aspect ratio. Any remaining area of the
            //             view's bounds is transparent.
            fit="aspectFill"
          />
        </div>
        <div className ="padded-poem col-4">
          <div className ="card">
            <div className ="poem card-header">Poem:</div>
            <div className ="poem card-body">
              <div className ="row">
                <div className ="poem-first-flower col-12" id="first">
                  {firstFlower}
                </div>
                <div className ="poem-second-flower col-12" id="second">
                  {secondFlower}
                </div>
                <div className ="poem-third-line col-12" id="third">
                  {thirdLine}
                </div>
                <div className ="poem-rhym-line col-12" id="fourth">
                  {fourthLine}
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className ="padded-button col-1">
          <button className ="btn poem btn-danger" type="button" onClick={createPoem}>Make!</button>
        </div>
      </div>
    </div>
  )
}

const rootElement = document.getElementById('root')
ReactDOM.render(<App />, rootElement)
