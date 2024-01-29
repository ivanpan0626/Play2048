var board;
var phantomBoard;
var score = 0;
var rows = 4;
var cols = 4;
var phantomSuccess = false;
var enabled = true;

window.onload = function(){
    gameStart();
}

//Checks for key presses
document.addEventListener('keyup', (k) => {
    if (!enabled){
        console.log("no clicks work!")
        return;
    }
    if (k.code == "ArrowLeft"){
        slideLeft();
        spawnTile();
    }
    else if (k.code == "ArrowRight"){
        slideRight();
        spawnTile();
    }
    else if (k.code == "ArrowUp"){
        slideUp();
        spawnTile();

    }
    else if (k.code == "ArrowDown"){
        slideDown();
        spawnTile();
    }
    document.getElementById("score").innerText = score;
})

//Handles the logic of sliding left, right, up, down
function merge(boardRow){
    boardRow = filterZero(boardRow);
    for (let i = 0; i < boardRow.length-1; i++){
        if (boardRow[i] == boardRow[i+1]){
            boardRow[i] *= 2;
            boardRow[i+1] = 0;
            score += boardRow[i];
        }
    } 
    boardRow = filterZero(boardRow);
    while (boardRow.length < cols){
        boardRow.push(0);
    }
    return boardRow;
}

function slideLeft() {
    for (let i = 0; i < rows; i++){
        let boardRow = board[i];
        boardRow = merge(boardRow);
        board[i] = boardRow;
        //phantomBoard[i] = boardRow;
        for (let j = 0; j < cols; j++){
            let tile = document.getElementById(i.toString() + "-" + j.toString());
            let num = board[i][j];
            updateTile(tile, num);
        }
    }
}

function slideRight() {
    for (let i = 0; i < rows; i++){
        let boardRow = board[i];  
        boardRow.reverse();       
        boardRow = merge(boardRow);
        board[i] = boardRow.reverse();  
        //phantomBoard[i] = boardRow.reverse();
        for (let j = 0; j < cols; j++){
            let tile = document.getElementById(i.toString() + "-" + j.toString());
            let num = board[i][j];
            updateTile(tile, num);
        }
    }
}

function slideUp() {
    for (let c = 0; c < cols; c++){
        let boardRow = [board[0][c], board[1][c], board[2][c], board[3][c]];
        boardRow = merge(boardRow);
        for (let r = 0; r < rows; r++){
            board[r][c] = boardRow[r];
            //phantomBoard[r][c] = boardRow[r];
            let tile = document.getElementById(r.toString() + "-" + c.toString());
            let num = board[r][c];
            updateTile(tile, num);
        }
    }
}

function slideDown() {
    for (let c = 0; c < cols; c++){
        let boardRow = [board[0][c], board[1][c], board[2][c], board[3][c]];
        boardRow.reverse();
        boardRow = merge(boardRow);
        boardRow.reverse();
        for (let r = 0; r < rows; r++){
            board[r][c] = boardRow[r];
            //phantomBoard[r][c] = boardRow[r];
            let tile = document.getElementById(r.toString() + "-" + c.toString());
            let num = board[r][c];
            updateTile(tile, num);
        }
    }
}

//Handles assigning appropriate values
function updateTile(tile, num){
    copyBoard();
    tile.innerText = "";
    tile.classList.value = "";
    tile.classList.add("tile");
    if (num > 0) {
        tile.innerText = num.toString();
        if (num <= 4096){
            tile.classList.add("x"+num.toString());
        } else{
            tile.classList.add("x8192");
        }                
    }
}

function copyBoard(){
    for (let i = 0; i < rows; i++){
        for (let j = 0; j < cols; j++){
            phantomBoard[i][j] = board[i][j];
        }
    }
}
//Checks for empty spaces and tile spawning functions
function filterZero(rowNum){
    return rowNum.filter(num => num != 0);
}

function spawnTile(){
    if (!hasEmptyTile()){
        if (possibleMoves()){
            return;
        }else{
            console.log("no moves possible");
            enabled = false;
            let end = document.getElementById("h1");
            end.style.color = "blue";
        }
    }
    createTile();
    if (!hasEmptyTile()){
        if (possibleMoves()){
            return;
        }else{
            console.log("no moves possible");
            enabled = false;
            let end = document.getElementById("h1");
            end.style.color = "blue";
        }
    }
}

function possibleMoves(){
    let possible = false;
    if (phantomslideLeft()){
        console.log("SlideLeft")
        possible = true;
        copyBoard();
    }
    if (phantomslideRight()){
        console.log("SlideRight")
        possible = true;
        copyBoard();
    }
    if (phantomslideUp()){
        console.log("SlideUp")
        possible = true;
        copyBoard();
    }
    if (phantomslideDown()){
        console.log("SlideDown")
        possible = true;
        copyBoard();
    }
    return possible;
}

function createTile (){
    let empty = false;
        while (!empty) {
        let r = Math.floor(Math.random() * rows);
        let c = Math.floor(Math.random() * cols);
        if (board[r][c] == 0) {
            let num = Math.floor(Math.random() * 10);
            if (num < 3){
                num = 4;
                board[r][c] = num;
                phantomBoard[r][c] == num;
            } else {
                num = 2;
                board[r][c] = num;
                phantomBoard[r][c] == num;
            }
            let tile = document.getElementById(r.toString() + "-" + c.toString());
            tile.innerText = num.toString();
            tile.classList.add("x"+num.toString());
            empty = true;
                }
        }
}

function hasEmptyTile(){
    let count = 0;
    for (let r = 0; r < rows; r++){
        for (let c = 0; c < cols; c++) {
            if (board[r][c] == 0){
                return true;
            }
        }
    }
    return false;
}

//Begins the game of 2048 and Game Over
function gameStart(){
    board = [
        [0, 0, 0, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0]
    ]
    phantomBoard = [
        [0, 0, 0, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0]
    ];

    for (let i = 0; i < rows; i++){
        for (let j = 0; j < cols; j++){
            let tile = document.createElement("div");
            tile.id = i.toString() + "-" + j.toString();
            let num = board[i][j];
            updateTile(tile, num);
            document.getElementById("board").append(tile);
        }
    }

    spawnTile();
    spawnTile();
}

function endGame(){
}





//phantomBoard actions, 
function phantomMerge(boardRow){
    boardRow = filterZero(boardRow);
    for (let i = 0; i < boardRow.length-1; i++){
        if (boardRow[i] == boardRow[i+1]){
            boardRow[i] *= 2;
            boardRow[i+1] = 0;
            phantomSuccess = true;
        }
    } 
    boardRow = filterZero(boardRow);
    while (boardRow.length < cols){
        boardRow.push(0);
    }
    return boardRow;
}

function phantomslideLeft() {
    for (let i = 0; i < rows; i++){
        let boardRow = phantomBoard[i];
        boardRow = phantomMerge(boardRow);
        if(phantomSuccess){
            phantomSuccess = false;
            return true;
        }
        phantomBoard[i] = boardRow;
    }
}

function phantomslideRight() {
    for (let i = 0; i < rows; i++){
        let boardRow = phantomBoard[i];  
        boardRow.reverse();       
        boardRow = phantomMerge(boardRow);
        if(phantomSuccess){
            phantomSuccess = false;
            return true;
        }
        phantomBoard[i] = boardRow.reverse();  
    }
}

function phantomslideUp() {
    for (let c = 0; c < cols; c++){
        let boardRow = [phantomBoard[0][c], phantomBoard[1][c], phantomBoard[2][c], phantomBoard[3][c]];
        boardRow = phantomMerge(boardRow);
        if(phantomSuccess){
            phantomSuccess = false;
            return true;
        }
        for (let r = 0; r < rows; r++){
            phantomBoard[r][c] = boardRow[r];
        }
    }
}

function phantomslideDown() {
    for (let c = 0; c < cols; c++){
        let boardRow = [phantomBoard[0][c], phantomBoard[1][c], phantomBoard[2][c], phantomBoard[3][c]];
        boardRow.reverse();
        boardRow = phantomMerge(boardRow);
        if(phantomSuccess){
            phantomSuccess = false;
            return true;
        }
        boardRow.reverse();
        for (let r = 0; r < rows; r++){
            phantomBoard[r][c] = boardRow[r];
        }
    }
}
