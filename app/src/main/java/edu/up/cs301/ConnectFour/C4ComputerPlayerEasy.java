package edu.up.cs301.ConnectFour;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.util.Tickable;

/**
 * Created by macnary17 on 11/21/2015.
 */
public class C4ComputerPlayerEasy extends GameComputerPlayer {

    ConnectFourState newState = new ConnectFourState();
    //set up 2D array to hold board values
    public int[][] board = new int[6][7];

    public C4ComputerPlayerEasy(String name) {
        super(name);
    }

    protected void receiveInfo(GameInfo info) {
        //Check if opponent can get 4 in a row
        if (info instanceof ConnectFourState) {
            newState = (ConnectFourState) info;
            board = newState.getBoard();
            int playerIdx = newState.getTurn();
        }

        int move;
        int stopHuman = -1;
        Random rand = new Random();


        if (stopVerticalWin() != -1) {
            stopHuman = stopVerticalWin();
        }
//        else if (stopDiagonalWin()!=-1) {
//            stopHuman = stopDiagonalWin();
//        }
        else if (stopHorizontalWin() != -1) {
            stopHuman = stopHorizontalWin();
        }


        if (stopHuman != -1) {
            move = stopHuman;
        } else {
            move = rand.nextInt(7);
        }

        //If opponent cant get 4 in a row, move randomly
        if (move == 0) {
            C4DropActionCol0 dropActionCol0 = new C4DropActionCol0(this);
            game.sendAction(dropActionCol0);
        } else if (move == 1) {
            C4DropActionCol1 dropActionCol1 = new C4DropActionCol1(this);
            game.sendAction(dropActionCol1);
        } else if (move == 2) {
            C4DropActionCol2 dropActionCol2 = new C4DropActionCol2(this);
            game.sendAction(dropActionCol2);
        } else if (move == 3) {
            C4DropActionCol3 dropActionCol3 = new C4DropActionCol3(this);
            game.sendAction(dropActionCol3);
        } else if (move == 4) {
            C4DropActionCol4 dropActionCol4 = new C4DropActionCol4(this);
            game.sendAction(dropActionCol4);
        } else if (move == 5) {
            C4DropActionCol5 dropActionCol5 = new C4DropActionCol5(this);
            game.sendAction(dropActionCol5);
        } else if (move == 6) {
            C4DropActionCol6 dropActionCol6 = new C4DropActionCol6(this);
            game.sendAction(dropActionCol6);
        }
        //random movements
    }

    public int stopVerticalWin() {
        int count = 0;
        int piece = newState.getEMPTY();
        if (newState.getTurn() == 1) {
            piece = newState.getRED();
        } else if (newState.getTurn() == 0) {
            piece = newState.getBLACK();
        }
        int tempCol = 0;

        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = 0; j < board[i].length; j++) {
                count = 0;
                if (board[i][j] == piece) {
                    if (i >= 3) {
                        tempCol = j;
                        for (int a = 0; a < 3; a++) {
                            if (board[i - a][tempCol] == piece) {
                                count++;
                                if (count >= 3) {
                                    if (board[i - a - 1][tempCol] == newState.getEMPTY()) {
                                        return tempCol;
                                    }
                                }
                            } else {
                                count = 0;
                            }
                        }
                    }
                }
//                else {
//                    count = 0;
//                }
            }
        }
        return -1;
    }

    public int stopHorizontalWin() {

        int count = 0;
        int piece = newState.getEMPTY();
        if (newState.getTurn() == 1) {
            piece = newState.getRED();
        } else if (newState.getTurn() == 0) {
            piece = newState.getBLACK();
        }
        int tempRow;

        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = 0; j < board[i].length; j++) {
                count = 0;
                if (board[i][j] == piece) {
                    if (j <= 5) {
                        tempRow = i;
                        for (int a = 0; a < 2; a++) {
                            if (board[tempRow][j + a] == piece) {
                                count++;
                                if (count >= 2) {
                                    if (j + a + 1 < 7 && board[tempRow][j + a + 1] == newState.getEMPTY()) {
                                        if (tempRow + 1 >= 6 || board[tempRow + 1][j + a + 1] != newState.getEMPTY()) {
                                            return j + a + 1;
                                        }
                                    }
                                    if (j - 1 >= 0 && board[tempRow][j - 1] == newState.getEMPTY()) {
                                        if (tempRow + 1 >= 6 || board[tempRow + 1][j - 1] != newState.getEMPTY()) {
                                            return j - 1;
                                        }
                                    }
                                }
                            } else {
                                count = 0;
                                a = 3;
                            }
                        }
                    }
                }
            }

        }
        return -1;
    }

//    public int stopDiagonalWin() {
//        boolean keepGoing = false;
//        int count = 0;
//        int piece = newState.getEMPTY();
//        if (newState.getTurn() == 1) {
//            piece = newState.getRED();
//        } else if (newState.getTurn() == 0) {
//            piece = newState.getBLACK();
//        }
//
//
//        //look through entire game board, starting in top left corner
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[i].length; j++) {
//                if (board[i][j] == piece) {
//                    count = 1;
//                    int k = i;
//                    int l = j;
//                    keepGoing = true;
//                    if (i < 4 && j < 5) {
//                        while (keepGoing) {
//                            k++;
//                            l++;
//                            if (board[k][l] == piece) {
//                                count++;
//                            } else {
//                                keepGoing = false;
//                                count = 0;
//                            }
//                            if (count == 3) {
//                                keepGoing = false;
//                                if (i >= 1 && j >= 1) {
//                                    if (board[i - 1][j - 1] == newState.getEMPTY() && board[i][j - 1] != newState.getEMPTY()) {
//                                        return j - 1;
//                                    }
//                                }
//                                if (k + 1 <= 5 && board[k + 1][l + 1] == newState.getEMPTY() && (k+2>5 || board[k + 1][l + 1] != newState.getEMPTY())) {
//                                    return l + 1;
//                                }
////                                else if (i < 3 && j < 4) {
////                                    if (k + 2 > 5 && board[k + 1][l + 1] == newState.getEMPTY()) {
////                                        return l + 1;
////                                    }
////                                }
//
//                            }
//                        }
//                    }
//                }
//                count = 0;
//            }
//        }
////        for (int i = 0; i < board.length; i++) {
////            for (int j = 0; j < board[i].length; j++) {
////                if (board[i][j] == piece) {
////                    count = 1;
////                    int k = i;
////                    int l = j;
////                    keepGoing = true;
////                    if (j > 2 && i < 3) {
////                        while (keepGoing) {
////                            k++;
////                            l--;
////                            if (board[k][l] == piece) {
////                                count++;
////                                if (count == 4) {
////                                    return true;
////                                }
////                            } else {
////                                keepGoing = false;
////                                count = 0;
////                            }
////
////                        }
////                    }
////                }
////            }
////        }
////        return false;
//    return -1;
//    }

}