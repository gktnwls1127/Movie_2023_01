package model;

public class OnBoardDTO {
    private int id;
    private int filmId;
    private int theaterId;
    private String runningTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public boolean equals(Object obj){
        if (obj instanceof OnBoardDTO){
            OnBoardDTO o = (OnBoardDTO) obj;
            return id == o.id;
        }
        return false;
    }

    public OnBoardDTO(OnBoardDTO original){
        id = original.id;
        filmId = original.filmId;
        theaterId = original.theaterId;
        runningTime = original.runningTime;
    }

    public OnBoardDTO(){

    }

    public OnBoardDTO(int id){
        this.id = id;
    }

}
