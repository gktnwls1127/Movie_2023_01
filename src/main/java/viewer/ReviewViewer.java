package viewer;

import controller.ReviewController;
import model.MemberDTO;
import model.ReviewDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class ReviewViewer {
    private final Scanner SCANNER;
    private ReviewController reviewController;
    private MemberViewer memberViewer;
    private MemberDTO logIn;
    private final int LEVEL_CRITIC = 2;
    private final int LEVEL_ADMIN = 3;
    private final int OPTION_ALL = 1;
    private final int OPTION_GENERAL = 2;
    private final int OPTION_CRITIC = 3;

    public ReviewViewer(Scanner scanner){
        SCANNER = scanner;
        reviewController = new ReviewController();
    }

    public void setMemberViewer(MemberViewer memberViewer){
        this.memberViewer = memberViewer;
    }

    public void setLogin(MemberDTO logIn){
        this.logIn = logIn;
    }

    public void showMenu(int filmId, int option){
        if (option == OPTION_ALL){
            printList(reviewController.selectAll(filmId));
        } else if (option == OPTION_GENERAL) {
            printList(reviewController.selectGeneralList(filmId));
        } else if (option == OPTION_CRITIC) {
            printList(reviewController.selectCriticList(filmId));
        }
        showOptions(filmId);
    }

    private void showOptions(int filmId){
        if (logIn.getLevel() != LEVEL_ADMIN){
            String message = "1. 전체 평점 보기 2. 일반회원 평점 보기 3. 전문 평론가 평점 보기\n4. 평점 입력 5. 평점 수정 6. 평점 삭제 7. 뒤로가기";
            int userChoice = ScannerUtil.nextInt(SCANNER, message, 1, 7);
            if (userChoice == 1){
                showMenu(filmId, OPTION_ALL);
            } else if (userChoice == 2) {
                showMenu(filmId, OPTION_GENERAL);
            } else if (userChoice == 3){
                showMenu(filmId, OPTION_CRITIC);
            } else if (userChoice == 4) {
                addReview(filmId);
            } else if (userChoice == 5) {
                updateReview(filmId);
            } else if (userChoice == 6) {
                deleteReview(filmId);
            }
        }
    }

    private void addReview(int filmId){
        if (!reviewController.validateReview(filmId, logIn.getId())){
            System.out.println("이미 해당 영화에 평점을 등록하셨습니다.");
            showMenu(filmId, OPTION_ALL);
        } else {
            ReviewDTO r = new ReviewDTO();
            r.setFilmId(filmId);
            r.setWriterId(logIn.getId());

            String message = "평점을 입력해주세요. (0~5)";
            r.setScore(ScannerUtil.nextInt(SCANNER, message, 0, 5));
            int nextOption = OPTION_GENERAL;

            if (logIn.getLevel() == LEVEL_CRITIC){
                message = "평론을 등록해주세요.";
                r.setReview(ScannerUtil.nextLine(SCANNER, message));
                nextOption = OPTION_CRITIC;
            }

            reviewController.add(r);
            showMenu(filmId, nextOption);

        }
    }

    private void updateReview(int filmId){
        if (reviewController.validateReview(filmId, logIn.getId())){
            System.out.println("해당 영화에 등록하신 평점이 존재하지 않습니다.");
            showMenu(filmId, OPTION_ALL);
        } else {
            ReviewDTO r = reviewController.selectOne(filmId, logIn.getId());
            String message = "새로운 점수를 입력해주세요. (0~5)";
            r.setScore(ScannerUtil.nextInt(SCANNER, message, 0, 5));
            int nextOption = OPTION_GENERAL;
            if (logIn.getLevel() == LEVEL_CRITIC){
                message = "새로운 평론은 입력해주세요. ";
                r.setReview(ScannerUtil.nextLine(SCANNER, message));
                nextOption = OPTION_CRITIC;
            }
            reviewController.update(r);
            showMenu(filmId, nextOption);
        }
    }

    private void deleteReview(int filmId){
        if (reviewController.validateReview(filmId, logIn.getId())){
            System.out.println("아직 해당 영화에 등록하신 평점이 존재하지 않습니다.");
        } else {
            String message = "정말 삭제 하시겠습니까? Y/N";
            String yesNo = ScannerUtil.nextLine(SCANNER, message);
            if (yesNo.equalsIgnoreCase("Y")){
                reviewController.delete(filmId, logIn.getId());
            }
        }
        showMenu(filmId, OPTION_ALL);
    }

    public void printList(ArrayList<ReviewDTO> list) {
        if (list.isEmpty()){
            System.out.println("아직 등록된 평점이 존재하지 않습니다.");
        } else {
            for (ReviewDTO r : list){
                // 번호. 닉네임 - score점
                // 전문가 의견 : (평론)
                System.out.println("-------------------------------------------------");
                System.out.print(r.getId()+". ");
                memberViewer.printNickname(r.getWriterId());
                System.out.printf(" - %d점\n", r.getScore());
                if (r.getReview() != null){
                    System.out.println("평론 : " + r.getReview());
                }
                System.out.println("-------------------------------------------------");
            }
            System.out.println("평균 평점 : " + reviewController.calculateAverage(list));
        }

    }

    public void deleteByWriterId(int writerId){
        reviewController.deleteByWriterId(writerId);
    }

    public void updateByWriterRank(int writerId){
        ArrayList<ReviewDTO> list = reviewController.selectAll();
        for (ReviewDTO r : list) {
            if (r.getWriterId() == writerId) {
                r.setReview("일반회원에서 전문 평론가로 등업된 회원이라 평론이 없습니다.");
            }
            reviewController.update(r);
        }
    }

}
