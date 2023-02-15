package viewer;

import controller.OnBoardController;
import model.*;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class OnBoardViewer {
    private Scanner SCANNER;
    private OnBoardController onBoardController;
    private FilmViewer filmViewer;
    private TheaterViewer theaterViewer;
    private MemberDTO logIn = null;

    private final int LEVEL_ADMIN = 3;

    public OnBoardViewer(Scanner scanner) {
        onBoardController = new OnBoardController();
        SCANNER = scanner;
    }

    public void setFilmViewer(FilmViewer filmViewer) {
        this.filmViewer = filmViewer;
    }

    public void setTheaterViewer(TheaterViewer theaterViewer) {
        this.theaterViewer = theaterViewer;
    }

    public void setLogIn(MemberDTO logIn) {
        this.logIn = logIn;
    }

    public void printOnBoardAll() {
        ArrayList<OnBoardDTO> list = onBoardController.selectAll();
        for (OnBoardDTO on : list) {
            System.out.printf("%d. 극장이름 : ", on.getId());
            theaterViewer.printTheaterName(on.getTheaterId());
            System.out.print(" - 영화 제목 : ");
            filmViewer.printFilmTitle(on.getFilmId());
            System.out.println(" - 상영시간 : " + on.getRunningTime());
        }

        String message = "상세보기할 상영정보의 번호나 뒤로 가실려면 0을 입력해주세요.";
        int userChoice = ScannerUtil.nextInt(SCANNER, message);
        while (userChoice != 0 && !list.contains(new OnBoardDTO(userChoice))) {
            System.out.println("잘못 입력하셨습니다.");
            userChoice = ScannerUtil.nextInt(SCANNER, message);
        }

        if (userChoice != 0) {
            printOnBoardOne(userChoice);
        }
    }

    public void showMenu(int theaterId) {
        String message;
        int userChoice;

        if (logIn.getLevel() == LEVEL_ADMIN) {
            message = "1. 상영 목록 보기 2. 상영정보 등록 하기 2. 뒤로가기";
            userChoice = ScannerUtil.nextInt(SCANNER, message, 1, 3);
            if (userChoice == 1) {
                printList(theaterId);
            } else if (userChoice == 2){
                addOnBoard();
            }
        } else {
            printList(theaterId);
        }
    }

    private void addOnBoard() {
        OnBoardDTO onBoardDTO = new OnBoardDTO();

        String message = "상영정보에 추가할 영화 번호를 등록해주세요.";
        int movieId = ScannerUtil.nextInt(SCANNER, message);

        if (filmViewer.validateMovieId(movieId)) {
            System.out.println("해당 영화 번호는 존재하지 않습니다.");
            movieId = ScannerUtil.nextInt(SCANNER, message);
        }
        onBoardDTO.setFilmId(movieId);

        int theaterId = ScannerUtil.nextInt(SCANNER, message);
        if (theaterViewer.validateTheaterId(theaterId)) {
            System.out.println("해당 극장 번호는 존재하지 않습니다.");
            theaterId = ScannerUtil.nextInt(SCANNER, message);
        }
        onBoardDTO.setTheaterId(theaterId);

        message = "상영되는 영화의 시간을 등록해주세요.";
        onBoardDTO.setRunningTime(ScannerUtil.nextLine(SCANNER, message));


        onBoardController.add(onBoardDTO);
    }

    public void printList(int theaterId) {
        ArrayList<OnBoardDTO> list = onBoardController.selectAll(theaterId);

        if (list.isEmpty()) {
            System.out.println("현재 상영중인 영화가 없습니다.");
            showMenu(theaterId);
        } else {
            for (OnBoardDTO on : list) {
                System.out.printf("%d. 극장이름 : ", on.getId());
                theaterViewer.printTheaterName(on.getTheaterId());
                System.out.print(" - 영화 제목 : ");
                filmViewer.printFilmTitle(on.getFilmId());
                System.out.println(" - 상영시간 : " + on.getRunningTime());
            }

            String message = "상세보기할 상영정보의 번호나 뒤로 가실려면 0을 입력해주세요.";
            int userChoice = ScannerUtil.nextInt(SCANNER, message);
            while (userChoice != 0 && !list.contains(new OnBoardDTO(userChoice))) {
                System.out.println("잘못 입력하셨습니다.");
                userChoice = ScannerUtil.nextInt(SCANNER, message);
            }

            if (userChoice != 0) {
                printOnBoardOne(userChoice);
            }
        }
    }

    private void printOnBoardOne(int id) {
        OnBoardDTO onBoardDTO = onBoardController.selectOne(id);
        System.out.println("=================================================");
        System.out.println("상영정보 번호: " + onBoardDTO.getId());
        System.out.println("-------------------------------------------------");
        System.out.print("영화 제목: ");
        filmViewer.printFilmTitle(onBoardDTO.getFilmId());
        System.out.println();
        System.out.print("극장 : ");
        theaterViewer.printTheaterName(onBoardDTO.getTheaterId());
        System.out.println();
        System.out.println("-------------------------------------------------");
        System.out.println("상영 시간 : " + onBoardDTO.getRunningTime());
        System.out.println("=================================================");

        if (logIn.getLevel() == LEVEL_ADMIN){
            showAdminOptions(id, onBoardDTO.getTheaterId());
        } else {
            printList(onBoardDTO.getTheaterId());
        }
    }

    private void showAdminOptions(int id, int theaterId) {
        String message = "1. 수정 2. 삭제 3. 뒤로가기";
        int userChoice = ScannerUtil.nextInt(SCANNER, message);
        if (userChoice == 1){
            updateOnBoard(id);
        } else if (userChoice == 2) {
            deleteOnBoard(id, theaterId);
        } else if (userChoice == 3) {
            printList(theaterId);
        }
    }

    private void updateOnBoard(int id) {
        OnBoardDTO on = onBoardController.selectOne(id);

        String message = "상영되는 영화의 시간을 등록해주세요.";
        on.setRunningTime(ScannerUtil.nextLine(SCANNER, message));

        onBoardController.update(on);
        printOnBoardOne(id);
    }

    private void deleteOnBoard(int id, int theaterId) {
        String message = "정말로 삭제하시겠습니까? Y/N";
        String yesNo = ScannerUtil.nextLine(SCANNER, message);
        if (yesNo.equalsIgnoreCase("Y")) {
            onBoardController.delete(id);
            printList(theaterId);
        } else {
            printOnBoardOne(id);
        }
    }

    public void deleteFilmId(int movieId) {
        ArrayList<OnBoardDTO> list = onBoardController.selectAll();
        for (OnBoardDTO onBoardDTO : list) {
            if (onBoardDTO.getFilmId() == movieId) {
                onBoardController.delete(onBoardDTO.getId());
            }
        }
    }

    public void deleteTheaterId(int theaterId) {
        ArrayList<OnBoardDTO> list = onBoardController.selectAll();
        for (OnBoardDTO onBoardDTO : list) {
            if (onBoardDTO.getTheaterId() == theaterId) {
                onBoardController.delete(onBoardDTO.getId());
            }
        }
    }


}


