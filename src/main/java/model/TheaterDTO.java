package model;

public class TheaterDTO {
    private int id;
    private String theaterName;
    private String theaterPlace;
    private String theaterNumber;

    public TheaterDTO(){

    }

    public TheaterDTO(int id) {
        this.id = id;
    }

    public TheaterDTO(TheaterDTO original){
        id = original.id;
        theaterName = original.theaterName;
        theaterPlace = original.theaterPlace;
        theaterNumber = original.theaterNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getTheaterPlace() {
        return theaterPlace;
    }

    public void setTheaterPlace(String theaterPlace) {
        this.theaterPlace = theaterPlace;
    }

    public String getTheaterNumber() {
        return theaterNumber;
    }

    public void setTheaterNumber(String theaterNumber) {
        this.theaterNumber = theaterNumber;
    }

    public boolean equals(Object obj){
        if (obj instanceof TheaterDTO){
            TheaterDTO t = (TheaterDTO) obj;
            return id == t.id;
        }
        return false;
    }

}
