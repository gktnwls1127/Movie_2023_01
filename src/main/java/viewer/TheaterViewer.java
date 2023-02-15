package viewer;

import controller.TheaterController;
import model.TheaterDTO;
import model.MemberDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class TheaterViewer {
    private TheaterController theaterController;
    private OnBoardViewer onBoardViewer;
    private FilmViewer filmViewer;
    private final Scanner SCANNER;
    private MemberDTO logIn;

    private final int LEVEL_GENERAL = 1;
    private final int LEVEL_CRITIC = 2;
    private final int LEVEL_ADMIN = 3;

    public void setFilmViewer(FilmViewer filmViewer){
        this.filmViewer = filmViewer;
    }

    public void setOnBoardViewer(OnBoardViewer onBoardViewer){
        this.onBoardViewer = onBoardViewer;
    }

    public TheaterViewer(Scanner scanner){
        theaterController = new TheaterController();
        SCANNER = scanner;
    }

    public void setLogin(MemberDTO logIn){
        this.logIn = logIn;
    }

    public void showMenu(){
        if (logIn.getLevel() == LEVEL_ADMIN){
            showAdminMenu();
        } else {
            showGeneralMenu();
        }
    }

    private void showAdminMenu(){
        String message = "1. 극장 목록 보기 2. 극장 등록 하기 3. 뒤로가기";
        while(true){
            int userChoice = ScannerUtil.nextInt(SCANNER, message);
            if (userChoice == 1){
                printList();
            } else if (userChoice == 2) {
                addTheater();
            } else if (userChoice == 3) {
                System.out.println("메인 화면으로 돌아갑니다.");
                break;
            }
        }
    }

    private void showGeneralMenu(){
        String message = "1. 극장 목록 보기 2. 뒤로가기";
        while(true){
            int userChoice = ScannerUtil.nextInt(SCANNER, message);
            if (userChoice == 1){
                printList();
            } else if (userChoice == 2) {
                System.out.println("메인 화면으로 돌아갑니다.");
                break;
            }
        }
    }

    private void addTheater(){
        TheaterDTO t = new TheaterDTO();

        String message = "극장의 이름을 입력해주세요.";
        t.setTheaterName(ScannerUtil.nextLine(SCANNER, message));

        message = "극장의 위치를 입력해주세요.";
        t.setTheaterPlace(ScannerUtil.nextLine(SCANNER, message));

        message = "극장의 전화번호를 입력해주세요.";
        t.setTheaterNumber(ScannerUtil.nextLine(SCANNER, message));

        theaterController.add(t);
    }

    public void printList(){
        ArrayList<TheaterDTO> list = theaterController.selectAll();
        if (list.isEmpty()) {
            System.out.println("아직 등록된 극장이 존재하지 않습니다.");
        } else {
            for (TheaterDTO t : list) {
                System.out.printf("%d. %s\n", t.getId(), t.getTheaterName());
            }
            String message = "상세보기할 극장 번호나 뒤로 가실려면 0을 입력해주세요.";
            int userChoice = ScannerUtil.nextInt(SCANNER, message);

            if (userChoice != 0 && !list.contains(new TheaterDTO(userChoice))){
                System.out.println("잘못 입력하셨습니다.");
                userChoice = ScannerUtil.nextInt(SCANNER, message);
            }

            if (userChoice != 0){
                printOne(userChoice);
            }
        }
    }

    public void printOne(int id){
        TheaterDTO theaterDTO = theaterController.selectOne(id);
        System.out.println("=================================================");
        System.out.println(theaterDTO.getTheaterName());
        System.out.println("-------------------------------------------------");
        System.out.println("극장 위치: " + theaterDTO.getTheaterPlace());
        System.out.println("극장 전화번호: " + theaterDTO.getTheaterNumber());
        System.out.println("=================================================");

        if (logIn.getLevel() == LEVEL_ADMIN){
            showAdminOptions(id);
        } else {
            showGeneralOptions(id);
        }

    }

    private void showAdminOptions(int id){
        String message = "1. 상영중인 영화 목록 보기 2. 수정 3. 삭제 4. 뒤로가기";
        int userChoice = ScannerUtil.nextInt(SCANNER, message);
        if (userChoice == 1){
            onBoardViewer.showMenu(id);
        } else if (userChoice == 2) {
            update(id);
        } else if (userChoice == 3) {
            delete(id);
        } else if (userChoice == 4) {
            printList();
        }

    }

    private void showGeneralOptions(int id){
        String message = "1. 상영중인 영화 목록 보기 2. 뒤로가기";
        int userChoice = ScannerUtil.nextInt(SCANNER, message);
        if (userChoice == 1){
            onBoardViewer.showMenu(id);
        } else if (userChoice == 2){
            printList();
        }
    }

    public void update(int id){
        TheaterDTO t = theaterController.selectOne(id);

        String message = "새로운 극장이름을 입력해주세요.";
        t.setTheaterName(ScannerUtil.nextLine(SCANNER, message));

        message = "새로운 극장 위치를 입력해주세요.";
        t.setTheaterPlace(ScannerUtil.nextLine(SCANNER, message));

        message = "새로운 극장 전화번호를 입력해주세요.";
        t.setTheaterNumber(ScannerUtil.nextLine(SCANNER, message));

        theaterController.update(t);
        printOne(id);
    }

    public void delete(int id){
        String message = "정말로 삭제하겠습니까? Y/N";
        String yesNo = ScannerUtil.nextLine(SCANNER, message);
        if (yesNo.equalsIgnoreCase("Y")){
            onBoardViewer.deleteTheaterId(id);
            theaterController.delete(id);
            printList();
        } else {
            printOne(id);
        }
    }

    public void printTheaterName(int id){
        System.out.print(theaterController.selectTheaterName(id));
    }

    public boolean validateTheaterId(int theaterId) {
        return theaterController.validatetheaterId(theaterId);
    }
}
