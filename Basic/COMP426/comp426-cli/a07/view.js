  import Game from "./engine/game.js";

  //blank board before the document 
  let gameBoard = null;

  $(document).ready(() =>{

    gameBoard = new Game(4);
    setup(gameBoard.getGameState());

    // returns the array of numbers, tied with logKey
    document.addEventListener('keydown', logKey);

    $("#restart").on('click', function(event){
        restart(event);
    })

    observeEvent();
  })

  let observeEvent = () => {
      gameBoard.onMove(gameState => {
          setup(gameState);
      });
      gameBoard.onWin(gameState => {
          winner(gameState);
      });
      gameBoard.onLose(gameState => {
          loser(gameState);
      });
  }

  let winner = () => {
      let div = $("#restart");
      let winDiv = $("#win");
      winDiv.empty();

      let flash = `<h3 class="title">${"Winner!"}</h1>
                   <h4 class="subtitle">${"You have won and may restart"}</h2>`;
      winDiv.append(flash);
      div.append(winDiv);
  }

  let loser = () => {
      let div = $("#restart");
      let loseDiv = $("#lose");
      loseDiv.empty();

      let flash = `<h3 class="title">${"Loser!"}</h1>
                   <h4 class="subtitle">${"You have lost and may restart"}</h2>`;
      loseDiv.append(flash);
      div.append(loseDiv);
  }

  let restart = function(event) {
      gameBoard = new Game(4);

      let score = $("#score");
      score.empty();
      score.append(0);
      setup(gameBoard.getGameState());
      observeEvent();
  }


  let setup = (gameState) => {
      let div = $("#root");
      let board = gameState.board;
      let table = $("<table class='gameBoard'></table>");

        for(let i = 0; i < 4; i++){
            let tr = document.createElement("tr");
            for(let j = 0; j < 4; j++){
                let td = document.createElement("td");
                td.append(board[i*4 + j]);
                tr.append(td);
            }
            table.append(tr);
        }
        div.append(table);
  }

 function logKey(key){
    // console.log(key.code)
      switch(key.code){
          case 'ArrowUp'|| 'KeyW':
              gameBoard.move('up');
              break;
          case 'ArrowLeft' || 'KeyA':
              gameBoard.move('left');
              break;
          case 'ArrowRight' || 'KeyD':
              gameBoard.move('right');
              break;
          case 'ArrowDown' || 'KeyS':
              gameBoard.move('down');
              break;
      }
      let score = $("#score");
      score.empty();
      score.append("Score = " + gameBoard.getGameState().score);
  }