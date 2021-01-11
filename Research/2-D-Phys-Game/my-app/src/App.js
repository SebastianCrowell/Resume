import React from 'react';
import { Component } from 'react';
import './App.css';
//firebase
import firebase, { auth } from './firebase.js';
//firebase

class App extends Component {
  //firebase
  constructor(){
    super();
    this.state = {}
    this.handleChange = this.handleChange.bind(this);
    this.handleSignup = this.handleSignup.bind(this);
    this.login = this.login.bind(this);
    this.logout = this.logout.bind(this);
  }
  handleChange(e) {
    this.setState({
      [e.target.name]: e.target.value
    });
  }
  handleSignup(e) {
    e.preventDefault();
    var email = this.state.usernameSign;
    var password = this.state.passwordSign;
    firebase.auth().createUserWithEmailAndPassword(email, password)
    .catch((error) => {
     var errorCode = error.code;
     var errorMessage = error.message;
     //remove alerts
    alert(errorCode, errorMessage);
    });
  }
  login(e) {
    e.preventDefault();
    var email = this.state.username;
    var password = this.state.password;
    firebase.auth().signInWithEmailAndPassword(email, password)
    .then((result) => {
      const user = result.user;
      this.setState({
        user
      });
    })
    .catch((error) => {
       var errorCode = error.code;
       var errorMessage = error.message;
       //remove alerts
    alert(errorCode, errorMessage);
    });
  }
  logout() {
    auth.signOut()
    .then(() => {
      this.setState({
        user: null
      });
    });
  }
  componentDidMount() {
    auth.onAuthStateChanged((user) => {
      if (user) {
        this.setState({ user });
      } 
    });
    const itemsRef = firebase.database().ref('items');
    itemsRef.on('value', (snapshot) => {
      let items = snapshot.val();
      let newState = [];
      for (let item in items) {
        newState.push({
          id: item,
          user: items[item].user,
          pass: items[item].pass
        });
      }
      this.setState({
        items: newState
      });
    });
  }
  
  //firebase
  render(){
    return (
      <div className='wrapper'>
      {this.state.user ?
      <div>
      <div className="a">
        <div id="scoreHead">Score:</div>
        <div id="score" value="0">0</div>
        <Timer/>
        <CharacterLoader/>
      </div>
      <button id="logout" onClick={this.logout}>Log Out</button>
      </div>
      :
      <div>
      <form id="login">
        <input type="text" name="username" placeholder="Email" onChange={this.handleChange} value={this.state.username} />
        <input type="text" name="password" placeholder="Password" onChange={this.handleChange} value={this.state.password} />
        <button id="loginButton" onClick={this.login}>Log In</button>  
      </form>
      <form id="signup" onSubmit={this.handleSignup}>
        <input type="text" name="usernameSign" placeholder="format@email.likethis" onChange={this.handleChange} value={this.state.usernameSign} />
        <input type="text" name="passwordSign" placeholder="6 Character Password" onChange={this.handleChange} value={this.state.passwordSign} />
        <button id="signupButton">Sign Up</button>
      </form>
      </div>
      }
      </div>
    );
  }
}

//////////////////////////////////////////////////BOARD///////////////////////////////////////////////////////////

//Current match length
class Timer extends React.Component {
  state = {
      minutes: 1,
      seconds: 0,
  }
  componentDidMount() {
      this.interval = setInterval(() => {
          const { seconds, minutes } = this.state

          if (seconds > 0) {
              this.setState(({ seconds }) => ({
                  seconds: seconds - 1
              }))
          }
          if (seconds === 0) {
              if (minutes === 0) {
                  clearInterval(this.interval)
              } else {
                  this.setState(({ minutes }) => ({
                      minutes: minutes - 1,
                      seconds: 59
                  }))
              }
          } 
      }, 1000)
  }

  componentWillUnmount() {
      clearInterval(this.interval)
  }

  render() {
      const { minutes, seconds } = this.state
      return (
          <div>
              { minutes === 0 && seconds === 0
                  ? <h1 id="end">Game Over!</h1>
                  : <h1 id="time">Time Left: {minutes}:{seconds < 10 ? `0${seconds}` : seconds}</h1>
              }
          </div>
      )
  }
}

/////////////////////////////////////////////PLAYER///////////////////////////////////////////////////////////////

class CharacterPlayer extends React.Component {
	render() {  
	  return <div className="b"> 
            <div id="character-player" style={{marginLeft: this.props.left + 'px', right: this.props.left + 'px'}}>
              {/* Player Name can go here */}
              <CharacterWeapon></CharacterWeapon>
            </div>
           </div>;
  }
}

class CharacterLoader extends React.Component {
  render(){
    var directiony = 0;
    var degree = 85;
    document.onkeydown = function(e) {
      var char = document.getElementById('character-player');
      var weap = document.getElementById('character-weapon');
      // get a target object using the directiony value and the id corresponding
      // console.log(e.keyCode);
      if (e.keyCode === 32) {
      // space
        char.style.marginTop = directiony + 'px';
        fireProjectile(degree)
      }
      if (e.keyCode === 37) {
      // left
        if(degree > 10){
          degree += -1;
          weap.style.transform = 'rotate(' + degree + 'deg)';
        }
      }
      if (e.keyCode === 38){
      // up
        if(directiony > -30){
          directiony += -3;
          char.style.marginTop = directiony + 'px';
        }
      }
      if (e.keyCode === 39) {
      // right
        if(degree < 85){
          degree += 1;
          weap.style.transform = 'rotate(' + degree + 'deg)';
        }
      }
      if (e.keyCode === 40){
      // down
        if(directiony < 480){
          directiony += 3;
          char.style.marginTop = directiony + 'px';
        }
      }
    }
    // console.log(doc.style.margin);
    return <CharacterPlayer></CharacterPlayer>;
  }
}

class CharacterWeapon extends React.Component {
  // between 46 deg and 89 deg
  render () {
    return  <div id="character-weapon" >
    </div>
  }
}

var projectile = [];
var l = 0;
var degreeMult = 0;

function fireProjectile(degree){
  var char = document.getElementById('character-player');
  var a = document.getElementsByClassName('a')[0];
  // push the value for ground, let top increase, then decrease with height calc and delete once the top = ground 
  // left constantly increases while top does
  // projectile.push({left: 0, top: parseInt(char.style.marginTop)})??
  var x = 34;
  var y = parseInt(char.style.marginTop) - 16;
  var ground = parseInt(char.style.marginTop) - 15;

  degreeMult = 1.91 - parseInt(degree)/100;
  // can add a smoothign fraction for an arc
  projectile.push({y: y, x: x, ground: ground, degree: degree, ticker: 0, swap: -1});
 
  var shot = document.createElement("DIV");
  shot.id = 'weapon-shot' + l;
  shot.style.width = 12 + 'px';
  shot.style.height = 12 + 'px';
  shot.style.backgroundColor = "red";
  shot.style.borderRadius = 12 + 'px';
  shot.style.position = "absolute";
  l++;
  a.appendChild(shot);
  //console.log(projectile)

  //console.log(projectile)
}

function moveProjectile(){
  //console.log(projectile.length)
  for(var i = 0; i < projectile.length; i++){
    //increase x linearly
    //increase y, then decrease after distance using the degree
    //r = 1^2 * cos(degree) * (2 * 1 * sin(degree)/(9.81))
    //1 - cos(x)
    var shot = document.getElementById("weapon-shot" + i);

   // while(projectile[i].y !== projectile[i].ground){ }
   if(projectile[i].ground > projectile[i].y){
      projectile[i].x = projectile[i].x + 1;
      shot.style.marginLeft = projectile[i].x + 'px';
      //console.log(projectile[i].x)
      if(projectile[i].ticker >= projectile[i].x){
        projectile[i].swap = 1;
      }
      projectile[i].y = projectile[i].y + projectile[i].swap;
      shot.style.marginTop = projectile[i].y + 'px';

      projectile[i].ticker = projectile[i].ticker + degreeMult;
      //console.log(projectile[i].y)
      //console.log(projectile[i].ground)
    }

    // console.log(projectile[i].ticker);
    // console.log(shot.style.marginTop)
    // while(projectile[i].top !== ground){ }
  }
}

//////////////////////////////////////////////////TARGET/////////////////////////////////////////////////////////////
var objs = [];
var o = 0;

function makeTarget(){
  //diff value targets with diff sizes
  //remove hit targets
    var par = document.getElementsByClassName('a')[0];

    var rangeMin = 110;
    var rangeMax = 1000;
    var domainMin = 0;
    var domainMax = 479;

    var randRange = Math.floor(Math.random() * rangeMax) + rangeMin;
    var randDomain = Math.floor(Math.random() * domainMax) + domainMin;

    if(par !== undefined){
      var target = document.createElement('DIV');
      target.id = "object" + o;
      target.style.marginTop = randDomain + 'px';
      target.style.marginLeft = randRange + 'px';
      target.style.backgroundColor = "goldenrod";
      target.style.borderRadius = 10 + 'px';
      target.style.height = 24 + 'px';
      target.style.width = 24 + 'px';
      target.style.position = 'absolute';
      o++;
      objs.push({y: randDomain, x: randRange});

      //console.log(target)
      //console.log(par)
      par.appendChild(target)
    }
}

//target scoring using for(each projectile) 
//if(proj.marginTop >= obj.marginTop + 10 && proj.marginTop <= obj.marginTop + 10)
//if(proj.marginLeft >= ...)
//score += obj.val;
//penalty for missing
var val = 0;
function targetScore() {
  for(var i = 0; i < projectile.length; i++){
    for(var j = 0; j < objs.length; j++){
      if(projectile[i].y === projectile[i].ground){
        if(projectile[i].x > objs[j].x - 10 && projectile[i].x < objs[j].x + 18){
          if(projectile[i].y > objs[j].y - 10 && projectile[i].y < objs[j].y + 18){
            var hitShot = document.getElementById("weapon-shot" + i);
            var hitTarget = document.getElementById("object" + j);
            var score = document.getElementById("score");
            if(hitTarget.style.backgroundColor !== "white"){
              //increase score 
              val += 10;
              score.innerHTML = val;
              hitShot.style.backgroundColor = "white";
              hitTarget.style.backgroundColor = "white";
            }
          } 
        } //else penalty? i dont even think its needed
      }
    }
  }
}

///////////////////////////////////////////////////LOOP/////////////////////////////////////////////////////////////


function gameOver() {
  var doc = document.getElementById("end");
  if(doc !== null){

    var dia = document.createElement('DIALOG');
    dia.id = "dialogBox";
    doc.appendChild(dia);

    var formDia = document.createElement('FORM');
    formDia.method = "dialog";
    dia.appendChild(formDia);

    var menuDia = document.createElement('MENU');
    formDia.appendChild(menuDia);

    var inputDia = document.createElement('INPUT');
    inputDia.id = "inputDia";
    inputDia.placeholder = "Type Name for Scoreboard";
    menuDia.appendChild(inputDia);

    var confirmDia = document.createElement('BUTTON');
    confirmDia.id = 'confrimBtn';
    confirmDia.value = 'default';
    confirmDia.innerHTML = 'Submit Score';
    menuDia.appendChild(confirmDia);

    var cancelDia = document.createElement('BUTTON');
    cancelDia.value = 'cancel';
    cancelDia.style.backgroundColor = 'red';
    cancelDia.innerHTML = 'Cancel';
    menuDia.appendChild(cancelDia);

    dia.showModal();

    inputDia.addEventListener('change', function onType() {
      confirmDia.value = inputDia.value;
    });

    //second dialog to show the high scores using a scope check from firebase
    dia.addEventListener('close', function onClose() {
      if(dia.returnValue !== 'cancel'){
        const sendScore = firebase.database().ref('items');
        const single = {
          user: confirmDia.value,
          score: val
        }
        sendScore.push(single);
      }

      const retScore = firebase.database().ref('items');
      var newState = [];
      retScore.on('value', (snapshot) => {
        let items = snapshot.val();
          for (let item in items) {
            newState.push({
              id: item,
              score: items[item].score,
              user: items[item].user
            });
          }
      });

      var num1 = 0;
      var num2 = 0;
      var num3 = 0;
      var num4 = 0;
      var num5 = 0;
      var top5 = [];

      for(var l = 0; l < newState.length; l++){
        if(newState[l].score > num5){
          num5 = newState[l].score;
          if(num5 > num4){
            var temp5= num4;
            num4 = num5;
            num5 = temp5;
            if(num4 > num3){
              var temp4 = num3;
              num3 = num4;
              num4 = temp4;
              if(num3 > num2){
                var temp3 = num2;
                num2 = num3;
                num3 = temp3;
                if(num2 > num1){
                  var temp2 = num1;
                  num1 = num2;
                  num2 = temp2;
                  top5[0] = [num1, newState[l].user];
                }
              }
            }
          }
        }
      }

      top5[1] = num2;
      top5[2] = num3;
      top5[3] = num4;
      top5[4] = num5;

      //console.log(top5)
      
      var scoreBoard = document.createElement('DIALOG');
      doc.appendChild(scoreBoard);

      var title = document.createElement('HEADER');
      title.innerHTML = 'High Scores:';
      scoreBoard.appendChild(title);

      var scoreFor = document.createElement('FORM');
      scoreFor.method = "dialog";
      scoreBoard.appendChild(scoreFor);
      
      //change scoretext to a list of li
      var scoreText = document.createElement('OL');
      scoreFor.appendChild(scoreText);
      for(var y = 0; y < top5.length; y++){
        var scoreLi = document.createElement('LI');
        scoreLi.innerHTML = top5[y];
        scoreText.appendChild(scoreLi);
      }
      
      var scoreMenu = document.createElement('MENU');
      scoreFor.appendChild(scoreMenu);

      var scoreClo = document.createElement('BUTTON');
      scoreClo.value = 'cancel';
      scoreClo.innerHTML = 'Close';
      scoreMenu.appendChild(scoreClo);

      scoreBoard.showModal();
    });
    
    doc.id = "done";
  }
}

function gameLoop() {
  setTimeout(gameLoop, 20)
  moveProjectile();
}

function targetLoop(){
  setTimeout(targetLoop, 5000)
  makeTarget();
}

function scoreLoop(){
  setTimeout(scoreLoop, 200)
  targetScore();
  gameOver();
}

gameLoop()
targetLoop()
scoreLoop()

export default App;