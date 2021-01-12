/*
Add your code for Game here

gameState = {
    board: number[],
    score: number,
    won: boolean,
    over: boolean
}
 */

export default class Game {
    constructor(size){
        this.size = size;
        this.gameState = {
            board: [],
            score: Number,
            won: Boolean,
            over: Boolean
        }
        this.setupNewGame();
        this.moveTiles = [];
        this.win = [];
        this.lose = [];
    }
    
    setupNewGame(){
        this.gameState = {
            board: [],
            score: Number,
            won: Boolean,
            over: Boolean
        }
        //get random int 
        let randSpot1 = Math.floor(Math.random() * (this.size*this.size)); // 4 should be size
        let randSpot2 = Math.floor(Math.random() * (this.size*this.size));
        //actually random
        if(randSpot1 == randSpot2){
            randSpot2 = Math.floor(Math.random() * (this.size*this.size));
        } else {
            //set up board state
            for(let i = 0; i < this.size*this.size; i++){
                //look for the rand spot in board
                if(i == randSpot1){
                    //need to have 90% chance for a 2
                    if(Math.random() > .9){
                        this.gameState.board.push(4);
                    } else {
                        this.gameState.board.push(2);
                    }
                } else if(i == randSpot2){
                    if(Math.random() > .9){
                        this.gameState.board.push(4);
                    } else {
                        this.gameState.board.push(2);
                    }
                } else {
                    this.gameState.board.push(0);
                }
            }
        }
        this.gameState.score = 0;
        this.gameState.won = false;
        this.gameState.over = false;
        //console.log(randSpot1);
    }

    loadGame(gameState){
        this.gameState = gameState;
    }

    toString(){
        //printable string for the board
        let boardState = "";
        let display = "";
        for(let i = 0; i < this.size*this.size; i++){
            boardState += this.gameState.board[i];
            boardState += " ";
            //even size board
            if((i + 1) % this.size == 0){
                boardState += "\n";
            }
        }
        boardState += "\n";
        display += "Current score = " + this.gameState.score;
        display += "\n";
        display += "Won game = " + this.gameState.won;
        display += "\n";
        display += "Game over = " + this.gameState.over;
        display += "\n"; 
        return boardState + display;
    }

    move(direction){
        //console.log("move happened")
        if(this.gameState.over == false){
            switch(direction){
                case 'left':
                    let j = 0;
                    for(let i = 1 + j; i < 4 + j; i++){
                        //check the row
                        if(this.gameState.board[i] != 0){
                            while(this.gameState.board[i - 1] == 0 && i-j != 0){
                                this.gameState.board[i - 1] = this.gameState.board[i];
                                this.gameState.board[i] = 0;
                                i--;
                            }
                            //check for comboination here
                            if(this.gameState.board[i] == this.gameState.board[i-1] && this.gameState.board[i-1] != undefined){
                                this.gameState.board[i - 1] = this.gameState.board[i - 1] + this.gameState.board[i];
                                this.gameState.board[i] = 0;
                            }
                        }
                        //go to next row
                        if(i == 4 + j - 1 && i < this.size * this.size - 1){
                            j += 4;
                        }
                    }

                //case next
                break;
                case 'right':
                    for(let g = 0; g < 15; g++){
                        if(this.gameState.board[g] != 0){
                            let k = 0;
                            while(this.gameState.board[g+1] == 0 && k < 3){ 
                                this.gameState.board[g+1] = this.gameState.board[g];
                                this.gameState.board[g] = 0;
                                g++;
                                k++;
                            }
                           
                            if(this.gameState.board[g] == this.gameState.board[g+1] && this.gameState.board[g+1] != undefined){
                                this.gameState.board[g+1] = this.gameState.board[g+1] + this.gameState.board[g];
                                this.gameState.board[g] = 0;
                            }
                        }
                    }
                break;
                case 'down':
                    let y = 0;
                    for(let t = 0 + y; t < 4 + y; t++){
                        if(this.gameState.board[t] != 0){
                            while(this.gameState.board[t+4] == 0){ 
                                this.gameState.board[t+4] = this.gameState.board[t];
                                this.gameState.board[t] = 0;
                                t++;
                            }

                            if(this.gameState.board[t] == this.gameState.board[t+4] && this.gameState.board[t+4] != undefined){
                                this.gameState.board[t+4] = this.gameState.board[t+4] + this.gameState.board[t];
                                this.gameState.board[t] = 0;
                            }
                        }
                        if(t == 3 + y - 1 && t < this.size * this.size - 1){
                            y += 4;
                        }
                    }
                break;
                case 'up':
                    for(let q = 4; q < 15; q++){
                        if(this.gameState.board[q] != 0){
                            while(this.gameState.board[q-4] == 0){  
                                this.gameState.board[q-4] = this.gameState.board[q];
                                this.gameState.board[q] = 0;
                                q++;
                                if(q > 14){
                                    break;
                                }
                            }
                            if(this.gameState.board[q] == this.gameState.board[q-4]){
                                this.gameState.board[q-4] = this.gameState.board[q-4] + this.gameState.board[q];
                                this.gameState.board[q] = 0;
                            }
                        }
                    }
                break;
                    
            }
            this.createRandomTiles();
            for(let i = 0; i < this.moveTiles.length; i++){
                this.moveTiles[i](this.gameState);
            }
            if(this.gameState.over){
                for(let i = 0; i < this.lose.length; i++){
                    this.lose[i](this.gameState);
                }
            }
            for(let i = 0; i < this.size*this.size - 1; i++){
                this.gameState.score += this.gameState.board[i];
                if(this.gameState.board[i] == 2048){
                    this.gameState.won = true;
                }
            }
        }
    }

    createRandomTiles() {
        let roomOnBoard = 0;
        for(let i = 0; i < this.size*this.size; i++){
            if(this.gameState.board[i] == 0){
                roomOnBoard++;
            }
        }
        if(roomOnBoard > 0){
            let randSpot = Math.floor(Math.random() * (this.size*this.size));
            //console.log(randSpot)   
            if(this.gameState.board[randSpot] == 0){
                if(Math.random() > .9){
                    this.gameState.board[randSpot] = 4;
                } else {
                    this.gameState.board[randSpot] = 2;
                }
            }
        } else if (roomOnBoard < 2){
            if(this.gameState[i] != this.gameState[i-1] || this.gameState[i] != this.gameState[i+1] || this.gameState[i] != this.gameState[i+4] || this.gameState[i] != this.gameState[i-4]){
                this.gameState.over = false;
            } else {
                this.gameState.over = true;
            }  
        }
    }

    onMove(callback) {
        this.moveTiles.push(callback);
    }

    onWin(callback) {
        this.win.push(callback);
    }

    onLose(callback){
        this.lose.push(callback);
    }

    getGameState(){
        return this.gameState;
    }
}
