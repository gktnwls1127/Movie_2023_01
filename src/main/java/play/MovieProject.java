package play;

import viewer.*;

import java.util.Scanner;

public class MovieProject {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 각 뷰어 초기화
        MemberViewer memberViewer = new MemberViewer(scanner);
        FilmViewer filmViewer = new FilmViewer(scanner);
        ReviewViewer reviewViewer = new ReviewViewer(scanner);
        TheaterViewer theaterViewer = new TheaterViewer(scanner);
        OnBoardViewer onBoardViewer = new OnBoardViewer(scanner);

        // 의존성 주입
        memberViewer.setFilmViewer(filmViewer);
        memberViewer.setReviewViewer(reviewViewer);
        memberViewer.setTheaterViewer(theaterViewer);
        memberViewer.setOnBoardViewer(onBoardViewer);

        filmViewer.setReviewViewer(reviewViewer);
        filmViewer.setOnBoardViewer(onBoardViewer);

        reviewViewer.setMemberViewer(memberViewer);

        theaterViewer.setFilmViewer(filmViewer);
        theaterViewer.setOnBoardViewer(onBoardViewer);

        onBoardViewer.setFilmViewer(filmViewer);
        onBoardViewer.setTheaterViewer(theaterViewer);

        memberViewer.showIndex();
    }
}
